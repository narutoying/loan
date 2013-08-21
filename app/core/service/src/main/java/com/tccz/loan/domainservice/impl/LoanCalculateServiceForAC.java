/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.domainservice.impl;

import java.util.List;

import com.tccz.loan.domain.enums.RepaymentMode;
import com.tccz.loan.domain.vo.LoanCalculateInput;
import com.tccz.loan.domain.vo.MonthLoanDetail;
import com.tccz.loan.domainservice.LoanCalculateService;

/**
 * 等额本金计算
 * 
 * @author narutoying09@gmail.com
 * @version $Id: LoanCalculateServiceAC.java, v 0.1 2013-8-21 上午11:36:40
 *          narutoying09@gmail.com Exp $
 */
public class LoanCalculateServiceForAC implements LoanCalculateService {

	@Override
	public List<MonthLoanDetail> calculate(LoanCalculateInput input) {
		return null;
	}

	@Override
	public RepaymentMode supportMode() {
		return null;
	}

}
