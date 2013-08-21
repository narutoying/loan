/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.domainservice;

import com.tccz.loan.domain.enums.RepaymentMode;

/**
 * 
 * @author narutoying09@gmail.com
 * @version $Id: LoanCalculateServiceFactory.java, v 0.1 2013-8-21 上午11:32:31
 *          narutoying09@gmail.com Exp $
 */
public class LoanCalculateServiceFactory {

	private LoanCalculateService loanCalculateServiceForAC;
	private LoanCalculateService loanCalculateServiceForACPI;
	private LoanCalculateService loanCalculateServiceForFragment;

	public LoanCalculateService getLoanCalculateService(
			RepaymentMode repaymentMode) {
		LoanCalculateService result = null;
		switch (repaymentMode) {
		case AC:
			result = loanCalculateServiceForAC;
			break;
		case ACPI:
			result = loanCalculateServiceForACPI;
			break;
		case FRAGMENT:
			result = loanCalculateServiceForFragment;
			break;

		default:
			break;
		}
		return result;
	}

	public LoanCalculateService getLoanCalculateServiceForAC() {
		return loanCalculateServiceForAC;
	}

	public void setLoanCalculateServiceForAC(
			LoanCalculateService loanCalculateServiceForAC) {
		this.loanCalculateServiceForAC = loanCalculateServiceForAC;
	}

	public LoanCalculateService getLoanCalculateServiceForACPI() {
		return loanCalculateServiceForACPI;
	}

	public void setLoanCalculateServiceForACPI(
			LoanCalculateService loanCalculateServiceForACPI) {
		this.loanCalculateServiceForACPI = loanCalculateServiceForACPI;
	}

	public LoanCalculateService getLoanCalculateServiceForFragment() {
		return loanCalculateServiceForFragment;
	}

	public void setLoanCalculateServiceForFragment(
			LoanCalculateService loanCalculateServiceForFragment) {
		this.loanCalculateServiceForFragment = loanCalculateServiceForFragment;
	}
}
