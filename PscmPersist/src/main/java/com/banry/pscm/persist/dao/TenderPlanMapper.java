package com.banry.pscm.persist.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.SelectProvider;

import com.banry.pscm.persist.mapper.TenderPlanSQLBuilder;
import com.banry.pscm.service.tender.TenderPlan;
import com.banry.pscm.service.tender.TenderPlanWithBLOBs;

public interface TenderPlanMapper {
	
	@SelectProvider(type = TenderPlanSQLBuilder.class,method = "findTenderPlanBySqlWhere")
	List<TenderPlanWithBLOBs> findTenderPlanBySqlWhere(String sqlWhere);
	
	List<TenderPlanWithBLOBs> findAll(String parentTenantAccount);
	
	List<TenderPlan> findTenderPlanRelease(Integer status);
	
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tender_plan
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    int deleteByPrimaryKey(String tenderPlanCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tender_plan
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    int insert(TenderPlanWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tender_plan
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    int insertSelective(TenderPlanWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tender_plan
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    TenderPlanWithBLOBs selectByPrimaryKey(Map map);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tender_plan
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    int updateByPrimaryKeySelective(TenderPlanWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tender_plan
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    int updateByPrimaryKeyWithBLOBs(TenderPlanWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tender_plan
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    int updateByPrimaryKey(TenderPlan record);
}