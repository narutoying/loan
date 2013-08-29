/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.web.util;

/**
 * 
 * @author narutoying09@gmail.com
 * @version $Id: WebPageCallback.java, v 0.1 2013-8-29 上午10:38:00
 *          narutoying09@gmail.com Exp $
 */
public abstract class WebPageCallback {

	public abstract String successPage();

	public String errorPage() {
		return "error";
	}

}
