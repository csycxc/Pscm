package com.banry.pscm.service.schedule;

import java.math.BigDecimal;
import java.util.List;

/**
 * 工程项目划分计划links Service
 * 
 * @author Xu Dingkui
 * @date 2017年6月17日
 */
public interface EngDivisionPlanLinksService {

	/**
	 * 根据Key查找工程项目划分计划links
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public EngDivisionPlanLinks findEngDivisionPlanLinksByPrimaryKey(Long key) throws ScheduleException;

	/**
	 * 保存工程项目划分计划links
	 * 
	 * @param engDivisionPlanLinks
	 * @return
	 * @throws ScheduleException
	 */
	public void saveEngDivisionPlanLinks(EngDivisionPlanLinks engDivisionPlanLinks) throws ScheduleException;


	/**
	 * 根据指定key删除工程项目划分计划links
	 * 
	 * @param key
	 * @return
	 * @throws ScheduleException
	 */
	public int deleteEngDivisionPlanLinks(Long key) throws ScheduleException;
	
	/**
	 * 依据条件查询
	 * @author Xu Dingkui
	 * @date 2017年7月2日
	 * @return
	 * @throws ScheduleException
	 */
	public List<EngDivisionPlanLinks> selectBySqlWhere(String sqlWhere) throws ScheduleException;

}
