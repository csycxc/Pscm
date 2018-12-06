package com.banry.pscm.service.schedule;

import java.util.List;

/**
 * 工程划分下的工程进度Service
 * 
 * @author Xu Dingkui
 * @date 2017年6月15日
 */
public interface EngDivFinishRateService {

	/**
	 * 根据Key查找工程划分下的工程进度
	 * 
	 * @param engDivFinishRate
	 * @return
	 * @throws Exception
	 */
	public EngDivFinishRate findEngDivFinishRateByPrimaryKey(EngDivFinishRate engDivFinishRate) throws ScheduleException;

	/**
	 * 保存工程划分下的工程进度
	 * 
	 * @param engDivFinishRate
	 * @return
	 * @throws ScheduleException
	 */
	public void saveEngDivFinishRate(EngDivFinishRate engDivFinishRate) throws ScheduleException;


	/**
	 * 查找工程划分下的工程进度
	 * 
	 * @param sqlWhere
	 * @return
	 * @throws ScheduleException
	 */
	public List<EngDivFinishRate> findEngDivFinishRateBySqlWhere(String sqlWhere) throws ScheduleException;

	/**
	 * 根据指定key删除工程划分下的工程进度
	 * 
	 * @param key
	 * @return
	 * @throws ScheduleException
	 */
	public int deleteEngDivFinishRate(EngDivFinishRate key) throws ScheduleException;
	
	/**
	 * 求和工程划分的完成数量
	 * @author Xu Dingkui
	 * @date 2017年7月7日
	 * @param divCode
	 * @return
	 * @throws ScheduleException
	 */
	public EngDivFinishRate sumFinishNumberByDivSnCode(String divCode) throws ScheduleException;

}
