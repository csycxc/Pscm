package com.banry.pscm.service.schedule;

import java.util.List;

/**
 * 工程划分计划Service
 * 
 * @author Xu Dingkui
 * @date 2017年6月15日
 */
public interface EngDivisionPlanService {

	/**
	 * 根据Key查找工程划分计划
	 * 
	 * @param engDivisionPlan
	 * @return
	 * @throws Exception
	 */
	public EngDivisionPlan findEngDivisionPlanByPrimaryKey(EngDivisionPlan engDivisionPlan) throws ScheduleException;

	/**
	 * 保存工程划分计划
	 * 
	 * @param engDivisionPlan
	 * @return
	 * @throws ScheduleException
	 */
	public void saveEngDivisionPlan(EngDivisionPlan engDivisionPlan) throws ScheduleException;

	/**
	 * 查找工程划分计划
	 * @author Xu Dingkui
	 * @date 2017年7月7日
	 * @param sqlWhere
	 * @return
	 * @throws ScheduleException
	 */
	public List<EngDivisionPlan> selectBySqlWhere(String sqlWhere) throws ScheduleException;

	/**
	 * 根据指定key删除工程划分计划
	 * 
	 * @param key
	 * @return
	 * @throws ScheduleException
	 */
	public int deleteEngDivisionPlan(EngDivisionPlan key) throws ScheduleException;

}
