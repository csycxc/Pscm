package com.banry.pscm.persist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import com.banry.pscm.service.util.EnumVar;

public interface EnumVarMapper {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enum_var
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    @Insert({
        "insert into enum_var (enum_name, enum_value, ",
        "enum_value_name, remark)",
        "values (#{enumName,jdbcType=VARCHAR}, #{enumValue,jdbcType=INTEGER}, ",
        "#{enumValueName,jdbcType=VARCHAR}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(EnumVar record);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enum_var
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    @Select({
        "select",
        "enum_name, enum_value, enum_value_name, remark",
        "from enum_var",
        "where enum_name = #{enumName,jdbcType=VARCHAR}",
        "and enum_value = #{enumValue,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="enum_name", property="enumName", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="enum_value", property="enumValue", jdbcType=JdbcType.INTEGER),
        @Result(column="enum_value_name", property="enumValueName", jdbcType=JdbcType.VARCHAR),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR)
    })
    EnumVar selectByPrimaryKey(EnumVar record);

    @Select({
    	"select",
        "enum_name, enum_value, enum_value_name, remark",
        "from enum_var",
        "where enum_name = #{enumName,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="enum_name", property="enumName", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="enum_value", property="enumValue", jdbcType=JdbcType.INTEGER),
        @Result(column="enum_value_name", property="enumValueName", jdbcType=JdbcType.VARCHAR),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR)
    })
    List<EnumVar> selectByEnumName(String enumName);
}