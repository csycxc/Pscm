package com.banry.pscm.persist.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.banry.pscm.persist.mapper.EngDivisionSqlProvider;
import com.banry.pscm.service.schedule.EngDivision;
import com.banry.pscm.service.schedule.EngDivisionModel;

public interface EngDivisionMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table eng_division
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	@Delete({ "delete from eng_division", "where division_sn_code = #{divisionSnCode,jdbcType=VARCHAR}" })
	int deleteByPrimaryKey(String divisionSnCode);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table eng_division
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	@Insert({ "insert into eng_division (division_sn_code, eng_code, ", "div_item_code, div_name, ",
			"skill, div_level, ", "parent_div_sn_code, real_start_date, ", "real_end_date, path)",
			"values (#{divisionSnCode,jdbcType=VARCHAR}, #{engCode,jdbcType=VARCHAR}, ",
			"#{divItemCode,jdbcType=VARCHAR}, #{divName,jdbcType=VARCHAR}, ",
			"#{skill,jdbcType=VARCHAR}, #{divLevel,jdbcType=INTEGER}, ",
			"#{parentDivSnCode,jdbcType=VARCHAR}, #{realStartDate,jdbcType=DATE}, ", "#{realEndDate,jdbcType=DATE},#{path,jdbcType=VARCHAR})" })
	int insert(EngDivision record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table eng_division
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	@InsertProvider(type = EngDivisionSqlProvider.class, method = "insertSelective")
	int insertSelective(EngDivision record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table eng_division
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	@Select({ "select", "division_sn_code, eng_code, div_item_code, div_name, skill, div_level, parent_div_sn_code, ",
			"real_start_date, real_end_date, path", "from eng_division",
			"where division_sn_code = #{divisionSnCode,jdbcType=VARCHAR}" })
	@ResultMap("BaseEngDivision")
	EngDivision selectByPrimaryKey(String divisionSnCode);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table eng_division
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	@UpdateProvider(type = EngDivisionSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(EngDivision record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table eng_division
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	@Update({ "update eng_division", "set eng_code = #{engCode,jdbcType=VARCHAR},",
			"div_item_code = #{divItemCode,jdbcType=VARCHAR},", "div_name = #{divName,jdbcType=VARCHAR},",
			"skill = #{skill,jdbcType=VARCHAR},", "div_level = #{divLevel,jdbcType=INTEGER},",
			"parent_div_sn_code = #{parentDivSnCode,jdbcType=VARCHAR},",
			"real_start_date = #{realStartDate,jdbcType=DATE},", "real_end_date = #{realEndDate,jdbcType=DATE},",
			"path = #{path,jdbcType=VARCHAR}",
			"where division_sn_code = #{divisionSnCode,jdbcType=VARCHAR}" })
	int updateByPrimaryKey(EngDivision record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table eng_division
	 * @mbg.generated  Sat Nov 18 20:19:44 CST 2017
	 */
	@Select({ "select", "division_sn_code, eng_code, div_item_code, div_name, skill, div_level, parent_div_sn_code, path", "from eng_division"})
	@ResultMap("BaseEngDivision")
	List<EngDivision> selectAll();

	@SelectProvider(type = EngDivisionSqlProvider.class, method = "selectBySqlWhere")
	List<EngDivision> selectBySqlWhere(String select, String sqlWhere);
	
	@SelectProvider(type = EngDivisionSqlProvider.class, method = "selectBySql")
	List<EngDivision> selectBySql(String sql);
	
	
	@SelectProvider(type = EngDivisionSqlProvider.class, method = "selectTechnologyLeaderFillData")
	List<EngDivisionModel> selectTechnologyLeaderFillData(String userid, String divcode, String pDivSnCode);
	
	@Select({ "select", "distinct div_level", "from eng_division order by div_level desc"})
	@Results({
		@Result(column = "div_level", property = "divLevel", jdbcType = JdbcType.INTEGER) })
	List<Integer> selectDivLevel();
	
	/**
	 * 查询该角色下的划分（只有level为6级别的，没有3，5，7）
	 * @param roleCode
	 * @return
	 */
	@SelectProvider(type = EngDivisionSqlProvider.class, method = "selectEngDivisionByRoleCode")
	List<EngDivision> selectEngDivisionByRoleCode(String roleCode);

	/**
	 * @param divItemCode
	 * @return
	 */
	@Select({"select ","division_sn_code,div_item_code,div_name,skill,div_level,parent_div_sn_code,path ",
    	"from eng_division ","where div_item_code = #{divItemCode,jdbcType=VARCHAR} "})
    @Results(id="BaseEngDivision",value={
    	@Result(column = "division_sn_code", property = "divisionSnCode", jdbcType = JdbcType.VARCHAR, id = true),
		@Result(column = "div_item_code", property = "divItemCode", jdbcType = JdbcType.VARCHAR),
		@Result(column = "div_name", property = "divName", jdbcType = JdbcType.VARCHAR),
		@Result(column = "skill", property = "skill", jdbcType = JdbcType.VARCHAR),
		@Result(column = "div_level", property = "divLevel", jdbcType = JdbcType.CHAR),
		@Result(column = "parent_div_sn_code", property = "parentDivSnCode", jdbcType = JdbcType.VARCHAR),
		@Result(column = "path", property = "path", jdbcType = JdbcType.VARCHAR)
    })
	List<EngDivision> findEngDivisionsByDivItemCode(String divItemCode);
	
	/**
	 * @param divName
	 * @return
	 */
	@Select({"select ","division_sn_code,div_item_code,div_name,skill,div_level,parent_div_sn_code,path ",
    	"from eng_division ","where div_name like concat('%',#{divName,jdbcType=VARCHAR},'%') "})
    @ResultMap("BaseEngDivision")
	List<EngDivision> findEngDivisionsByDivName(String divName);

	/**
	 * @return
	 */
	@Select({"select ","division_sn_code,div_item_code,div_name,skill,div_level,parent_div_sn_code,path ",
    	"from eng_division "})
	@ResultMap("BaseEngDivision")
	List<EngDivision> findAllEngDivision();
	
	/**
	 * @return
	 */
	@Select({"select ","division_sn_code,div_item_code,div_name,skill,div_level,parent_div_sn_code,path ",
    	"from eng_division "})
	 @Results(id="BaseEngDivisionForZTree",value={
		    	@Result(column = "division_sn_code", property = "divisionSnCode"),
		    	@Result(column = "division_sn_code", property = "id"),
				@Result(column = "div_item_code", property = "divItemCode", jdbcType = JdbcType.VARCHAR),
				@Result(column = "div_name", property = "name", jdbcType = JdbcType.VARCHAR),
				@Result(column = "skill", property = "skill", jdbcType = JdbcType.VARCHAR),
				@Result(column = "div_level", property = "divLevel", jdbcType = JdbcType.CHAR),
				@Result(column = "parent_div_sn_code", property = "parentDivSnCode", jdbcType = JdbcType.VARCHAR),
				@Result(column = "parent_div_sn_code", property = "pId"),
				@Result(column = "path", property = "path", jdbcType = JdbcType.VARCHAR)
		    })
	List<HashMap> findAllEngDivisionForZTree();

	/**
	 * @param parentDivisionSnCode
	 * @return
	 */
	@Select({"select ","division_sn_code,div_item_code,div_name,skill,div_level,parent_div_sn_code,path ",
    	"from eng_division ","where parent_div_sn_code = #{parentDivisionSnCode,jdbcType=VARCHAR} "})
	@ResultMap("BaseEngDivision")
	List<EngDivision> findSonEngDivisions(String parentDivisionSnCode);
	
	/**
	 * @param divisionsncode
	 * @return
	 */
	@Select("select count(1) from eng_division where div_item_code = "
			+ "(select div_item_code from eng_division where division_sn_code = #{divisionsncode})")
	int findDivItemCodeCountFromEngDivisionByDivisionSnCode(String divisionsncode);
	
}