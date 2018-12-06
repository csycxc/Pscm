package com.banry.pscm.account;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author xudk
 *	2018-10-24
 */
public class SessionUtil {

	//公司级租户账号
	public final static String COMPANY_LEVEL_TENANT_ACCOUNT = "COMPANY_LEVEL_TENANT_ACCOUNT";
	//租户类型
	public final static String TENANT_TYPE = "TENANT_TYPE";
	//用户编码
	public final static String USER_CODE = "USER_CODE";
	//当前用户租户账号
	public final static String CURRENT_TENANT_ACCOUNT = "CURRENT_TENANT_ACCOUNT";
	//当前用户自护编码
	public final static String CURRENT_TENANT_CODE = "CURRENT_TENANT_CODE";
	//当前用户是否供方
	public final static String IS_SUPPLIER = "IS_SUPPLIER";
	//供方查看招标公告，选择的招标计划编码
	public final static String TENDER_PLAN_CODE = "TENDER_PLAN_CODE";
	//项目级租户账号
	public final static String TENANT_ACCOUNT = "TENANT_ACCOUNT";
	//项目部名称
	public final static String PROJECT_DEPARTMENT_NAME = "PROJECT_DEPARTMENT_NAME";
	//公司名称
	public final static String COMPANY_NAME = "COMPANY_NAME";
	//供方编码
	public final static String SUPPLIER_CREDIT_CODE = "SUPPLIER_CREDIT_CODE";
	//供方名称
	public final static String SUPPLIER_NAME = "SUPPLIER_NAME";
	//工程编码
	public final static String ENG_CODE = "ENG_CODE";
	//
	public final static String MAJOR_SOURCE = "MAJOR_SOURCE";
	
	public static void setSessionValue(HttpServletRequest request, String key, String value) {
		request.getSession().setAttribute(key, value);
	}
	
	public static String getSessionValue(HttpServletRequest request, String key) {
		if (request.getSession().getAttribute(key) != null) {
			return request.getSession().getAttribute(key).toString();
		} else {
			return "";
		}
	}
}
