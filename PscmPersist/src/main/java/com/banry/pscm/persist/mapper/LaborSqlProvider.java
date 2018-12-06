package com.banry.pscm.persist.mapper;

import com.banry.pscm.service.labour.LaborWithBLOBs;
import org.apache.ibatis.jdbc.SQL;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class LaborSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table labor
     *
     * @mbggenerated
     */
    public String insertSelective(LaborWithBLOBs record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("labor");
        
        if (record.getIdNumber() != null) {
            sql.VALUES("id_number", "#{idNumber,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("name", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getSex() != null) {
            sql.VALUES("sex", "#{sex,jdbcType=VARCHAR}");
        }
        
        if (record.getBirthday() != null) {
            sql.VALUES("birthday", "#{birthday,jdbcType=DATE}");
        }
        
        if (record.getEducationDegree() != null) {
            sql.VALUES("education_degree", "#{educationDegree,jdbcType=INTEGER}");
        }
        
        if (record.getHeight() != null) {
            sql.VALUES("height", "#{height,jdbcType=INTEGER}");
        }
        
        if (record.getWeigh() != null) {
            sql.VALUES("weigh", "#{weigh,jdbcType=INTEGER}");
        }
        
        if (record.getWorkerType() != null) {
            sql.VALUES("worker_type", "#{workerType,jdbcType=VARCHAR}");
        }
        
        if (record.getCareerYear() != null) {
            sql.VALUES("career_year", "#{careerYear,jdbcType=INTEGER}");
        }
        
        if (record.getAddress() != null) {
            sql.VALUES("address", "#{address,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=INTEGER}");
        }
        
        if (record.getIdPhoto() != null) {
            sql.VALUES("id_photo", "#{idPhoto,jdbcType=VARCHAR}");
        }
        
        if (record.getRemark() != null) {
            sql.VALUES("remark", "#{remark,jdbcType=VARCHAR}");
        }
        
        if (record.getTrainedHistory() != null) {
            sql.VALUES("trained_history", "#{trainedHistory,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getProfessQualify() != null) {
            sql.VALUES("profess_qualify", "#{professQualify,jdbcType=LONGVARCHAR}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table labor
     *
     * @mbggenerated
     */
    public String updateByPrimaryKeySelective(LaborWithBLOBs record) {
        SQL sql = new SQL();
        sql.UPDATE("labor");
        
        if (record.getName() != null) {
            sql.SET("name = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getSex() != null) {
            sql.SET("sex = #{sex,jdbcType=VARCHAR}");
        }
        
        if (record.getBirthday() != null) {
            sql.SET("birthday = #{birthday,jdbcType=DATE}");
        }
        
        if (record.getEducationDegree() != null) {
            sql.SET("education_degree = #{educationDegree,jdbcType=INTEGER}");
        }
        
        if (record.getHeight() != null) {
            sql.SET("height = #{height,jdbcType=INTEGER}");
        }
        
        if (record.getWeigh() != null) {
            sql.SET("weigh = #{weigh,jdbcType=INTEGER}");
        }
        
        if (record.getWorkerType() != null) {
            sql.SET("worker_type = #{workerType,jdbcType=VARCHAR}");
        }
        
        if (record.getCareerYear() != null) {
            sql.SET("career_year = #{careerYear,jdbcType=INTEGER}");
        }
        
        if (record.getAddress() != null) {
            sql.SET("address = #{address,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("status = #{status,jdbcType=INTEGER}");
        }
        
        if (record.getIdPhoto() != null) {
            sql.SET("id_photo = #{idPhoto,jdbcType=VARCHAR}");
        }
        
        if (record.getRemark() != null) {
            sql.SET("remark = #{remark,jdbcType=VARCHAR}");
        }
        
        if (record.getTrainedHistory() != null) {
            sql.SET("trained_history = #{trainedHistory,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getProfessQualify() != null) {
            sql.SET("profess_qualify = #{professQualify,jdbcType=LONGVARCHAR}");
        }

        sql.WHERE("id_number = #{idNumber,jdbcType=VARCHAR}");
        
        return sql.toString();
    }
}