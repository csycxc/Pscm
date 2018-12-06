package com.banry.pscm.persist.dao;

import com.banry.pscm.service.contract.ContractTmpl;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractTmplMapper {
    @Delete({
        "delete from contract_tmpl",
        "where contract_tmpl_code = #{contractTmplCode,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String contractTmplCode);

    @Insert({
        "insert into contract_tmpl (contract_tmpl_code, biz_type, ",
        "contract_tmpl_name, source, ",
        "version, upload_by_ownerid, ",
        "upload_date, attachement)",
        "values (#{contractTmplCode,jdbcType=VARCHAR}, #{bizType,jdbcType=INTEGER}, ",
        "#{contractTmplName,jdbcType=VARCHAR}, #{source,jdbcType=VARCHAR}, ",
        "#{version,jdbcType=VARCHAR}, #{uploadByOwnerid,jdbcType=VARCHAR}, ",
        "#{uploadDate,jdbcType=DATE}, #{attachement,jdbcType=VARCHAR})"
    })
    int insert(ContractTmpl record);

    @Select({
        "select",
        "contract_tmpl_code, biz_type, contract_tmpl_name, source, version, upload_by_ownerid, ",
        "upload_date, attachement",
        "from contract_tmpl",
        "where contract_tmpl_code = #{contractTmplCode,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="contract_tmpl_code", property="contractTmplCode", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="biz_type", property="bizType", jdbcType=JdbcType.INTEGER),
        @Result(column="contract_tmpl_name", property="contractTmplName", jdbcType=JdbcType.VARCHAR),
        @Result(column="source", property="source", jdbcType=JdbcType.VARCHAR),
        @Result(column="version", property="version", jdbcType=JdbcType.VARCHAR),
        @Result(column="upload_by_ownerid", property="uploadByOwnerid", jdbcType=JdbcType.VARCHAR),
        @Result(column="upload_date", property="uploadDate", jdbcType=JdbcType.DATE),
        @Result(column="attachement", property="attachement", jdbcType=JdbcType.VARCHAR)
    })
    ContractTmpl selectByPrimaryKey(String contractTmplCode);

    @Select({
        "select",
        "contract_tmpl_code, biz_type, contract_tmpl_name, source, version, upload_by_ownerid, ",
        "upload_date, attachement",
        "from contract_tmpl"
    })
    @Results({
        @Result(column="contract_tmpl_code", property="contractTmplCode", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="biz_type", property="bizType", jdbcType=JdbcType.INTEGER),
        @Result(column="contract_tmpl_name", property="contractTmplName", jdbcType=JdbcType.VARCHAR),
        @Result(column="source", property="source", jdbcType=JdbcType.VARCHAR),
        @Result(column="version", property="version", jdbcType=JdbcType.VARCHAR),
        @Result(column="upload_by_ownerid", property="uploadByOwnerid", jdbcType=JdbcType.VARCHAR),
        @Result(column="upload_date", property="uploadDate", jdbcType=JdbcType.DATE),
        @Result(column="attachement", property="attachement", jdbcType=JdbcType.VARCHAR)
    })
    List<ContractTmpl> selectAll();

    @Update({
        "update contract_tmpl",
        "set biz_type = #{bizType,jdbcType=INTEGER},",
          "contract_tmpl_name = #{contractTmplName,jdbcType=VARCHAR},",
          "source = #{source,jdbcType=VARCHAR},",
          "version = #{version,jdbcType=VARCHAR},",
          "upload_by_ownerid = #{uploadByOwnerid,jdbcType=VARCHAR},",
          "upload_date = #{uploadDate,jdbcType=DATE},",
          "attachement = #{attachement,jdbcType=VARCHAR}",
        "where contract_tmpl_code = #{contractTmplCode,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(ContractTmpl record);
}