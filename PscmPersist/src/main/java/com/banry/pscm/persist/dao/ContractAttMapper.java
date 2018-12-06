package com.banry.pscm.persist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.banry.pscm.persist.mapper.ContractAttSqlProvider;
import com.banry.pscm.service.util.ContractAtt;

public interface ContractAttMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table contract_att
     *
     * @mbg.generated Wed Jun 13 14:02:24 CST 2018
     */
    @Delete({
        "delete from contract_att",
        "where file_in_name = #{fileInName,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String fileInName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table contract_att
     *
     * @mbg.generated Wed Jun 13 14:02:24 CST 2018
     */
    @Insert({
        "insert into contract_att (file_in_name, type, ",
        "actual_file_name, location)",
        "values (#{fileInName,jdbcType=VARCHAR}, #{type,jdbcType=VARCHAR}, ",
        "#{actualFileName,jdbcType=VARCHAR}, #{location,jdbcType=VARCHAR})"
    })
    int insert(ContractAtt record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table contract_att
     *
     * @mbg.generated Wed Jun 13 14:02:24 CST 2018
     */
    @InsertProvider(type=ContractAttSqlProvider.class, method="insertSelective")
    int insertSelective(ContractAtt record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table contract_att
     *
     * @mbg.generated Wed Jun 13 14:02:24 CST 2018
     */
    @Select({
        "select",
        "file_in_name, type, actual_file_name, location",
        "from contract_att",
        "where file_in_name = #{fileInName,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="file_in_name", property="fileInName", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="actual_file_name", property="actualFileName", jdbcType=JdbcType.VARCHAR),
        @Result(column="location", property="location", jdbcType=JdbcType.VARCHAR)
    })
    ContractAtt selectByPrimaryKey(String fileInName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table contract_att
     *
     * @mbg.generated Wed Jun 13 14:02:24 CST 2018
     */
    @UpdateProvider(type=ContractAttSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ContractAtt record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table contract_att
     *
     * @mbg.generated Wed Jun 13 14:02:24 CST 2018
     */
    @Update({
        "update contract_att",
        "set type = #{type,jdbcType=VARCHAR},",
          "actual_file_name = #{actualFileName,jdbcType=VARCHAR},",
          "location = #{location,jdbcType=VARCHAR}",
        "where file_in_name = #{fileInName,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(ContractAtt record);
    
    @Select({
        "select",
        "file_in_name, type, actual_file_name, location",
        "from contract_att",
        "where file_in_name in ( ${fileInNames})"
    })
    @Results({
        @Result(column="file_in_name", property="fileInName", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="actual_file_name", property="actualFileName", jdbcType=JdbcType.VARCHAR),
        @Result(column="location", property="location", jdbcType=JdbcType.VARCHAR)
    })
    List<ContractAtt> selectByFileInNames(@Param("fileInNames") String fileInNames);
}