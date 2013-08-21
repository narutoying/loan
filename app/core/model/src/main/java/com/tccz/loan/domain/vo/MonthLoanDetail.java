/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.domain.vo;

import java.math.BigDecimal;

import com.tccz.loan.domain.enums.RepaymentMode;

/**
 * 贷款按月明细
 * 
 * @author narutoying09@gmail.com
 * @version $Id: MonthLoanDetail.java, v 0.1 2013-8-21 上午11:06:48
 *          narutoying09@gmail.com Exp $
 */
public class MonthLoanDetail {
	/** 序号，从1开始 */
	private int index;
	/** 年份 */
	private int year;
	/** 月份，从1开始 */
	private int month;
	/** 还款方式 */
	private RepaymentMode repaymentMode;
	/** 当月还款金额 = 当月还款本金 + 当月还款利息 */
	private BigDecimal repaymentMoney;
	/** 当月还款本金 */
	private BigDecimal repaymentCapital;
	/** 当月还款利息 */
	private BigDecimal repaymentInterest;
	/** 备注 */
	private String remarks;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public RepaymentMode getRepaymentMode() {
		return repaymentMode;
	}

	public void setRepaymentMode(RepaymentMode repaymentMode) {
		this.repaymentMode = repaymentMode;
	}

	public BigDecimal getRepaymentCapital() {
		return repaymentCapital;
	}

	public void setRepaymentCapital(BigDecimal repaymentCapital) {
		this.repaymentCapital = repaymentCapital;
	}

	public BigDecimal getRepaymentInterest() {
		return repaymentInterest;
	}

	public void setRepaymentInterest(BigDecimal repaymentInterest) {
		this.repaymentInterest = repaymentInterest;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getRepaymentMoney() {
		return repaymentMoney;
	}

	public void setRepaymentMoney(BigDecimal repaymentMoney) {
		this.repaymentMoney = repaymentMoney;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
