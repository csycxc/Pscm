package com.banry.pscm.persist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.banry.pscm.persist.mapper.SysResourceSqlProvider;
import com.banry.pscm.service.account.SysResource;

public interface SysResourceMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_resource
	 * @mbg.generated  Fri Jul 13 22:06:18 CST 2018
	 */
	@Delete({ "delete from sys_resource", "where resource_code = #{resourceCode,jdbcType=VARCHAR}" })
	int deleteByPrimaryKey(String resourceCode);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_resource
	 * @mbg.generated  Fri Jul 13 22:06:18 CST 2018
	 */
	@Insert({ "insert into sys_resource (resource_code, parent_resource_code, ", "tenant_code, resource_name, ",
			"resource_type, resource_url, ", "is_memu, is_dir, status)",
			"values (#{resourceCode,jdbcType=VARCHAR}, #{parentResourceCode,jdbcType=VARCHAR}, ",
			"#{tenantCode,jdbcType=VARCHAR}, #{resourceName,jdbcType=VARCHAR}, ",
			"#{resourceType,jdbcType=VARCHAR}, #{resourceUrl,jdbcType=VARCHAR}, ",
			"#{isMemu,jdbcType=INTEGER}, #{isDir,jdbcType=INTEGER}, #{status,jdbcType=INTEGER})" })
	int insert(SysResource record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_resource
	 * @mbg.generated  Fri Jul 13 22:06:18 CST 2018
	 */
	@InsertProvider(type = SysResourceSqlProvider.class, method = "insertSelective")
	int insertSelective(SysResource record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_resource
	 * @mbg.generated  Fri Jul 13 22:06:18 CST 2018
	 */
	@Select({ "select", "resource_code, parent_resource_code, tenant_code, resource_name, resource_type, ",
			"resource_url, is_memu, is_dir, status", "from sys_resource",
			"where resource_code = #{resourceCode,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "resource_code", property = "resourceCode", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "parent_resource_code", property = "parentResourceCode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "tenant_code", property = "tenantCode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "resource_name", property = "resourceName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "resource_type", property = "resourceType", jdbcType = JdbcType.VARCHAR),
			@Result(column = "resource_url", property = "resourceUrl", jdbcType = JdbcType.VARCHAR),
			@Result(column = "is_memu", property = "isMemu", jdbcType = JdbcType.INTEGER),
			@Result(column = "is_dir", property = "isDir", jdbcType = JdbcType.INTEGER),
			@Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER) })
	SysResource selectByPrimaryKey(String resourceCode);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_resource
	 * @mbg.generated  Fri Jul 13 22:06:18 CST 2018
	 */
	@UpdateProvider(type = SysResourceSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(SysResource record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_resource
	 * @mbg.generated  Fri Jul 13 22:06:18 CST 2018
	 */
	@Update({ "update sys_resource", "set parent_resource_code = #{parentResourceCode,jdbcType=VARCHAR},",
			"tenant_code = #{tenantCode,jdbcType=VARCHAR},", "resource_name = #{resourceName,jdbcType=VARCHAR},",
			"resource_type = #{resourceType,jdbcType=VARCHAR},", "resource_url = #{resourceUrl,jdbcType=VARCHAR},",
			"is_memu = #{isMemu,jdbcType=INTEGER},", "is_dir = #{isDir,jdbcType=INTEGER},",
			"status = #{status,jdbcType=INTEGER}", "where resource_code = #{resourceCode,jdbcType=VARCHAR}" })
	int updateByPrimaryKey(SysResource record);

	@Select({ "select", "resource_code, parent_resource_code, tenant_code, resource_name, resource_type, ",
		"resource_url, is_memu, is_dir, status", "from sys_resource", "where status = 1" })
	@Results({ @Result(column = "resource_code", property = "resourceCode", jdbcType = JdbcType.VARCHAR, id = true),
		@Result(column = "parent_resource_code", property = "parentResourceCode", jdbcType = JdbcType.VARCHAR),
		@Result(column = "tenant_code", property = "tenantCode", jdbcType = JdbcType.VARCHAR),
		@Result(column = "resource_name", property = "resourceName", jdbcType = JdbcType.VARCHAR),
		@Result(column = "resource_type", property = "resourceType", jdbcType = JdbcType.VARCHAR),
		@Result(column = "resource_url", property = "resourceUrl", jdbcType = JdbcType.VARCHAR),
		@Result(column = "is_memu", property = "isMemu", jdbcType = JdbcType.INTEGER),
		@Result(column = "is_dir", property = "isDir", jdbcType = JdbcType.INTEGER),
		@Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER) })
	List<SysResource> selectAll();
	
	
	@SelectProvider(type = SysResourceSqlProvider.class, method = "selectResourceByRoleCode")
	List<SysResource> selectResourceByRoleCode(@Param("roleCode") String roleCode, @Param("tenantCode") String tenantCode);
	
	@SelectProvider(type = SysResourceSqlProvider.class, method = "selectResourceByUserCode")
	List<SysResource> selectResourceByUserCode(@Param("userCode") String userCode, @Param("prCode") String prCode, @Param("menu") String menu, @Param("tenantCode") String tenantCode);
}