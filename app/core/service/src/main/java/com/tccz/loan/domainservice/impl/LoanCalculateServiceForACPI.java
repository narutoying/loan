/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.domainservice.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tccz.loan.common.util.DateUtil;
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

	protected static final int ROUND_NUM = 2;
	protected static final int ROUND_MODE = BigDecimal.ROUND_HALF_UP;

	@Override
	public List<MonthLoanDetail> calculate(LoanCalculateInput input) {
		List<MonthLoanDetail> result = new ArrayList<MonthLoanDetail>();
		int term = input.getTerm();
		BigDecimal amount = input.getAmount();
		BigDecimal annualRate = input.getAnnualRate();
		BigDecimal monthRate = annualRate.divide(new BigDecimal("12"));
		BigDecimal monthRepaymentMoney = calculateRepaymentMoney(amount,
				monthRate, term);
		Date firstRepaymentDate = input.getFirstRepaymentDate();
		for (int i = 0; i < term; i++) {
			MonthLoanDetail detail = new MonthLoanDetail();
			detail.setYear(DateUtil.getYear(firstRepaymentDate));
			detail.setMonth(DateUtil.getMonth(firstRepaymentDate));
			detail.setRepaymentMode(supportMode());
			detail.setRemarks(supportMode().toString());
			BigDecimal repaymentInterest = calculateRepaymentInterest(amount,
					monthRate, i, monthRepaymentMoney);
			detail.setRepaymentMoney(monthRepaymentMoney);
			detail.setRepaymentInterest(repaymentInterest);
			detail.setRepaymentCapital(monthRepaymentMoney.subtract(
					repaymentInterest).setScale(ROUND_NUM, ROUND_MODE));
			result.add(detail);
			addRepaymentMonth(firstRepaymentDate);
		}
		return result;
	}

	/**
	 * 
	 * 
	 * @param firstRepaymentDate
	 * @param index
	 */
	protected void addRepaymentMonth(Date firstRepaymentDate) {
		firstRepaymentDate.setMonth(firstRepaymentDate.getMonth() + 1);
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
			BigDecimal monthRate, int index, BigDecimal repaymentMoney) {
		BigDecimal one = new BigDecimal("1");
		BigDecimal rate = monthRate.add(one);
		BigDecimal pow = rate.pow(index);
		return amount.multiply(pow).multiply(monthRate)
				.subtract(repaymentMoney.multiply(pow.subtract(one)))
				.setScale(ROUND_NUM, ROUND_MODE);
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
		BigDecimal one = new BigDecimal("1");
		BigDecimal rate = monthRate.add(one);
		return amount.multiply(rate.pow(term)).multiply(monthRate)
				.divide(rate.pow(term).subtract(one), ROUND_NUM, ROUND_MODE);
	}

	@Override
	public RepaymentMode supportMode() {
		return RepaymentMode.ACPI;
	}

	public static void main(String[] args) {
		BigDecimal amount = new BigDecimal("300000");
		BigDecimal monthRate = new BigDecimal("0.005");
		int term = 360;
		LoanCalculateServiceForACPI loanCalculateServiceForACPI = new LoanCalculateServiceForACPI();
		BigDecimal repaymentMoney = loanCalculateServiceForACPI
				.calculateRepaymentMoney(amount, monthRate, term);
		System.out.println("每月还款：" + repaymentMoney);
		int index = 5;
		System.out.println(index
				+ "月还利息："
				+ loanCalculateServiceForACPI.calculateRepaymentInterest(
						amount, monthRate, index, repaymentMoney));
		NumberFormat percentInstance = NumberFormat.getPercentInstance();
		percentInstance.setMaximumFractionDigits(4);
		try {
			Number number = percentInstance.parse("6.15%");
			System.out.println(number.doubleValue());
		} catch (ParseException e) {

		}
	}
}
