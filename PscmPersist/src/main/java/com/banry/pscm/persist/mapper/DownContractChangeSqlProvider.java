package com.banry.pscm.persist.mapper;

import com.banry.pscm.service.contract.DownContractChangeWithBLOBs;
import org.springframework.util.StringUtils;

import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class DownContractChangeSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table down_contract_change
     *
     * @mbggenerated
     */
    public String insertSelective(DownContractChangeWithBLOBs record) {
        BEGIN();
        INSERT_INTO("down_contract_change");

        if (record.getChangeCode() != null) {
            VALUES("change_code", "#{changeCode,jdbcType=VARCHAR}");
        }

        if (record.getContractCode() != null) {
            VALUES("contract_code", "#{contractCode.downContractCode,jdbcType=VARCHAR}");
        }

        if (record.getChangeDate() != null) {
            VALUES("change_date", "#{changeDate,jdbcType=DATE}");
        }

        if (record.getSupplementAmount() != null) {
            VALUES("supplement_amount", "#{supplementAmount,jdbcType=DOUBLE}");
        }

        if (record.getStatus() != null) {
            VALUES("status", "#{status,jdbcType=INTEGER}");
        }

        if (record.getChangeAttach() != null) {
            VALUES("change_attach", "#{changeAttach,jdbcType=VARCHAR}");
        }

        if (record.getChangeContent() != null) {
            VALUES("change_content", "#{changeContent,jdbcType=LONGVARCHAR}");
        }

        if (record.getChangeCause() != null) {
            VALUES("change_cause", "#{changeCause,jdbcType=LONGVARCHAR}");
        }

        if (record.getChangeEvidence() != null) {
            VALUES("change_evidence", "#{changeEvidence,jdbcType=LONGVARCHAR}");
        }

        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table down_contract_change
     *
     * @mbggenerated
     */
    public String updateByPrimaryKeySelective(DownContractChangeWithBLOBs record) {
        BEGIN();
        UPDATE("down_contract_change");

        if (record.getContractCode() != null) {
            SET("contract_code = #{contractCode.downContractCode,jdbcType=VARCHAR}");
        }

        if (record.getChangeDate() != null) {
            SET("change_date = #{changeDate,jdbcType=DATE}");
        }

        if (record.getSupplementAmount() != null) {
            SET("supplement_amount = #{supplementAmount,jdbcType=DOUBLE}");
        }

        if (record.getStatus() != null) {
            SET("status = #{status,jdbcType=INTEGER}");
        }

        if (record.getChangeAttach() != null) {
            SET("change_attach = #{changeAttach,jdbcType=VARCHAR}");
        }

        if (record.getChangeContent() != null) {
            SET("change_content = #{changeContent,jdbcType=LONGVARCHAR}");
        }

        if (record.getChangeCause() != null) {
            SET("change_cause = #{changeCause,jdbcType=LONGVARCHAR}");
        }

        if (record.getChangeEvidence() != null) {
            SET("change_evidence = #{changeEvidence,jdbcType=LONGVARCHAR}");
        }

        WHERE("change_code = #{changeCode,jdbcType=VARCHAR}");

        return SQL();
    }

    public String findContractChanges(Map<String, Object> map) {
        String sql =
                "SELECT" +
                        "	dcc.change_code," +
                        "	dcc.contract_code," +
                        "	dcc.change_date," +
                        "	dcc.supplement_amount," +
                        "	dcc.`status`," +
                        "	dcc.change_attach," +
                        "	dcc.change_content," +
                        "	dcc.change_cause," +
                        "	dcc.change_evidence," +
                        "	dc.contract_name," +
                        "   dc.contract_part_second," +
                        "   dc.contract_amount " +
                        "FROM" +
                        "	down_contract_change dcc" +
                        "	LEFT JOIN down_contract dc ON dcc.contract_code = dc.down_contract_code " +
                        "WHERE 1 = 1";
        if (!StringUtils.isEmpty(map.get("originalContractName"))) {
            sql +=
                    "	AND dc.contract_name LIKE CONCAT('%',#{originalContractName, jdbcType=VARCHAR},'%') ";
        }
        if (!StringUtils.isEmpty(map.get("status"))) {
            sql +=
                    "	AND dcc.`status` = #{status, jdbcType=INTEGER}";
        }
        return sql;
    }
}