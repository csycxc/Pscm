package com.banry.pscm.account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

//@Component
public class CustomerAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		//用户编号
		String userCode = request.getSession().getAttribute("USER_CODE").toString();
		//租户类别
		String tenantType = request.getSession().getAttribute("TENANT_TYPE").toString();
		//配置用户
		if ("conf".equals(userCode)) {
			request.getRequestDispatcher("/conf").forward(request, response);
		} else {
			//公司级用户
			if ("1".equals(tenantType)) {
				request.getRequestDispatcher("/comp").forward(request, response);
			} else if ("0".equals(tenantType)) {
				request.getRequestDispatcher("/index").forward(request, response);
			}
		}
	}

}
