package com.banry.pscm.persist.dao;

import java.util.List;
import java.util.Map;

import com.banry.pscm.service.tender.SubDivWorkTenderResultChangeBill;
import com.banry.pscm.service.tender.SubDivWorkTenderResultChangeBillKey;

public interface SubDivWorkTenderResultChangeBillMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sub_div_work_tender_result_change_bill
	 * @mbg.generated  Wed Oct 24 09:53:27 CST 2018
	 */
	int deleteByPrimaryKey(SubDivWorkTenderResultChangeBillKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sub_div_work_tender_result_change_bill
	 * @mbg.generated  Wed Oct 24 09:53:27 CST 2018
	 */
	int insert(SubDivWorkTenderResultChangeBill record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sub_div_work_tender_result_change_bill
	 * @mbg.generated  Wed Oct 24 09:53:27 CST 2018
	 */
	int insertSelective(SubDivWorkTenderResultChangeBill record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sub_div_work_tender_result_change_bill
	 * @mbg.generated  Wed Oct 24 09:53:27 CST 2018
	 */
	SubDivWorkTenderResultChangeBill selectByPrimaryKey(SubDivWorkTenderResultChangeBillKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sub_div_work_tender_result_change_bill
	 * @mbg.generated  Wed Oct 24 09:53:27 CST 2018
	 */
	int updateByPrimaryKeySelective(SubDivWorkTenderResultChangeBill record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sub_div_work_tender_result_change_bill
	 * @mbg.generated  Wed Oct 24 09:53:27 CST 2018
	 */
	int updateByPrimaryKey(SubDivWorkTenderResultChangeBill record);
	
	public List<SubDivWorkTenderResultChangeBill> findSubDivWorkTenderResultChangeBillByChangeCode(Map map);
	
	public SubDivWorkTenderResultChangeBill findMoveInResultChangeBillByDivisionSnCode(Map map);
}