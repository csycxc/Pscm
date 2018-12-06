package com.banry.pscm.persist.mapper;

import org.apache.ibatis.jdbc.SQL;

import com.banry.pscm.service.comm.Constants;

public class TenderPlanSQLBuilder {
	
	public String findTenderPlanBySqlWhere(String sqlWhere){
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("tender_plan");
		if (sqlWhere != null && !"".equals(sqlWhere)) {
			sql.WHERE(sqlWhere);
		}
		return sql.toString();
	}
	
	public String findReportBySqlWhere(String sqlWhere){
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("bid_evaluation_report");
		if (sqlWhere != null && !"".equals(sqlWhere)) {
			sql.WHERE(sqlWhere);
		}
		return sql.toString();
	}
	
	public String findBidSupplierBySqlWhere(String sqlWhere){
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("bid_supplier");
		if (sqlWhere != null && !"".equals(sqlWhere)) {
			sql.WHERE(sqlWhere);
		}
		return sql.toString();
	}

	public String findTargetBySqlWhere(String sqlWhere){
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("release_target");
		if (sqlWhere != null && !"".equals(sqlWhere)) {
			sql.WHERE(sqlWhere);
		}
		return sql.toString();
	}
	
	public String findSubBySqlWhere(String sqlWhere){
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("sub_div_work_bill");
		if (sqlWhere != null && !"".equals(sqlWhere)) {
			sql.WHERE(sqlWhere);
		}
		return sql.toString();
	}
	
	public String findRateBySqlWhere(String sqlWhere){
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("supplier_bid_item_rate");
		if (sqlWhere != null && !"".equals(sqlWhere)) {
			sql.WHERE(sqlWhere);
		}
		return sql.toString();
	}
	
	public String findSupplierBySqlWhere(String sqlWhere){
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("supplier");
		if (sqlWhere != null && !"".equals(sqlWhere)) {
			sql.WHERE(sqlWhere);
		}
		return sql.toString();
	}
	
	public String findApprovalBySqlWhere(String sqlWhere){
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("tender_plan_approval");
		if (sqlWhere != null && !"".equals(sqlWhere)) {
			sql.WHERE(sqlWhere);
		}
		return sql.toString();
	}
	
	public String findVarNameBySqlWhere(String sqlWhere){
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("var_name");
		if (sqlWhere != null && !"".equals(sqlWhere)) {
			sql.WHERE(sqlWhere);
		}
		return sql.toString();
	}
	
	public String findEngineeringBySqlWhere(String sqlWhere){
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("engineering");
		if (sqlWhere != null && !"".equals(sqlWhere)) {
			sql.WHERE(sqlWhere);
		}
		return sql.toString();
	}
	
	public String findChangeBillBySqlWhere(String sqlWhere){
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("tender_result_change_bill");
		if (sqlWhere != null && !"".equals(sqlWhere)) {
			sql.WHERE(sqlWhere);
		}
		return sql.toString();
	}
	
	public String findApprovalFinishReport() {
		SQL sql = new SQL();
		sql.SELECT("r.*, t.tender_plan_code, t.work_item_name");
		sql.FROM("bid_evaluation_report r join tender_plan t on r.tender_plan_code=t.tender_plan_code");
		sql.WHERE("r.status = " + Constants.WF_STATUS_FINISH);
		return sql.toString();
	}
}
