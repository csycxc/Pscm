package com.banry.pscm.persist.mapper;

import com.banry.pscm.service.schedule.RoleDivision;
import org.apache.ibatis.jdbc.SQL;

public class RoleDivisionSqlProvider {

	/**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_division
     *
     * @mbg.generated Fri Jul 13 09:45:52 CST 2018
     */
    public String insertSelective(RoleDivision record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("role_division");
        
        if (record.getRoleDivCode() != null) {
            sql.VALUES("role_div_code", "#{roleDivCode,jdbcType=VARCHAR}");
        }
        
        if (record.getRoleCode() != null) {
            sql.VALUES("role_code", "#{roleCode,jdbcType=VARCHAR}");
        }
        
        if (record.getDivCode() != null) {
            sql.VALUES("div_code", "#{divCode,jdbcType=VARCHAR}");
        }
        
        if (record.getTenantCode() != null) {
            sql.VALUES("tenant_code", "#{tenantCode,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_division
     *
     * @mbg.generated Fri Jul 13 09:45:52 CST 2018
     */
    public String updateByPrimaryKeySelective(RoleDivision record) {
        SQL sql = new SQL();
        sql.UPDATE("role_division");
        
        if (record.getRoleCode() != null) {
            sql.SET("role_code = #{roleCode,jdbcType=VARCHAR}");
        }
        
        if (record.getDivCode() != null) {
            sql.SET("div_code = #{divCode,jdbcType=VARCHAR}");
        }
        
        if (record.getTenantCode() != null) {
            sql.SET("tenant_code = #{tenantCode,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("role_div_code = #{roleDivCode,jdbcType=VARCHAR}");
        
        return sql.toString();
    }
    
    /**
     * 查询所有部门
     * @param sqlWhere
     * @return
     */
    public String selectBySqlWhere(String sqlWhere) {
		SQL sql = new SQL();
		sql.SELECT("a.*");
		sql.FROM("role_division a");
		if (sqlWhere != null && !"".equals(sqlWhere)) {
			sql.WHERE(sqlWhere);
		}
		return sql.toString();
	}
}