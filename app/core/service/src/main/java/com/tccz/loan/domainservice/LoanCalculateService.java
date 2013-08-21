/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.domainservice;

import java.util.List;

import com.tccz.loan.domain.enums.RepaymentMode;
import com.tccz.loan.domain.vo.LoanCalculateInput;
import com.tccz.loan.domain.vo.MonthLoanDetail;

/**
 * 
 * @author narutoying09@gmail.com
 * @version $Id: LoanCalculateService.java, v 0.1 2013-8-21 上午11:31:17
 *          narutoying09@gmail.com Exp $
 */
public interface LoanCalculateService {
	/**
	 * 按月计算还款明细
	 * 
	 * @param input
	 * @return
	 */
	List<MonthLoanDetail> calculate(LoanCalculateInput input);

	RepaymentMode supportMode();
}
