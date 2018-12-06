package com.banry.pscm.service.schedule;

import java.util.List;
/**
 * 角色对应模块划分service
 * @author chenshiyu
 * @date 2017年12月15日
 */
public interface RoleDivisionService{
	
	/**
	 * 保存角色对应的划分
	 * @date 2017年12月15日
	 * @param roleCode 角色id
	 * @param ids 勾选的划分id集合
	 */
	public void saveRoleDivisions(String roleCode, String ids, String tenantCode);

	/**
	 * 查询角色对应的划分
	 * @date 2017年12月15日
	 * @param roleCode 角色id
	 * @return 该角色下拥有的划分
	 */
	public List<EngDivision> findRoleDivisions(String roleCode);
	
	/**
	 * 删除角色对应的划分
	 * @date 2017年12月15日
	 * @param roleCode 角色id
	 * @return 删除的划分个数（controller中没有用到）
	 */
	public int deleteRoleDivisions(String roleCode, String tenantCode);

}
