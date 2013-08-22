/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.test.unit;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.tccz.loan.domain.enums.RepaymentMode;
import com.tccz.loan.domain.vo.FragmentRepaymentConfig;
import com.tccz.loan.domain.vo.LoanCalculateInput;
import com.tccz.loan.domain.vo.MonthLoanDetail;
import com.tccz.loan.domainservice.impl.LoanCalculateServiceForFragment;

/**
 * 
 * @author narutoying09@gmail.com
 * @version $Id: TestLoanService.java, v 0.1 2013-8-22 上午11:43:10
 *          narutoying09@gmail.com Exp $
 */
public class TestLoanService extends TestCase {

	public void testACPI() {
		/*
		 * LoanCalculateServiceForACPI service = new
		 * LoanCalculateServiceForACPI(); LoanCalculateInput input = new
		 * LoanCalculateInput(); BigDecimal amount = new BigDecimal("300000");
		 * BigDecimal annualRate = new BigDecimal("0.06"); int term = 360; Date
		 * firstRepaymentDate = new Date(); Object repaymentConfig = null;
		 * input.setAmount(amount); input.setAnnualRate(annualRate);
		 * input.setFirstRepaymentDate(firstRepaymentDate);
		 * input.setRepaymentConfig(repaymentConfig); input.setTerm(term);
		 * List<MonthLoanDetail> result = service.calculate(input); for
		 * (MonthLoanDetail detail : result) { System.out.println(detail); }
		 */
	}

	public void testFragment() {
		LoanCalculateServiceForFragment service = new LoanCalculateServiceForFragment();
		LoanCalculateInput input = new LoanCalculateInput();
		BigDecimal amount = new BigDecimal("50000");
		BigDecimal annualRate = new BigDecimal("0.06");
		int term = 60;
		Date firstRepaymentDate = new Date();
		FragmentRepaymentConfig repaymentConfig = new FragmentRepaymentConfig();
		repaymentConfig.setOnlyInterestMonths(24);
		repaymentConfig.setLeftMonths(36);
		repaymentConfig.setRepaymentMode(RepaymentMode.ACPI.toString());
		input.setAmount(amount);
		input.setAnnualRate(annualRate);
		input.setFirstRepaymentDate(firstRepaymentDate);
		input.setRepaymentConfig(repaymentConfig);
		input.setTerm(term);
		List<MonthLoanDetail> result = service.calculate(input);
		System.out
				.println("----------------------------分段----------------------------");
		for (MonthLoanDetail detail : result) {
			System.out.println(detail);
		}
	}

}
