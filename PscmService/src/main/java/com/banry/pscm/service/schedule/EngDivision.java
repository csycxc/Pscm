package com.banry.pscm.service.schedule;

import java.util.Date;

public class EngDivision {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column eng_division.division_sn_code
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	private String divisionSnCode;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column eng_division.eng_code
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	private String engCode;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column eng_division.div_item_code
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	private String divItemCode;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column eng_division.div_name
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	private String divName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column eng_division.skill
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	private String skill;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column eng_division.div_level
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	private Integer divLevel;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column eng_division.parent_div_sn_code
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	private String parentDivSnCode;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column eng_division.real_start_date
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	private Date realStartDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column eng_division.real_end_date
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	private Date realEndDate;
	
	/**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column eng_division.path
    *
    * @mbg.generated Fri Jun 22 09:44:28 CST 2018
    */
   private String path;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column eng_division.division_sn_code
	 * @return  the value of eng_division.division_sn_code
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	public String getDivisionSnCode() {
		return divisionSnCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column eng_division.division_sn_code
	 * @param divisionSnCode  the value for eng_division.division_sn_code
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	public void setDivisionSnCode(String divisionSnCode) {
		this.divisionSnCode = divisionSnCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column eng_division.eng_code
	 * @return  the value of eng_division.eng_code
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	public String getEngCode() {
		return engCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column eng_division.eng_code
	 * @param engCode  the value for eng_division.eng_code
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	public void setEngCode(String engCode) {
		this.engCode = engCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column eng_division.div_item_code
	 * @return  the value of eng_division.div_item_code
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	public String getDivItemCode() {
		return divItemCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column eng_division.div_item_code
	 * @param divItemCode  the value for eng_division.div_item_code
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	public void setDivItemCode(String divItemCode) {
		this.divItemCode = divItemCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column eng_division.div_name
	 * @return  the value of eng_division.div_name
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	public String getDivName() {
		return divName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column eng_division.div_name
	 * @param divName  the value for eng_division.div_name
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	public void setDivName(String divName) {
		this.divName = divName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column eng_division.skill
	 * @return  the value of eng_division.skill
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	public String getSkill() {
		return skill;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column eng_division.skill
	 * @param skill  the value for eng_division.skill
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	public void setSkill(String skill) {
		this.skill = skill;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column eng_division.div_level
	 * @return  the value of eng_division.div_level
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	public Integer getDivLevel() {
		return divLevel;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column eng_division.div_level
	 * @param divLevel  the value for eng_division.div_level
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	public void setDivLevel(Integer divLevel) {
		this.divLevel = divLevel;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column eng_division.parent_div_sn_code
	 * @return  the value of eng_division.parent_div_sn_code
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	public String getParentDivSnCode() {
		return parentDivSnCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column eng_division.parent_div_sn_code
	 * @param parentDivSnCode  the value for eng_division.parent_div_sn_code
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	public void setParentDivSnCode(String parentDivSnCode) {
		this.parentDivSnCode = parentDivSnCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column eng_division.real_start_date
	 * @return  the value of eng_division.real_start_date
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	public Date getRealStartDate() {
		return realStartDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column eng_division.real_start_date
	 * @param realStartDate  the value for eng_division.real_start_date
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	public void setRealStartDate(Date realStartDate) {
		this.realStartDate = realStartDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column eng_division.real_end_date
	 * @return  the value of eng_division.real_end_date
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	public Date getRealEndDate() {
		return realEndDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column eng_division.real_end_date
	 * @param realEndDate  the value for eng_division.real_end_date
	 * @mbg.generated  Thu Jun 14 14:52:28 CST 2018
	 */
	public void setRealEndDate(Date realEndDate) {
		this.realEndDate = realEndDate;
	}
	
	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column eng_division.path
     *
     * @return the value of eng_division.path
     *
     * @mbg.generated Fri Jun 22 09:44:28 CST 2018
     */
    public String getPath() {
        return path;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column eng_division.path
     *
     * @param path the value for eng_division.path
     *
     * @mbg.generated Fri Jun 22 09:44:28 CST 2018
     */
    public void setPath(String path) {
        this.path = path;
    }
}