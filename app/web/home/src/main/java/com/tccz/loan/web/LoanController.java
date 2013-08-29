package com.tccz.loan.web;

import java.math.BigDecimal;
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
import com.tccz.loan.domain.entity.Loan;
import com.tccz.loan.domain.enums.RepaymentMode;
import com.tccz.loan.domain.result.CommonResult;
import com.tccz.loan.domain.vo.FragmentRepaymentConfig;
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

	@Autowired
	private LoanService loanService;

	private static Logger logger = LoggerFactory
			.getLogger(LoanController.class);

	@RequestMapping(value = "/showLoanList.htm")
	public String showLoanList(ModelMap map) {
		return "showLoanList";
	}

	@RequestMapping(value = "/addLoan.htm")
	public String goAddLoan(ModelMap map) {
		return "addLoan";
	}

	@RequestMapping(value = "/addLoan.htm", method = RequestMethod.POST)
	public String doAddLoan(ModelMap map, LoanForm form) {
		CommonResult commonResult = loanService
				.createLoan(convertToDomain(form));
		return WebUtil.goPage(map, commonResult, new WebPageCallback() {

			@Override
			public String successPage() {
				return "redirect:/showLoanList.htm";
			}
		});
	}

	@RequestMapping(value = "/calLoan.json", method = RequestMethod.POST)
	public void calLoan(HttpServletResponse res, ModelMap map, LoanForm form) {
		List<MonthLoanDetail> calculate = loanService
				.calculate(convertToDomain(form));
		JSONUtil.writeBackJsonWithConfig(res, calculate);
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
