package com.banry.pscm.service.schedule;


public class EngDivisionModel {

	private String divisionsncode;		//划分编码
	private String divitemcode;	//划分项
	private String divname;	//划分名称
	private String divlevel;	//划分级别
	private String startDate; //计划开工日期
	private String endDate;	//计划完工日期
	private Double planToady;//计划天数
	private String actualStartDate;//实际开工日期
	private String actualEndDate;//实际完工日期
	private String actualConstDays;//实际施工天数
	private Double completedQuantity;//累计完成数量
	private Double consSchemeQuantity; //施工方案工程总量
	private Double remainingQuantity;//剩余数量
	private Double designQuantity; //工程设计数量
	/**
	 * @return the divisionsncode
	 */
	public String getDivisionsncode() {
		return divisionsncode;
	}
	/**
	 * @param divisionsncode the divisionsncode to set
	 */
	public void setDivisionsncode(String divisionsncode) {
		this.divisionsncode = divisionsncode;
	}
	/**
	 * @return the divitemcode
	 */
	public String getDivitemcode() {
		return divitemcode;
	}
	/**
	 * @param divitemcode the divitemcode to set
	 */
	public void setDivitemcode(String divitemcode) {
		this.divitemcode = divitemcode;
	}
	/**
	 * @return the divname
	 */
	public String getDivname() {
		return divname;
	}
	/**
	 * @param divname the divname to set
	 */
	public void setDivname(String divname) {
		this.divname = divname;
	}
	/**
	 * @return the divlevel
	 */
	public String getDivlevel() {
		return divlevel;
	}
	/**
	 * @param divlevel the divlevel to set
	 */
	public void setDivlevel(String divlevel) {
		this.divlevel = divlevel;
	}
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the planToady
	 */
	public Double getPlanToady() {
		return planToady;
	}
	/**
	 * @param planToady the planToady to set
	 */
	public void setPlanToady(Double planToady) {
		this.planToady = planToady;
	}
	/**
	 * @return the actualStartDate
	 */
	public String getActualStartDate() {
		return actualStartDate;
	}
	/**
	 * @param actualStartDate the actualStartDate to set
	 */
	public void setActualStartDate(String actualStartDate) {
		this.actualStartDate = actualStartDate;
	}
	/**
	 * @return the actualEndDate
	 */
	public String getActualEndDate() {
		return actualEndDate;
	}
	/**
	 * @param actualEndDate the actualEndDate to set
	 */
	public void setActualEndDate(String actualEndDate) {
		this.actualEndDate = actualEndDate;
	}
	/**
	 * @return the completedQuantity
	 */
	public Double getCompletedQuantity() {
		return completedQuantity;
	}
	/**
	 * @param completedQuantity the completedQuantity to set
	 */
	public void setCompletedQuantity(Double completedQuantity) {
		this.completedQuantity = completedQuantity;
	}
	/**
	 * @return the consSchemeQuantity
	 */
	public Double getConsSchemeQuantity() {
		return consSchemeQuantity;
	}
	/**
	 * @param consSchemeQuantity the consSchemeQuantity to set
	 */
	public void setConsSchemeQuantity(Double consSchemeQuantity) {
		this.consSchemeQuantity = consSchemeQuantity;
	}
	/**
	 * @return the remainingQuantity
	 */
	public Double getRemainingQuantity() {
		return remainingQuantity;
	}
	/**
	 * @param remainingQuantity the remainingQuantity to set
	 */
	public void setRemainingQuantity(Double remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}
	/**
	 * @return the designQuantity
	 */
	public Double getDesignQuantity() {
		return designQuantity;
	}
	/**
	 * @param designQuantity the designQuantity to set
	 */
	public void setDesignQuantity(Double designQuantity) {
		this.designQuantity = designQuantity;
	}
	/**
	 * @return the actualConstDays
	 */
	public String getActualConstDays() {
		return actualConstDays;
	}
	/**
	 * @param actualConstDays the actualConstDays to set
	 */
	public void setActualConstDays(String actualConstDays) {
		this.actualConstDays = actualConstDays;
	}
}