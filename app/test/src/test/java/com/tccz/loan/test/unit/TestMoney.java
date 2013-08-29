/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.test.unit;

import java.math.BigDecimal;
import java.math.RoundingMode;

import junit.framework.TestCase;

import com.tccz.loan.common.util.Money;

/**
 * 
 * @author narutoying09@gmail.com
 * @version $Id: TestLoanService.java, v 0.1 2013-8-22 上午11:43:10
 *          narutoying09@gmail.com Exp $
 */
public class TestMoney extends TestCase {

	public void test() {
		Money m1 = new Money("888.88");
		Money m2 = new Money("5");
		Money divide = m1.divide(m2.getAmount());
		System.out.println(divide.getAmount());
		System.out.println(1000 % 100);
		System.out.println(new BigDecimal("0.0655").divide(
				new BigDecimal("12"), 4, RoundingMode.HALF_EVEN));
	}

}
