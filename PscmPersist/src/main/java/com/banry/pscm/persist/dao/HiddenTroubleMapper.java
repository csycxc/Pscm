package com.banry.pscm.persist.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;

import com.banry.pscm.persist.mapper.HiddenTroubleSqlProvider;
import com.banry.pscm.service.engsafety.HiddenTrouble;
import com.banry.pscm.service.util.EnumVar;

public interface HiddenTroubleMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hidden_trouble
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	@Delete({ "delete from hidden_trouble", "where trouble_code = #{troubleCode,jdbcType=VARCHAR}" })
	int deleteByPrimaryKey(String troubleCode);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hidden_trouble
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	@Insert({ "insert into hidden_trouble (trouble_code, trouble_bill_item_code, ",
			"division_sn_code, trouble_version, ", "start_date, take_place, ", "description, status, ",
			"deduct_date, real_deduct, ", "amercement, rectify_time_limit, ", "rectify_postpone, trouble_attach, ",
			"rectify_steps)", "values (#{troubleCode,jdbcType=VARCHAR}, #{troubleBillItemCode.troubleCode,jdbcType=VARCHAR}, ",
			"#{divisionSnCode,jdbcType=VARCHAR}, #{troubleVersion,jdbcType=VARCHAR}, ",
			"#{startDate,jdbcType=VARCHAR}, #{takePlace,jdbcType=VARCHAR}, ",
			"#{description,jdbcType=VARCHAR}, #{status.enumValue,jdbcType=INTEGER}, ",
			"#{deductDate,jdbcType=DATE}, #{realDeduct,jdbcType=DOUBLE}, ",
			"#{amercement,jdbcType=DOUBLE}, #{rectifyTimeLimit,jdbcType=DATE}, ",
			"#{rectifyPostpone,jdbcType=INTEGER}, #{troubleAttach,jdbcType=VARCHAR}, ",
			"#{rectifyTime,jdbcType=DATE}, #{safetyChargeUser,jdbcType=VARCHAR}, ",
			"#{rectifySteps,jdbcType=LONGVARCHAR})" })
	int insert(HiddenTrouble record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hidden_trouble
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	@InsertProvider(type = HiddenTroubleSqlProvider.class, method = "insertSelective")
	int insertSelective(HiddenTrouble record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hidden_trouble
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	@Select({ "select", "trouble_code, trouble_bill_item_code, division_sn_code, trouble_version, start_date, ",
			"take_place, description, status, deduct_date, real_deduct, amercement, rectify_time_limit, ",
			"rectify_postpone, trouble_attach, rectify_steps, #{parent_tenant_code,jdbcType=VARCHAR} parent_tenant_code",
			"from hidden_trouble",
			"where trouble_code = #{troubleCode,jdbcType=VARCHAR}" })
	@Results(id="BaseHiddenTrouble",value={ @Result(column = "trouble_code", property = "troubleCode", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "{trouble_bill_item_code = trouble_bill_item_code,parent_tenant_code = parent_tenant_code}",property = "troubleBillItemCode", jdbcType = JdbcType.VARCHAR,
				one=@One(
        				select="com.banry.pscm.persist.dao.HiddenTroubleBillMapper.selectHiddenTroubleBillByMap",
        				fetchType=FetchType.EAGER
        				)),
			@Result(column = "division_sn_code", property = "divisionSnCode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "trouble_version", property = "troubleVersion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "start_date", property = "startDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "take_place", property = "takePlace", jdbcType = JdbcType.VARCHAR),
			@Result(column = "description", property = "description", jdbcType = JdbcType.VARCHAR),
			@Result(column = "{status = status,parent_tenant_code = parent_tenant_code}", property = "status", jdbcType = JdbcType.INTEGER,
				one=@One(
    				select="com.banry.pscm.persist.dao.HiddenTroubleMapper.selectHiddenTroubleStatusValue",
    				fetchType=FetchType.EAGER
    				)),
			@Result(column = "deduct_date", property = "deductDate", jdbcType = JdbcType.DATE),
			@Result(column = "real_deduct", property = "realDeduct", jdbcType = JdbcType.DOUBLE),
			@Result(column = "amercement", property = "amercement", jdbcType = JdbcType.DOUBLE),
			@Result(column = "rectify_time_limit", property = "rectifyTimeLimit", jdbcType = JdbcType.DATE),
			@Result(column = "rectify_postpone", property = "rectifyPostpone", jdbcType = JdbcType.INTEGER),
			@Result(column = "trouble_attach", property = "troubleAttach", jdbcType = JdbcType.VARCHAR),
			@Result(column = "rectify_time", property = "rectifyTime", jdbcType = JdbcType.DATE),
			@Result(column = "safety_charge_user", property = "safetyChargeUser", jdbcType = JdbcType.VARCHAR),
			@Result(column = "rectify_steps", property = "rectifySteps", jdbcType = JdbcType.LONGVARCHAR) })
	HiddenTrouble selectByPrimaryKey(@Param("troubleCode") String troubleCode, @Param("parent_tenant_code") String parentTenantCode);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hidden_trouble
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	@UpdateProvider(type = HiddenTroubleSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(HiddenTrouble record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hidden_trouble
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	@Update({ "update hidden_trouble", "set trouble_bill_item_code = #{troubleBillItemCode.troubleCode,jdbcType=VARCHAR},",
			"division_sn_code = #{divisionSnCode,jdbcType=VARCHAR},",
			"trouble_version = #{troubleVersion,jdbcType=VARCHAR},", "start_date = #{startDate,jdbcType=VARCHAR},",
			"take_place = #{takePlace,jdbcType=VARCHAR},", "description = #{description,jdbcType=VARCHAR},",
			"status = #{status.enumValue,jdbcType=INTEGER},", "deduct_date = #{deductDate,jdbcType=DATE},",
			"real_deduct = #{realDeduct,jdbcType=DOUBLE},", "amercement = #{amercement,jdbcType=DOUBLE},",
			"rectify_time_limit = #{rectifyTimeLimit,jdbcType=DATE},",
			"rectify_postpone = #{rectifyPostpone,jdbcType=INTEGER},",
			"trouble_attach = #{troubleAttach,jdbcType=VARCHAR},",
			"rectify_steps = #{rectifySteps,jdbcType=LONGVARCHAR}","rectify_time = #{rectifyTime,jdbcType=DATE},",
			"safety_charge_user = #{safetyChargeUser,jdbcType=VARCHAR}",
			"where trouble_code = #{troubleCode,jdbcType=VARCHAR}" })
	int updateByPrimaryKey(HiddenTrouble record);

	
	@SelectProvider(type = HiddenTroubleSqlProvider.class, method = "selectBySqlWhere")
	@ResultMap("BaseHiddenTrouble")
	List<HiddenTrouble> selectBySqlWhere(String sqlWhere, String parentTenantCode);
	
	@SelectProvider(type = HiddenTroubleSqlProvider.class, method = "selectHiddenTroubleStatusValue")
    @Results({
        @Result(column="enum_name", property="enumName", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="enum_value", property="enumValue", jdbcType=JdbcType.INTEGER),
        @Result(column="enum_value_name", property="enumValueName", jdbcType=JdbcType.VARCHAR),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR)
    })
    EnumVar selectHiddenTroubleStatusValue(Map<String,Object> map);
	
	@Select({ "select", "trouble_code, division_sn_code, trouble_version, start_date, ",
		"take_place, description, deduct_date, real_deduct, amercement, rectify_time_limit, ",
		"rectify_postpone, trouble_attach, rectify_steps, #{parent_tenant_code,jdbcType=VARCHAR} parent_tenant_code",
		"from hidden_trouble",
		"where trouble_code = #{troubleCode,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "trouble_code", property = "troubleCode", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "division_sn_code", property = "divisionSnCode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "trouble_version", property = "troubleVersion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "start_date", property = "startDate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "take_place", property = "takePlace", jdbcType = JdbcType.VARCHAR),
			@Result(column = "description", property = "description", jdbcType = JdbcType.VARCHAR),
			@Result(column = "deduct_date", property = "deductDate", jdbcType = JdbcType.DATE),
			@Result(column = "real_deduct", property = "realDeduct", jdbcType = JdbcType.DOUBLE),
			@Result(column = "amercement", property = "amercement", jdbcType = JdbcType.DOUBLE),
			@Result(column = "rectify_time_limit", property = "rectifyTimeLimit", jdbcType = JdbcType.DATE),
			@Result(column = "rectify_postpone", property = "rectifyPostpone", jdbcType = JdbcType.INTEGER),
			@Result(column = "trouble_attach", property = "troubleAttach", jdbcType = JdbcType.VARCHAR),
			@Result(column = "rectify_time", property = "rectifyTime", jdbcType = JdbcType.DATE),
			@Result(column = "safety_charge_user", property = "safetyChargeUser", jdbcType = JdbcType.VARCHAR),
			@Result(column = "rectify_steps", property = "rectifySteps", jdbcType = JdbcType.LONGVARCHAR) })
	HiddenTrouble selectByKeyWithoutBill(String troubleCode);
}