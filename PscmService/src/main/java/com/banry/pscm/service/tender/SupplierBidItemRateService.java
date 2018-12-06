package com.banry.pscm.service.tender;

import java.util.List;
import java.util.Map;

/**
 * 供方投标清单报价Service
 * @author chenJuan
 * @date 2018-5-29
 */
public interface SupplierBidItemRateService {

	/**
	 * 根据清单项投标编码查找供方投标清单报价信息
	 * @param itemBidCode
	 * @return SupplierBidItemRate
	 * @throws TenderPlanException 
	 */
	public SupplierBidItemRate selectByPrimaryKey(String itemBidCode) throws TenderPlanException;
	/**
	 * 查找全部供方投标清单报价信息
	 * @return list
	 * @throws TenderPlanException 
	 */
	public List<SupplierBidItemRate> findAllSupplierBidItemRate() throws TenderPlanException;
	/**
	 * 保存供方投标清单报价，不论为空，全部保存
	 * @param supplierBidItemRate
	 * @throws TenderPlanException 
	 */
	public void saveSupplierBidItemRate(SupplierBidItemRate supplierBidItemRate) throws TenderPlanException;
	/**
	 * 保存供方投标清单报价，如果为空，不保存
	 * @param supplierBidItemRate
	 * @throws TenderPlanException 
	 */
	public void saveSupplierBidItemRateSelective(SupplierBidItemRate supplierBidItemRate) throws TenderPlanException;
	/**
	 * 更新供方投标清单报价，清单项投标编码不为空（更新条件），其它项，不论为空，一律更新，如果为空，则置空
	 * @param supplierBidItemRate
	 * @throws TenderPlanException 
	 */
	public void updateByPrimaryKey(SupplierBidItemRate supplierBidItemRate) throws TenderPlanException;
	/**
	 * 更新供方投标清单报价，清单项投标编码不为空（更新条件），其它项，若不论为空，则更新
	 * @param supplierBidItemRate
	 * @throws TenderPlanException 
	 */
	public void updateByPrimaryKeySelective(SupplierBidItemRate supplierBidItemRate) throws TenderPlanException;
	/**
	 * 根据清单项投标编码删除清单报价信息
	 * @param itemBidCode
	 * @return int
	 * @throws TenderPlanException 
	 */
	public int deleteByPrimaryKey(String itemBidCode) throws TenderPlanException;
	/**
	 * 条件查找
	 * @param sqlWhere
	 * @return list
	 * @throws TenderPlanException 
	 */
	public List<SupplierBidItemRate> findRateBySqlWhere(String sqlWhere) throws TenderPlanException;
	
	/**
	 * 根据划分序号查询清单
	 * @param divisionSnCode
	 * @return  SupplierBidItemRate
	 * @throws TenderPlanException
	 */
	public SupplierBidItemRate findSupplierBidItemRateByDivCode(String divisionSnCode) throws TenderPlanException;
	
	/**
	 * 根据招标计划编码和供方编码查询投标清单报价
	 * @param map
	 * @return
	 * @throws TenderPlanException
	 */
	public List<SupplierBidItemRate> getSupBidItemRateByTpCodeAndSupCode(Map map) throws TenderPlanException;
	
	/**
	 * 批量保存
	 * @param itemList
	 * @param supplierCreditCode
	 * @param bidSupplier
	 * @throws TenderPlanException
	 */
	public void saveSupplierBidItemRate(List<SupplierBidItemRate> itemList, String supplierCreditCode, BidSupplier bidSupplier) throws TenderPlanException;
	
	
	/**
	 * 根据招标计划编码查询投标清单报价
	 * @param tenderPlanCode
	 * @return
	 * @throws TenderPlanException
	 */
	public List<SupplierBidItemRate> findSupplierBidItemRateByTenderPlanCode(String tenderPlanCode) throws TenderPlanException;
	
	
	/**
	 * 根据划分和供方编号查询报价信息
	 * @param divisionSnCode
	 * @param supplierCreditCode
	 * @return
	 * @throws TenderPlanException
	 */
	public SupplierBidItemRate findSupplierBidItemRateByDivCodeAndSupplierCode(String divisionSnCode, String supplierCreditCode) throws TenderPlanException;
	
	
	/**
	 * 根据招标结果变更编码和中标单位查询被移入此变更中或者变更为此中标单位的划分的报价信息
	 * @param changeCode
	 * @param changeSupplier
	 * @return
	 * @throws TenderPlanException
	 */
	public List<SupplierBidItemRate> getMoveInOrChangeSupplierSupBidItemRateByChangeCode(String changeCode, String changeSupplier) throws TenderPlanException;
	
}
