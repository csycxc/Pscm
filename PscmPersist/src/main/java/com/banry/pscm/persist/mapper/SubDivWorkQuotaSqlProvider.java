package com.banry.pscm.persist.mapper;

import org.apache.ibatis.jdbc.SQL;

import com.banry.pscm.service.schedule.SubDivWorkQuota;

public class SubDivWorkQuotaSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sub_div_work_quota
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public String insertSelective(SubDivWorkQuota record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("sub_div_work_quota");
        
        if (record.getResCode() != null) {
            sql.VALUES("res_code", "#{resCode,jdbcType=VARCHAR}");
        }
        
        if (record.getDivisionSnCode() != null) {
            sql.VALUES("division_sn_code", "#{divisionSnCode,jdbcType=VARCHAR}");
        }
        
        if (record.getResourcesType() != null) {
            sql.VALUES("resources_type", "#{resourcesType,jdbcType=VARCHAR}");
        }
        
        if (record.getResName() != null) {
            sql.VALUES("res_name", "#{resName,jdbcType=VARCHAR}");
        }
        
        if (record.getResTypeLevel() != null) {
            sql.VALUES("res_type_level", "#{resTypeLevel,jdbcType=VARCHAR}");
        }
        
        if (record.getUnit() != null) {
            sql.VALUES("unit", "#{unit,jdbcType=VARCHAR}");
        }
        
        if (record.getBidMapQuan() != null) {
            sql.VALUES("bid_map_quan", "#{bidMapQuan,jdbcType=DOUBLE}");
        }
        
        if (record.getRawConsMapQuan() != null) {
            sql.VALUES("raw_cons_map_quan", "#{rawConsMapQuan,jdbcType=DOUBLE}");
        }
        
        if (record.getConsMapSumVaryQuan() != null) {
            sql.VALUES("cons_map_sum_vary_quan", "#{consMapSumVaryQuan,jdbcType=DOUBLE}");
        }
        
        if (record.getLossRate() != null) {
            sql.VALUES("loss_rate", "#{lossRate,jdbcType=DOUBLE}");
        }
        
        if (record.getCompUnitPrice() != null) {
            sql.VALUES("comp_unit_price", "#{compUnitPrice,jdbcType=DOUBLE}");
        }
        
        if (record.getSaveExcessRate() != null) {
            sql.VALUES("save_excess_rate", "#{saveExcessRate,jdbcType=DOUBLE}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sub_div_work_quota
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public String updateByPrimaryKeySelective(SubDivWorkQuota record) {
        SQL sql = new SQL();
        sql.UPDATE("sub_div_work_quota");
        
        if (record.getDivisionSnCode() != null) {
            sql.SET("division_sn_code = #{divisionSnCode,jdbcType=VARCHAR}");
        }
        
        if (record.getResourcesType() != null) {
            sql.SET("resources_type = #{resourcesType,jdbcType=VARCHAR}");
        }
        
        if (record.getResName() != null) {
            sql.SET("res_name = #{resName,jdbcType=VARCHAR}");
        }
        
        if (record.getResTypeLevel() != null) {
            sql.SET("res_type_level = #{resTypeLevel,jdbcType=VARCHAR}");
        }
        
        if (record.getUnit() != null) {
            sql.SET("unit = #{unit,jdbcType=VARCHAR}");
        }
        
        if (record.getBidMapQuan() != null) {
            sql.SET("bid_map_quan = #{bidMapQuan,jdbcType=DOUBLE}");
        }
        
        if (record.getRawConsMapQuan() != null) {
            sql.SET("raw_cons_map_quan = #{rawConsMapQuan,jdbcType=DOUBLE}");
        }
        
        if (record.getConsMapSumVaryQuan() != null) {
            sql.SET("cons_map_sum_vary_quan = #{consMapSumVaryQuan,jdbcType=DOUBLE}");
        }
        
        if (record.getLossRate() != null) {
            sql.SET("loss_rate = #{lossRate,jdbcType=DOUBLE}");
        }
        
        if (record.getCompUnitPrice() != null) {
            sql.SET("comp_unit_price = #{compUnitPrice,jdbcType=DOUBLE}");
        }
        
        if (record.getSaveExcessRate() != null) {
            sql.SET("save_excess_rate = #{saveExcessRate,jdbcType=DOUBLE}");
        }
        
        sql.WHERE("res_code = #{resCode,jdbcType=VARCHAR}");
        
        return sql.toString();
    }
}