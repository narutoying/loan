/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.domain.vo;

import com.tccz.loan.common.util.PageUtil;

/**
 * 
 * @author narutoying09@gmail.com
 * @version $Id: LoanQueryCondition.java, v 0.1 2013-9-2 上午10:49:26
 *          narutoying09@gmail.com Exp $
 */
public class LoanQueryCondition {
	private Integer pageSize = PageUtil.PAGE_SIZE;
	private Integer currentPage = 1; // 默认第一页
	private String loaner;

	// 其他查询条件
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public String getLoaner() {
		return loaner;
	}

	public void setLoaner(String loaner) {
		this.loaner = loaner;
	}
}
