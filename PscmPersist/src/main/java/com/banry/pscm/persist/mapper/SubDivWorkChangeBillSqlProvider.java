package com.banry.pscm.persist.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.banry.pscm.service.schedule.SubDivWorkChangeBill;

public class SubDivWorkChangeBillSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sub_div_work_change_bill
     *
     * @mbggenerated
     */
    public String insertSelective(SubDivWorkChangeBill record) {
        BEGIN();
        INSERT_INTO("sub_div_work_change_bill");
        
        if (record.getDivisionSnCode() != null) {
            VALUES("division_sn_code", "#{divisionSnCode,jdbcType=VARCHAR}");
        }
        
        if (record.getChangeCode() != null) {
            VALUES("change_code", "#{changeCode,jdbcType=VARCHAR}");
        }
        
        if (record.getChangeType() != null) {
            VALUES("change_type", "#{changeType.code,jdbcType=INTEGER}");
        }
        
        if (record.getSupplierCreditCode() != null) {
            VALUES("supplier_credit_code", "#{supplierCreditCode,jdbcType=VARCHAR}");
        }
        
        if (record.getEngNumBefore() != null) {
            VALUES("eng_num_before", "#{engNumBefore,jdbcType=DOUBLE}");
        }
        
        if (record.getEngNumChange() != null) {
            VALUES("eng_num_change", "#{engNumChange,jdbcType=DOUBLE}");
        }
        
        if (record.getEngNumAfter() != null) {
            VALUES("eng_num_after", "#{engNumAfter,jdbcType=DOUBLE}");
        }
        
        if (record.getUnitPriceBefore() != null) {
            VALUES("unit_price_before", "#{unitPriceBefore,jdbcType=DOUBLE}");
        }
        
        if (record.getUnitPriceChange() != null) {
            VALUES("unit_price_change", "#{unitPriceChange,jdbcType=DOUBLE}");
        }
        
        if (record.getUnitPriceAfter() != null) {
            VALUES("unit_price_after", "#{unitPriceAfter,jdbcType=DOUBLE}");
        }
        
        if (record.getChangedContractCode() != null) {
            VALUES("changed_contract_code", "#{changedContractCode,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sub_div_work_change_bill
     *
     * @mbggenerated
     */
    public String updateByPrimaryKeySelective(SubDivWorkChangeBill record) {
        BEGIN();
        UPDATE("sub_div_work_change_bill");
        
        if (record.getChangeType() != null) {
            SET("change_type = #{changeType.code,jdbcType=INTEGER}");
        }
        
        if (record.getSupplierCreditCode() != null) {
            SET("supplier_credit_code = #{supplierCreditCode,jdbcType=VARCHAR}");
        }
        
        if (record.getEngNumBefore() != null) {
            SET("eng_num_before = #{engNumBefore,jdbcType=DOUBLE}");
        }
        
        if (record.getEngNumChange() != null) {
            SET("eng_num_change = #{engNumChange,jdbcType=DOUBLE}");
        }
        
        if (record.getEngNumAfter() != null) {
            SET("eng_num_after = #{engNumAfter,jdbcType=DOUBLE}");
        }
        
        if (record.getUnitPriceBefore() != null) {
            SET("unit_price_before = #{unitPriceBefore,jdbcType=DOUBLE}");
        }
        
        if (record.getUnitPriceChange() != null) {
            SET("unit_price_change = #{unitPriceChange,jdbcType=DOUBLE}");
        }
        
        if (record.getUnitPriceAfter() != null) {
            SET("unit_price_after = #{unitPriceAfter,jdbcType=DOUBLE}");
        }
        
        if (record.getChangedContractCode() != null) {
            SET("changed_contract_code = #{changedContractCode,jdbcType=VARCHAR}");
        }
        
        WHERE("division_sn_code = #{divisionSnCode,jdbcType=VARCHAR}");
        WHERE("change_code = #{changeCode,jdbcType=VARCHAR}");
        
        return SQL();
    }

}