package com.banry.pscm.persist.mapper;

import com.banry.pscm.service.account.*;
import org.apache.ibatis.jdbc.SQL;

public class SysUsersSqlProvider {

	/**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_users
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public String insertSelective(SysUsers record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("sys_users");
        
        if (record.getUserCode() != null) {
            sql.VALUES("user_code", "#{userCode,jdbcType=VARCHAR}");
        }
        
        if (record.getTenantCode() != null) {
            sql.VALUES("tenant_code", "#{tenantCode,jdbcType=VARCHAR}");
        }
        
        if (record.getUserName() != null) {
            sql.VALUES("user_name", "#{userName,jdbcType=VARCHAR}");
        }
        
        if (record.getUserPassword() != null) {
            sql.VALUES("user_password", "#{userPassword,jdbcType=VARCHAR}");
        }
        
        if (record.getEncryption() != null) {
            sql.VALUES("encryption", "#{encryption,jdbcType=VARCHAR}");
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
        
        if (record.getDeptCode() != null) {
            sql.VALUES("dept_code", "#{deptCode,jdbcType=VARCHAR}");
        }
        
        if (record.getPositionCode() != null) {
            sql.VALUES("position_code", "#{positionCode,jdbcType=VARCHAR}");
        }
        
        if (record.getAlias() != null) {
            sql.VALUES("alias", "#{alias,jdbcType=VARCHAR}");
        }
        
        if (record.getLastLoginTime() != null) {
            sql.VALUES("last_login_time", "#{lastLoginTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getLoginNumber() != null) {
            sql.VALUES("login_number", "#{loginNumber,jdbcType=INTEGER}");
        }
        
        if (record.getDescription() != null) {
            sql.VALUES("description", "#{description,jdbcType=VARCHAR}");
        }
        
        if (record.getTelephone() != null) {
            sql.VALUES("telephone", "#{telephone,jdbcType=VARCHAR}");
        }
        
        if (record.getEmail() != null) {
            sql.VALUES("email", "#{email,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_users
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public String updateByPrimaryKeySelective(SysUsers record) {
        SQL sql = new SQL();
        sql.UPDATE("sys_users");
        
        if (record.getUserName() != null) {
            sql.SET("user_name = #{userName,jdbcType=VARCHAR}");
        }
        
        if (record.getUserPassword() != null) {
            sql.SET("user_password = #{userPassword,jdbcType=VARCHAR}");
        }
        
        if (record.getEncryption() != null) {
            sql.SET("encryption = #{encryption,jdbcType=VARCHAR}");
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
        
        if (record.getDeptCode() != null) {
            sql.SET("dept_code = #{deptCode,jdbcType=VARCHAR}");
        }
        
        if (record.getPositionCode() != null) {
            sql.SET("position_code = #{positionCode,jdbcType=VARCHAR}");
        }
        
        if (record.getAlias() != null) {
            sql.SET("alias = #{alias,jdbcType=VARCHAR}");
        }
        
        if (record.getLastLoginTime() != null) {
            sql.SET("last_login_time = #{lastLoginTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getLoginNumber() != null) {
            sql.SET("login_number = #{loginNumber,jdbcType=INTEGER}");
        }
        
        if (record.getDescription() != null) {
            sql.SET("description = #{description,jdbcType=VARCHAR}");
        }
        
        if (record.getTelephone() != null) {
            sql.SET("telephone = #{telephone,jdbcType=VARCHAR}");
        }
        
        if (record.getEmail() != null) {
            sql.SET("email = #{email,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("user_code = #{userCode,jdbcType=VARCHAR}");
        sql.WHERE("tenant_code = #{tenantCode,jdbcType=VARCHAR}");
        
        return sql.toString();
    }
	
	public String checkEngDivAuthority(String usercode, String divSnCode) {
		String sql = "select checkEngDivAuthority('" + usercode + "', '" + divSnCode + "')";
		return sql;
	}
	
	/**
	 * 依据划分编号查询有此权限安全责任人
	 * @author Xu Dingkui
	 * @date 2018年3月2日
	 * @param divSnCode
	 * @return
	 */
	public String selectSafetyOfficerByDivSnCode(String divSnCode, String parentTenantAccount) {
		String sql = "select b.user_code"
			+ " from role_division a, tenant_" + parentTenantAccount + ".role_user b, tenant_" + parentTenantAccount + ".sys_users c"
			+ " where div_code = getParentDivSnCode('" + divSnCode + "')"
			+ " and a.role_code = b.role_code and c.user_code = b.user_code"
			+ " and a.tenant_code = b.tenant_code and c.tenant_code = b.tenant_code"
			+ "	 and b.role_code='safetyOfficerGroup'";
		return sql;
	}
}