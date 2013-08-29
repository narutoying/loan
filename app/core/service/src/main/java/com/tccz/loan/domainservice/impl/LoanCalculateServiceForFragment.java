/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.domainservice.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tccz.loan.common.util.DateUtil;
import com.tccz.loan.common.util.Money;
import com.tccz.loan.domain.enums.RepaymentMode;
import com.tccz.loan.domain.exception.CommonException;
import com.tccz.loan.domain.vo.FragmentRepaymentConfig;
import com.tccz.loan.domain.vo.LoanCalculateInput;
import com.tccz.loan.domain.vo.MonthLoanDetail;
import com.tccz.loan.domainservice.LoanCalculateService;

/**
 * 分段计算
 * 
 * @author narutoying09@gmail.com
 * @version $Id: LoanCalculateServiceForFragment.java, v 0.1 2013-8-21
 *          上午11:37:15 narutoying09@gmail.com Exp $
 */
public class LoanCalculateServiceForFragment implements LoanCalculateService {

	@Autowired
	private LoanCalculateServiceForACPI loanCalculateServiceForACPI;

	@Override
	public List<MonthLoanDetail> calculate(LoanCalculateInput input) {
		List<MonthLoanDetail> result = new ArrayList<MonthLoanDetail>();
		Object repaymentConfig = input.getRepaymentConfig();
		if (repaymentConfig instanceof FragmentRepaymentConfig) {
			FragmentRepaymentConfig config = (FragmentRepaymentConfig) repaymentConfig;
			int onlyInterestMonths = config.getOnlyInterestMonths();
			Money amount = input.getAmount();
			BigDecimal monthRate = input.getMonthRate();
			Date calRepaymentDate = new Date(input.getFirstRepaymentDate()
					.getTime());
			// 只计算利息
			for (int i = 0; i < onlyInterestMonths; i++) {
				MonthLoanDetail detail = new MonthLoanDetail();
				detail.setYear(DateUtil.getYear(calRepaymentDate));
				detail.setMonth(DateUtil.getMonth(calRepaymentDate));
				detail.setRemarks(supportMode().toString());
				detail.setRepaymentMode(supportMode());
				Money interest = calculateRepaymentMoneyForOnlyInterest(amount,
						monthRate);
				detail.setRepaymentMoney(interest);
				detail.setRepaymentInterest(interest);
				detail.setRepaymentCapital(new Money("0"));
				result.add(detail);
				DateUtil.addMonth(calRepaymentDate);
			}
			// TODO 目前第二段只支持等额本息
			reBuildACPIInput(input, config.getLeftMonths(), calRepaymentDate);
			result.addAll(loanCalculateServiceForACPI.calculate(input));
		} else {
			throw new CommonException("不支持的分段配置参数对象：" + repaymentConfig);
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
	private Money calculateRepaymentMoneyForOnlyInterest(Money amount,
			BigDecimal monthRate) {
		return amount.multiply(monthRate);
	}

	private void reBuildACPIInput(LoanCalculateInput input, int newTerm,
			Date calRepaymentDate) {
		input.setFirstRepaymentDate(calRepaymentDate);
		input.setTerm(newTerm);
	}

	@Override
	public RepaymentMode supportMode() {
		return RepaymentMode.FRAGMENT;
	}

}
