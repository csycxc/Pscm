package com.banry.pscm.persist.dao;

import com.banry.pscm.persist.mapper.DownContractSqlProvider;
import com.banry.pscm.service.contract.DownContract;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DownContractMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table down_contract
     *
     * @mbggenerated
     */
    @Delete({
        "delete from down_contract",
        "where down_contract_code = #{downContractCode,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String downContractCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table down_contract
     *
     * @mbggenerated
     */
    @Insert({
        "insert into down_contract (down_contract_code, eng_code, ",
        "biz_type, contract_name, ",
        "contract_part_first, contract_part_second, ",
        "contract_part_third, contract_target_matter, ",
        "contract_amount, expected_sign_date, ",
        "actual_sign_date, fulfil_spot, ",
        "fulfil_date, fulfil_period, ",
        "deposit_amount, break_cost, ",
        "risk_level, contract_payment, ",
        "contract_pay_rate, corporate_assets, ",
        "alarm_date, status, ",
        "comtract_tmpl, contract_attach, ",
        "scan_file_path, tender_result_code, construction_team)",
        "values (#{downContractCode,jdbcType=VARCHAR}, #{engCode,jdbcType=VARCHAR}, ",
        "#{bizType,jdbcType=INTEGER}, #{contractName,jdbcType=VARCHAR}, ",
        "#{contractPartFirst,jdbcType=VARCHAR}, #{contractPartSecond.supplierCreditCode,jdbcType=VARCHAR}, ",
        "#{contractPartThird,jdbcType=VARCHAR}, #{contractTargetMatter,jdbcType=VARCHAR}, ",
        "#{contractAmount,jdbcType=DOUBLE}, #{expectedSignDate,jdbcType=DATE}, ",
        "#{actualSignDate,jdbcType=DATE}, #{fulfilSpot,jdbcType=VARCHAR}, ",
        "#{fulfilDate,jdbcType=DATE}, #{fulfilPeriod,jdbcType=INTEGER}, ",
        "#{depositAmount,jdbcType=DOUBLE}, #{breakCost,jdbcType=VARCHAR}, ",
        "#{riskLevel,jdbcType=VARCHAR}, #{contractPayment,jdbcType=VARCHAR}, ",
        "#{contractPayRate,jdbcType=DOUBLE}, #{corporateAssets,jdbcType=VARCHAR}, ",
        "#{alarmDate,jdbcType=DATE}, #{status,jdbcType=INTEGER}, ",
        "#{comtractTmpl,jdbcType=VARCHAR}, #{contractAttach,jdbcType=VARCHAR}, ",
        "#{scanFilePath,jdbcType=VARCHAR}, #{tenderResultCode,jdbcType=VARCHAR}, #{constructionTeam,jdbcType=VARCHAR})"
    })
    int insert(DownContract record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table down_contract
     *
     * @mbggenerated
     */
    @InsertProvider(type= DownContractSqlProvider.class, method="insertSelective")
    int insertSelective(DownContract record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table down_contract
     *
     * @mbggenerated
     */
    @Select({
        "select",
        "down_contract_code, eng_code, biz_type, contract_name, contract_part_first, ",
        "contract_part_second, contract_part_third, contract_target_matter, contract_amount, ",
        "expected_sign_date, actual_sign_date, fulfil_spot, fulfil_date, fulfil_period, ",
        "deposit_amount, break_cost, risk_level, contract_payment, contract_pay_rate, ",
        "corporate_assets, alarm_date, status, comtract_tmpl, contract_attach, scan_file_path, ",
        "tender_result_code, construction_team",
        "from down_contract",
        "where down_contract_code = #{downContractCode,jdbcType=VARCHAR}"
    })
    @Results(id = "BaseResultMap",value = {
        @Result(column="down_contract_code", property="downContractCode", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="eng_code", property="engCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="biz_type", property="bizType", jdbcType=JdbcType.INTEGER),
        @Result(column="contract_name", property="contractName", jdbcType=JdbcType.VARCHAR),
        @Result(column="contract_part_first", property="contractPartFirst", jdbcType=JdbcType.VARCHAR),
        @Result(column="contract_part_second", property="contractPartSecond", one = @One(
                select = "com.banry.pscm.persist.dao.SupplierMapper.selectByPrimaryKey",
                fetchType = FetchType.EAGER
        )),
        @Result(column="contract_part_third", property="contractPartThird", jdbcType=JdbcType.VARCHAR),
        @Result(column="contract_target_matter", property="contractTargetMatter", jdbcType=JdbcType.VARCHAR),
        @Result(column="contract_amount", property="contractAmount", jdbcType=JdbcType.DOUBLE),
        @Result(column="expected_sign_date", property="expectedSignDate", jdbcType=JdbcType.DATE),
        @Result(column="actual_sign_date", property="actualSignDate", jdbcType=JdbcType.DATE),
        @Result(column="fulfil_spot", property="fulfilSpot", jdbcType=JdbcType.VARCHAR),
        @Result(column="fulfil_date", property="fulfilDate", jdbcType=JdbcType.DATE),
        @Result(column="fulfil_period", property="fulfilPeriod", jdbcType=JdbcType.INTEGER),
        @Result(column="deposit_amount", property="depositAmount", jdbcType=JdbcType.DOUBLE),
        @Result(column="break_cost", property="breakCost", jdbcType=JdbcType.VARCHAR),
        @Result(column="risk_level", property="riskLevel", jdbcType=JdbcType.VARCHAR),
        @Result(column="contract_payment", property="contractPayment", jdbcType=JdbcType.VARCHAR),
        @Result(column="contract_pay_rate", property="contractPayRate", jdbcType=JdbcType.DOUBLE),
        @Result(column="corporate_assets", property="corporateAssets", jdbcType=JdbcType.VARCHAR),
        @Result(column="alarm_date", property="alarmDate", jdbcType=JdbcType.DATE),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="comtract_tmpl", property="comtractTmpl", jdbcType=JdbcType.VARCHAR),
        @Result(column="contract_attach", property="contractAttach", jdbcType=JdbcType.VARCHAR),
        @Result(column="scan_file_path", property="scanFilePath", jdbcType=JdbcType.VARCHAR),
        @Result(column="tender_result_code", property="tenderResultCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="construction_team", property="constructionTeam", jdbcType=JdbcType.VARCHAR)
    })
    DownContract selectByPrimaryKey(String downContractCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table down_contract
     *
     * @mbggenerated
     */
    @UpdateProvider(type=DownContractSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DownContract record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table down_contract
     *
     * @mbggenerated
     */
    @Update({
        "update down_contract",
        "set eng_code = #{engCode,jdbcType=VARCHAR},",
          "biz_type = #{bizType,jdbcType=INTEGER},",
          "contract_name = #{contractName,jdbcType=VARCHAR},",
          "contract_part_first = #{contractPartFirst,jdbcType=VARCHAR},",
          "contract_part_second = #{contractPartSecond.supplierCreditCode,jdbcType=VARCHAR},",
          "contract_part_third = #{contractPartThird,jdbcType=VARCHAR},",
          "contract_target_matter = #{contractTargetMatter,jdbcType=VARCHAR},",
          "contract_amount = #{contractAmount,jdbcType=DOUBLE},",
          "expected_sign_date = #{expectedSignDate,jdbcType=DATE},",
          "actual_sign_date = #{actualSignDate,jdbcType=DATE},",
          "fulfil_spot = #{fulfilSpot,jdbcType=VARCHAR},",
          "fulfil_date = #{fulfilDate,jdbcType=DATE},",
          "fulfil_period = #{fulfilPeriod,jdbcType=INTEGER},",
          "deposit_amount = #{depositAmount,jdbcType=DOUBLE},",
          "break_cost = #{breakCost,jdbcType=VARCHAR},",
          "risk_level = #{riskLevel,jdbcType=VARCHAR},",
          "contract_payment = #{contractPayment,jdbcType=VARCHAR},",
          "contract_pay_rate = #{contractPayRate,jdbcType=DOUBLE},",
          "corporate_assets = #{corporateAssets,jdbcType=VARCHAR},",
          "alarm_date = #{alarmDate,jdbcType=DATE},",
          "status = #{status,jdbcType=INTEGER},",
          "comtract_tmpl = #{comtractTmpl,jdbcType=VARCHAR},",
          "contract_attach = #{contractAttach,jdbcType=VARCHAR},",
          "scan_file_path = #{scanFilePath,jdbcType=VARCHAR},",
          "tender_result_code = #{tenderResultCode,jdbcType=VARCHAR}",
          "construction_team = #{constructionTeam,jdbcType=VARCHAR}",
        "where down_contract_code = #{downContractCode,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(DownContract record);

    @SelectProvider(type = DownContractSqlProvider.class, method = "findDownContracts")
    @ResultMap("BaseResultMap")
    List<DownContract> findDownContracts(@Param("biz_type") Integer bizType,
                                         @Param("status") Integer status,
                                         @Param("contract_name") String contractName);

    @SelectProvider(type = DownContractSqlProvider.class, method = "findContractsBySupplier")
    @ResultMap("BaseResultMap")
    List<DownContract> findContractsBySupplier(String supplierCreditCode);
    
    @Select({
        "select",
        "down_contract_code, eng_code, biz_type, contract_name, contract_part_first, ",
        "contract_part_second, contract_part_third, contract_target_matter, contract_amount, ",
        "expected_sign_date, actual_sign_date, fulfil_spot, fulfil_date, fulfil_period, ",
        "deposit_amount, break_cost, risk_level, contract_payment, contract_pay_rate, ",
        "corporate_assets, alarm_date, status, comtract_tmpl, contract_attach, scan_file_path, ",
        "tender_result_code, construction_team",
        "from down_contract",
        "where tender_result_code = #{tenderResultCode,jdbcType=VARCHAR}",
        "and status <> #{draftStatus,jdbcType=INTEGER} and status <> #{returnStatus,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    DownContract findDownContractByTenderResultCode(@Param("tenderResultCode") String tenderResultCode, @Param("draftStatus") Integer draftStatus, @Param("returnStatus") Integer returnStatus);
}