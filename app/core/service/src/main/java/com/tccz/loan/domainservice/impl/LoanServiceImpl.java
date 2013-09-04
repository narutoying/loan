/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.domainservice.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.tccz.loan.common.dal.daointerface.LoanDAO;
import com.tccz.loan.common.dal.dataobject.LoanDO;
import com.tccz.loan.common.util.DateUtil;
import com.tccz.loan.common.util.Money;
import com.tccz.loan.common.util.PageList;
import com.tccz.loan.common.util.PageUtil;
import com.tccz.loan.common.util.exception.CommonException;
import com.tccz.loan.domain.entity.Loan;
import com.tccz.loan.domain.enums.RepaymentMode;
import com.tccz.loan.domain.result.CommonResult;
import com.tccz.loan.domain.vo.FragmentRepaymentConfig;
import com.tccz.loan.domain.vo.LoanCalculateInput;
import com.tccz.loan.domain.vo.LoanQueryCondition;
import com.tccz.loan.domain.vo.MonthLoanDetail;
import com.tccz.loan.domainservice.LoanCalculateService;
import com.tccz.loan.domainservice.LoanCalculateServiceFactory;
import com.tccz.loan.domainservice.LoanService;
import com.tccz.loan.util.template.CommonManageTemplate;
import com.tccz.loan.util.template.callback.CommonManageCallback;

/**
 * 
 * @author narutoying09@gmail.com
 * @version $Id: LoanServiceImpl.java, v 0.1 2013-8-21 上午11:16:52
 *          narutoying09@gmail.com Exp $
 */
public class LoanServiceImpl implements LoanService {

	private static final Logger logger = LoggerFactory
			.getLogger(LoanServiceImpl.class);

	@Autowired
	private LoanCalculateServiceFactory loanCalculateServiceFactory;
	@Autowired
	private CommonManageTemplate commonManageTemplate;
	@Autowired
	private LoanDAO loanDAO;

	/**
	 * @see com.tccz.loan.domainservice.LoanService#calculate(com.tccz.loan.domain.entity.Loan)
	 */
	@Override
	public List<MonthLoanDetail> calculate(Loan loan) {
		// TODO 对loan中的一些参数做校验，如贷款月份不能低于6？
		LoanCalculateService loanCalculateService = loanCalculateServiceFactory
				.getLoanCalculateService(loan.getRepaymentMode());
		LoanCalculateInput input = new LoanCalculateInput();
		BeanUtils.copyProperties(loan, input);
		input.setMonthRate(input.getAnnualRate().divide(new BigDecimal(12), 10,
				RoundingMode.HALF_UP));
		return loanCalculateService.calculate(input);
	}

	public LoanCalculateServiceFactory getLoanCalculateServiceFactory() {
		return loanCalculateServiceFactory;
	}

	public void setLoanCalculateServiceFactory(
			LoanCalculateServiceFactory loanCalculateServiceFactory) {
		this.loanCalculateServiceFactory = loanCalculateServiceFactory;
	}

	@Override
	public Loan getLoan(Integer loanId) {
		if (loanId == null) {
			return null;
		} else {
			LoanDO byId = loanDAO.getById(loanId);
			return convertToDomain(byId);
		}
	}

	private Loan convertToDomain(LoanDO loan) {
		if (loan != null) {
			Loan result = new Loan();
			result.setAmount(new Money(loan.getAmount(), Currency
					.getInstance(loan.getCurrency())));
			result.setAnnualRate(new BigDecimal(loan.getAnnualRate()));
			String repaymentMode = loan.getRepaymentMode();
			result.setRepaymentMode(RepaymentMode.getByCode(repaymentMode));
			result.setRepaymentConfig(buildRepaymentConfig(
					loan.getRepaymentConfig(), repaymentMode));
			result.setExecutor(loan.getExecutor());
			result.setFirstRepaymentDate(loan.getFirstRepaymentDate());
			result.setId(loan.getId());
			result.setLoaner(loan.getLoaner());
			result.setTerm(loan.getTerm());
			result.setCreateTime(loan.getCreateTime());
			result.setReleaseDate(loan.getReleaseDate());
			return result;
		} else {
			return null;
		}
	}

	private Object buildRepaymentConfig(String repaymentConfig,
			String repaymentMode) {
		if (RepaymentMode.getByCode(repaymentMode) == RepaymentMode.FRAGMENT) {
			FragmentRepaymentConfig config = (FragmentRepaymentConfig) JSONObject
					.toBean(JSONObject.fromObject(repaymentConfig),
							FragmentRepaymentConfig.class);
			return config;
		}
		return null;
	}

	@Override
	public CommonResult createLoan(final Loan loan) {
		final CommonResult result = new CommonResult();
		commonManageTemplate.manageWithTransaction(result,
				new CommonManageCallback() {

					@Override
					public void doManage() {
						loanDAO.insert(convertDomainToDO(loan));
						CommonResult.buildResult(result, true, "创建贷款成功");
					}

					@Override
					public void checkParameter() {
					}
				});
		return result;
	}

	private LoanDO convertDomainToDO(Loan loan) {
		LoanDO result = new LoanDO();
		Money amount = loan.getAmount();
		String configJson = JSONSerializer.toJSON(loan.getRepaymentConfig())
				.toString();
		result.setAmount(amount.getCent());
		result.setAnnualRate(loan.getAnnualRate().toString());
		result.setRepaymentMode(loan.getRepaymentMode().toString());
		result.setRepaymentConfig(configJson);
		result.setCurrency(amount.getCurrency().getCurrencyCode());
		result.setExecutor(loan.getExecutor());
		result.setFirstRepaymentDate(loan.getFirstRepaymentDate());
		result.setId(loan.getId());
		result.setLoaner(loan.getLoaner());
		result.setTerm(loan.getTerm());
		result.setCreateTime(loan.getCreateTime());
		result.setReleaseDate(loan.getReleaseDate());
		return result;
	}

	@Override
	public CommonResult exportCalculateResult(final Loan loan,
			final OutputStream outputStream) {
		final CommonResult result = new CommonResult();
		commonManageTemplate.manage(result, new CommonManageCallback() {

			@Override
			public void doManage() {
				/*
				 * 1. 生成excel 1.1 sheet1展示贷款摘要 1.2 sheet2展示计算结果
				 */
				HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
				HSSFSheet summarySheet = hssfWorkbook.createSheet("贷款摘要");
				HSSFSheet calResultSheet = hssfWorkbook.createSheet("贷款明细计算结果");
				buildSummarySheet(summarySheet, loan);
				buildCalResultSheet(calResultSheet, loan);
				try {
					hssfWorkbook.write(outputStream);
				} catch (IOException e) {
					String message = "输出excel文件出错";
					logger.error(message, e);
					throw new CommonException(message, e);
				}
				// 2. 导出文件
				CommonResult.buildResult(result, true, "导出贷款计算结果成功");
			}

			@Override
			public void checkParameter() {
			}
		});
		return result;
	}

	protected void buildCalResultSheet(HSSFSheet calResultSheet, Loan loan) {
		// 标题栏 (单位: 元) (单位: 元) (单位: 元)
		HSSFRow titleRow = calResultSheet.createRow(0);
		setCell(titleRow, 0, "年份");
		setCell(titleRow, 1, "月份");
		setCell(titleRow, 2, "当月还款金额（元）");
		setCell(titleRow, 3, "当月还款本金（元）");
		setCell(titleRow, 4, "当月还款利息（元）");
		// 具体数据
		List<MonthLoanDetail> details = calculate(loan);
		int index = 1;
		if (!CollectionUtils.isEmpty(details)) {
			for (MonthLoanDetail detail : details) {
				HSSFRow contentRow = calResultSheet.createRow(index++);
				setCell(contentRow, 0, detail.getYear() + "");
				setCell(contentRow, 1, detail.getMonth() + "");
				setCell(contentRow, 2, detail.getRepaymentMoneyStr());
				setCell(contentRow, 3, detail.getRepaymentCapitalStr());
				setCell(contentRow, 4, detail.getRepaymentInterestStr());
			}
		}
	}

	/**
	 * 构建贷款摘要sheet
	 * 
	 * @param summarySheet
	 * @param loan
	 */
	protected void buildSummarySheet(HSSFSheet summarySheet, Loan loan) {
		// 标题栏
		HSSFRow titleRow = summarySheet.createRow(0);
		setCell(titleRow, 0, "贷款人");
		setCell(titleRow, 1, "受理人");
		setCell(titleRow, 2, "贷款金额（元）");
		setCell(titleRow, 3, "贷款期限（月）");
		setCell(titleRow, 4, "年利率（%）");
		setCell(titleRow, 5, "放款日");
		setCell(titleRow, 6, "首个还款日");
		setCell(titleRow, 7, "还款方式");
		// 具体数据
		HSSFRow contentRow = summarySheet.createRow(1);
		setCell(contentRow, 0, loan.getLoaner());
		setCell(contentRow, 1, loan.getExecutor());
		setCell(contentRow, 2, loan.getAmount().toString());
		setCell(contentRow, 3, loan.getTerm() + "");
		setCell(contentRow, 4, loan.getAnnualRateString());
		setCell(contentRow, 5, DateUtil.getWebDateString(loan.getReleaseDate()));
		setCell(contentRow, 6,
				DateUtil.getWebDateString(loan.getFirstRepaymentDate()));
		setCell(contentRow, 7, loan.getRepaymentMode().getDescription());
		if (loan.getRepaymentMode() == RepaymentMode.FRAGMENT) {
			setCell(titleRow, 8, "仅利息（月）");
			setCell(titleRow, 9, "等额本息（月）");
			FragmentRepaymentConfig repaymentConfig = (FragmentRepaymentConfig) loan
					.getRepaymentConfig();
			setCell(contentRow, 8, "" + repaymentConfig.getOnlyInterestMonths());
			setCell(contentRow, 9, "" + repaymentConfig.getLeftMonths());
		}
	}

	public void setCell(HSSFRow row, int index, String value) {
		HSSFCell cell = row.createCell((short) index);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(value);
	}

	@Override
	public PageList<Loan> queryReportsByCondition(
			LoanQueryCondition queryCondition) {
		PageList<Loan> result = new PageList<Loan>();
		int pageSize = queryCondition.getPageSize();
		Integer currentPage = queryCondition.getCurrentPage();
		int pageNum = (currentPage == null) ? 1 : currentPage;
		com.tccz.loan.dal.util.PageList pageList = loanDAO.getByCondition(
				queryCondition.getLoaner(), pageSize,
				PageUtil.getOffset(pageSize, pageNum));
		result.setDataList(convertToDataList(pageList));
		result.setPaginator(pageList.getPaginator());
		return result;
	}

	private List<Loan> convertToDataList(
			com.tccz.loan.dal.util.PageList pageList) {
		List<Loan> result = new ArrayList<Loan>();
		if (!CollectionUtils.isEmpty(pageList)) {
			for (Object o : pageList) {
				if (o instanceof LoanDO) {
					result.add(convertToDomain((LoanDO) o));
				}
			}
		}
		return result;
	}

	@Override
	public CommonResult deleteLoan(final int loanId) {
		final CommonResult result = new CommonResult();
		commonManageTemplate.manageWithTransaction(result,
				new CommonManageCallback() {

					@Override
					public void doManage() {
						loanDAO.delete(loanId);
						CommonResult.buildResult(result, true, "删除贷款成功");
					}

					@Override
					public void checkParameter() {
					}
				});
		return result;
	}
}
