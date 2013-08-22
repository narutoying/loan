/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.web.form;

import java.util.Date;

/**
 * 
 * @author narutoying09@gmail.com
 * @version $Id: LoanForm.java, v 0.1 2013-8-22 下午3:46:28 narutoying09@gmail.com
 *          Exp $
 */
public class LoanForm {
	/** 贷款人 */
	private String loaner;

	/** 受理人 */
	private String executor;

	/** 贷款金额 */
	private String amount;

	/** 贷款时间，单位（月） */
	private Integer term;

	/** 年利率（百分数） */
	private String annualRate;

	/** 首个还款日 */
	private Date firstRepaymentDate;

	/** 还款方式 */
	private String repaymentMode;

	/** 对应于分段还款方式的配置参数 */
	private Integer onlyInterestMonths;
	private Integer leftMonths;

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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public String getAnnualRate() {
		return annualRate;
	}

	public void setAnnualRate(String annualRate) {
		this.annualRate = annualRate;
	}

	public Date getFirstRepaymentDate() {
		return firstRepaymentDate;
	}

	public void setFirstRepaymentDate(Date firstRepaymentDate) {
		this.firstRepaymentDate = firstRepaymentDate;
	}

	public String getRepaymentMode() {
		return repaymentMode;
	}

	public void setRepaymentMode(String repaymentMode) {
		this.repaymentMode = repaymentMode;
	}

	public Integer getOnlyInterestMonths() {
		return onlyInterestMonths;
	}

	public void setOnlyInterestMonths(Integer onlyInterestMonths) {
		this.onlyInterestMonths = onlyInterestMonths;
	}

	public Integer getLeftMonths() {
		return leftMonths;
	}

	public void setLeftMonths(Integer leftMonths) {
		this.leftMonths = leftMonths;
	}

}
