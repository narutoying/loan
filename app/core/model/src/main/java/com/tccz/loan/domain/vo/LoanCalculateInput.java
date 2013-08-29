/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.domain.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.tccz.loan.common.util.Money;

/**
 * 贷款计算输入
 * 
 * @author narutoying09@gmail.com
 * @version $Id: LoanCalculateInput.java, v 0.1 2013-8-21 上午11:27:50
 *          narutoying09@gmail.com Exp $
 */
public class LoanCalculateInput {
	/** 贷款金额 */
	private Money amount;

	/** 贷款时间，单位（月） */
	private int term;

	/** 年利率 */
	private BigDecimal annualRate;

	/** 月利率 */
	private BigDecimal monthRate;

	/** 首个还款日 */
	private Date firstRepaymentDate;

	/** 对应于指定还款方式的配置参数 */
	private Object repaymentConfig;

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public BigDecimal getAnnualRate() {
		return annualRate;
	}

	public void setAnnualRate(BigDecimal annualRate) {
		this.annualRate = annualRate;
	}

	public Date getFirstRepaymentDate() {
		return firstRepaymentDate;
	}

	public void setFirstRepaymentDate(Date firstRepaymentDate) {
		this.firstRepaymentDate = firstRepaymentDate;
	}

	public Object getRepaymentConfig() {
		return repaymentConfig;
	}

	public void setRepaymentConfig(Object repaymentConfig) {
		this.repaymentConfig = repaymentConfig;
	}

	public Money getAmount() {
		return amount;
	}

	public void setAmount(Money amount) {
		this.amount = amount;
	}

	public BigDecimal getMonthRate() {
		return monthRate;
	}

	public void setMonthRate(BigDecimal monthRate) {
		this.monthRate = monthRate;
	}
}
