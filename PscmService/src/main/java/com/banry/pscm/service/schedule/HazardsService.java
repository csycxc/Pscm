package com.banry.pscm.service.schedule;

import java.util.List;

/**
 * 工程危险源
 * @author Administrator
 *
 */
public interface HazardsService {
	/**
	 * 根据主键查找危险源
	 * @param hazardsCode
	 * @return
	 * @throws ScheduleException
	 */
	public Hazards findHazardsByKey(String hazardsCode) throws ScheduleException;
	/**
	 * 根据divitemcode查找危险源
	 * @param divItemCode
	 * @return
	 * @throws ScheduleException
	 */
	public List<Hazards> findHazardssByDivItemCode(String divItemCode) throws ScheduleException;
	
	
	/**
	 * 根据divitemcode查找危险源
	 * @param divItemCode
	 * @return
	 * @throws ScheduleException
	 */
	public List<Hazards> findHazardssByDivSnCode(String divSnCode) throws ScheduleException;
	
	
	/**
	 * 根据危险源及危害因素 模糊查询 危险源
	 * @param hazardsFactors
	 * @return
	 * @throws ScheduleException
	 */
	public List<Hazards> findHazardssByHazardsFactors(String hazardsFactors) throws ScheduleException;
	/**
	 * 查找所有危险源
	 * @return
	 * @throws ScheduleException
	 */
	public List<Hazards> findAllHazards() throws ScheduleException;
	
	/**
	 * 保存危险源   无论属性是否为null,都保存       保存包括更新和插入两种操作
	 * @param hazards
	 * @throws ScheduleException
	 */
	public void saveHazards(Hazards hazards) throws ScheduleException;
	/**
	 * 保存危险源   属性为null,不保存       保存包括更新和插入两种操作
	 * @param hazards
	 * @throws ScheduleException
	 */
	public void saveHazardsSelective(Hazards hazards) throws ScheduleException;
	/**
	 * 根据主键删除Hazards
	 * @param hazardsCode
	 * @return
	 * @throws ScheduleException
	 */
	public int deleteHazardsByKey(String hazardsCode) throws ScheduleException;
	/**
	 * 更新危险源，无论属性是否为null,都更新
	 * @param hazards
	 * @throws ScheduleException
	 */
	public void updateHazards(Hazards hazards) throws ScheduleException;
	/**
	 * 更新危险源，属性为null,不更新
	 * @param hazards
	 * @throws ScheduleException
	 */
	public void updateHazardsSelective(Hazards hazards) throws ScheduleException;
	
	/**
	 * 根据divisionsncode 查找divItemCode,再根据DivItemCode删除危险源
	 * @param divisionsncode
	 */
	public int deleteHazardsByDivisionSnCode(String divisionsncode);
}