package com.banry.pscm.persist.mapper;

import com.banry.pscm.service.account.SysRolesResources;
import org.apache.ibatis.jdbc.SQL;

public class SysRolesResourcesSqlProvider {

	/**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_roles_resources
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public String insertSelective(SysRolesResources record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("sys_roles_resources");
        
        if (record.getRoleResourceCode() != null) {
            sql.VALUES("role_resource_code", "#{roleResourceCode,jdbcType=VARCHAR}");
        }
        
        if (record.getRoleCode() != null) {
            sql.VALUES("role_code", "#{roleCode,jdbcType=VARCHAR}");
        }
        
        if (record.getTenantCode() != null) {
            sql.VALUES("tenant_code", "#{tenantCode,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateUser() != null) {
            sql.VALUES("create_user", "#{createUser,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateDate() != null) {
            sql.VALUES("create_date", "#{createDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=INTEGER}");
        }
        
        if (record.getResourceCode() != null) {
            sql.VALUES("resource_code", "#{resourceCode,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_roles_resources
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public String updateByPrimaryKeySelective(SysRolesResources record) {
        SQL sql = new SQL();
        sql.UPDATE("sys_roles_resources");
        
        if (record.getRoleCode() != null) {
            sql.SET("role_code = #{roleCode,jdbcType=VARCHAR}");
        }
        
        if (record.getTenantCode() != null) {
            sql.SET("tenant_code = #{tenantCode,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateUser() != null) {
            sql.SET("create_user = #{createUser,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateDate() != null) {
            sql.SET("create_date = #{createDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("status = #{status,jdbcType=INTEGER}");
        }
        
        if (record.getResourceCode() != null) {
            sql.SET("resource_code = #{resourceCode,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("role_resource_code = #{roleResourceCode,jdbcType=VARCHAR}");
        
        return sql.toString();
    }
}