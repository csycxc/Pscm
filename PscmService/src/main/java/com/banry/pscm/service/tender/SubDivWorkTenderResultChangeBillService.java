package com.banry.pscm.service.tender;

import java.util.List;
import java.util.Map;

/**
 * 分项工程招标结果变更清单service
 * @author xudk
 * @date 2018-9-27
 */
public interface SubDivWorkTenderResultChangeBillService {

	/**
	 * 根据主键查询招标结果变更清单
	 * @param key
	 * @return SubDivWorkTenderResultChangeBill
	 * @throws TenderPlanException 
	 */
	public SubDivWorkTenderResultChangeBill selectByPrimaryKey(SubDivWorkTenderResultChangeBillKey key) throws TenderPlanException;
	
	/**
	 * 保存变更清单，不论是否为空，全都保存。
	 * @param bill
	 * @throws TenderPlanException 
	 */
	public void saveSubDivWorkTenderResultChangeBill(SubDivWorkTenderResultChangeBill bill) throws TenderPlanException;
	/**
	 * 保存变更清单，如果为空，不保存。
	 * @param bill
	 * @throws TenderPlanException 
	 */
	public void saveSubDivWorkTenderResultChangeBillSelective(SubDivWorkTenderResultChangeBill bill) throws TenderPlanException;
	
	/**
	 * 更新变更清单。主键不能为空（更新条件），其它项，不论为空，一律更新，如果为空，则置空。
	 * @param bill
	 * @throws TenderPlanException 
	 */
	public void updateByPrimaryKey(SubDivWorkTenderResultChangeBill bill) throws TenderPlanException;
	
	/**
	 * 更新变更清单。主键不能为空（更新条件），其它项，若不论为空，则更新。
	 * @param bill
	 * @throws TenderPlanException 
	 */
	public void updateByPrimaryKeySelective(SubDivWorkTenderResultChangeBill bill) throws TenderPlanException;
	
	/**
	 * 删除变更清单
	 * @param key
	 * @return int
	 * @throws TenderPlanException 
	 */
	public int deleteByPrimaryKey(SubDivWorkTenderResultChangeBillKey key) throws TenderPlanException;
	
	/**
	 * 依据变更编码查询清单历史变更信息
	 * @param map
	 * @return list
	 * @throws TenderPlanException 
	 */
	public List<SubDivWorkTenderResultChangeBill> findSubDivWorkTenderResultChangeBillByChangeCode(Map map) throws TenderPlanException;
	
	/**
	 * 查询划分是否被移入（不包含已退回的结果变更）
	 * @param divisionSnCode
	 * @return
	 * @throws TenderPlanException
	 */
	public SubDivWorkTenderResultChangeBill findMoveInResultChangeBillByDivisionSnCode(String divisionSnCode) throws TenderPlanException;
}
