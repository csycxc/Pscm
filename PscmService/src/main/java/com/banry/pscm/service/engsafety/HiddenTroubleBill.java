package com.banry.pscm.service.engsafety;

import com.banry.pscm.service.util.EnumVar;

public class HiddenTroubleBill {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hidden_trouble_bill.trouble_code
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	private String troubleCode;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hidden_trouble_bill.div_item_code
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	private String divItemCode;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hidden_trouble_bill.div_level
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	private Integer divLevel;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hidden_trouble_bill.trouble_cate
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	private EnumVar troubleCate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hidden_trouble_bill.trouble_level
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	private EnumVar troubleLevel;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hidden_trouble_bill.invest_item
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	private String investItem;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hidden_trouble_bill.invest_content
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	private String investContent;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hidden_trouble_bill.description
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	private String description;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hidden_trouble_bill.troble_from
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	private String troubleFrom;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hidden_trouble_bill.from_code
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	private String fromCode;
	
	//按照隐患类别，排查项目分组的treegrid用
	private String classname;
	
	private String classid;
	
	private String frequency;	//上报频率

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hidden_trouble_bill.trouble_code
	 * @return  the value of hidden_trouble_bill.trouble_code
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	public String getTroubleCode() {
		return troubleCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hidden_trouble_bill.trouble_code
	 * @param troubleCode  the value for hidden_trouble_bill.trouble_code
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	public void setTroubleCode(String troubleCode) {
		this.troubleCode = troubleCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hidden_trouble_bill.div_item_code
	 * @return  the value of hidden_trouble_bill.div_item_code
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	public String getDivItemCode() {
		return divItemCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hidden_trouble_bill.div_item_code
	 * @param divItemCode  the value for hidden_trouble_bill.div_item_code
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	public void setDivItemCode(String divItemCode) {
		this.divItemCode = divItemCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hidden_trouble_bill.div_level
	 * @return  the value of hidden_trouble_bill.div_level
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	public Integer getDivLevel() {
		return divLevel;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hidden_trouble_bill.div_level
	 * @param divLevel  the value for hidden_trouble_bill.div_level
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	public void setDivLevel(Integer divLevel) {
		this.divLevel = divLevel;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hidden_trouble_bill.trouble_cate
	 * @return  the value of hidden_trouble_bill.trouble_cate
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	public EnumVar getTroubleCate() {
		return troubleCate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hidden_trouble_bill.trouble_cate
	 * @param troubleCate  the value for hidden_trouble_bill.trouble_cate
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	public void setTroubleCate(EnumVar troubleCate) {
		this.troubleCate = troubleCate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hidden_trouble_bill.trouble_level
	 * @return  the value of hidden_trouble_bill.trouble_level
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	public EnumVar getTroubleLevel() {
		return troubleLevel;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hidden_trouble_bill.trouble_level
	 * @param troubleLevel  the value for hidden_trouble_bill.trouble_level
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	public void setTroubleLevel(EnumVar troubleLevel) {
		this.troubleLevel = troubleLevel;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hidden_trouble_bill.invest_item
	 * @return  the value of hidden_trouble_bill.invest_item
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	public String getInvestItem() {
		return investItem;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hidden_trouble_bill.invest_item
	 * @param investItem  the value for hidden_trouble_bill.invest_item
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	public void setInvestItem(String investItem) {
		this.investItem = investItem;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hidden_trouble_bill.invest_content
	 * @return  the value of hidden_trouble_bill.invest_content
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	public String getInvestContent() {
		return investContent;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hidden_trouble_bill.invest_content
	 * @param investContent  the value for hidden_trouble_bill.invest_content
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	public void setInvestContent(String investContent) {
		this.investContent = investContent;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hidden_trouble_bill.description
	 * @return  the value of hidden_trouble_bill.description
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hidden_trouble_bill.description
	 * @param description  the value for hidden_trouble_bill.description
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hidden_trouble_bill.troble_from
	 * @return  the value of hidden_trouble_bill.troble_from
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	public String getTroubleFrom() {
		return troubleFrom;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hidden_trouble_bill.troble_from
	 * @param troubleFrom  the value for hidden_trouble_bill.troble_from
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	public void setTroubleFrom(String troubleFrom) {
		this.troubleFrom = troubleFrom;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hidden_trouble_bill.from_code
	 * @return  the value of hidden_trouble_bill.from_code
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	public String getFromCode() {
		return fromCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hidden_trouble_bill.from_code
	 * @param fromCode  the value for hidden_trouble_bill.from_code
	 * @mbg.generated  Fri Jun 15 11:21:07 CST 2018
	 */
	public void setFromCode(String fromCode) {
		this.fromCode = fromCode;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
}