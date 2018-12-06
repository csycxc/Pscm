/*
 * @(#) MyUserDetailsService.java  2011-3-23 上午09:04:31
 *
 * Copyright 2011 by Sparta 
 */

package com.banry.pscm.account;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.banry.pscm.persist.dao.RoleMapper;
import com.banry.pscm.persist.dao.SupplierMapper;
import com.banry.pscm.persist.dao.SysResourceMapper;
import com.banry.pscm.persist.dao.SysUsersMapper;
import com.banry.pscm.service.account.Role;
import com.banry.pscm.service.account.SysResource;
import com.banry.pscm.service.account.SysUsers;
import com.banry.pscm.service.account.SysUsersKey;
import com.banry.pscm.service.tender.Supplier;


/**
 *该类的主要作用是为Spring Security提供一个经过用户认证后的UserDetails。
 *该UserDetails包括用户名、密码、是否可用、是否过期等信息。
 */
@Service("customUserDetails")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private SysUsersMapper sysUsersDao;
	@Autowired
	private SupplierMapper supplierMapper;
	@Autowired
	private RoleMapper roleDao;
	@Autowired
	private SysResourceMapper resourceDao;
	
	//登录验证
	public UserDetails loadUserByUsername(String usernameAndTenantcodeAccount) throws UsernameNotFoundException {  
		System.out.println("userCode is " + usernameAndTenantcodeAccount);
		String args[]  = usernameAndTenantcodeAccount.split(DisUsernamePasswordAuthenticationFilter.USERNAME_LOGINID_SPLIT);  
		String username = args[0];
		String tenantcode = args[1];
		String tenantAcount = args[2];
		String isSupplier = args[3];
		if ("Y".equals(isSupplier)) {
			Supplier sp = supplierMapper.selectByUserCodeOrTel(username);
			if (sp == null) {
				throw new BadCredentialsException("用户不存在");
			}
			Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
			//封装成spring security的user
			CustomUserDetails userdetail = new CustomUserDetails(sp.getUserCode(), sp.getSupplierName(), "", "", null, sp.getPassword(), true, true, true, true, authSet);
			return userdetail;
		} else {
			SysUsersKey key = new SysUsersKey(username, tenantcode);
	
			SysUsers users = (SysUsers)sysUsersDao.selectByPrimaryKey(key);
			
			if (users == null) {
				users = (SysUsers)sysUsersDao.selectByTelephone(username);
				if (users == null) {
					throw new BadCredentialsException("用户不存在");
				} else {
					username = users.getUserCode();
				}
			} else {
				//记住密码自动登录的时候不校验租户
	//			if (args.length > 1) {
	//				String tenantcode = args[1];
	//				if (!tenantcode.equals(users.getTenantCode())) {
	//					throw new BadCredentialsException("租户编码不正确");
	//				}
	//			}
			}
			List<Role> set = roleDao.selectRoleByUserCode(tenantcode, username);
			Iterator<Role> it = set.iterator();
			String userRole = "";
			while(it.hasNext()) {
				Role role=it.next();
				userRole += role.getRoleCode() + ",";
			}
			if (userRole.length() > 0) {
				userRole = userRole.substring(0, userRole.length() - 1);
			}
			Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(users);
			
			//获取当前用户的工程划分权限
			List<String> engDiv = roleDao.selectEngDivByUserCode(tenantAcount, username);
			boolean enables = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;
			//封装成spring security的user
			CustomUserDetails userdetail = new CustomUserDetails(users.getUserCode(), users.getUserName(), users.getDeptCode(), userRole, engDiv, users.getUserPassword(), enables, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths);
			return userdetail;
		}
    }  
      
    //取得用户的权限  
    private Set<GrantedAuthority> obtionGrantedAuthorities(SysUsers user) {  
    	Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		List<SysResource> resources = new ArrayList<SysResource>();
		List<Role> roles = roleDao.selectRoleByUserCode(user.getTenantCode(), user.getUserCode());
		
		for(Role role : roles) {
			List<SysResource> tempRes = resourceDao.selectResourceByRoleCode(role.getRoleCode(), user.getTenantCode());
			for(SysResource res : tempRes) {
				resources.add(res);
			}
		}
		for(SysResource res : resources) {
			authSet.add(new SimpleGrantedAuthority(res.getResourceCode()));
		}
		return authSet;
    }  
}
