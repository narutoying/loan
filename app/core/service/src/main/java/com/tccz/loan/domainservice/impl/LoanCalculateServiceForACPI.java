/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.domainservice.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tccz.loan.common.util.DateUtil;
import com.tccz.loan.common.util.Money;
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
		Money amount = input.getAmount();
		BigDecimal monthRate = input.getMonthRate();
		Money monthRepaymentMoney = calculateRepaymentMoney(amount, monthRate,
				term);
		Date calRepaymentDate = new Date(input.getFirstRepaymentDate()
				.getTime());
		for (int i = 0; i < term; i++) {
			MonthLoanDetail detail = new MonthLoanDetail();
			detail.setYear(DateUtil.getYear(calRepaymentDate));
			detail.setMonth(DateUtil.getMonth(calRepaymentDate));
			detail.setRepaymentMode(supportMode());
			detail.setRemarks(supportMode().toString());
			Money monthRepaymentInterest = calculateRepaymentInterest(amount,
					monthRate, i, monthRepaymentMoney);
			detail.setRepaymentMoney(monthRepaymentMoney);
			detail.setRepaymentInterest(monthRepaymentInterest);
			detail.setRepaymentCapital(monthRepaymentMoney
					.subtract(monthRepaymentInterest));
			result.add(detail);
			DateUtil.addMonth(calRepaymentDate);
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
	private Money calculateRepaymentInterest(Money amount,
			BigDecimal monthRate, int index, Money monthRepaymentMoney) {
		BigDecimal one = new BigDecimal("1");
		BigDecimal rate = monthRate.add(one);
		BigDecimal pow = rate.pow(index);
		return amount.multiply(pow).multiply(monthRate)
				.subtract(monthRepaymentMoney.multiply(pow.subtract(one)));
	}

	/**
	 * 计算等额本息每月还款金额
	 * 
	 * @param amount
	 * @param monthRate
	 * @param term
	 * @return
	 */
	private Money calculateRepaymentMoney(Money amount, BigDecimal monthRate,
			int term) {
		BigDecimal one = new BigDecimal("1");
		BigDecimal rate = monthRate.add(one);
		return amount.multiply(rate.pow(term)).multiply(monthRate)
				.divide(rate.pow(term).subtract(one));
	}

	@Override
	public RepaymentMode supportMode() {
		return RepaymentMode.ACPI;
	}
}
