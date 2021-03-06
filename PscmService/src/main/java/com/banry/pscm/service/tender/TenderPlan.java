package com.banry.pscm.service.tender;

import java.sql.Date;

import com.banry.pscm.service.util.EnumVar;

public class TenderPlan {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tender_plan.tender_plan_code
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    private String tenderPlanCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tender_plan.biz_type
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    private EnumVar bizType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tender_plan.work_item_name
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    private String workItemName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tender_plan.tender_plan_date
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    private Date tenderPlanDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tender_plan.latest_marchin_date
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    private Date latestMarchinDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tender_plan.tender_way
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    private EnumVar tenderWay;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tender_plan.plan_supplier_number
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    private Integer planSupplierNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tender_plan.status
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    private EnumVar status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tender_plan.remark
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    private String remark;
    
    /**
     * 任务ID
     */
    private String taskId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tender_plan.tender_plan_code
     *
     * @return the value of tender_plan.tender_plan_code
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public String getTenderPlanCode() {
        return tenderPlanCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tender_plan.tender_plan_code
     *
     * @param tenderPlanCode the value for tender_plan.tender_plan_code
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public void setTenderPlanCode(String tenderPlanCode) {
        this.tenderPlanCode = tenderPlanCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tender_plan.biz_type
     *
     * @return the value of tender_plan.biz_type
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public EnumVar getBizType() {
        return bizType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tender_plan.biz_type
     *
     * @param bizType the value for tender_plan.biz_type
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public void setBizType(EnumVar bizType) {
        this.bizType = bizType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tender_plan.work_item_name
     *
     * @return the value of tender_plan.work_item_name
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public String getWorkItemName() {
        return workItemName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tender_plan.work_item_name
     *
     * @param workItemName the value for tender_plan.work_item_name
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public void setWorkItemName(String workItemName) {
        this.workItemName = workItemName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tender_plan.tender_plan_date
     *
     * @return the value of tender_plan.tender_plan_date
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public Date getTenderPlanDate() {
        return tenderPlanDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tender_plan.tender_plan_date
     *
     * @param tenderPlanDate the value for tender_plan.tender_plan_date
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public void setTenderPlanDate(Date tenderPlanDate) {
        this.tenderPlanDate = tenderPlanDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tender_plan.latest_marchin_date
     *
     * @return the value of tender_plan.latest_marchin_date
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public Date getLatestMarchinDate() {
        return latestMarchinDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tender_plan.latest_marchin_date
     *
     * @param latestMarchinDate the value for tender_plan.latest_marchin_date
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public void setLatestMarchinDate(Date latestMarchinDate) {
        this.latestMarchinDate = latestMarchinDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tender_plan.tender_way
     *
     * @return the value of tender_plan.tender_way
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public EnumVar getTenderWay() {
        return tenderWay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tender_plan.tender_way
     *
     * @param tenderWay the value for tender_plan.tender_way
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public void setTenderWay(EnumVar tenderWay) {
        this.tenderWay = tenderWay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tender_plan.plan_supplier_number
     *
     * @return the value of tender_plan.plan_supplier_number
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public Integer getPlanSupplierNumber() {
        return planSupplierNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tender_plan.plan_supplier_number
     *
     * @param planSupplierNumber the value for tender_plan.plan_supplier_number
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public void setPlanSupplierNumber(Integer planSupplierNumber) {
        this.planSupplierNumber = planSupplierNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tender_plan.status
     *
     * @return the value of tender_plan.status
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public EnumVar getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tender_plan.status
     *
     * @param status the value for tender_plan.status
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public void setStatus(EnumVar status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tender_plan.remark
     *
     * @return the value of tender_plan.remark
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tender_plan.remark
     *
     * @param remark the value for tender_plan.remark
     *
     * @mbg.generated Fri May 25 16:21:28 CST 2018
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

}