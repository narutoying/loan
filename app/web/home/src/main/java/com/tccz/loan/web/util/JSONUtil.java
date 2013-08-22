package com.tccz.loan.web.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

/**
 * JSON������
 * 
 * @author feng.jin
 * @version $Id: JSONUtil.java, v 0.1 2011-12-2 ����11:55:27 feng.jin Exp $
 */
public class JSONUtil {

	/** 日志 */
	private static final Logger logger = LoggerFactory
			.getLogger(JSONUtil.class);

	private static JsonConfig jsonConfig;

	/**
	 * 得到json转换配置
	 */
	public synchronized static JsonConfig getJsonConfig() {
		if (jsonConfig == null) {
			jsonConfig = new JsonConfig();
			return jsonConfig;
		}
		return jsonConfig;
	}

	/**
	 * 传回单种类型数据到浏览器 默认是GBK
	 */
	public static void writeBack(HttpServletResponse res, Object ret) {
		if (ret == null) {
			return;
		}
		try {
			String r = ret.toString();
			res.setContentType("application/json; charset=gbk");
			res.getWriter().write(r);
		} catch (IOException e) {
			logger.error("Json数据转换出错", e);
		}
	}

	/**
	 * 写回JSON内容 某些js组件需要明确指定编码类型
	 * 
	 * @param res
	 * @param ret
	 * @param charset
	 */
	public static void writeBack(HttpServletResponse res, Object ret,
			String charset) {

		if (ret == null) {
			return;
		}
		try {
			String r = ret.toString();
			res.setContentType("application/json; charset=" + charset);
			res.getWriter().write(r);
		} catch (IOException e) {
			logger.error("Json数据转换出错", e);
		}
	}

	/**
	 * 写回JSON内容 某些js组件需要明确指定编码类型
	 * 
	 * @param res
	 * @param ret
	 * @param charset
	 */
	public static void writeBack(HttpServletResponse res, String ret,
			String charset) {

		if (ret == null) {
			return;
		}
		try {
			res.setContentType("application/json; charset=" + charset);
			res.getWriter().write(ret);
		} catch (IOException e) {
			logger.error("Json数据转换出错", e);
		}
	}

	public static void writeBackJsonWithConfig(HttpServletResponse response,
			Object obj) {
		JSON json = JSONSerializer.toJSON(obj, JSONUtil.getJsonConfig());
		writeBack(response, json, WebConst.UTF_8);
	}

}
