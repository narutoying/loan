/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.domainservice.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tccz.loan.common.util.DateUtil;
import com.tccz.loan.common.util.Money;
import com.tccz.loan.common.util.exception.CommonException;
import com.tccz.loan.domain.enums.RepaymentMode;
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
			Date firstRepaymentDate = input.getFirstRepaymentDate();
			FragmentRepaymentConfig config = (FragmentRepaymentConfig) repaymentConfig;
			int onlyInterestMonths = config.getOnlyInterestMonths();
			Money amount = input.getAmount();
			BigDecimal monthRate = input.getMonthRate();
			Date calRepaymentDate = new Date(input.getFirstRepaymentDate()
					.getTime());
			// 日利率
			BigDecimal dayRate = monthRate.divide(new BigDecimal(30), 10,
					RoundingMode.HALF_UP);
			// 只计算利息
			for (int i = 0; i < onlyInterestMonths; i++) {
				MonthLoanDetail detail = new MonthLoanDetail();
				detail.setYear(DateUtil.getYear(calRepaymentDate));
				detail.setMonth(DateUtil.getMonth(calRepaymentDate));
				detail.setRemarks(supportMode().toString());
				detail.setRepaymentMode(supportMode());
				Money interest = null;
				int days = 0;
				if (i == 0) {
					// 第一个还款周期不一定满月，故特殊处理
					days = DateUtil.getDiffDaysWithStart(firstRepaymentDate,
							input.getReleaseDate());
				} else {
					// 从第二个还款周期起，天数即该周期起始日期当月的天数
					Calendar cal = Calendar.getInstance();
					Date lastMonthDay = new Date(calRepaymentDate.getTime());
					lastMonthDay.setMonth(lastMonthDay.getMonth() - 1);
					cal.setTime(lastMonthDay);
					days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				}
				// 计算当月利息
				interest = amount.multiply(days).multiply(dayRate);
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
