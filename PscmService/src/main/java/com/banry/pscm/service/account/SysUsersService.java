package com.banry.pscm.service.account;

import java.util.List;

/**
 * 
 * @author Xudk
 */
public interface SysUsersService {

	/**
	 * 
	 * @param key
	 * @return
	 * @throws AccountException
	 */
	public SysUsers getByCode(SysUsersKey key) throws AccountException;
	
	/**
	 * 
	 * @param code
	 * @return
	 * @throws AccountException
	 */
	public SysUsers getByCodeAndTenantAccount(String code, String tenantCode, String parentTenantAccount) throws AccountException;
	
	/**
	 * 
	 * @param telephone
	 * @return
	 * @throws AccountException
	 */
	public SysUsers getByTelephone(String telephone) throws AccountException;

	/**
	 * 新增用户
	 * @param user
	 * @return 返回用户id
	 */
	public void saveUser(SysUsers user) throws AccountException ;
	
	/**
	 * 删除用户
	 * @param code
	 */
	public void deleteUser(SysUsersKey code) throws AccountException;
	
	/**
	 * 根据条件查询用户
	 * @return
	 */
	public List<SysUsers> findAllUser(String tenantCode) throws AccountException;
	
	/**
	 * 判断用户是否有此叶子节点划分权限
	 * @author Xu Dingkui
	 * @date 2017年12月21日
	 * @param userCode
	 * @param divSnCode
	 * @return
	 */
	public String checkEngDivAuthority(String userCode, String divSnCode);
}
