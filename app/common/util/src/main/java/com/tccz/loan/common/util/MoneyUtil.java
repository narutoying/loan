/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.common.util;

/**
 * 
 * @author narutoying09@gmail.com
 * @version $Id: MoneyUtil.java, v 0.1 2013-8-27 下午4:29:39
 *          narutoying09@gmail.com Exp $
 */
public class MoneyUtil {
	/**
	 * 计算乘方
	 * 
	 * @param baseMoney
	 * @param exp
	 * @return
	 */
	public static Money power(Money baseMoney, int exp) {
		return new Money(baseMoney.getAmount().pow(exp));
	}
}
