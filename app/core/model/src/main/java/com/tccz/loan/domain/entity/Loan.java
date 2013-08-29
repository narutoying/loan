/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.tccz.loan.common.util.Money;
import com.tccz.loan.domain.enums.RepaymentMode;

/**
 * 一笔贷款
 * 
 * @author narutoying09@gmail.com
 * @version $Id: Loan.java, v 0.1 2013-8-21 上午9:28:44 narutoying09@gmail.com Exp
 *          $
 */
public class Loan {
	private int id;
	/** 贷款人 */
	private String loaner;

	/** 受理人 */
	private String executor;

	/** 贷款金额 */
	private Money amount;

	/** 贷款时间，单位（月） */
	private int term;

	/** 年利率 */
	private BigDecimal annualRate;

	/** 首个还款日 */
	private Date firstRepaymentDate;

	/** 还款方式 */
	private RepaymentMode repaymentMode;

	/** 对应于指定还款方式的配置参数 */
	private Object repaymentConfig;

	public String getLoaner() {
		return loaner;
	}

	public void setLoaner(String loaner) {
		this.loaner = loaner;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

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

	public RepaymentMode getRepaymentMode() {
		return repaymentMode;
	}

	public void setRepaymentMode(RepaymentMode repaymentMode) {
		this.repaymentMode = repaymentMode;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// TODO
	// 贷款状态、已还款金额/月份等

}
