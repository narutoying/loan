/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.domainservice;

import java.util.List;

import com.tccz.loan.domain.entity.Loan;
import com.tccz.loan.domain.result.CommonResult;
import com.tccz.loan.domain.vo.MonthLoanDetail;

/**
 * 
 * @author narutoying09@gmail.com
 * @version $Id: LoanService.java, v 0.1 2013-8-21 上午11:01:33
 *          narutoying09@gmail.com Exp $
 */
public interface LoanService {
	/**
	 * 计算指定贷款的按月还款明细
	 * 
	 * @param loan
	 * @return
	 */
	List<MonthLoanDetail> calculate(Loan loan);

	/**
	 * 获取贷款对象
	 * 
	 * @param loanId
	 * @return
	 */
	Loan getLoan(int loanId);

	/**
	 * 创建贷款对象并持久化
	 * 
	 * @param loan
	 * @return
	 */
	CommonResult createLoan(Loan loan);
	
}
