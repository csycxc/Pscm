package com.banry.pscm.service.util;

public class EnumVar {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column enum_var.enum_name
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    private String enumName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column enum_var.enum_value
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    private Integer enumValue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column enum_var.enum_value_name
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    private String enumValueName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column enum_var.remark
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    private String remark;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column enum_var.enum_name
     *
     * @return the value of enum_var.enum_name
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public String getEnumName() {
        return enumName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column enum_var.enum_name
     *
     * @param enumName the value for enum_var.enum_name
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column enum_var.enum_value
     *
     * @return the value of enum_var.enum_value
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public Integer getEnumValue() {
        return enumValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column enum_var.enum_value
     *
     * @param enumValue the value for enum_var.enum_value
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public void setEnumValue(Integer enumValue) {
        this.enumValue = enumValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column enum_var.enum_value_name
     *
     * @return the value of enum_var.enum_value_name
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public String getEnumValueName() {
        return enumValueName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column enum_var.enum_value_name
     *
     * @param enumValueName the value for enum_var.enum_value_name
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public void setEnumValueName(String enumValueName) {
        this.enumValueName = enumValueName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column enum_var.remark
     *
     * @return the value of enum_var.remark
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column enum_var.remark
     *
     * @param remark the value for enum_var.remark
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public EnumVar() {
		super();
	}

	public EnumVar(Integer enumValue) {
		super();
		this.enumValue = enumValue;
	}
}