package com.banry.pscm.service.schedule;

import java.util.List;

/**
 * 劳务 Service
 * 
 * @author Xu Dingkui
 * @date 2017年6月17日
 */
public interface ScheduleLaborService {

	/**
	 * 根据Key查找劳务
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public ScheduleLabor findLaborByPrimaryKey(ScheduleLabor key) throws ScheduleException;
	
	/**
	 * 保存劳务
	 * @param labor
	 * @return
	 * @throws Exception
	 */
	public void updateByPrimaryKey(ScheduleLabor labor) throws ScheduleException;
	
	/**
	 * 根据userCode查找劳务
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public ScheduleLabor findLaborByUserCode(String userCode) throws ScheduleException;
	
	/**
	 * 查询劳务公司
	 * @author Xu Dingkui
	 * @date 2017年7月23日
	 * @param divSnCode
	 * @return
	 * @throws ScheduleException
	 */
	public String selectLaborCompanyByDivSnCode(String divSnCode) throws ScheduleException;

	/**
	 * 保存劳务
	 * 
	 * @param labor
	 * @return
	 * @throws ScheduleException
	 */
	public void saveLabor(ScheduleLabor labor) throws ScheduleException;

	/**
	 * 根据指定key删除劳务
	 * 
	 * @param key
	 * @return
	 * @throws ScheduleException
	 */
	public int deleteLabor(ScheduleLabor key) throws ScheduleException;
	
	/**
	 * 查询所有
	 * @author Xu Dingkui
	 * @return
	 * @throws ScheduleException
	 */
	public List<ScheduleLabor> selectAll() throws ScheduleException;
	
	/**
	 * 根据划分编号查询负责该划分交底的所有劳工
	 * @author chenJuan
	 * @param divisionsncode
	 * @return
	 * @throws ScheduleException
	 */
	public List<ScheduleLabor> findLaborByDivSnCode(String divisionsncode) throws ScheduleException;
}
