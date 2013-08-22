/**
 * narutoying09@gmail.com
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.tccz.loan.web.util;

import java.text.SimpleDateFormat;

/**
 * 
 * @author narutoying09@gmail.com
 * @version $Id: WebConst.java, v 0.1 2012-2-10 ����11:48:05 narutoying09@gmail.com Exp $
 */
public class WebConst {
    /** 默认的每页大小为10 */
    public static final int              PAGE_SIZE            = 10;

    /** 默认的当前页码为1 */
    public static final int              PAGE_NUM             = 1;

    public static final int              MID_PAGE_SIZE        = 15;
    public static final int              LONG_PAGE_SIZE       = 60;
    public static final int              SERVICE_PAGE_SIZE    = 100;

    public static final SimpleDateFormat DateFormat           = new SimpleDateFormat(
                                                                  "yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat HHMMSSDateFormat     = new SimpleDateFormat(
                                                                  "yyyy-MM-dd HH:mm");
    // DHTMLX组件用的硬编码json格式
    public static final String           DHTMLX_ID            = "id";
    public static final String           DHTMLX_ROWS          = "rows";
    public static final String           DHTMLX_DATA          = "data";

    public static final String           HREF_TARGET_START    = "<a target=_blank href=";
    public static final String           HREF_START           = "<a href=";
    public static final String           HREFMID              = ">";
    public static final String           HREFEND              = "</a>";

    public static final String           UTF_8                = "utf-8";

    /** 当前用户管理应用列表 */
    public static final String           CLIENT_USER_APP_LIST = "clientUserAppList";
    public static final String           CLIENT_USER          = "clientUser";
}
