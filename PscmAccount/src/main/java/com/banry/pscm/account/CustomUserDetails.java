/*
 * @(#) CustomUserDetails.java
 *
 */

package com.banry.pscm.account;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 *实现了User，扩展几项信息。
 */
public class CustomUserDetails extends User {
	
	public CustomUserDetails(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
	}
	
	public CustomUserDetails(String userCode, String userName, String userDeptCode, String userRole, List<String> engDiv, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(userCode, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
		this.userCode = userCode;
		this.userName = userName;
		this.userRole = userRole;
		this.userDeptCode = userDeptCode;
		this.engDiv = engDiv;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String userCode;
	private String userDeptCode;
	private String userRole;
	private List<String> engDiv;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getUserDeptCode() {
		return userDeptCode;
	}
	public void setUserDeptCode(String userDeptCode) {
		this.userDeptCode = userDeptCode;
	}

	/**
	 * @return the engDiv
	 */
	public List<String> getEngDiv() {
		return engDiv;
	}

	/**
	 * @param engDiv the engDiv to set
	 */
	public void setEngDiv(List<String> engDiv) {
		this.engDiv = engDiv;
	}
}
