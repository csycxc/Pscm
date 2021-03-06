package com.banry.pscm.persist.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.banry.pscm.persist.mapper.EngineeringSqlProvider;
import com.banry.pscm.service.schedule.Engineering;

public interface EngineeringMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table engineering
	 * @mbg.generated  Mon Jul 02 14:55:30 CST 2018
	 */
	@Delete({ "delete from engineering", "where eng_code = #{engCode,jdbcType=VARCHAR}" })
	int deleteByPrimaryKey(String engCode);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table engineering
	 * @mbg.generated  Mon Jul 02 14:55:30 CST 2018
	 */
	@Insert({ "insert into engineering (eng_code, tenant_code, ", "eng_type, eng_name, ",
			"eng_full_name, eng_address, ", "contract_price, budget, ", "pay_ration_threshold, status, ",
			"conf_source, eng_survey)", "values (#{engCode,jdbcType=VARCHAR}, #{tenantCode,jdbcType=VARCHAR}, ",
			"#{engType,jdbcType=INTEGER}, #{engName,jdbcType=VARCHAR}, ",
			"#{engFullName,jdbcType=VARCHAR}, #{engAddress,jdbcType=VARCHAR}, ",
			"#{contractPrice,jdbcType=DOUBLE}, #{budget,jdbcType=DOUBLE}, ",
			"#{payRationThreshold,jdbcType=DOUBLE}, #{status,jdbcType=INTEGER}, ",
			"#{confSource,jdbcType=VARCHAR}, #{engSurvey,jdbcType=LONGVARCHAR})" })
	int insert(Engineering record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table engineering
	 * @mbg.generated  Mon Jul 02 14:55:30 CST 2018
	 */
	@InsertProvider(type = EngineeringSqlProvider.class, method = "insertSelective")
	int insertSelective(Engineering record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table engineering
	 * @mbg.generated  Mon Jul 02 14:55:30 CST 2018
	 */
	@Select({ "select", "eng_code, tenant_code, eng_type, eng_name, eng_full_name, eng_address, contract_price, ",
			"budget, pay_ration_threshold, status, conf_source, eng_survey", "from engineering",
			"where eng_code = #{engCode,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "eng_code", property = "engCode", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "tenant_code", property = "tenantCode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "eng_type", property = "engType", jdbcType = JdbcType.INTEGER),
			@Result(column = "eng_name", property = "engName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "eng_full_name", property = "engFullName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "eng_address", property = "engAddress", jdbcType = JdbcType.VARCHAR),
			@Result(column = "contract_price", property = "contractPrice", jdbcType = JdbcType.DOUBLE),
			@Result(column = "budget", property = "budget", jdbcType = JdbcType.DOUBLE),
			@Result(column = "pay_ration_threshold", property = "payRationThreshold", jdbcType = JdbcType.DOUBLE),
			@Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
			@Result(column = "conf_source", property = "confSource", jdbcType = JdbcType.VARCHAR),
			@Result(column = "eng_survey", property = "engSurvey", jdbcType = JdbcType.LONGVARCHAR) })
	Engineering selectByPrimaryKey(String engCode);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table engineering
	 * @mbg.generated  Mon Jul 02 14:55:30 CST 2018
	 */
	@UpdateProvider(type = EngineeringSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(Engineering record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table engineering
	 * @mbg.generated  Mon Jul 02 14:55:30 CST 2018
	 */
	@Update({ "update engineering", "set tenant_code = #{tenantCode,jdbcType=VARCHAR},",
			"eng_type = #{engType,jdbcType=INTEGER},", "eng_name = #{engName,jdbcType=VARCHAR},",
			"eng_full_name = #{engFullName,jdbcType=VARCHAR},", "eng_address = #{engAddress,jdbcType=VARCHAR},",
			"contract_price = #{contractPrice,jdbcType=DOUBLE},", "budget = #{budget,jdbcType=DOUBLE},",
			"pay_ration_threshold = #{payRationThreshold,jdbcType=DOUBLE},", "status = #{status,jdbcType=INTEGER},",
			"conf_source = #{confSource,jdbcType=VARCHAR},", "eng_survey = #{engSurvey,jdbcType=LONGVARCHAR}",
			"where eng_code = #{engCode,jdbcType=VARCHAR}" })
	int updateByPrimaryKeyWithBLOBs(Engineering record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table engineering
	 * @mbg.generated  Mon Jul 02 14:55:30 CST 2018
	 */
	@Update({ "update engineering", "set tenant_code = #{tenantCode,jdbcType=VARCHAR},",
			"eng_type = #{engType,jdbcType=INTEGER},", "eng_name = #{engName,jdbcType=VARCHAR},",
			"eng_full_name = #{engFullName,jdbcType=VARCHAR},", "eng_address = #{engAddress,jdbcType=VARCHAR},",
			"contract_price = #{contractPrice,jdbcType=DOUBLE},", "budget = #{budget,jdbcType=DOUBLE},",
			"pay_ration_threshold = #{payRationThreshold,jdbcType=DOUBLE},", "status = #{status,jdbcType=INTEGER},",
			"conf_source = #{confSource,jdbcType=VARCHAR}", "where eng_code = #{engCode,jdbcType=VARCHAR}" })
	int updateByPrimaryKey(Engineering record);
	
	@Select({ "select", "eng_code, tenant_code, eng_type, eng_name, eng_full_name, eng_address, contract_price, ",
		"budget, pay_ration_threshold, status, conf_source, eng_survey", "from engineering",
		"where tenant_code = #{tenantCode,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "eng_code", property = "engCode", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "tenant_code", property = "tenantCode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "eng_type", property = "engType", jdbcType = JdbcType.INTEGER),
			@Result(column = "eng_name", property = "engName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "eng_full_name", property = "engFullName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "eng_address", property = "engAddress", jdbcType = JdbcType.VARCHAR),
			@Result(column = "contract_price", property = "contractPrice", jdbcType = JdbcType.DOUBLE),
			@Result(column = "budget", property = "budget", jdbcType = JdbcType.DOUBLE),
			@Result(column = "pay_ration_threshold", property = "payRationThreshold", jdbcType = JdbcType.DOUBLE),
			@Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
			@Result(column = "conf_source", property = "confSource", jdbcType = JdbcType.VARCHAR),
			@Result(column = "eng_survey", property = "engSurvey", jdbcType = JdbcType.LONGVARCHAR) })
	Engineering selectEngineeringByTenantCode(String tenantCode);
}