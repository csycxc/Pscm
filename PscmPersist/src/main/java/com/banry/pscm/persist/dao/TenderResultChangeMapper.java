package com.banry.pscm.persist.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import com.banry.pscm.service.tender.TenderResultChange;

public interface TenderResultChangeMapper {
	
	
	@Select({"select c.tender_result_id_change_code,c.change_reason,c.change_date,",
		"r.bid_result_code,r.tender_open_date,r.future_price,r.guide_price,r.tender_open_place,r.evaluate_group_comment,",
		"c.status, e.enum_value_name statusName,",
		"t.tender_plan_code,t.work_item_name,t.plan_supplier_number,t.main_contents,t.requirements,t.scope_workload,",
		"t.biz_type, b.enum_value_name biz_type_name, t.tender_way, w.enum_value_name tender_way_name",
		" from tender_result_change c join bid_evaluation_report r on c.bid_result_code = r.bid_result_code",
		" join tender_plan t on r.tender_plan_code=t.tender_plan_code",
		" left join tenant_${parentTenantAccount}.enum_var e on c.status = e.enum_value and e.enum_name = 'TenderResultChangeStatus' ",
		" left join tenant_${parentTenantAccount}.enum_var b on t.biz_type = b.enum_value and b.enum_name = 'BizType' ",
		" left join tenant_${parentTenantAccount}.enum_var w on t.tender_way = w.enum_value and w.enum_name = 'TenderWay' "})
		@Results({@Result(column = "tender_result_id_change_code", property = "tenderResultIdChangeCode", jdbcType = JdbcType.VARCHAR, id = true),
		@Result(column = "change_reason", property = "changeReason", jdbcType = JdbcType.VARCHAR),
		@Result(column = "change_date", property = "changeDate", jdbcType = JdbcType.DATE),
		@Result(column = "status", property = "status.enumValue", jdbcType = JdbcType.INTEGER),
		@Result(column = "statusName", property = "status.enumValueName", jdbcType = JdbcType.VARCHAR),
		
		@Result(column = "bid_result_code", property = "bidResultCode.bidResultCode", jdbcType = JdbcType.VARCHAR, id = true),
		@Result(column = "tender_open_date", property = "bidResultCode.tenderOpenDate", jdbcType = JdbcType.DATE),
		@Result(column = "future_price", property = "bidResultCode.futurePrice", jdbcType = JdbcType.DOUBLE),
		@Result(column = "guide_price", property = "bidResultCode.guidePrice", jdbcType = JdbcType.DOUBLE),
		@Result(column = "tender_open_place", property = "bidResultCode.tenderOpenPlace", jdbcType = JdbcType.VARCHAR),
		@Result(column = "evaluate_group_comment", property = "bidResultCode.evaluateGroupComment", jdbcType = JdbcType.LONGVARCHAR),
		
		
		@Result(column = "tender_plan_code", property = "bidResultCode.tenderPlanCode.tenderPlanCode", jdbcType = JdbcType.VARCHAR),
		@Result(column = "work_item_name", property = "bidResultCode.tenderPlanCode.workItemName", jdbcType = JdbcType.VARCHAR),
		@Result(column = "biz_type", property = "bidResultCode.tenderPlanCode.bizType.enumValue", jdbcType = JdbcType.INTEGER),
		@Result(column = "biz_type_name", property = "bidResultCode.tenderPlanCode.bizType.enumValueName", jdbcType = JdbcType.VARCHAR),
		@Result(column = "tender_way", property = "bidResultCode.tenderPlanCode.tenderWay.enumValue", jdbcType = JdbcType.INTEGER),
		@Result(column = "tender_way_name", property = "bidResultCode.tenderPlanCode.tenderWay.enumValueName", jdbcType = JdbcType.VARCHAR),
		
		@Result(column = "main_contents", property = "bidResultCode.tenderPlanCode.mainContents", jdbcType = JdbcType.VARCHAR),
		@Result(column = "scope_workload", property = "bidResultCode.tenderPlanCode.scopeWorkload", jdbcType = JdbcType.VARCHAR),
		@Result(column = "requirements", property = "bidResultCode.tenderPlanCode.requirements", jdbcType = JdbcType.VARCHAR),
		@Result(column = "plan_supplier_number", property = "bidResultCode.tenderPlanCode.planSupplierNumber", jdbcType = JdbcType.INTEGER)})
	List<TenderResultChange> findAllChange(@Param("parentTenantAccount") String parentTenantAccount);
	
	@Select({"select c.*",
		" from tender_result_change c",
		" where (c.status <> #{finishStatus,jdbcType=INTEGER} and c.status <> #{returnStatus,jdbcType=INTEGER})",
		" and bid_result_code = #{resultCode,jdbcType=VARCHAR}"})
	@Results({@Result(column = "status", property = "status.enumValue", jdbcType = JdbcType.INTEGER)})
	TenderResultChange selectNoFinishByResultCode(@Param("resultCode") String resultCode, @Param("finishStatus") Integer finishStatus, @Param("returnStatus") Integer returnStatus);
	
	@Select({"select c.*",
		" from tender_result_change c",
		" where c.status = #{finishStatus,jdbcType=INTEGER}",
		" and bid_result_code = #{resultCode,jdbcType=VARCHAR}"})
	@Results({@Result(column = "status", property = "status.enumValue", jdbcType = JdbcType.INTEGER)})
	TenderResultChange selectApprovalByResultCode(@Param("resultCode") String resultCode, @Param("finishStatus") Integer finishStatus);
	
	@Update({"update sub_div_work_bill a ", 
			"	join sub_div_work_tender_result_change_bill b on a.division_sn_code = b.division_sn_code", 
			"	    set a.raw_con_map_quan = (case when b.change_type = 3 or b.change_type = 5 then b.eng_num_new else a.raw_con_map_quan end)", 
			"		, a.contract_unit_price = (case when b.change_type = 4 or b.change_type = 5 then b.unit_price_new else a.contract_unit_price end)", 
			"		, a.supplier_credit_code = (case when b.change_type = 2 then b.change_supplier when b.change_type = 1 then  #{bidSupplier,jdbcType=VARCHAR}", 
			"			else a.supplier_credit_code end)", 
			"	where  b.change_code = #{changeCode,jdbcType=VARCHAR}"})
	public void updateChangeToSubDivWorkBill(@Param("bidSupplier") String bidSupplier, @Param("changeCode") String changeCode);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tender_result_change
     *
     * @mbg.generated Wed May 30 10:40:14 CST 2018
     */
    int deleteByPrimaryKey(String tenderResultIdChangeCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tender_result_change
     *
     * @mbg.generated Wed May 30 10:40:14 CST 2018
     */
    int insert(TenderResultChange record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tender_result_change
     *
     * @mbg.generated Wed May 30 10:40:14 CST 2018
     */
    int insertSelective(TenderResultChange record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tender_result_change
     *
     * @mbg.generated Wed May 30 10:40:14 CST 2018
     */
    TenderResultChange selectByPrimaryKey(Map map);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tender_result_change
     *
     * @mbg.generated Wed May 30 10:40:14 CST 2018
     */
    int updateByPrimaryKeySelective(TenderResultChange record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tender_result_change
     *
     * @mbg.generated Wed May 30 10:40:14 CST 2018
     */
    int updateByPrimaryKey(TenderResultChange record);
}