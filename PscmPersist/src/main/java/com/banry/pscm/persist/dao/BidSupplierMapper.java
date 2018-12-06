package com.banry.pscm.persist.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import com.banry.pscm.service.tender.BidSupplier;

public interface BidSupplierMapper {
	
	List<BidSupplier> findBidSupplierBySqlWhere(Map sqlWhere);
	
	BidSupplier findBidSupplierByChangeCode(String changeCode);
	
	@Select({"select * from bid_supplier"})
	@Results({@Result(column = "supplier_bid_code", property = "supplierBidCode", jdbcType = JdbcType.VARCHAR, id = true),
		@Result(column = "tender_plan_code", property = "tenderPlanCode", jdbcType = JdbcType.VARCHAR),
		@Result(column = "bid_start_date", property = "bidStartDate", jdbcType = JdbcType.DATE),
		@Result(column = "bid_end_date", property = "bidEndDate", jdbcType = JdbcType.DATE)})
	List<BidSupplier> findAllBidSupplier();
	
	List<BidSupplier> findAllBidSupplierList(String sqlWhere);
	
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bid_spullier
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    int deleteByPrimaryKey(String supplierBidCode);
    
    int deleteByTenderPlanCode(String tenderPlanCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bid_spullier
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    int insert(BidSupplier record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bid_spullier
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    int insertSelective(BidSupplier record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bid_spullier
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    BidSupplier selectByPrimaryKey(String supplierBidCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bid_spullier
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    int updateByPrimaryKeySelective(BidSupplier record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bid_spullier
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    int updateByPrimaryKey(BidSupplier record);
    
    BidSupplier selectBySupplierCreditCodeAndTenderPlanCode(Map map);
    
    BidSupplier findBidSupplierByTenderResultCode(String tenderResultCode);
}