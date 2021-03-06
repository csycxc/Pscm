package com.banry.pscm.persist.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import com.banry.pscm.persist.mapper.TenderPlanSQLBuilder;
import com.banry.pscm.service.tender.BidEvaluationReport;
import com.banry.pscm.service.tender.TenderPlan;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.HashMap;
import java.util.List;

public interface BidEvaluationReportMapper {
	
	@SelectProvider(type = TenderPlanSQLBuilder.class,method = "findReportBySqlWhere")
	List<BidEvaluationReport> findReportBySqlWhere(String sqlWhere);
	@Select({"select r.bid_result_code,r.tender_open_date,r.future_price,r.guide_price,r.tender_open_place,r.evaluate_group_comment,",
		"r.status, e.enum_value_name statusName,",
		"t.tender_plan_code,t.work_item_name,t.plan_supplier_number,t.main_contents,t.requirements,t.scope_workload,",
		"t.biz_type, b.enum_value_name biz_type_name, t.tender_way, w.enum_value_name tender_way_name",
		" from bid_evaluation_report r join tender_plan t on r.tender_plan_code=t.tender_plan_code",
		" left join tenant_${parentTenantAccount}.enum_var e on r.status = e.enum_value and e.enum_name = 'TenderResultStatus' ",
		" left join tenant_${parentTenantAccount}.enum_var b on t.biz_type = b.enum_value and b.enum_name = 'BizType' ",
		" left join tenant_${parentTenantAccount}.enum_var w on t.tender_way = w.enum_value and w.enum_name = 'TenderWay' "})
		@Results({@Result(column = "bid_result_code", property = "bidResultCode", jdbcType = JdbcType.VARCHAR, id = true),
		@Result(column = "tender_open_date", property = "tenderOpenDate", jdbcType = JdbcType.DATE),
		@Result(column = "future_price", property = "futurePrice", jdbcType = JdbcType.DOUBLE),
		@Result(column = "guide_price", property = "guidePrice", jdbcType = JdbcType.DOUBLE),
		@Result(column = "tender_open_place", property = "tenderOpenPlace", jdbcType = JdbcType.VARCHAR),
		@Result(column = "evaluate_group_comment", property = "evaluateGroupComment", jdbcType = JdbcType.LONGVARCHAR),
		@Result(column = "status", property = "status.enumValue", jdbcType = JdbcType.INTEGER),
		@Result(column = "statusName", property = "status.enumValueName", jdbcType = JdbcType.VARCHAR),

		@Result(column = "tender_plan_code", property = "tenderPlanCode.tenderPlanCode", jdbcType = JdbcType.VARCHAR),
		@Result(column = "work_item_name", property = "tenderPlanCode.workItemName", jdbcType = JdbcType.VARCHAR),
		@Result(column = "biz_type", property = "tenderPlanCode.bizType.enumValue", jdbcType = JdbcType.INTEGER),
		@Result(column = "biz_type_name", property = "tenderPlanCode.bizType.enumValueName", jdbcType = JdbcType.VARCHAR),
		@Result(column = "tender_way", property = "tenderPlanCode.tenderWay.enumValue", jdbcType = JdbcType.INTEGER),
		@Result(column = "tender_way_name", property = "tenderPlanCode.tenderWay.enumValueName", jdbcType = JdbcType.VARCHAR),

		@Result(column = "main_contents", property = "tenderPlanCode.mainContents", jdbcType = JdbcType.VARCHAR),
		@Result(column = "scope_workload", property = "tenderPlanCode.scopeWorkload", jdbcType = JdbcType.VARCHAR),
		@Result(column = "requirements", property = "tenderPlanCode.requirements", jdbcType = JdbcType.VARCHAR),
		@Result(column = "plan_supplier_number", property = "tenderPlanCode.planSupplierNumber", jdbcType = JdbcType.INTEGER)})
	public List<BidEvaluationReport> findAllReport(@Param("parentTenantAccount") String parentTenantAccount);

	@SelectProvider(type = TenderPlanSQLBuilder.class,method = "findApprovalFinishReport")
	@Results({@Result(column = "bid_result_code", property = "bidResultCode", jdbcType = JdbcType.VARCHAR, id = true),
	@Result(column = "tender_open_date", property = "tenderOpenDate", jdbcType = JdbcType.DATE),
	@Result(column = "future_price", property = "futurePrice", jdbcType = JdbcType.DOUBLE),
	@Result(column = "guide_price", property = "guidePrice", jdbcType = JdbcType.DOUBLE),
	@Result(column = "tender_open_place", property = "tenderOpenPlace", jdbcType = JdbcType.VARCHAR),
	@Result(column = "evaluate_group_comment", property = "evaluateGroupComment", jdbcType = JdbcType.LONGVARCHAR),

	@Result(column = "tender_plan_code", property = "tenderPlanCode.tenderPlanCode", jdbcType = JdbcType.VARCHAR),
	@Result(column = "work_item_name", property = "tenderPlanCode.workItemName", jdbcType = JdbcType.VARCHAR)})
	public List<BidEvaluationReport> findApprovalFinishReport();
	
	public void updateBidResultCodeBidSupplierPriceToSubDivWorkBill(Map<String, String> map);
	
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bid_evaluation_report
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    int deleteByPrimaryKey(String bidResultCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bid_evaluation_report
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    int insert(BidEvaluationReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bid_evaluation_report
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    int insertSelective(BidEvaluationReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bid_evaluation_report
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    BidEvaluationReport selectByPrimaryKey(Map map);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bid_evaluation_report
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    int updateByPrimaryKeySelective(BidEvaluationReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bid_evaluation_report
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    int updateByPrimaryKeyWithBLOBs(BidEvaluationReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bid_evaluation_report
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    int updateByPrimaryKey(BidEvaluationReport record);
}