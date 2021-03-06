package com.banry.pscm.persist.mapper;

import com.banry.pscm.service.account.RoleUser;
import org.apache.ibatis.jdbc.SQL;

public class RoleUserSqlProvider {

	/**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_user
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public String insertSelective(RoleUser record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("role_user");
        
        if (record.getRoleUserCode() != null) {
            sql.VALUES("role_user_code", "#{roleUserCode,jdbcType=VARCHAR}");
        }
        
        if (record.getRoleCode() != null) {
            sql.VALUES("role_code", "#{roleCode,jdbcType=VARCHAR}");
        }
        
        if (record.getTenantCode() != null) {
            sql.VALUES("tenant_code", "#{tenantCode,jdbcType=VARCHAR}");
        }
        
        if (record.getUserCode() != null) {
            sql.VALUES("user_code", "#{userCode,jdbcType=VARCHAR}");
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
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_user
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public String updateByPrimaryKeySelective(RoleUser record) {
        SQL sql = new SQL();
        sql.UPDATE("role_user");
        
        if (record.getRoleCode() != null) {
            sql.SET("role_code = #{roleCode,jdbcType=VARCHAR}");
        }
        
        if (record.getTenantCode() != null) {
            sql.SET("tenant_code = #{tenantCode,jdbcType=VARCHAR}");
        }
        
        if (record.getUserCode() != null) {
            sql.SET("user_code = #{userCode,jdbcType=VARCHAR}");
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
        
        sql.WHERE("role_user_code = #{roleUserCode,jdbcType=VARCHAR}");
        
        return sql.toString();
    }
    
    public String selectByRoleAndUserCode(String roleCode, String tenantCode, String userCode) {
    	String sql = "select * from role_user where status = 1 and user_code ='" + userCode + "' and tenant_code='" + tenantCode +  "' and role_code='" + roleCode + "'";
    	return sql;
    }
    
    public String findUsersByRole(String roleCode, String tenantCode) {
    	String sql = "select * from sys_users a where status = 1  and a.tenant_code='" + tenantCode + "' and exists (select 1 from role_user b where a.user_code = b.user_code and a.tenant_code = b.tenant_code and  b.role_code='" + roleCode + "')";
    	return sql;
    }
    
    public String findUsersByRoleWithParentTenantAccount(String roleCode, String tenantCode, String parentTenantAccount) {
    	String sql = "select * from tenant_" + parentTenantAccount + ".sys_users a where status = 1 and tenant_code = '" + tenantCode + "' and exists (select 1 from tenant_" + parentTenantAccount
    			+ ".role_user b where a.user_code = b.user_code and a.tenant_code = b.tenant_code and b.role_code='" + roleCode + "')";
    	return sql;
    }
    
    public String findWaitUsersByRole(String roleCode, String tenantCode) {
    	String sql = "select * from sys_users a where status = 1 and a.tenant_code='" + tenantCode + "' and exists (select 1 from role c where a.dept_code = c.dept_code and c.role_code='" + roleCode + "') "
    			+ " and not exists (select 1 from role_user b where a.user_code = b.user_code and a.tenant_code = b.tenant_code and b.role_code='" + roleCode + "')";
    	return sql;
    }
}