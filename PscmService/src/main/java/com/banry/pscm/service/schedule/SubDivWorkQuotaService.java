package com.banry.pscm.service.schedule;

import java.util.List;

/**
 * 分项工程定额资源Service
 * 
 * @author Xu Dingkui
 * @date 2018年7月4日
 */
public interface SubDivWorkQuotaService {

	/**
	 * 根据主键 查找 分项工程定额数据表信息
	 * @param resCode
	 * @return
	 * @throws ScheduleException
	 */
	public SubDivWorkQuota findSubDivWorkQuotaByKey(String resCode) throws ScheduleException;
	/**
	 * 根据工程划分主键 查找 分析工程定额数据表信息
	 * @param divisionSnCode
	 * @return
	 * @throws ScheduleException
	 */
	public List<SubDivWorkQuota> findSubDivWorkQuotaByDivisionSnCode(String divisionSnCode) throws ScheduleException;
	/**
	 * 根据资源类型（人工 ，材料，机械）查找 分项工程定额数据表信息
	 * @param resourcesType
	 * @return
	 * @throws ScheduleException
	 */
	public List<SubDivWorkQuota> findSubDivWorkQuotaByResourcesType(String resourcesType) throws ScheduleException;
	/**
	 * 根据项目名称（res_name） 查找 分项工程定额数据表信息
	 * @param resName
	 * @return
	 * @throws ScheduleException
	 */
	public List<SubDivWorkQuota> findSubDivWorkQuotaByResName(String resName) throws ScheduleException;
	/**
	 * 根据资源单位查找 分项工程定额数据表信息
	 * @param unit
	 * @return
	 * @throws ScheduleException
	 */
	public List<SubDivWorkQuota> findSubDivWorkQuotaByUnit(String unit) throws ScheduleException;
	/**
	 * 
	 */
	public List<SubDivWorkQuota> findAllSubDivWorkQuota() throws ScheduleException;
	
	/**
	 * 保存 分项工程定额数据表信息（无论属性是否为null,都保存）    保存包括 更新和插入
	 * @param subDivWorkQuota
	 * @throws ScheduleException
	 */
	public void saveSubDivWorkQuota(SubDivWorkQuota subDivWorkQuota) throws ScheduleException;
	/**
	 * 保存 分项工程定额数据表信息（属性为null,不保存）    保存包括 更新和插入
	 * @param subDivWorkQuota
	 * @throws ScheduleException
	 */
	public void saveSubDivWorkQuotaSelective(SubDivWorkQuota subDivWorkQuota) throws ScheduleException;
	/**
	 * 根据主键删除 定额数据表信息
	 * @param resCode
	 * @return
	 * @throws ScheduleException
	 */
	public int deleteSubDivWorkQuotaByKey(String resCode) throws ScheduleException;
	/**
	 * 根据划分主键 divisionsncode删除多个定额数据表信息
	 * @param divisionsncode
	 */
	public int deleteSubDivWorkQuotaByDivisionSnCode(String divisionsncode);
	/**
	 * 更新 定额数据表信息      无论属性是否为null,都更新；    
	 * @param subDivWorkQuota
	 * @throws ScheduleException
	 */
	public void updateSubDivWorkQuota(SubDivWorkQuota subDivWorkQuota) throws ScheduleException;
	/**
	 * 更新 定额数据表信息      属性为null,不更新；    
	 * @param subDivWorkQuota
	 * @throws ScheduleException
	 */
	public void updateSubDivWorkQuotaSelective(SubDivWorkQuota subDivWorkQuota) throws ScheduleException;
	/**
	 * 根据rescode查询清单是否提交
	 * @param rescode
	 * @return
	 */
	public int findSubDivWorkBillStatus(String rescode);

}
