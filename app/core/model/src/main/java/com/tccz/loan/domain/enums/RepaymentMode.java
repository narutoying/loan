/**
 * Taicang mscz Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.tccz.loan.domain.enums;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 还款方式
 * 
 * @author narutoying09@gmail.com
 * @version $Id: RepaymentMode.java, v 0.1 2013-8-21 上午10:00:07
 *          narutoying09@gmail.com Exp $
 */
public enum RepaymentMode {

	ACPI("ACPI", "等额本息"), // Average capital plus interest

	AC("AC", "等额本金"),

	FRAGMENT("FRAGMENT", "分段");

	private final String code;

	private final String description;

	private RepaymentMode(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static RepaymentMode getByCode(String code) {
		if (StringUtils.isNotBlank(code)) {
			for (RepaymentMode mode : values()) {
				if (StringUtils.equals(code, mode.getCode())) {
					return mode;
				}
			}
		}
		return null;
	}

	public static Map<String, String> toMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (RepaymentMode RepaymentMode : values()) {
			map.put(RepaymentMode.getCode(), RepaymentMode.getDescription());
		}
		return map;
	}

}
