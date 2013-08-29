/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.web.util;

import org.springframework.ui.ModelMap;

import com.tccz.loan.domain.result.CommonResult;

/**
 * 
 * @author narutoying09@gmail.com
 * @version $Id: ErrorUtil.java, v 0.1 2013-8-29 上午9:52:44
 *          narutoying09@gmail.com Exp $
 */
public class WebUtil {

	public static String goPage(ModelMap modelMap, CommonResult result,
			WebPageCallback callback) {
		if (result.isSuccess()) {
			modelMap.addAttribute("successMessage", result.getResultMessage());
			return callback.successPage();
		} else {
			modelMap.addAttribute("error", result.getResultMessage());
			return callback.errorPage();
		}
	}

}


