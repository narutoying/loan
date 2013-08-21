/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.domainservice.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.tccz.loan.domain.entity.Loan;
import com.tccz.loan.domain.vo.LoanCalculateInput;
import com.tccz.loan.domain.vo.MonthLoanDetail;
import com.tccz.loan.domainservice.LoanCalculateService;
import com.tccz.loan.domainservice.LoanCalculateServiceFactory;
import com.tccz.loan.domainservice.LoanService;

/**
 * 
 * @author narutoying09@gmail.com
 * @version $Id: LoanServiceImpl.java, v 0.1 2013-8-21 上午11:16:52
 *          narutoying09@gmail.com Exp $
 */
public class LoanServiceImpl implements LoanService {

	private LoanCalculateServiceFactory loanCalculateServiceFactory;

	/**
	 * @see com.tccz.loan.domainservice.LoanService#calculate(com.tccz.loan.domain.entity.Loan)
	 */
	@Override
	public List<MonthLoanDetail> calculate(Loan loan) {
		// TODO 对loan中的一些参数做校验，如贷款月份不能低于6？
		LoanCalculateService loanCalculateService = loanCalculateServiceFactory
				.getLoanCalculateService(loan.getRepaymentMode());
		LoanCalculateInput input = new LoanCalculateInput();
		BeanUtils.copyProperties(loan, input);
		return loanCalculateService.calculate(input);
	}

	public LoanCalculateServiceFactory getLoanCalculateServiceFactory() {
		return loanCalculateServiceFactory;
	}

	public void setLoanCalculateServiceFactory(
			LoanCalculateServiceFactory loanCalculateServiceFactory) {
		this.loanCalculateServiceFactory = loanCalculateServiceFactory;
	}

}
