/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.test.unit;

import java.util.Date;

import junit.framework.TestCase;

import com.tccz.loan.common.util.DateUtil;

/**
 * 
 * @author narutoying09@gmail.com
 * @version $Id: TestLoanService.java, v 0.1 2013-8-22 上午11:43:10
 *          narutoying09@gmail.com Exp $
 */
public class TestDate extends TestCase {

	public void test() {
		// yyyyMMddHHmmss
		Date one = DateUtil.parseDateLongFormat("20130820142212");
		Date two = DateUtil.parseDateLongFormat("20130908142212");
		int days = DateUtil.getDiffDays3(two, one);
		System.out.println(days);
	}

}
