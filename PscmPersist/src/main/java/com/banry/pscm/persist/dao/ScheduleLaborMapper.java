package com.banry.pscm.persist.dao;

import java.util.List;

import com.banry.pscm.service.schedule.ScheduleLabor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.banry.pscm.persist.mapper.ScheduleLaborSqlProvider;
import com.banry.pscm.service.schedule.LaborKey;

public interface ScheduleLaborMapper {
    /**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table labor
	 * @mbg.generated  Tue Dec 19 22:18:06 CST 2017
	 */
	@Delete({ "delete from labor", "where idcard = #{idcard,jdbcType=VARCHAR}",
			"and DivisionSnCode = #{divisionsncode,jdbcType=VARCHAR}" })
	int deleteByPrimaryKey(LaborKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table labor
	 * @mbg.generated  Tue Dec 19 22:18:06 CST 2017
	 */
	@Insert({ "insert into labor (idcard, DivisionSnCode, ", "labor, laborPost, ", "laborCompany, acceptTime)",
			"values (#{idcard,jdbcType=VARCHAR}, #{divisionsncode,jdbcType=VARCHAR}, ",
			"#{labor,jdbcType=VARCHAR}, #{laborpost,jdbcType=VARCHAR}, ",
			"#{laborcompany,jdbcType=VARCHAR}, #{acceptTime,jdbcType=DATE})" })
	int insert(ScheduleLabor record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table labor
	 * @mbg.generated  Tue Dec 19 22:18:06 CST 2017
	 */
	@InsertProvider(type = ScheduleLaborSqlProvider.class, method = "insertSelective")
	int insertSelective(ScheduleLabor record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table labor
	 * @mbg.generated  Tue Dec 19 22:18:06 CST 2017
	 */
	@Select({ "select", "idcard, DivisionSnCode, labor, laborPost, laborCompany, acceptTime", "from labor",
			"where idcard = #{idcard,jdbcType=VARCHAR}", "and DivisionSnCode = #{divisionsncode,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "idcard", property = "idcard", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "DivisionSnCode", property = "divisionsncode", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "labor", property = "labor", jdbcType = JdbcType.VARCHAR),
			@Result(column = "laborPost", property = "laborpost", jdbcType = JdbcType.VARCHAR),
			@Result(column = "laborCompany", property = "laborcompany", jdbcType = JdbcType.VARCHAR),
			@Result(column = "acceptTime", property = "acceptTime", jdbcType = JdbcType.DATE) })
	ScheduleLabor selectByPrimaryKey(LaborKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table labor
	 * @mbg.generated  Tue Dec 19 22:18:06 CST 2017
	 */
	@UpdateProvider(type = ScheduleLaborSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(ScheduleLabor record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table labor
	 * @mbg.generated  Tue Dec 19 22:18:06 CST 2017
	 */
	@Update({ "update labor", "set labor = #{labor,jdbcType=VARCHAR},", "laborPost = #{laborpost,jdbcType=VARCHAR},",
			"laborCompany = #{laborcompany,jdbcType=VARCHAR},", "acceptTime = #{acceptTime,jdbcType=DATE}",
			"where idcard = #{idcard,jdbcType=VARCHAR}", "and DivisionSnCode = #{divisionsncode,jdbcType=VARCHAR}" })
	int updateByPrimaryKey(ScheduleLabor record);

	@Select({
        "select",
        "max(laborCompany) laborCompany",
        "from labor",
        "where DivisionSnCode = #{divisionsncode,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="laborCompany", property="laborcompany", jdbcType=JdbcType.VARCHAR)
    })
    String selectLaborCompanyByDivSnCode(String divisionsncode);
    
    @Select({
        "select",
        "idcard, DivisionSnCode, labor, laborPost, laborCompany,acceptTime",
        "from labor"
    })
    @Results({
        @Result(column="idcard", property="idcard", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="DivisionSnCode", property="divisionsncode", jdbcType=JdbcType.VARCHAR),
        @Result(column="labor", property="labor", jdbcType=JdbcType.VARCHAR),
        @Result(column="laborPost", property="laborpost", jdbcType=JdbcType.VARCHAR),
        @Result(column="laborCompany", property="laborcompany", jdbcType=JdbcType.VARCHAR),
        @Result(column="acceptTime", property = "acceptTime", jdbcType = JdbcType.DATE)
    })
    List<ScheduleLabor> selectAll();
    
    /*根据划分编号查询负责该划分交底的所有劳工*/
    @Select({
        "select",
        "idcard, DivisionSnCode, labor, laborPost, laborCompany,acceptTime",
        "from labor",
        "where DivisionSnCode = #{divisionsncode,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="idcard", property="idcard", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="DivisionSnCode", property="divisionsncode", jdbcType=JdbcType.VARCHAR),
        @Result(column="labor", property="labor", jdbcType=JdbcType.VARCHAR),
        @Result(column="laborPost", property="laborpost", jdbcType=JdbcType.VARCHAR),
        @Result(column="laborCompany", property="laborcompany", jdbcType=JdbcType.VARCHAR),
        @Result(column="acceptTime", property = "acceptTime", jdbcType = JdbcType.DATE)
    })
    List<ScheduleLabor> findLaborByDivSnCode(String divisionsncode);
    
    @Select({ "select", "idcard, DivisionSnCode, labor, laborPost, laborCompany", "from labor",
		"where idcard = #{idcard,jdbcType=VARCHAR} limit 1" })
	@Results({ @Result(column = "idcard", property = "idcard", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "DivisionSnCode", property = "divisionsncode", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "labor", property = "labor", jdbcType = JdbcType.VARCHAR),
			@Result(column = "laborPost", property = "laborpost", jdbcType = JdbcType.VARCHAR),
			@Result(column = "laborCompany", property = "laborcompany", jdbcType = JdbcType.VARCHAR) })
	ScheduleLabor selectByUserCode(String userCode);
}