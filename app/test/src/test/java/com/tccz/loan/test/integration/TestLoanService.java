/**
 * narutoying09@gmail.com
 * Copyright (c) 2004-2013 All Rights Reserved.
 */
package com.tccz.loan.test.integration;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tccz.loan.domain.entity.Loan;
import com.tccz.loan.domain.enums.RepaymentMode;
import com.tccz.loan.domainservice.LoanService;

/**
 * 
 * @author narutoying09@gmail.com
 * @version $Id: TestContactService.java, v 0.1 2013-4-9 下午9:16:10
 *          narutoying09@gmail.com Exp $
 */
public class TestLoanService extends BaseTestCase {

	@Autowired
	private LoanService loanService;

	@Test
	public void test() {
		Loan loan = new Loan();
		loan.setRepaymentMode(RepaymentMode.ACPI);
		loanService.calculate(loan);
	}
}
