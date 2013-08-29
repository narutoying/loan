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
 * 等额本金计算
 * 
 * @author narutoying09@gmail.com
 * @version $Id: LoanCalculateServiceAC.java, v 0.1 2013-8-21 上午11:36:40
 *          narutoying09@gmail.com Exp $
 */
public class LoanCalculateServiceForAC implements LoanCalculateService {

	@Override
	public List<MonthLoanDetail> calculate(LoanCalculateInput input) {
		List<MonthLoanDetail> result = new ArrayList<MonthLoanDetail>();
		int term = input.getTerm();
		Money amount = input.getAmount();
		BigDecimal monthRate = input.getMonthRate();
		Date calRepaymentDate = new Date(input.getFirstRepaymentDate()
				.getTime());
		Money monthRepaymentCapital = amount.divide(new BigDecimal(term));
		for (int i = 0; i < term; i++) {
			Money monthRepaymentInterest = calMonthRepaymentInterest(amount,
					monthRepaymentCapital, i + 1, monthRate);
			MonthLoanDetail detail = new MonthLoanDetail();
			detail.setYear(DateUtil.getYear(calRepaymentDate));
			detail.setMonth(DateUtil.getMonth(calRepaymentDate));
			detail.setRepaymentMode(supportMode());
			detail.setRemarks(supportMode().toString());
			detail.setRepaymentMoney(monthRepaymentInterest
					.add(monthRepaymentCapital));
			detail.setRepaymentInterest(monthRepaymentInterest);
			detail.setRepaymentCapital(monthRepaymentCapital);
			result.add(detail);
			DateUtil.addMonth(calRepaymentDate);
		}
		return result;
	}

	private Money calMonthRepaymentInterest(Money amount,
			Money monthRepaymentCapital, int n, BigDecimal monthRate) {
		// Calendar calendar = new GregorianCalendar();
		// calendar.setTime(calRepaymentDate);
		// calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
		// [a- c*(n-1)]*i
		return amount.subtract(monthRepaymentCapital.multiply(n - 1)).multiply(
				monthRate);
	}

	@Override
	public RepaymentMode supportMode() {
		return RepaymentMode.AC;
	}

}
