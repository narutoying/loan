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
import com.tccz.loan.domain.enums.RepaymentMode;
import com.tccz.loan.domain.vo.FragmentRepaymentConfig;
import com.tccz.loan.domain.vo.LoanCalculateInput;
import com.tccz.loan.domain.vo.MonthLoanDetail;

/**
 * 分段计算
 * 
 * @author narutoying09@gmail.com
 * @version $Id: LoanCalculateServiceForFragment.java, v 0.1 2013-8-21
 *          上午11:37:15 narutoying09@gmail.com Exp $
 */
public class LoanCalculateServiceForFragment extends
		LoanCalculateServiceForACPI {

	@Override
	public List<MonthLoanDetail> calculate(LoanCalculateInput input) {
		List<MonthLoanDetail> result = new ArrayList<MonthLoanDetail>();
		Object repaymentConfig = input.getRepaymentConfig();
		if (repaymentConfig instanceof FragmentRepaymentConfig) {
			FragmentRepaymentConfig config = (FragmentRepaymentConfig) repaymentConfig;
			int onlyInterestMonths = config.getOnlyInterestMonths();
			BigDecimal amount = input.getAmount();
			BigDecimal annualRate = input.getAnnualRate();
			BigDecimal monthRate = annualRate.divide(new BigDecimal("12"));
			Date firstRepaymentDate = input.getFirstRepaymentDate();
			// 只计算利息
			for (int i = 0; i < onlyInterestMonths; i++) {
				MonthLoanDetail detail = new MonthLoanDetail();
				detail.setYear(DateUtil.getYear(firstRepaymentDate));
				detail.setMonth(DateUtil.getMonth(firstRepaymentDate));
				detail.setRemarks(supportMode().toString());
				detail.setRepaymentMode(supportMode());
				BigDecimal interest = calculateRepaymentMoneyForOnlyInterest(
						amount, monthRate).setScale(ROUND_NUM, ROUND_MODE);
				detail.setRepaymentMoney(interest);
				detail.setRepaymentInterest(interest);
				detail.setRepaymentCapital(new BigDecimal("0.00"));
				result.add(detail);
				addRepaymentMonth(firstRepaymentDate);
			}
			// TODO 目前第二段只支持等额本息
			reBuildACPIInput(input, config.getLeftMonths());
			result.addAll(super.calculate(input));
		}
		return result;
	}

	/**
	 * 计算只付利息的每月金额
	 * 
	 * @param amount
	 * @param monthRate
	 * @return
	 */
	private BigDecimal calculateRepaymentMoneyForOnlyInterest(
			BigDecimal amount, BigDecimal monthRate) {
		return amount.multiply(monthRate).setScale(ROUND_NUM, ROUND_MODE);
	}

	private void reBuildACPIInput(LoanCalculateInput input, int newTerm) {
		input.setTerm(newTerm);
	}

	@Override
	public RepaymentMode supportMode() {
		return RepaymentMode.FRAGMENT;
	}

}
