package com.banry.pscm.persist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.banry.pscm.persist.mapper.EngDivisionPlanLinksSqlProvider;
import com.banry.pscm.persist.mapper.EngDivisionSqlProvider;
import com.banry.pscm.service.schedule.EngDivisionPlanLinks;

public interface EngDivisionPlanLinksMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table eng_division_plan_links
	 * @mbg.generated  Wed Jul 05 23:49:08 CST 2017
	 */
	@Delete({ "delete from eng_division_plan_links", "where id = #{id,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table eng_division_plan_links
	 * @mbg.generated  Wed Jul 05 23:49:08 CST 2017
	 */
	@Insert({ "insert into eng_division_plan_links (id, source, ", "target, type, version)",
			"values (#{id,jdbcType=DECIMAL}, #{source,jdbcType=VARCHAR}, ",
			"#{target,jdbcType=VARCHAR}, #{type,jdbcType=VARCHAR}, #{version,jdbcType=VARCHAR})" })
	int insert(EngDivisionPlanLinks record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table eng_division_plan_links
	 * @mbg.generated  Wed Jul 05 23:49:08 CST 2017
	 */
	@InsertProvider(type = EngDivisionPlanLinksSqlProvider.class, method = "insertSelective")
	int insertSelective(EngDivisionPlanLinks record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table eng_division_plan_links
	 * @mbg.generated  Wed Jul 05 23:49:08 CST 2017
	 */
	@Select({ "select", "id, source, target, type, version", "from eng_division_plan_links",
			"where id = #{id,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "id", property = "id", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "source", property = "source", jdbcType = JdbcType.VARCHAR),
			@Result(column = "target", property = "target", jdbcType = JdbcType.VARCHAR),
			@Result(column = "type", property = "type", jdbcType = JdbcType.VARCHAR),
			@Result(column = "version", property = "version", jdbcType = JdbcType.VARCHAR) })
	EngDivisionPlanLinks selectByPrimaryKey(Long id);
	
	
	@SelectProvider(type = EngDivisionPlanLinksSqlProvider.class, method = "selectBySqlWhere")
	List<EngDivisionPlanLinks> selectBySqlWhere(String sqlWhere);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table eng_division_plan_links
	 * @mbg.generated  Wed Jul 05 23:49:08 CST 2017
	 */
	@UpdateProvider(type = EngDivisionPlanLinksSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(EngDivisionPlanLinks record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table eng_division_plan_links
	 * @mbg.generated  Wed Jul 05 23:49:08 CST 2017
	 */
	@Update({ "update eng_division_plan_links", "set source = #{source,jdbcType=VARCHAR},",
			"target = #{target,jdbcType=VARCHAR},", "type = #{type,jdbcType=VARCHAR},",
			"version = #{version,jdbcType=VARCHAR}", "where id = #{id,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(EngDivisionPlanLinks record);
}