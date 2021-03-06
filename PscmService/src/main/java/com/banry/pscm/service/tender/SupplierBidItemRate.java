package com.banry.pscm.service.tender;

import com.banry.pscm.service.schedule.SubDivWorkBill;

public class SupplierBidItemRate {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column supplier_bid_item_rate.item_bid_code
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    private String itemBidCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column supplier_bid_item_rate.division_sn_code
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    private SubDivWorkBill divisionSnCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column supplier_bid_item_rate.supplier_credit_code
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    private String supplierCreditCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column supplier_bid_item_rate.first_bid_unit_rate
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    private Double firstBidUnitRate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column supplier_bid_item_rate.end_bid_unit_rate
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    private Double endBidUnitRate;
    
    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column supplier_bid_item_rate.remark
    *
    * @mbg.generated Sat Sep 08 20:18:37 CST 2018
    */
   private String remark;
    
	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column supplier_bid_item_rate.item_bid_code
     *
     * @return the value of supplier_bid_item_rate.item_bid_code
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public String getItemBidCode() {
        return itemBidCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column supplier_bid_item_rate.item_bid_code
     *
     * @param itemBidCode the value for supplier_bid_item_rate.item_bid_code
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public void setItemBidCode(String itemBidCode) {
        this.itemBidCode = itemBidCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column supplier_bid_item_rate.division_sn_code
     *
     * @return the value of supplier_bid_item_rate.division_sn_code
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public SubDivWorkBill getDivisionSnCode() {
        return divisionSnCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column supplier_bid_item_rate.division_sn_code
     *
     * @param divisionSnCode the value for supplier_bid_item_rate.division_sn_code
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public void setDivisionSnCode(SubDivWorkBill divisionSnCode) {
        this.divisionSnCode = divisionSnCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column supplier_bid_item_rate.supplier_credit_code
     *
     * @return the value of supplier_bid_item_rate.supplier_credit_code
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public String getSupplierCreditCode() {
        return supplierCreditCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column supplier_bid_item_rate.supplier_credit_code
     *
     * @param supplierCreditCode the value for supplier_bid_item_rate.supplier_credit_code
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public void setSupplierCreditCode(String supplierCreditCode) {
        this.supplierCreditCode = supplierCreditCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column supplier_bid_item_rate.first_bid_unit_rate
     *
     * @return the value of supplier_bid_item_rate.first_bid_unit_rate
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public Double getFirstBidUnitRate() {
        return firstBidUnitRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column supplier_bid_item_rate.first_bid_unit_rate
     *
     * @param firstBidUnitRate the value for supplier_bid_item_rate.first_bid_unit_rate
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public void setFirstBidUnitRate(Double firstBidUnitRate) {
        this.firstBidUnitRate = firstBidUnitRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column supplier_bid_item_rate.end_bid_unit_rate
     *
     * @return the value of supplier_bid_item_rate.end_bid_unit_rate
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public Double getEndBidUnitRate() {
        return endBidUnitRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column supplier_bid_item_rate.end_bid_unit_rate
     *
     * @param endBidUnitRate the value for supplier_bid_item_rate.end_bid_unit_rate
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public void setEndBidUnitRate(Double endBidUnitRate) {
        this.endBidUnitRate = endBidUnitRate;
    }
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column supplier_bid_item_rate.remark
     *
     * @return the value of supplier_bid_item_rate.remark
     *
     * @mbg.generated Sat Sep 08 20:18:37 CST 2018
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column supplier_bid_item_rate.remark
     *
     * @param remark the value for supplier_bid_item_rate.remark
     *
     * @mbg.generated Sat Sep 08 20:18:37 CST 2018
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}