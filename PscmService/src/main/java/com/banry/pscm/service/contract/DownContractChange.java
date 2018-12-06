package com.banry.pscm.service.contract;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DownContractChange {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column down_contract_change.change_code
     *
     * @mbggenerated
     */
    private String changeCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column down_contract_change.contract_code
     *
     * @mbggenerated
     */
    private DownContract contractCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column down_contract_change.change_date
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date changeDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column down_contract_change.supplement_amount
     *
     * @mbggenerated
     */
    private Double supplementAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column down_contract_change.status
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column down_contract_change.change_attach
     *
     * @mbggenerated
     */
    private String changeAttach;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column down_contract_change.change_code
     *
     * @return the value of down_contract_change.change_code
     *
     * @mbggenerated
     */
    public String getChangeCode() {
        return changeCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column down_contract_change.change_code
     *
     * @param changeCode the value for down_contract_change.change_code
     *
     * @mbggenerated
     */
    public void setChangeCode(String changeCode) {
        this.changeCode = changeCode == null ? null : changeCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column down_contract_change.contract_code
     *
     * @return the value of down_contract_change.contract_code
     *
     * @mbggenerated
     */
    public DownContract getContractCode() {
        return contractCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column down_contract_change.contract_code
     *
     * @param contractCode the value for down_contract_change.contract_code
     *
     * @mbggenerated
     */
    public void setContractCode(DownContract contractCode) {
        this.contractCode = contractCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column down_contract_change.change_date
     *
     * @return the value of down_contract_change.change_date
     *
     * @mbggenerated
     */
    public Date getChangeDate() {
        return changeDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column down_contract_change.change_date
     *
     * @param changeDate the value for down_contract_change.change_date
     *
     * @mbggenerated
     */
    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column down_contract_change.supplement_amount
     *
     * @return the value of down_contract_change.supplement_amount
     *
     * @mbggenerated
     */
    public Double getSupplementAmount() {
        return supplementAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column down_contract_change.supplement_amount
     *
     * @param supplementAmount the value for down_contract_change.supplement_amount
     *
     * @mbggenerated
     */
    public void setSupplementAmount(Double supplementAmount) {
        this.supplementAmount = supplementAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column down_contract_change.status
     *
     * @return the value of down_contract_change.status
     *
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column down_contract_change.status
     *
     * @param status the value for down_contract_change.status
     *
     * @mbggenerated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column down_contract_change.change_attach
     *
     * @return the value of down_contract_change.change_attach
     *
     * @mbggenerated
     */
    public String getChangeAttach() {
        return changeAttach;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column down_contract_change.change_attach
     *
     * @param changeAttach the value for down_contract_change.change_attach
     *
     * @mbggenerated
     */
    public void setChangeAttach(String changeAttach) {
        this.changeAttach = changeAttach == null ? null : changeAttach.trim();
    }
}