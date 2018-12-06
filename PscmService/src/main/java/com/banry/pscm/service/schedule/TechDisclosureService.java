package com.banry.pscm.service.schedule;

import java.util.List;

/**
 * 技术交底Service
 * 
 * @author Xu Dingkui
 * @date 2017年6月17日
 */
public interface TechDisclosureService {

	/**
	 * 根据Key查找技术交底
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public TechDisclosure findTechDisclosureByPrimaryKey(String key) throws ScheduleException;
	
	/**
	 * 根据交底工程项编码查找技术交底
	 * @author Xu Dingkui
	 * @date 2017年7月17日
	 * @param disDivCode
	 * @return
	 * @throws ScheduleException
	 */
	public List<TechDisclosure> findTechDisclosureByDisDivSnCode(String disDivCode) throws ScheduleException;
	
	
	/**
	 * 根据交底对象查找技术交底
	 * 
	 * @param disclo
	 * @return
	 * @throws Exception
	 */
	public List<TechDisclosure> findTechDisclosureByDisclo(String disclo) throws ScheduleException;
	
	/**
	 * 根据交底对象查找已经接受的技术交底
	 * 
	 * @param disclo
	 * @return
	 * @throws Exception
	 */
	public List<TechDisclosure> findHisTechDisclosureByDisclo(String disclo) throws ScheduleException;

	/**
	 * 保存技术交底
	 * 
	 * @param techDisclosure
	 * @return
	 * @throws ScheduleException
	 */
	public void saveTechDisclosure(TechDisclosure techDisclosure) throws ScheduleException;


	/**
	 * 分页查找技术交底
	 * 
	 * @param sqlWhere
	 * @return
	 * @throws ScheduleException
	 */
	public List<TechDisclosure> findTechDisclosureBySqlWhere(String sqlWhere) throws ScheduleException;

	/**
	 * 根据指定key删除技术交底
	 * 
	 * @param key
	 * @return
	 * @throws ScheduleException
	 */
	public int deleteTechDisclosure(String key) throws ScheduleException;
	
	/**
	 * 删除技术交底附件
	 * 
	 * @param fileInName
	 * @return
	 * @throws ScheduleException
	 */
	public int deleteContractAtt(String disclosureCode, String fileInName) throws ScheduleException;

}
