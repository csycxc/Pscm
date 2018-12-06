package com.banry.pscm.persist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import com.banry.pscm.service.tender.TenderResultChangeBill;

public interface TenderResultChangeBillMapper {
	
	@Select({"select * from tender_result_change_bill"})
	@Results({@Result(column = "division_sn_code", property = "divisionSnCode", jdbcType = JdbcType.VARCHAR, id = true),
		@Result(column = "tender_result_id_change_code", property = "tenderResultIdChangeCode", jdbcType = JdbcType.VARCHAR),
		@Result(column = "pre_bid_result_code", property = "preBidResultCode", jdbcType = JdbcType.VARCHAR),
		@Result(column = "post_bid_result_code", property = "postBidResultCode", jdbcType = JdbcType.VARCHAR),
		@Result(column = "pre_unit_price", property = "preUnitPrice", jdbcType = JdbcType.VARCHAR),
		@Result(column = "post_unit_price", property = "postUnitPrice", jdbcType = JdbcType.VARCHAR)})
	List<TenderResultChangeBill> findAllChangeBill();
	
	
	@Select({"select * from tender_result_change_bill where tender_result_id_change_code = #{tenderResultIdChangeCode,jdbcType=VARCHAR}"})
	@Results({@Result(column = "division_sn_code", property = "divisionSnCode", jdbcType = JdbcType.VARCHAR, id = true),
		@Result(column = "tender_result_id_change_code", property = "tenderResultIdChangeCode", jdbcType = JdbcType.VARCHAR),
		@Result(column = "pre_bid_result_code", property = "preBidResultCode", jdbcType = JdbcType.VARCHAR),
		@Result(column = "post_bid_result_code", property = "postBidResultCode", jdbcType = JdbcType.VARCHAR),
		@Result(column = "pre_unit_price", property = "preUnitPrice", jdbcType = JdbcType.VARCHAR),
		@Result(column = "post_unit_price", property = "postUnitPrice", jdbcType = JdbcType.VARCHAR)})
	List<TenderResultChangeBill> findChangeBillByChangeCode(String tenderResultIdChangeCode);
	
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tender_result_change_bill
     *
     * @mbg.generated Wed May 30 10:40:14 CST 2018
     */
    int deleteByPrimaryKey(String divisionSnCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tender_result_change_bill
     *
     * @mbg.generated Wed May 30 10:40:14 CST 2018
     */
    int insert(TenderResultChangeBill record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tender_result_change_bill
     *
     * @mbg.generated Wed May 30 10:40:14 CST 2018
     */
    int insertSelective(TenderResultChangeBill record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tender_result_change_bill
     *
     * @mbg.generated Wed May 30 10:40:14 CST 2018
     */
    TenderResultChangeBill selectByPrimaryKey(String divisionSnCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tender_result_change_bill
     *
     * @mbg.generated Wed May 30 10:40:14 CST 2018
     */
    int updateByPrimaryKeySelective(TenderResultChangeBill record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tender_result_change_bill
     *
     * @mbg.generated Wed May 30 10:40:14 CST 2018
     */
    int updateByPrimaryKey(TenderResultChangeBill record);
}