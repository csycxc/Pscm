package com.banry.pscm.service.schedule;
import java.util.HashMap;
import java.util.List;

import com.banry.pscm.service.conf.TreeNode;
/**
 * 工程项目划分(包括工序)Service
 * 
 * @author Xu Dingkui
 * @date 2017年6月17日
 */
public interface EngDivisionService {
	
	/**
	 * 依据查询条件查询
	 * @author Xu Dingkui
	 * @date 2017年7月19日
	 * @param select
	 * @param sqlWhere
	 * @return
	 * @throws ScheduleException
	 */
	public List<EngDivision> selectBySqlWhere(String select, String sqlWhere) throws ScheduleException;
	
	/**
	 * 获取技术主管填报数据
	 * @author Xu Dingkui
	 * @date 2017年7月24日
	 * @param userid 技术主管用户ID
	 * @param divcode 工程划分code
	 * @param pDivSnCode 工程划分父code
	 * @return
	 * @throws ScheduleException
	 */
	public List<EngDivisionModel> selectTechnologyLeaderFillData(String userid, String divcode, String pDivSnCode) throws ScheduleException;
	
	/**
	 * 查询划分级别
	 * @author Xu Dingkui
	 * @date 2017年7月2日
	 * @return
	 * @throws ScheduleException
	 */
	public List<Integer> selectDivLevel() throws ScheduleException;
	
	
	/**
	 * 根据主键查找所有划分工程
	 * @param divisionSnCode
	 * @return
	 * @throws ScheduleException
	 */
	public EngDivision findEngDivisionByKey(String divisionSnCode) throws ScheduleException;
	/**
	 * 根据 工程划分编码（Clazz） 查询 工程划分
	 * @param divItemCode
	 * @return
	 * @throws ScheduleException
	 */
	public List<EngDivision> findEngDivisionsByDivItemCode(String divItemCode) throws ScheduleException;
	/**
	 * 根据divname查询 工程划分
	 * @param divName
	 * @return
	 * @throws ScheduleException
	 */
	public List<EngDivision> findEngDivisionsByDivName(String divName) throws ScheduleException;
	/**
	 * 查询所有工程划分
	 * @return
	 * @throws ScheduleException
	 */
	public List<EngDivision> findAllEngDivision() throws ScheduleException;
	/**
	 * 根据主键查找树形结构的划分
	 * @param divisionSnCode
	 * @return
	 * @throws ScheduleException
	 */
	public TreeNode findTreeByDivisionSnCode(String divisionSnCode) throws ScheduleException;
	/**
	 * 查询子划分
	 * @param parentDivisionSnCode
	 * @return
	 * @throws ScheduleException
	 */
	public List<EngDivision> findSonEngDivisions(String parentDivisionSnCode) throws ScheduleException;
	
	/**
	 * 保存划分，无论属性是否为null,都保存        保存包括更新和插入两种操作
	 * @param engDivision
	 * @throws ScheduleException
	 */
	public String saveEngDivision(EngDivision engDivision) throws ScheduleException;
	/**
	 * 保存划分，属性如果为null,不保存        保存包括更新和插入两种操作
	 * @param engDivision
	 * @throws ScheduleException
	 */
	public String saveEngDivisionSelective(EngDivision engDivision) throws ScheduleException;
	/**
	 * 根据主键删除
	 * @param divisionSnCode
	 * @return
	 * @throws ScheduleException
	 */
	public int deleteEngDivisionByKey(String divisionSnCode)throws ScheduleException;
	/** 更新划分，无论属性是否为null,都更新 
	 * @param engDivision
	 * @throws ScheduleException
	 */
	public void updateEngDivision(EngDivision engDivision) throws ScheduleException;
	/**
	 * 更新划分，属性如果为null,不更新
	 * @param engDivision
	 * @throws ScheduleException
	 */
	public void updateEngDivisionSelective(EngDivision engDivision) throws ScheduleException;
	
	List<HashMap> findAllEngDivisionForZTree() throws ScheduleException;
	
	/**
	 * 从划分表中查询所有级别。
	 * @return
	 */
	public List<Integer> findAllDivLevelFromEngDivision();
	
	/**
	 * 根据主键查询divitemcode,再查询划分表中该divitemcode的数量
	 * @param divisionsncode
	 * @return
	 */
	public int findDivItemCodeCountFromEngDivisionByDivisionSnCode(String divisionsncode);
}