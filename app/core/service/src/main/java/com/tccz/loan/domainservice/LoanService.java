/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.domainservice;

import java.io.OutputStream;
import java.util.List;

import com.tccz.loan.common.util.PageList;
import com.tccz.loan.domain.entity.Loan;
import com.tccz.loan.domain.result.CommonResult;
import com.tccz.loan.domain.vo.LoanQueryCondition;
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
	 * 导出贷款计算结果
	 * 
	 * @param loan
	 * @return
	 */
	CommonResult exportCalculateResult(Loan loan, OutputStream outputStream);

	/**
	 * 获取贷款对象
	 * 
	 * @param loanId
	 * @return
	 */
	Loan getLoan(Integer loanId);

	PageList<Loan> queryReportsByCondition(LoanQueryCondition queryCondition);

	/**
	 * 创建贷款对象并持久化
	 * 
	 * @param loan
	 * @return
	 */
	CommonResult createLoan(Loan loan);

	/**
	 * 删除指定贷款
	 * 
	 * @param loanId
	 * @return
	 */
	CommonResult deleteLoan(int loanId);

}
