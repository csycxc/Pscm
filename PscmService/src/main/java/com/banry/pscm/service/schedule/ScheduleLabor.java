package com.banry.pscm.service.schedule;

import java.util.Date;

public class ScheduleLabor extends LaborKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column labor.labor
	 * @mbg.generated  Tue Dec 19 22:18:06 CST 2017
	 */
	private String labor;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column labor.laborPost
	 * @mbg.generated  Tue Dec 19 22:18:06 CST 2017
	 */
	private String laborpost;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column labor.laborCompany
	 * @mbg.generated  Tue Dec 19 22:18:06 CST 2017
	 */
	private String laborcompany;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column labor.acceptTime
	 * @mbg.generated  Tue Dec 19 22:18:06 CST 2017
	 */
	private Date acceptTime;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column labor.labor
	 * @return  the value of labor.labor
	 * @mbg.generated  Tue Dec 19 22:18:06 CST 2017
	 */
	public String getLabor() {
		return labor;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column labor.labor
	 * @param labor  the value for labor.labor
	 * @mbg.generated  Tue Dec 19 22:18:06 CST 2017
	 */
	public void setLabor(String labor) {
		this.labor = labor;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column labor.laborPost
	 * @return  the value of labor.laborPost
	 * @mbg.generated  Tue Dec 19 22:18:06 CST 2017
	 */
	public String getLaborpost() {
		return laborpost;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column labor.laborPost
	 * @param laborpost  the value for labor.laborPost
	 * @mbg.generated  Tue Dec 19 22:18:06 CST 2017
	 */
	public void setLaborpost(String laborpost) {
		this.laborpost = laborpost;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column labor.laborCompany
	 * @return  the value of labor.laborCompany
	 * @mbg.generated  Tue Dec 19 22:18:06 CST 2017
	 */
	public String getLaborcompany() {
		return laborcompany;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column labor.laborCompany
	 * @param laborcompany  the value for labor.laborCompany
	 * @mbg.generated  Tue Dec 19 22:18:06 CST 2017
	 */
	public void setLaborcompany(String laborcompany) {
		this.laborcompany = laborcompany;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column labor.acceptTime
	 * @return  the value of labor.acceptTime
	 * @mbg.generated  Tue Dec 19 22:18:06 CST 2017
	 */
	public Date getAcceptTime() {
		return acceptTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column labor.acceptTime
	 * @param acceptTime  the value for labor.acceptTime
	 * @mbg.generated  Tue Dec 19 22:18:06 CST 2017
	 */
	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}
}