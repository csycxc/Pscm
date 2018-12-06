package com.banry.pscm.service.tender;

import java.util.List;
import java.util.Map;

/**
 * 招标结果变更Service
 * @author chenJuan
 * @date 2018-5-30
 */
public interface TenderResultChangeService {
	
	/**
	 * 根据招标结果变更编码查找结果变更
	 * @param map
	 * @return
	 * @throws TenderPlanException
	 */
	public TenderResultChange selectByPrimaryKey(Map map) throws TenderPlanException;
	/**
	 * 查找所有的招标结果变更
	 * @return
	 * @throws TenderPlanException
	 */
	public List<TenderResultChange> findAllChange(String parentTenantAccount) throws TenderPlanException;
	/**
	 * 保存招标结果变更，不论为空，均保存
	 * @param record
	 * @throws TenderPlanException
	 */
	public void saveChange(TenderResultChange record) throws TenderPlanException;
	/**
	 * 保存招标结果变更，如果为空，不保存
	 * @param record
	 * @throws TenderPlanException
	 */
	public void saveChangeSelective(TenderResultChange record) throws TenderPlanException;
	 /**
     * 更新招标结果变更，招标结果变更编码不为空（更新条件），其它项，若不论为空，则更新
     * @param record
     * @throws TenderPlanException
     */
	public void updateByPrimaryKeySelective(TenderResultChange record) throws TenderPlanException;
    /**
     * 更新招标结果变更，招标结果变更编码不为空（更新条件），其它项，不论为空，一律更新，如果为空，则置空
     * @param record
     * @throws TenderPlanException
     */
	public void updateByPrimaryKey(TenderResultChange record) throws TenderPlanException;
	/**
	 * 根据招标结果变更编码删除招标结果变更
	 * @param tenderResultIdChangeCode
	 * @return int
	 * @throws TenderPlanException
	 */
	public int deleteByPrimaryKey(String tenderResultIdChangeCode) throws TenderPlanException;
	
	/**
	 * 根据招标结果编码查询没有结束的招标结果变更
	 * @param resultCode
	 * @return
	 * @throws TenderPlanException
	 */
    public TenderResultChange selectNoFinishByResultCode(String resultCode) throws TenderPlanException;
    
    /**
	 * 根据招标结果编码查询已经审批通过的招标结果变更
	 * @param resultCode
	 * @return
	 * @throws TenderPlanException
	 */
    public TenderResultChange selectApprovalByResultCode(String resultCode) throws TenderPlanException;
    
    /**
     * 将招标结果的变更信息更新至划分清单中
     * @param tenderResultIdChangeCode
     * @throws TenderPlanException
     */
    public void updateChangeToSubDivWorkBill(String tenderResultIdChangeCode) throws TenderPlanException;
}
