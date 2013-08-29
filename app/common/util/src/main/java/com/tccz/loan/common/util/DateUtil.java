/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.common.util;

import java.util.Date;

/**
 * 
 * @author narutoying09@gmail.com
 * @version $Id: DateUtil.java, v 0.1 2013-8-22 下午2:58:37 narutoying09@gmail.com
 *          Exp $
 */
public class DateUtil {
	public static int getYear(Date date) {
		return date.getYear() + 1900;
	}

	public static int getMonth(Date date) {
		return date.getMonth() + 1;
	}

	/**
	 * 日期对象月份+1
	 * 
	 * @param date
	 */
	public static void addMonth(Date date) {
		date.setMonth(date.getMonth() + 1);
	}
}
