/*
 * @(#) MyFilterSecurityInterceptor.java  2011-3-23 上午07:53:03
 *
 * Copyright 2011 by Sparta 
 */

package com.banry.pscm.account;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

/**
 * 该过滤器的主要作用就是通过spring著名的IoC生成securityMetadataSource。
 * securityMetadataSource相当于本包中自定义的MyInvocationSecurityMetadataSourceService。
 * 该MyInvocationSecurityMetadataSourceService的作用提从数据库提取权限和资源，装配到HashMap中，
 * 供Spring Security使用，用于权限校验。
 * @author sparta 11/3/29
 *
 */
@Service
public class CustomFilterSecurityInterceptor 
	extends AbstractSecurityInterceptor
	implements Filter{
	
	@Autowired
	private FilterInvocationSecurityMetadataSource securityMetadataSource;
	
	@Autowired
    public void setMyAccessDecisionManager(CustomAccessDecisionManager myAccessDecisionManager) {
        super.setAccessDecisionManager(myAccessDecisionManager);
    }
	
	public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain)
	throws IOException, ServletException{
		
		FilterInvocation fi = new FilterInvocation( request, response, chain );
		invoke(fi);
		
	}
	
	public Class<? extends Object> getSecureObjectClass(){
		return FilterInvocation.class;
	}

	
	public void invoke( FilterInvocation fi ) throws IOException, ServletException{
		
		InterceptorStatusToken  token = super.beforeInvocation(fi);
		
		try{
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		}finally{
			super.afterInvocation(token, null);
		}
		
	}
		
	
	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource(){
		return this.securityMetadataSource;
	}
	
	
	protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,Authentication authResult) throws IOException { 
		CustomUserDetails cu = (CustomUserDetails)authResult.getPrincipal(); 
	    request.getSession().setAttribute("USERCODE", cu.getUserCode()); 
	    request.getSession().setAttribute("USERNAME", cu.getUserName()); 
	} 
	
	public void destroy(){
		
	}
	
	public void init( FilterConfig filterconfig ) throws ServletException{
		
	}
	
	
}
