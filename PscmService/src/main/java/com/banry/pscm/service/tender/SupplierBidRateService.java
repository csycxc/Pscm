package com.banry.pscm.service.tender;

import java.util.List;
import java.util.Map;

/**
 * 临时供方投标清单报价Service
 * @author xudingkui
 * @date 2018-8-3
 */
public interface SupplierBidRateService {

	/**
	 * 根据清单项投标编码查找供方投标清单报价信息
	 * @param itemBidCode
	 * @return SupplierBidRate
	 * @throws TenderPlanException 
	 */
	public SupplierBidRate selectByPrimaryKey(String itemBidCode) throws TenderPlanException;
	/**
	 * 查找全部供方投标清单报价信息
	 * @return list
	 * @throws TenderPlanException 
	 */
	public List<SupplierBidRate> findAllSupplierBidRate(String parentTenantAccount) throws TenderPlanException;
	/**
	 * 保存供方投标清单报价，不论为空，全部保存
	 * @param supplierBidRate
	 * @throws TenderPlanException 
	 */
	public void saveSupplierBidRate(SupplierBidRate supplierBidRate) throws TenderPlanException;
	/**
	 * 保存供方投标清单报价，如果为空，不保存
	 * @param supplierBidRate
	 * @throws TenderPlanException 
	 */
	public void saveSupplierBidRateSelective(SupplierBidRate supplierBidRate) throws TenderPlanException;
	/**
	 * 更新供方投标清单报价，清单项投标编码不为空（更新条件），其它项，不论为空，一律更新，如果为空，则置空
	 * @param supplierBidRate
	 * @throws TenderPlanException 
	 */
	public void updateByPrimaryKey(SupplierBidRate supplierBidRate) throws TenderPlanException;
	/**
	 * 更新供方投标清单报价，清单项投标编码不为空（更新条件），其它项，若不论为空，则更新
	 * @param supplierBidRate
	 * @throws TenderPlanException 
	 */
	public void updateByPrimaryKeySelective(SupplierBidRate supplierBidRate) throws TenderPlanException;
	/**
	 * 根据清单项投标编码删除清单报价信息
	 * @param itemBidCode
	 * @return int
	 * @throws TenderPlanException 
	 */
	public int deleteByPrimaryKey(String itemBidCode) throws TenderPlanException;
	
	/**
	 * 根据招标结果编码查询清单
	 * @param map
	 * @return  SupplierBidRate
	 * @throws TenderPlanException
	 */
	public List<SupplierBidRate> findSupplierBidRateByResultCode(Map map) throws TenderPlanException;
	
	/**
	 * 判断招标结果是否已经维护次供方的报价信息
	 * @param record
	 * @return
	 * @throws TenderPlanException
	 */
	public SupplierBidRate findSupplierIsExists(SupplierBidRate record) throws TenderPlanException;
}
