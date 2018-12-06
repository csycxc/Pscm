package com.banry.pscm.service.schedule;

/**
 * 工程表Service
 * 
 * @author Xu Dingkui
 * @date 2017年6月17日
 */
public interface EngineeringService {

	/**
	 * 根据Key查找工程表
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Engineering selectEngineeringByPrimaryKey(String key) throws ScheduleException;

	/**
	 * 保存工程表
	 * 
	 * @param engineering
	 * @return
	 * @throws ScheduleException
	 */
	public String saveEngineering(Engineering engineering) throws ScheduleException;

	/**
	 * 根据指定key删除工程表
	 * 
	 * @param key
	 * @return
	 * @throws ScheduleException
	 */
	public int deleteEngineering(String key) throws ScheduleException;
	
	/**
	 * 取当前租户的工程
	 * @author Xu Dingkui
	 * @date 2017年10月18日
	 * @param tenantCode
	 * @return
	 * @throws ScheduleException
	 */
	public Engineering getEngineeringByTenantCode(String tenantCode) throws ScheduleException;


}
