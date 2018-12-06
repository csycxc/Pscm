package com.banry.pscm.service.schedule;

import java.util.List;

/**
 * 施工方案措施Service
 * 
 * @author Xu Dingkui
 * @date 2017年6月15日
 */
public interface DivConsSchemeService {

	/**
	 * 根据Key查找施工方案措施
	 * 
	 * @param DivConsScheme
	 * @return
	 * @throws Exception
	 */
	public DivConsScheme findDivConsSchemeByPrimaryKey(String schemeCode) throws ScheduleException;

	/**
	 * 根据 工程划分 项目编码(Obj) 查询 分项工程施工方案
	 * @param divisionSnCode
	 * @return
	 * @throws PscmConfException
	 */
	public List<DivConsScheme> findDivConsSchemesByDivisionSnCode(String divisionSnCode) throws ScheduleException;

	public List<DivConsScheme> findDivConsSchemesByName(String name) throws ScheduleException;
	/**
	 * 查找所有 分项工程施工方案
	 * @return
	 * @throws PscmConfException
	 */
	public List<DivConsScheme> findAllDivConsScheme() throws ScheduleException;
	
	/**
	 * 保存  分项工程施工方案     无论属性是否为null，都保存             保存包括 更新和插入两种操作
	 * @param divConsScheme
	 * @throws PscmConfException
	 */
	public void saveDivConsScheme(DivConsScheme divConsScheme) throws ScheduleException;
	/**
	 * 保存  分项工程施工方案  如果属性为null,不保存             保存包括 更新和插入两种操作
	 * @param divConsScheme
	 * @throws PscmConfException
	 */
	public void saveDivConsSchemeSelective(DivConsScheme divConsScheme) throws ScheduleException;
	/**
	 * 根据主键删除一条 分项工程施工方案
	 * @param schemeCode
	 * @return
	 * @throws PscmConfException
	 */
	public int deleteDivConsSchemeByKey(String schemeCode) throws ScheduleException;
	/**
	 * 更新  分项工程施工方案   无论属性是否为null，都更新
	 * @param divConsScheme
	 * @throws PscmConfException
	 */
	public void updateDivConsScheme(DivConsScheme divConsScheme) throws ScheduleException;
	/**
	 * 更新  分项工程施工方案，如果属性为null，不更新该属性
	 * @param divConsScheme
	 * @throws PscmConfException
	 */
	public void updateDivConsSchemeSelective(DivConsScheme divConsScheme) throws ScheduleException;
}
