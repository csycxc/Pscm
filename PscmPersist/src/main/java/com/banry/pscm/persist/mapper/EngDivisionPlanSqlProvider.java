package com.banry.pscm.persist.mapper;

import com.banry.pscm.service.schedule.*;
import org.apache.ibatis.jdbc.SQL;

public class EngDivisionPlanSqlProvider {

	/**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table eng_division_plan
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public String insertSelective(EngDivisionPlan record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("eng_division_plan");
        
        if (record.getDivisionSnCode() != null) {
            sql.VALUES("division_sn_code", "#{divisionSnCode,jdbcType=VARCHAR}");
        }
        
        if (record.getDivVersion() != null) {
            sql.VALUES("div_version", "#{divVersion,jdbcType=VARCHAR}");
        }
        
        if (record.getStartDate() != null) {
            sql.VALUES("start_date", "#{startDate,jdbcType=DATE}");
        }
        
        if (record.getEndDate() != null) {
            sql.VALUES("end_date", "#{endDate,jdbcType=DATE}");
        }
        
        if (record.getPreJobs() != null) {
            sql.VALUES("pre_jobs", "#{preJobs,jdbcType=VARCHAR}");
        }
        
        if (record.getVersionDate() != null) {
            sql.VALUES("version_date", "#{versionDate,jdbcType=DATE}");
        }
        
        if (record.getVersionManCode() != null) {
            sql.VALUES("version_man_code", "#{versionManCode,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table eng_division_plan
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public String updateByPrimaryKeySelective(EngDivisionPlan record) {
        SQL sql = new SQL();
        sql.UPDATE("eng_division_plan");
        
        if (record.getStartDate() != null) {
            sql.SET("start_date = #{startDate,jdbcType=DATE}");
        }
        
        if (record.getEndDate() != null) {
            sql.SET("end_date = #{endDate,jdbcType=DATE}");
        }
        
        if (record.getPreJobs() != null) {
            sql.SET("pre_jobs = #{preJobs,jdbcType=VARCHAR}");
        }
        
        if (record.getVersionDate() != null) {
            sql.SET("version_date = #{versionDate,jdbcType=DATE}");
        }
        
        if (record.getVersionManCode() != null) {
            sql.SET("version_man_code = #{versionManCode,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("division_sn_code = #{divisionSnCode,jdbcType=VARCHAR}");
        sql.WHERE("div_version = #{divVersion,jdbcType=VARCHAR}");
        
        return sql.toString();
    }
	
	public String selectBySqlWhere(String sqlWhere) {
		SQL sql = new SQL();
		sql.SELECT("a.*");
		sql.FROM("eng_division_plan a");
		sql.WHERE(sqlWhere);
		return sql.toString();
	}
}