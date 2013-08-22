/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.domain.vo;

/**
 * 分段还款配置参数
 * 
 * @author narutoying09@gmail.com
 * @version $Id: FragmentRepaymentConfig.java, v 0.1 2013-8-21 上午10:05:33
 *          narutoying09@gmail.com Exp $
 */
public class FragmentRepaymentConfig {
	/** 仅付利息 */
	private int onlyInterestMonths;
	/** 除仅付利息月份剩余的月份 */
	private int leftMonths;
	/** 还款方式 */
	private String repaymentMode; // 此处只能是等额本息或等额本金

	public int getOnlyInterestMonths() {
		return onlyInterestMonths;
	}

	public void setOnlyInterestMonths(int onlyInterestMonths) {
		this.onlyInterestMonths = onlyInterestMonths;
	}

	public int getLeftMonths() {
		return leftMonths;
	}

	public void setLeftMonths(int leftMonths) {
		this.leftMonths = leftMonths;
	}

	public String getRepaymentMode() {
		return repaymentMode;
	}

	public void setRepaymentMode(String repaymentMode) {
		this.repaymentMode = repaymentMode;
	}

}
