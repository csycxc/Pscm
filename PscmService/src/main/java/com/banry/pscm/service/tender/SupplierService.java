package com.banry.pscm.service.tender;

import java.util.List;

/**
 * 投标（项目部）service
 * @author chenJuan
 * @date 2018-5-28
 */
public interface SupplierService {

	/**
	 * 根据社会信用代码查询供方信息
	 * @param supplierCreditCode
	 * @return Supplier
	 * @throws TenderPlanException 
	 */
	public Supplier selectByPrimaryKey(String supplierCreditCode) throws TenderPlanException;
	
	/**
	 * 根据供方用户名或者手机号查询供方信息
	 * @param userCodeOrTel
	 * @return
	 * @throws TenderPlanException
	 */
	public Supplier selectByUserCodeOrTel(String userCodeOrTel) throws TenderPlanException;
	
	/**
	 * 查询全部供方信息
	 * @return list
	 * @throws TenderPlanException 
	 */
	public List<Supplier> findAllSupplier() throws TenderPlanException;
	/**
	 * 保存供方，不论是否为空，全都保存。
	 * @param supplier
	 * @throws TenderPlanException 
	 */
	public void saveSupplier(Supplier supplier) throws TenderPlanException;
	/**
	 * 保存供方，如果为空，不保存。
	 * @param supplier
	 * @throws TenderPlanException 
	 */
	public void saveSupplierSelective(Supplier supplier) throws TenderPlanException;
	/**
	 * 更新供方信息。社会信用代码不能为空（更新条件），其它项，不论为空，一律更新，如果为空，则置空。
	 * @param supplier
	 * @throws TenderPlanException 
	 */
	public void updateByPrimaryKey(Supplier supplier) throws TenderPlanException;
	/**
	 * 更新供方信息。社会信用代码不能为空（更新条件），其它项，若不论为空，则更新。
	 * @param supplier
	 * @throws TenderPlanException 
	 */
	public void updateByPrimaryKeySelective(Supplier supplier) throws TenderPlanException;
	/**
	 * 根据社会信用代码删除供方
	 * @param supplierCreditCode
	 * @return int
	 * @throws TenderPlanException 
	 */
	public int deleteByPrimaryKey(String supplierCreditCode) throws TenderPlanException;
	/**
	 * 条件查找
	 * @param sqlWhere
	 * @return list
	 * @throws TenderPlanException 
	 */
	public List<Supplier> findSupplierBySqlWhere(String sqlWhere) throws TenderPlanException;

	List<Supplier> findByTenderResultCode(String tenderPlanCode);
	
}
