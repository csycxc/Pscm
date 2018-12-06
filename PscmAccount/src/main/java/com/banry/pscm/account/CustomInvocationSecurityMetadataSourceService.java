/*
 * @(#) MyInvocationSecurityMetadataSourceService.java  2011-3-23 下午02:58:29
 *
 * Copyright 2011 by Sparta 
 */

package com.banry.pscm.account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import com.banry.pscm.persist.dao.SysResourceMapper;
import com.banry.pscm.service.account.SysResource;


/**
 * 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。 此类在初始化时，应该取到所有资源及其对应角色的定义。
 * 
 */
@Service
public class CustomInvocationSecurityMetadataSourceService implements
		FilterInvocationSecurityMetadataSource {

	@Autowired
	private SysResourceMapper resourcesDao;
	
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public CustomInvocationSecurityMetadataSourceService(SysResourceMapper resourcesDao) {
		this.resourcesDao = resourcesDao;
		loadResourceDefine();
	}

	private void loadResourceDefine() {
		if(resourceMap == null) {  
            resourceMap = new HashMap<String, Collection<ConfigAttribute>>();  
            List<SysResource> resources = this.resourcesDao.selectAll();  
            for (SysResource resource : resources) {  
                Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();  
                //以权限名封装为Spring的security Object  
                ConfigAttribute configAttribute = new SecurityConfig(resource.getResourceCode() + "");  
                configAttributes.add(configAttribute);  
                resourceMap.put(resource.getResourceUrl(), configAttributes);  
            }  
        }  
          
        Set<Entry<String, Collection<ConfigAttribute>>> resourceSet = resourceMap.entrySet();  
        Iterator<Entry<String, Collection<ConfigAttribute>>> iterator = resourceSet.iterator();  

	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {

		return null;
	}

	// 根据URL，找到相关的权限配置。
	public Collection<ConfigAttribute> getAttributes(Object object)
		throws IllegalArgumentException { 
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		if (requestUrl.length() > 0) {
			//去掉url第一位/
			requestUrl = requestUrl.substring(1, requestUrl.length());
		}
		if (!requestUrl.startsWith("to?url=") && requestUrl.indexOf("?") > -1) {
			requestUrl = requestUrl.substring(0, requestUrl.indexOf("?"));
		}
//        System.out.println("requestUrl is " + requestUrl);  
        if(resourceMap == null) {  
            loadResourceDefine();  
        }  
        return resourceMap.get(requestUrl);  
    }

	public boolean supports(Class<?> arg0) {

		return true;
	}
}
