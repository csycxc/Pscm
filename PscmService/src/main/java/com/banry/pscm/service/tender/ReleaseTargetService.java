package com.banry.pscm.service.tender;

import java.util.List;

/**
 * 发布目标Service
 * @author chenJuan
 * @date 2018-5-29
 */
public interface ReleaseTargetService {

	/**
	 * 根据发布目标编码查找发布目标
	 * @param targetCode
	 * @return
	 * @throws TenderPlanException 
	 */
	public ReleaseTarget selectByPrimaryKey(String targetCode) throws TenderPlanException;
	/**
	 * 查找全部的发布目标
	 * @return list
	 * @throws TenderPlanException 
	 */
	public List<ReleaseTarget> findAllReleaseTarget() throws TenderPlanException;
	/**
	 * 保存发布目标，不论为空，全部保存
	 * @param releaseTarget
	 * @throws TenderPlanException 
	 */
	public void saveReleaseTarget(ReleaseTarget releaseTarget) throws TenderPlanException;
	/**
	 * 保存发布目标，如果为空，不保存
	 * @param releaseTarget
	 * @throws TenderPlanException 
	 */
	public void saveReleaseTargetSelective(ReleaseTarget releaseTarget) throws TenderPlanException;
	/**
	 * 更新发布目标，发布目标编码不为空（更新条件），其它项，不论为空，一律更新，如果为空，则置空
	 * @param releaseTarget
	 * @throws TenderPlanException 
	 */
	public void updateByPrimaryKey(ReleaseTarget releaseTarget) throws TenderPlanException;
	/**
	 * 更新发布目标，发布目标编码不为空（更新条件），其它项，若不论为空，则更新
	 * @param releaseTarget
	 * @throws TenderPlanException 
	 */
	public void updateByPrimaryKeySelective(ReleaseTarget releaseTarget) throws TenderPlanException;
	/**
	 * 根据发布目标编码删除发布目标
	 * @param targetCode
	 * @return
	 * @throws TenderPlanException 
	 */
	public int deleteByPrimaryKey(String targetCode) throws TenderPlanException;
	/**
	 * 条件查找
	 * @param sqlWhere
	 * @return list
	 * @throws TenderPlanException 
	 */
	public List<ReleaseTarget> findTargetBySqlWhere(String sqlWhere) throws TenderPlanException;
	
	/**
	 * 根据招标计划编码查找目标
	 * @param tenderPlanCode
	 * @return list 
	 * @throws TenderPlanException
	 */
	public List<ReleaseTarget> findReleaseByTenderPlanCode(String tenderPlanCode) throws TenderPlanException;
	
	/**
	 * 保存或者发布 发布目标
	 * @param rtList
	 * @param method
	 * @param tenderPlanCode
	 * @throws TenderPlanException
	 */
	public void saveOrSubmitTarget(List<ReleaseTarget> rtList, String method, String tenderPlanCode) throws TenderPlanException;
	
}
