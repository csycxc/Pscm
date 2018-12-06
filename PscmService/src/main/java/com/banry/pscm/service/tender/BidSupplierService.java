package com.banry.pscm.service.tender;

import java.util.List;
import java.util.Map;

/**
 * 投标供方（项目部）Service
 * @author chenJuan
 * @date 2018-5-29
 */
public interface BidSupplierService {

	/**
	 * 根据供方投标编码查找供方
	 * @param supplierBidCode
	 * @return BidSupplier
	 * @throws TenderPlanException 
	 */
	public BidSupplier selectByPrimaryKey(String supplierBidCode) throws TenderPlanException;
	/**
	 * 查找全部供方信息
	 * @return list
	 * @throws TenderPlanException 
	 */
	public List<BidSupplier> findAllBidSupplier() throws TenderPlanException;
	/**
	 * 保存供方，不论为空，全部保存
	 * @param bidSupplier
	 * @throws TenderPlanException 
	 */
	public void saveBidSupplier(BidSupplier bidSupplier) throws TenderPlanException;
	/**
	 * 保存供方，如果为空，不保存
	 * @param bidSupplier
	 * @throws TenderPlanException 
	 */
	public void saveBidSupplierSelective(BidSupplier bidSupplier) throws TenderPlanException;
	/**
	 * 更新供方，供方投标编号不为空（更新条件），其它项，不论为空，一律更新，如果为空，则置空
	 * @param bidSupplier
	 * @throws TenderPlanException 
	 */
	public void updateByPrimaryKey(BidSupplier bidSupplier) throws TenderPlanException;
	/**
	 * 更新供方，供方投标编号不为空（更新条件），其它项，若不论为空，则更新
	 * @param bidSupplier
	 * @throws TenderPlanException 
	 */
	public void updateByPrimaryKeySelective(BidSupplier bidSupplier) throws TenderPlanException;
	/**
	 * 根据供方投标编码删除供方
	 * @param supplierBidCode
	 * @return
	 * @throws TenderPlanException 
	 */
	public int deleteByPrimaryKey(String supplierBidCode) throws TenderPlanException;
	
	/**
	 * 根据招标编码删除供方
	 * @param supplierBidCode
	 * @returnd
	 * @throws TenderPlanException 
	 */
	public int deleteByTenderPlanCode(String supplierBidCode) throws TenderPlanException;
	/**
	 * 条件查找
	 * @param sqlWhere
	 * @return list
	 * @throws TenderPlanException 
	 */
	public List<BidSupplier> findBidSupplierBySqlWhere(Map sqlWhere) throws TenderPlanException;
	
	
	/**
	 * 批量保存
	 * @param bidSupplier
	 * @throws TenderPlanException 
	 */
	public void saveBidSupplier(List<BidSupplier> bidSupplier, String tenderPlanCode) throws TenderPlanException;
	
	/**
	 * 根据供方编码和招标计划编码查找投标供方
	 * @param map
	 * @return BidSupplier
	 * @throws TenderPlanException 
	 */
	public BidSupplier selectBySupplierCreditCodeAndTenderPlanCode(Map map) throws TenderPlanException;
	
	/**
	 * 根据招标结果变更编码查找中标供方
	 * @param tenderResultIdChangeCode
	 * @return BidSupplier
	 * @throws TenderPlanException 
	 */
	public BidSupplier findBidSupplierByChangeCode(String tenderResultIdChangeCode) throws TenderPlanException;
	
	/**
	 * 依据招标结果编码查询中标供方
	 * @param tenderResultCode
	 * @return
	 * @throws TenderPlanException
	 */
	public BidSupplier findBidSupplierByTenderResultCode(String tenderResultCode) throws TenderPlanException;
	
	/**
	 * 查询所有中标单位
	 * @return
	 * @throws TenderPlanException
	 */
	public List<BidSupplier> findAllBidSupplierList() throws TenderPlanException;
}
