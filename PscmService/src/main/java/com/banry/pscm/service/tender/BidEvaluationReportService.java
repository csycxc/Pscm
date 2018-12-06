package com.banry.pscm.service.tender;

import java.util.List;
import java.util.Map;

/**
 * 招标结果（评标报告）service
 * @author Administrator
 *
 */
public interface BidEvaluationReportService {

	/**
	 * 根据招标结果编码查找招标结果
	 * @param map
	 * @return BidEvaluationReport
	 * @throws TenderPlanException 
	 */
	public BidEvaluationReport selectByPrimaryKey(Map map) throws TenderPlanException;
	/**
	 * 查询全部招标结果
	 * @return list
	 * @throws TenderPlanException 
	 */
	public List<BidEvaluationReport> findAllReport(String parentTenantAccount) throws TenderPlanException;
	/**
	 * 保存招标结果，不论为空，全部保存。
	 * @param bidEvaluationReport
	 * @throws TenderPlanException 
	 */
	public void saveBidEvaluationReport(BidEvaluationReport bidEvaluationReport) throws TenderPlanException;
	/**
	 * 保存招标结果，如果为空，不保存。
	 * @param bidEvaluationReport
	 * @throws TenderPlanException 
	 */
	public void saveBidEvaluationReportSelective(BidEvaluationReport bidEvaluationReport, List<BidSupplier> supplierList) throws TenderPlanException;
	/**
	 * 更新招标结果（不包含评论小组意见），招标结果编号不为空（更新条件），其它项，不论为空，一律更新，如果为空，则置空。
	 * @param record
	 * @throws TenderPlanException 
	 */
	public void updateByPrimaryKey(BidEvaluationReport record) throws TenderPlanException;
	/**
	 * 更新招标结果（包含评论小组意见），招标结果编号不为空（更新条件），其它项，不论为空，一律更新，如果为空，则置空。
	 * @param record
	 * @throws TenderPlanException 
	 */
	public void updateByPrimaryKeyWithBLOBs(BidEvaluationReport record) throws TenderPlanException;
	/**
	 * 更新招标结果，招标结果编号不为空（更新条件），其它项，若不论为空，则更新。
	 * @param record
	 * @throws TenderPlanException 
	 */
	public void updateByPrimaryKeySelective(BidEvaluationReport record, List<BidSupplier> supplierList) throws TenderPlanException;
	/**
	 * 根据招标结果删除该招标记录
	 * @param bidResultCode
	 * @return int
	 * @throws TenderPlanException 
	 */
	public int deleteByPrimaryKey(String bidResultCode) throws TenderPlanException;
	/**
	 * 条件查找
	 * @param sqlWhere
	 * @return list
	 * @throws TenderPlanException 
	 */
	public List<BidEvaluationReport> findReportBySqlWhere(String sqlWhere) throws TenderPlanException;
	
	/**
	 * 查询已经审批结束
	 * 
	 * @return
	 */
	public List<BidEvaluationReport> findApprovalFinishReport();
	
	/**
	 * 将招标结果编码，中标单位，投标价格更新至划分清单表中
	 * @param bidResultCode
	 * @throws TenderPlanException
	 */
	public void updateBidResultCodeBidSupplierPriceToSubDivWorkBill(String bidResultCode) throws TenderPlanException;
	
}
