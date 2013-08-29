/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.domainservice.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import net.sf.json.JSONSerializer;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tccz.loan.common.dal.daointerface.LoanDAO;
import com.tccz.loan.common.dal.dataobject.LoanDO;
import com.tccz.loan.common.util.Money;
import com.tccz.loan.domain.entity.Loan;
import com.tccz.loan.domain.result.CommonResult;
import com.tccz.loan.domain.vo.LoanCalculateInput;
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
	public Loan getLoan(int loanId) {
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

	protected LoanDO convertDomainToDO(Loan loan) {
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
		return result;
	}
}
