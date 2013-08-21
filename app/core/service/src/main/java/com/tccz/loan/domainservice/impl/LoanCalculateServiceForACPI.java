/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.domainservice.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.tccz.loan.domain.enums.RepaymentMode;
import com.tccz.loan.domain.vo.LoanCalculateInput;
import com.tccz.loan.domain.vo.MonthLoanDetail;
import com.tccz.loan.domainservice.LoanCalculateService;

/**
 * 等额本息计算
 * 
 * @author narutoying09@gmail.com
 * @version $Id: LoanCalculateServiceACPI.java, v 0.1 2013-8-21 上午11:35:55
 *          narutoying09@gmail.com Exp $
 */
public class LoanCalculateServiceForACPI implements LoanCalculateService {

	@Override
	public List<MonthLoanDetail> calculate(LoanCalculateInput input) {
		List<MonthLoanDetail> result = new ArrayList<MonthLoanDetail>();
		int term = input.getTerm();
		for (int i = 0; i < term; i++) {
			MonthLoanDetail detail = new MonthLoanDetail();
			int index = i + 1;
			detail.setIndex(index);
			detail.setRepaymentMode(supportMode());
			detail.setRemarks("等额本息");
			BigDecimal amount = input.getAmount();
			BigDecimal annualRate = input.getAnnualRate();
			BigDecimal monthRate = annualRate.divide(new BigDecimal("12"));
			BigDecimal repaymentMoney = calculateRepaymentMoney(amount,
					monthRate, term);
			BigDecimal repaymentInterest = calculateRepaymentInterest(amount,
					monthRate, index);
			detail.setRepaymentMoney(repaymentMoney);
			detail.setRepaymentInterest(repaymentInterest);
			detail.setRepaymentCapital(repaymentMoney
					.subtract(repaymentInterest));
			result.add(detail);
		}
		return result;
	}

	/**
	 * 计算当月还款的利息
	 * 
	 * @param amount
	 * @param monthRate
	 * @param index
	 * @return
	 */
	private BigDecimal calculateRepaymentInterest(BigDecimal amount,
			BigDecimal monthRate, int index) {
		return null;
	}

	/**
	 * 计算等额本息每月还款金额
	 * 
	 * @param amount
	 * @param monthRate
	 * @param term
	 * @return
	 */
	private BigDecimal calculateRepaymentMoney(BigDecimal amount,
			BigDecimal monthRate, int term) {
		BigDecimal result = null;
		
		return result;
	}

	@Override
	public RepaymentMode supportMode() {
		return RepaymentMode.ACPI;
	}

}
