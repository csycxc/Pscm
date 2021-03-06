package com.banry.pscm.persist.mapper;

import org.apache.ibatis.jdbc.SQL;

import com.banry.pscm.service.schedule.TechDisclosure;

public class TechDisclosureSqlProvider {

	/**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tech_disclosure
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public String insertSelective(TechDisclosure record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("tech_disclosure");
        
        if (record.getDisclosureCode() != null) {
            sql.VALUES("disclosure_code", "#{disclosureCode,jdbcType=VARCHAR}");
        }
        
        if (record.getDivisionSnCode() != null) {
            sql.VALUES("division_sn_code", "#{divisionSnCode.divisionSnCode}");
        }
        
        if (record.getDisclosurer() != null) {
            sql.VALUES("disclosurer", "#{disclosurer,jdbcType=VARCHAR}");
        }
        
        if (record.getDisRole() != null) {
            sql.VALUES("dis_role", "#{disRole,jdbcType=VARCHAR}");
        }
        
        if (record.getDisDate() != null) {
            sql.VALUES("dis_date", "#{disDate,jdbcType=DATE}");
        }
        
        if (record.getDisLevel() != null) {
            sql.VALUES("dis_level", "#{disLevel,jdbcType=INTEGER}");
        }
        
        if (record.getDisRecipient() != null) {
            sql.VALUES("dis_recipient", "#{disRecipient,jdbcType=VARCHAR}");
        }
        
        if (record.getRecipientRole() != null) {
            sql.VALUES("recipient_role", "#{recipientRole,jdbcType=VARCHAR}");
        }
        
        if (record.getDisInclude() != null) {
            sql.VALUES("dis_include", "#{disInclude,jdbcType=VARCHAR}");
        }
        
        if (record.getDisContent() != null) {
            sql.VALUES("dis_content", "#{disContent,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getDisAttach() != null) {
            sql.VALUES("dis_attach", "#{disAttach,jdbcType=LONGVARCHAR}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tech_disclosure
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public String updateByPrimaryKeySelective(TechDisclosure record) {
        SQL sql = new SQL();
        sql.UPDATE("tech_disclosure");
        
        if (record.getDivisionSnCode() != null) {
            sql.SET("division_sn_code = #{divisionSnCode.divisionSnCode}");
        }
        
        if (record.getDisclosurer() != null) {
            sql.SET("disclosurer = #{disclosurer,jdbcType=VARCHAR}");
        }
        
        if (record.getDisRole() != null) {
            sql.SET("dis_role = #{disRole,jdbcType=VARCHAR}");
        }
        
        if (record.getDisDate() != null) {
            sql.SET("dis_date = #{disDate,jdbcType=DATE}");
        }
        
        if (record.getDisLevel() != null) {
            sql.SET("dis_level = #{disLevel,jdbcType=INTEGER}");
        }
        
        if (record.getDisRecipient() != null) {
            sql.SET("dis_recipient = #{disRecipient,jdbcType=VARCHAR}");
        }
        
        if (record.getRecipientRole() != null) {
            sql.SET("recipient_role = #{recipientRole,jdbcType=VARCHAR}");
        }
        
        if (record.getDisInclude() != null) {
            sql.SET("dis_include = #{disInclude,jdbcType=VARCHAR}");
        }
        
        if (record.getDisContent() != null) {
            sql.SET("dis_content = #{disContent,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getDisAttach() != null) {
            sql.SET("dis_attach = #{disAttach,jdbcType=LONGVARCHAR}");
        }
        
        sql.WHERE("disclosure_code = #{disclosureCode,jdbcType=VARCHAR}");
        
        return sql.toString();
    }
	
	public String findTechDisclosureBySqlWhere(String sqlWhere) {
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("tech_disclosure");
		if (sqlWhere != null && !"".equals(sqlWhere)) {
			sql.WHERE(sqlWhere);
		}
		return sql.toString();
	}
}