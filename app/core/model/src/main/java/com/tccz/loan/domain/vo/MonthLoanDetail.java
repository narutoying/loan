/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.domain.vo;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.tccz.loan.domain.enums.RepaymentMode;

/**
 * 贷款按月明细
 * 
 * @author narutoying09@gmail.com
 * @version $Id: MonthLoanDetail.java, v 0.1 2013-8-21 上午11:06:48
 *          narutoying09@gmail.com Exp $
 */
public class MonthLoanDetail {
	/** 年份 */
	private int year;
	/** 月份，从1开始 */
	private int month;
	/** 还款方式 */
	private RepaymentMode repaymentMode;
	/** 当月还款金额 = 当月还款本金 + 当月还款利息 */
	private BigDecimal repaymentMoney;
	// TODO json序列化无法完整保留小数点数值，待解决
	private String repaymentMoneyStr;
	/** 当月还款本金 */
	private BigDecimal repaymentCapital;
	private String repaymentCapitalStr;
	/** 当月还款利息 */
	private BigDecimal repaymentInterest;
	private String repaymentInterestStr;
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
		this.repaymentCapitalStr = repaymentCapital.toString();
	}

	public BigDecimal getRepaymentInterest() {
		return repaymentInterest;
	}

	public void setRepaymentInterest(BigDecimal repaymentInterest) {
		this.repaymentInterest = repaymentInterest;
		this.repaymentInterestStr = repaymentInterest.toString();
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
		this.repaymentMoneyStr = repaymentMoney.toString();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getRepaymentMoneyStr() {
		return repaymentMoneyStr;
	}

	public void setRepaymentMoneyStr(String repaymentMoneyStr) {
		this.repaymentMoneyStr = repaymentMoneyStr;
	}

	public String getRepaymentCapitalStr() {
		return repaymentCapitalStr;
	}

	public void setRepaymentCapitalStr(String repaymentCapitalStr) {
		this.repaymentCapitalStr = repaymentCapitalStr;
	}

	public String getRepaymentInterestStr() {
		return repaymentInterestStr;
	}

	public void setRepaymentInterestStr(String repaymentInterestStr) {
		this.repaymentInterestStr = repaymentInterestStr;
	}
}
