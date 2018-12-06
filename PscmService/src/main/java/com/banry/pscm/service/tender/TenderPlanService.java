package com.banry.pscm.service.tender;

import java.util.List;
import java.util.Map;

/**
 * 招标计划service
 * @author chenJuan
 * @date 2018-5-28
 */
public interface TenderPlanService {
	
	/**
	 * 根据tenderPlanCode查找招标计划
	 * @param map
	 * @return TenderPlanWithBLOBs
	 * @throws TenderPlanException 
	 */
	public TenderPlanWithBLOBs selectByPrimaryKey(Map map) throws TenderPlanException;
	/**
	 * 查询全部招标计划
	 * @return list
	 * @throws TenderPlanException 
	 */
	public List<TenderPlanWithBLOBs> findAll(String parentTenantAccount) throws TenderPlanException;
	/**
	 * 保存招标计划，不论是否为空，全都保存
	 * @param tenderPlanWithBLOBS
	 * @throws TenderPlanException 
	 */
	public void saveTenderPlan(TenderPlanWithBLOBs tenderPlanWithBLOBS) throws TenderPlanException;
	/**
	 * 保存招标计划，如果为空，不保存
	 * @param tenderPlanWithBLOBS
	 * @throws TenderPlanException 
	 */
	public void saveTenderPlanSelective(TenderPlanWithBLOBs tenderPlanWithBLOBS, List<BidSupplier> supplierList) throws TenderPlanException;
	/**
	 * 更新招标计划。招标计划编码不能为空，这是更新的条件。其它项，不论为空，一律更新，如果为空，则置空。
	 * @param tenderPlan
	 * @throws TenderPlanException 
	 */
	public void updateByPrimaryKey(TenderPlan tenderPlan) throws TenderPlanException;
	/**
	 * /**
	 * 更新招标计划。招标计划编码不能为空，这是更新的条件。其它项，不论为空，一律更新，如果为空，则置空
	 * @param TenderPlanWithBLOBs
	 * @throws TenderPlanException 
	 */
	public void updateByPrimaryKeyWithBLOBS(TenderPlanWithBLOBs record, List<BidSupplier> supplierList) throws TenderPlanException;
	/**
	 * 更新招标计划。招标计划编码不能为空，这是更新的条件。其它项，若不论为空，则更新
	 * @param record
	 * @throws TenderPlanException 
	 */
	public void  updateByPrimaryKeySelective(TenderPlanWithBLOBs record) throws TenderPlanException;
	/**
	 * 根据招标计划编码删除该招标计划
	 * @param tenderPlanCode
	 * @return int
	 * @throws TenderPlanException 
	 */
	public int deleteByPrimaryKey(String tenderPlanCode) throws TenderPlanException;
	/**
	 * 条件查找
	 * @param sqlWhere
	 * @return list
	 * @throws TenderPlanException 
	 */
	public List<TenderPlanWithBLOBs> findTenderPlanBySqlWhere(String sqlWhere) throws TenderPlanException;
	
	/**
	 * 查询当前发布的招标计划
	 * @return
	 * @throws TenderPlanException
	 */
	public List<TenderPlan> findTenderPlanRelease() throws TenderPlanException;

}
