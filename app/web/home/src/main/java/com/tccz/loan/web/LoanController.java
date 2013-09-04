package com.tccz.loan.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.tccz.loan.common.util.Money;
import com.tccz.loan.common.util.PageList;
import com.tccz.loan.domain.entity.Loan;
import com.tccz.loan.domain.enums.RepaymentMode;
import com.tccz.loan.domain.result.CommonResult;
import com.tccz.loan.domain.vo.FragmentRepaymentConfig;
import com.tccz.loan.domain.vo.LoanQueryCondition;
import com.tccz.loan.domain.vo.MonthLoanDetail;
import com.tccz.loan.domainservice.LoanService;
import com.tccz.loan.web.form.LoanForm;
import com.tccz.loan.web.util.JSONUtil;
import com.tccz.loan.web.util.WebPageCallback;
import com.tccz.loan.web.util.WebUtil;

/**
 * 
 * @author narutoying09@gmail.com
 * @version $Id: LoanController.java, v 0.1 2013-8-22 下午3:42:21
 *          narutoying09@gmail.com Exp $
 */
@Controller
public class LoanController {

	private static final String REDIRECT_SHOW_LOAN_LIST_HTM = "redirect:/showLoanList.htm";

	private static final String OPERATION = "operation";

	@Autowired
	private LoanService loanService;

	private static Logger logger = LoggerFactory
			.getLogger(LoanController.class);

	@RequestMapping(value = "/showLoanList.htm")
	public String showLoanList(ModelMap map, LoanQueryCondition condition) {
		PageList<Loan> pageList = loanService
				.queryReportsByCondition(condition);
		map.addAttribute("pageList", pageList);
		return "showLoanList";
	}

	@RequestMapping(value = "/addLoan.htm")
	public String goAddLoan(ModelMap modelMap) {
		modelMap.addAttribute(OPERATION, "add");
		modelMap.addAttribute("modeMap", RepaymentMode.toMap());
		return "oneLoan";
	}

	@RequestMapping(value = "/addLoan.htm", method = RequestMethod.POST)
	public String doAddLoan(ModelMap map, LoanForm form) {
		CommonResult commonResult = loanService
				.createLoan(convertToDomain(form));
		return WebUtil.goPage(map, commonResult, new WebPageCallback() {

			@Override
			public String successPage() {
				return REDIRECT_SHOW_LOAN_LIST_HTM;
			}
		});
	}

	@RequestMapping(value = "/deleteLoan.htm")
	public String deleteLoan(ModelMap modelMap, Integer loanId) {
		CommonResult commonResult = loanService.deleteLoan(loanId);
		return WebUtil.goPage(modelMap, commonResult, new WebPageCallback() {

			@Override
			public String successPage() {
				return REDIRECT_SHOW_LOAN_LIST_HTM;
			}
		});
	}

	@RequestMapping(value = "/showLoan.htm")
	public String showLoan(ModelMap modelMap, Integer loanId) {
		modelMap.addAttribute(OPERATION, "show");
		modelMap.addAttribute("loan", loanService.getLoan(loanId));
		modelMap.addAttribute("modeMap", RepaymentMode.toMap());
		return "oneLoan";
	}

	@RequestMapping(value = "/calLoan.json", method = RequestMethod.POST)
	public void calLoan(HttpServletResponse res, ModelMap map, LoanForm form) {
		List<MonthLoanDetail> calculate = loanService
				.calculate(convertToDomain(form));
		JSONUtil.writeBackJsonWithConfig(res, calculate);
	}

	@RequestMapping(value = "/downloadLoan.htm", method = RequestMethod.POST)
	public void download(HttpServletResponse response, ModelMap map,
			LoanForm form) throws UnsupportedEncodingException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName="
				+ URLEncoder.encode("贷款明细", "UTF-8") + ".xls");
		try {
			OutputStream os = response.getOutputStream();
			loanService.exportCalculateResult(convertToDomain(form), os);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Loan convertToDomain(LoanForm form) {
		Loan result = new Loan();
		result.setAmount(new Money(form.getAmount()));
		result.setAnnualRate(new BigDecimal(form.getAnnualRate())
				.divide(new BigDecimal("100")));
		result.setExecutor(form.getExecutor());
		result.setFirstRepaymentDate(form.getFirstRepaymentDate());
		result.setLoaner(form.getLoaner());
		result.setRepaymentConfig(buildConfig(form));
		result.setRepaymentMode(RepaymentMode.getByCode(form.getRepaymentMode()));
		result.setTerm(form.getTerm());
		result.setReleaseDate(form.getReleaseDate());
		return result;
	}

	private Object buildConfig(LoanForm form) {
		if (StringUtils.equals(form.getRepaymentMode(),
				RepaymentMode.FRAGMENT.toString())) {
			FragmentRepaymentConfig config = new FragmentRepaymentConfig();
			config.setLeftMonths(form.getLeftMonths());
			config.setOnlyInterestMonths(form.getOnlyInterestMonths());
			config.setRepaymentMode(form.getRepaymentMode());
			return config;
		}
		return null;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ServletException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}

}
