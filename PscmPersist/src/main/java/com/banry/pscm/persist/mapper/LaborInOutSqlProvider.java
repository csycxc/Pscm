package com.banry.pscm.persist.mapper;

import com.banry.pscm.service.labour.LaborInOutWithBLOBs;
import org.apache.ibatis.jdbc.SQL;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class LaborInOutSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table labor_in_out
     *
     * @mbggenerated
     */
    public String insertSelective(LaborInOutWithBLOBs record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("labor_in_out");
        
        if (record.getInId() != null) {
           sql.VALUES("in_id", "#{inId,jdbcType=VARCHAR}");
        }
        
        if (record.getIdNumber() != null) {
           sql.VALUES("id_number", "#{idNumber,jdbcType=VARCHAR}");
        }
        
        if (record.getInWorkType() != null) {
           sql.VALUES("in_work_type", "#{inWorkType,jdbcType=INTEGER}");
        }
        
        if (record.getDownContractCode() != null) {
           sql.VALUES("down_contract_code", "#{downContractCode,jdbcType=VARCHAR}");
        }
        
        if (record.getTrainCode() != null) {
           sql.VALUES("train_code", "#{trainCode,jdbcType=VARCHAR}");
        }
        
        if (record.getExamScore() != null) {
           sql.VALUES("exam_score", "#{examScore,jdbcType=DOUBLE}");
        }
        
        if (record.getInDate() != null) {
           sql.VALUES("in_date", "#{inDate,jdbcType=DATE}");
        }
        
        if (record.getOutDate() != null) {
           sql.VALUES("out_date", "#{outDate,jdbcType=DATE}");
        }
        
        if (record.getOutWhy() != null) {
           sql.VALUES("out_why", "#{outWhy,jdbcType=VARCHAR}");
        }
        
        if (record.getReamrk() != null) {
           sql.VALUES("reamrk", "#{reamrk,jdbcType=VARCHAR}");
        }
        
        if (record.getOutPhoto() != null) {
           sql.VALUES("out_photo", "#{outPhoto,jdbcType=VARCHAR}");
        }
        
        if (record.getInIdPhoto() != null) {
           sql.VALUES("in_id_photo", "#{inIdPhoto,jdbcType=VARCHAR}");
        }

        if (record.getWageModel() != null) {
           sql.VALUES("wage_model", "#{wageModel,jdbcType=INTEGER}");
        }

        if (record.getWageUnitPrice() != null) {
           sql.VALUES("wage_unit_price", "#{wageUnitPrice,jdbcType=DOUBLE}");
        }

        if (record.getWageUmint() != null) {
           sql.VALUES("wage_umint", "#{wageUmint,jdbcType=VARCHAR}");
        }

        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table labor_in_out
     *
     * @mbggenerated
     */
    public String updateByPrimaryKeySelective(LaborInOutWithBLOBs record) {
        SQL sql = new SQL();
        sql.UPDATE("labor_in_out");
        
        if (record.getIdNumber() != null) {
            sql.SET("id_number = #{idNumber,jdbcType=VARCHAR}");
        }
        
        if (record.getInWorkType() != null) {
            sql.SET("in_work_type = #{inWorkType,jdbcType=INTEGER}");
        }
        
        if (record.getDownContractCode() != null) {
            sql.SET("down_contract_code = #{downContractCode,jdbcType=VARCHAR}");
        }
        
        if (record.getTrainCode() != null) {
            sql.SET("train_code = #{trainCode,jdbcType=VARCHAR}");
        }
        
        if (record.getExamScore() != null) {
            sql.SET("exam_score = #{examScore,jdbcType=DOUBLE}");
        }
        
        if (record.getInDate() != null) {
            sql.SET("in_date = #{inDate,jdbcType=DATE}");
        }
        
        if (record.getOutDate() != null) {
            sql.SET("out_date = #{outDate,jdbcType=DATE}");
        }
        
        if (record.getOutWhy() != null) {
            sql.SET("out_why = #{outWhy,jdbcType=VARCHAR}");
        }
        
        if (record.getReamrk() != null) {
            sql.SET("reamrk = #{reamrk,jdbcType=VARCHAR}");
        }
        
        if (record.getOutPhoto() != null) {
            sql.SET("out_photo = #{outPhoto,jdbcType=VARCHAR}");
        }
        
        if (record.getInIdPhoto() != null) {
            sql.SET("in_id_photo = #{inIdPhoto,jdbcType=VARCHAR}");
        }

        if (record.getWageModel() != null) {
            sql.SET("wage_model = #{wageModel,jdbcType=INTEGER}");
        }

        if (record.getWageUnitPrice() != null) {
            sql.SET("wage_unit_price = #{wageUnitPrice,jdbcType=DOUBLE}");
        }

        if (record.getWageUmint() != null) {
            sql.SET("wage_umint = #{wageUmint,jdbcType=VARCHAR}");
        }

        sql.WHERE("in_id = #{inId,jdbcType=VARCHAR}");

        return sql.toString();
    }
}