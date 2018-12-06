package com.banry.pscm.service.account;


import java.util.List;

/**
 * 
 * @author Xudk
 */
public interface SysDeptsService {

	/**
	 * 根据key获取部门
	 * @param key
	 * @return
	 */
	public SysDepts getByDeptCode(SysDeptsKey key)  throws AccountException;
	
	/**
	 * 新增部门
	 * @param dept
	 */
	public void saveDept(SysDepts dept)  throws AccountException;
	
	/**
	 * 删除部门
	 * @param key
	 */
	public void deleteDept(SysDeptsKey key) throws AccountException;
	
	/**
	 * 根据条件查询部门
	 * @return
	 */
	public List findAllDept(String tenantCode) throws AccountException;
	
}
