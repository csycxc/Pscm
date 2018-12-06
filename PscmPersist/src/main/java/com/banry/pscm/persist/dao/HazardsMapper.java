package com.banry.pscm.persist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.banry.pscm.persist.mapper.HazardsSqlProvider;
import com.banry.pscm.service.schedule.Hazards;

public interface HazardsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hazards
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    @Delete({
        "delete from hazards",
        "where hazards_code = #{hazardsCode,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String hazardsCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hazards
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    @Insert({
        "insert into hazards (hazards_code, div_item_code, ",
        "div_level, hazards_factors, ",
        "hazards_level, accidents, ",
        "controls_measures, description)",
        "values (#{hazardsCode,jdbcType=VARCHAR}, #{divItemCode,jdbcType=VARCHAR}, ",
        "#{divLevel,jdbcType=CHAR}, #{hazardsFactors,jdbcType=VARCHAR}, ",
        "#{hazardsLevel,jdbcType=VARCHAR}, #{accidents,jdbcType=VARCHAR}, ",
        "#{controlsMeasures,jdbcType=VARCHAR}, #{description,jdbcType=VARCHAR})"
    })
    int insert(Hazards record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hazards
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    @InsertProvider(type=HazardsSqlProvider.class, method="insertSelective")
    int insertSelective(Hazards record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hazards
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    @Select({
        "select",
        "hazards_code, div_item_code, div_level, hazards_factors, hazards_level, accidents, ",
        "controls_measures, description",
        "from hazards",
        "where hazards_code = #{hazardsCode,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="hazards_code", property="hazardsCode", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="div_item_code", property="divItemCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="div_level", property="divLevel", jdbcType=JdbcType.CHAR),
        @Result(column="hazards_factors", property="hazardsFactors", jdbcType=JdbcType.VARCHAR),
        @Result(column="hazards_level", property="hazardsLevel", jdbcType=JdbcType.VARCHAR),
        @Result(column="accidents", property="accidents", jdbcType=JdbcType.VARCHAR),
        @Result(column="controls_measures", property="controlsMeasures", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR)
    })
    Hazards selectByPrimaryKey(String hazardsCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hazards
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    @UpdateProvider(type=HazardsSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Hazards record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hazards
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    @Update({
        "update hazards",
        "set div_item_code = #{divItemCode,jdbcType=VARCHAR},",
          "div_level = #{divLevel,jdbcType=CHAR},",
          "hazards_factors = #{hazardsFactors,jdbcType=VARCHAR},",
          "hazards_level = #{hazardsLevel,jdbcType=VARCHAR},",
          "accidents = #{accidents,jdbcType=VARCHAR},",
          "controls_measures = #{controlsMeasures,jdbcType=VARCHAR},",
          "description = #{description,jdbcType=VARCHAR}",
        "where hazards_code = #{hazardsCode,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Hazards record);
    
    /**
	 * @param divItemCode
	 * @return
	 */
	@Select({"select ","hazards_code,div_item_code,div_level,hazards_factors,"
			+ "hazards_level,accidents,controls_measures,description ",
    	"from hazards ","where div_item_code = #{divItemCode,jdbcType=VARCHAR} "})
    @Results(id="BaseHazards",value={
    	@Result(column = "hazards_code", property = "hazardsCode", jdbcType = JdbcType.VARCHAR, id = true),
		@Result(column = "div_item_code", property = "divItemCode", jdbcType = JdbcType.VARCHAR),
		@Result(column = "div_level", property = "divLevel", jdbcType = JdbcType.CHAR),
		@Result(column = "hazards_factors", property = "hazardsFactors", jdbcType = JdbcType.VARCHAR),
		@Result(column = "hazards_level", property = "hazardsLevel", jdbcType = JdbcType.VARCHAR),
		@Result(column = "accidents", property = "accidents", jdbcType = JdbcType.VARCHAR),
		@Result(column = "controls_measures", property = "controlsMeasures", jdbcType = JdbcType.VARCHAR),
		@Result(column = "description", property = "description", jdbcType = JdbcType.VARCHAR) })
	List<Hazards> findHazardssByDivItemCode(String divItemCode);
	
	
	/**
	 * @param divItemCode
	 * @return
	 */
	@Select({"select ","hazards_code,div_item_code,div_level,hazards_factors,"
			+ "hazards_level,accidents,controls_measures,description ",
    	"from hazards ","where div_item_code = " 
    	+ "(select div_item_code from eng_division where division_sn_code = #{divSnCode})"})    
    @ResultMap("BaseHazards")   
	List<Hazards> findHazardssByDivSnCode(String divSnCode);
	

	/**
	 * @param hazardsFactors  危险源及危害因素
	 * @return
	 */
	//@Select("select * from hazards where hazards_factors like concat('%',#{hazardsFactors},'%') ")
	@Select({"select ","hazards_code,div_item_code,div_level,hazards_factors,"
			+ "hazards_level,accidents,controls_measures,description ",
    	"from hazards ","where hazards_factors like concat('%',#{hazardsFactors,jdbcType=VARCHAR},'%') "})
    @ResultMap("BaseHazards")
	List<Hazards> findHazardssByHazardsFactors(String hazardsFactors);

	/**
	 * @return
	 */
	//@Select("select * from hazards ")
	@Select({"select ","hazards_code,div_item_code,div_level,hazards_factors,"
			+ "hazards_level,accidents,controls_measures,description ",
    	"from hazards "})
	@ResultMap("BaseHazards")
	List<Hazards> findAllHazards();
	
	/**
	 * @param divisionsncode
	 * @return
	 */
	@Delete("delete from hazards where div_item_code = "
			+ "(select div_item_code from eng_division where division_sn_code = #{divisionsncode})")
	int deleteHazardsByDivisionSnCode(String divisionsncode);
}