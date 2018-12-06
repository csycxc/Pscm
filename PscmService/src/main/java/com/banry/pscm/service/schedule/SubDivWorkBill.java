package com.banry.pscm.service.schedule;

import com.banry.pscm.service.tender.Supplier;

public class SubDivWorkBill {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sub_div_work_bill.division_sn_code
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    private EngDivision divisionSnCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sub_div_work_bill.name
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sub_div_work_bill.unit
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    private String unit;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sub_div_work_bill.bid_map_quan
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    private Double bidMapQuan;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sub_div_work_bill.raw_con_map_quan
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    private Double rawConMapQuan;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sub_div_work_bill.cons_map_sum_vary_quan
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    private Double consMapSumVaryQuan;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sub_div_work_bill.calculate_unit_price
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    private Double calculateUnitPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sub_div_work_bill.contract_unit_price
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    private Double contractUnitPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sub_div_work_bill.comp_unit_price
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    private Double compUnitPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sub_div_work_bill.temporary_measure_price
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    private Double temporaryMeasurePrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sub_div_work_bill.quota_manual_fee
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    private Double quotaManualFee;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sub_div_work_bill.quota_code
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    private String quotaCode;
    
    /**
     * Database Column status:
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sub_div_work_bill.status
     *
     * @mbg.generated Fri Jun 22 09:44:28 CST 2018
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sub_div_work_bill.tender_plan_code
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    private String tenderPlanCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sub_div_work_bill.tender_result_code
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    private String tenderResultCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sub_div_work_bill.contract_code
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    private String contractCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sub_div_work_bill.charact_des
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    private String charactDes;

    private Supplier supplier;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sub_div_work_bill.division_sn_code
     *
     * @return the value of sub_div_work_bill.division_sn_code
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public EngDivision getDivisionSnCode() {
        return divisionSnCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sub_div_work_bill.division_sn_code
     *
     * @param divisionSnCode the value for sub_div_work_bill.division_sn_code
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public void setDivisionSnCode(EngDivision divisionSnCode) {
        this.divisionSnCode = divisionSnCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sub_div_work_bill.name
     *
     * @return the value of sub_div_work_bill.name
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sub_div_work_bill.name
     *
     * @param name the value for sub_div_work_bill.name
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sub_div_work_bill.unit
     *
     * @return the value of sub_div_work_bill.unit
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public String getUnit() {
        return unit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sub_div_work_bill.unit
     *
     * @param unit the value for sub_div_work_bill.unit
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sub_div_work_bill.bid_map_quan
     *
     * @return the value of sub_div_work_bill.bid_map_quan
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public Double getBidMapQuan() {
        return bidMapQuan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sub_div_work_bill.bid_map_quan
     *
     * @param bidMapQuan the value for sub_div_work_bill.bid_map_quan
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public void setBidMapQuan(Double bidMapQuan) {
        this.bidMapQuan = bidMapQuan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sub_div_work_bill.raw_con_map_quan
     *
     * @return the value of sub_div_work_bill.raw_con_map_quan
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public Double getRawConMapQuan() {
        return rawConMapQuan == null ? 0 : rawConMapQuan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sub_div_work_bill.raw_con_map_quan
     *
     * @param rawConMapQuan the value for sub_div_work_bill.raw_con_map_quan
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public void setRawConMapQuan(Double rawConMapQuan) {
        this.rawConMapQuan = rawConMapQuan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sub_div_work_bill.cons_map_sum_vary_quan
     *
     * @return the value of sub_div_work_bill.cons_map_sum_vary_quan
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public Double getConsMapSumVaryQuan() {
        return consMapSumVaryQuan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sub_div_work_bill.cons_map_sum_vary_quan
     *
     * @param consMapSumVaryQuan the value for sub_div_work_bill.cons_map_sum_vary_quan
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public void setConsMapSumVaryQuan(Double consMapSumVaryQuan) {
        this.consMapSumVaryQuan = consMapSumVaryQuan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sub_div_work_bill.calculate_unit_price
     *
     * @return the value of sub_div_work_bill.calculate_unit_price
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public Double getCalculateUnitPrice() {
        return calculateUnitPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sub_div_work_bill.calculate_unit_price
     *
     * @param calculateUnitPrice the value for sub_div_work_bill.calculate_unit_price
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public void setCalculateUnitPrice(Double calculateUnitPrice) {
        this.calculateUnitPrice = calculateUnitPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sub_div_work_bill.contract_unit_price
     *
     * @return the value of sub_div_work_bill.contract_unit_price
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public Double getContractUnitPrice() {
        return contractUnitPrice == null ? 0 : contractUnitPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sub_div_work_bill.contract_unit_price
     *
     * @param contractUnitPrice the value for sub_div_work_bill.contract_unit_price
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public void setContractUnitPrice(Double contractUnitPrice) {
        this.contractUnitPrice = contractUnitPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sub_div_work_bill.comp_unit_price
     *
     * @return the value of sub_div_work_bill.comp_unit_price
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public Double getCompUnitPrice() {
        return compUnitPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sub_div_work_bill.comp_unit_price
     *
     * @param compUnitPrice the value for sub_div_work_bill.comp_unit_price
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public void setCompUnitPrice(Double compUnitPrice) {
        this.compUnitPrice = compUnitPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sub_div_work_bill.temporary_measure_price
     *
     * @return the value of sub_div_work_bill.temporary_measure_price
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public Double getTemporaryMeasurePrice() {
        return temporaryMeasurePrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sub_div_work_bill.temporary_measure_price
     *
     * @param temporaryMeasurePrice the value for sub_div_work_bill.temporary_measure_price
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public void setTemporaryMeasurePrice(Double temporaryMeasurePrice) {
        this.temporaryMeasurePrice = temporaryMeasurePrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sub_div_work_bill.quota_manual_fee
     *
     * @return the value of sub_div_work_bill.quota_manual_fee
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public Double getQuotaManualFee() {
        return quotaManualFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sub_div_work_bill.quota_manual_fee
     *
     * @param quotaManualFee the value for sub_div_work_bill.quota_manual_fee
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public void setQuotaManualFee(Double quotaManualFee) {
        this.quotaManualFee = quotaManualFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sub_div_work_bill.quota_code
     *
     * @return the value of sub_div_work_bill.quota_code
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public String getQuotaCode() {
        return quotaCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sub_div_work_bill.quota_code
     *
     * @param quotaCode the value for sub_div_work_bill.quota_code
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public void setQuotaCode(String quotaCode) {
        this.quotaCode = quotaCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sub_div_work_bill.tender_plan_code
     *
     * @return the value of sub_div_work_bill.tender_plan_code
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public String getTenderPlanCode() {
        return tenderPlanCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sub_div_work_bill.tender_plan_code
     *
     * @param tenderPlanCode the value for sub_div_work_bill.tender_plan_code
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public void setTenderPlanCode(String tenderPlanCode) {
        this.tenderPlanCode = tenderPlanCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sub_div_work_bill.tender_result_code
     *
     * @return the value of sub_div_work_bill.tender_result_code
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public String getTenderResultCode() {
        return tenderResultCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sub_div_work_bill.tender_result_code
     *
     * @param tenderResultCode the value for sub_div_work_bill.tender_result_code
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public void setTenderResultCode(String tenderResultCode) {
        this.tenderResultCode = tenderResultCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sub_div_work_bill.contract_code
     *
     * @return the value of sub_div_work_bill.contract_code
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public String getContractCode() {
        return contractCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sub_div_work_bill.contract_code
     *
     * @param contractCode the value for sub_div_work_bill.contract_code
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sub_div_work_bill.charact_des
     *
     * @return the value of sub_div_work_bill.charact_des
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public String getCharactDes() {
        return charactDes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sub_div_work_bill.charact_des
     *
     * @param charactDes the value for sub_div_work_bill.charact_des
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public void setCharactDes(String charactDes) {
        this.charactDes = charactDes;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SubDivWorkBill [divisionSnCode=" + divisionSnCode + ", name=" + name + ", unit=" + unit
				+ ", bidMapQuan=" + bidMapQuan + ", rawConMapQuan=" + rawConMapQuan + ", consMapSumVaryQuan="
				+ consMapSumVaryQuan + ", calculateUnitPrice=" + calculateUnitPrice + ", contractUnitPrice="
				+ contractUnitPrice + ", compUnitPrice=" + compUnitPrice + ", temporaryMeasurePrice="
				+ temporaryMeasurePrice + ", quotaManualFee=" + quotaManualFee + ", quotaCode=" + quotaCode
				+ ", status=" + status + ", tenderPlanCode=" + tenderPlanCode + ", tenderResultCode=" + tenderResultCode
				+ ", contractCode=" + contractCode + ", charactDes=" + charactDes + "]";
	}

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}