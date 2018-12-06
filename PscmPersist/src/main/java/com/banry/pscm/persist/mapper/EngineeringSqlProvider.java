package com.banry.pscm.persist.mapper;

import org.apache.ibatis.jdbc.SQL;

import com.banry.pscm.service.schedule.Engineering;

public class EngineeringSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table engineering
	 * @mbg.generated  Mon Jul 02 14:55:30 CST 2018
	 */
	public String insertSelective(Engineering record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("engineering");
		if (record.getEngCode() != null) {
			sql.VALUES("eng_code", "#{engCode,jdbcType=VARCHAR}");
		}
		if (record.getTenantCode() != null) {
			sql.VALUES("tenant_code", "#{tenantCode,jdbcType=VARCHAR}");
		}
		if (record.getEngType() != null) {
			sql.VALUES("eng_type", "#{engType,jdbcType=INTEGER}");
		}
		if (record.getEngName() != null) {
			sql.VALUES("eng_name", "#{engName,jdbcType=VARCHAR}");
		}
		if (record.getEngFullName() != null) {
			sql.VALUES("eng_full_name", "#{engFullName,jdbcType=VARCHAR}");
		}
		if (record.getEngAddress() != null) {
			sql.VALUES("eng_address", "#{engAddress,jdbcType=VARCHAR}");
		}
		if (record.getContractPrice() != null) {
			sql.VALUES("contract_price", "#{contractPrice,jdbcType=DOUBLE}");
		}
		if (record.getBudget() != null) {
			sql.VALUES("budget", "#{budget,jdbcType=DOUBLE}");
		}
		if (record.getPayRationThreshold() != null) {
			sql.VALUES("pay_ration_threshold", "#{payRationThreshold,jdbcType=DOUBLE}");
		}
		if (record.getStatus() != null) {
			sql.VALUES("status", "#{status,jdbcType=INTEGER}");
		}
		if (record.getConfSource() != null) {
			sql.VALUES("conf_source", "#{confSource,jdbcType=VARCHAR}");
		}
		if (record.getEngSurvey() != null) {
			sql.VALUES("eng_survey", "#{engSurvey,jdbcType=LONGVARCHAR}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table engineering
	 * @mbg.generated  Mon Jul 02 14:55:30 CST 2018
	 */
	public String updateByPrimaryKeySelective(Engineering record) {
		SQL sql = new SQL();
		sql.UPDATE("engineering");
		if (record.getTenantCode() != null) {
			sql.SET("tenant_code = #{tenantCode,jdbcType=VARCHAR}");
		}
		if (record.getEngType() != null) {
			sql.SET("eng_type = #{engType,jdbcType=INTEGER}");
		}
		if (record.getEngName() != null) {
			sql.SET("eng_name = #{engName,jdbcType=VARCHAR}");
		}
		if (record.getEngFullName() != null) {
			sql.SET("eng_full_name = #{engFullName,jdbcType=VARCHAR}");
		}
		if (record.getEngAddress() != null) {
			sql.SET("eng_address = #{engAddress,jdbcType=VARCHAR}");
		}
		if (record.getContractPrice() != null) {
			sql.SET("contract_price = #{contractPrice,jdbcType=DOUBLE}");
		}
		if (record.getBudget() != null) {
			sql.SET("budget = #{budget,jdbcType=DOUBLE}");
		}
		if (record.getPayRationThreshold() != null) {
			sql.SET("pay_ration_threshold = #{payRationThreshold,jdbcType=DOUBLE}");
		}
		if (record.getStatus() != null) {
			sql.SET("status = #{status,jdbcType=INTEGER}");
		}
		if (record.getConfSource() != null) {
			sql.SET("conf_source = #{confSource,jdbcType=VARCHAR}");
		}
		if (record.getEngSurvey() != null) {
			sql.SET("eng_survey = #{engSurvey,jdbcType=LONGVARCHAR}");
		}
		sql.WHERE("eng_code = #{engCode,jdbcType=VARCHAR}");
		return sql.toString();
	}
}