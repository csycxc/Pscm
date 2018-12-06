package com.banry.pscm.persist.mapper;

import org.apache.ibatis.jdbc.SQL;

import com.banry.pscm.service.schedule.Hazards;

public class HazardsSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hazards
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public String insertSelective(Hazards record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("hazards");
        
        if (record.getHazardsCode() != null) {
            sql.VALUES("hazards_code", "#{hazardsCode,jdbcType=VARCHAR}");
        }
        
        if (record.getDivItemCode() != null) {
            sql.VALUES("div_item_code", "#{divItemCode,jdbcType=VARCHAR}");
        }
        
        if (record.getDivLevel() != null) {
            sql.VALUES("div_level", "#{divLevel,jdbcType=CHAR}");
        }
        
        if (record.getHazardsFactors() != null) {
            sql.VALUES("hazards_factors", "#{hazardsFactors,jdbcType=VARCHAR}");
        }
        
        if (record.getHazardsLevel() != null) {
            sql.VALUES("hazards_level", "#{hazardsLevel,jdbcType=VARCHAR}");
        }
        
        if (record.getAccidents() != null) {
            sql.VALUES("accidents", "#{accidents,jdbcType=VARCHAR}");
        }
        
        if (record.getControlsMeasures() != null) {
            sql.VALUES("controls_measures", "#{controlsMeasures,jdbcType=VARCHAR}");
        }
        
        if (record.getDescription() != null) {
            sql.VALUES("description", "#{description,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hazards
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public String updateByPrimaryKeySelective(Hazards record) {
        SQL sql = new SQL();
        sql.UPDATE("hazards");
        
        if (record.getDivItemCode() != null) {
            sql.SET("div_item_code = #{divItemCode,jdbcType=VARCHAR}");
        }
        
        if (record.getDivLevel() != null) {
            sql.SET("div_level = #{divLevel,jdbcType=CHAR}");
        }
        
        if (record.getHazardsFactors() != null) {
            sql.SET("hazards_factors = #{hazardsFactors,jdbcType=VARCHAR}");
        }
        
        if (record.getHazardsLevel() != null) {
            sql.SET("hazards_level = #{hazardsLevel,jdbcType=VARCHAR}");
        }
        
        if (record.getAccidents() != null) {
            sql.SET("accidents = #{accidents,jdbcType=VARCHAR}");
        }
        
        if (record.getControlsMeasures() != null) {
            sql.SET("controls_measures = #{controlsMeasures,jdbcType=VARCHAR}");
        }
        
        if (record.getDescription() != null) {
            sql.SET("description = #{description,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("hazards_code = #{hazardsCode,jdbcType=VARCHAR}");
        
        return sql.toString();
    }
}