package com.banry.pscm.service.account;

import java.util.Date;

public class Role extends RoleKey {
    public Role(String roleCode, String tenantCode) {
		super(roleCode, tenantCode);
		// TODO Auto-generated constructor stub
	}
    
    public Role() {
    	
    }

	/**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role.dept_code
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    private String deptCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role.name
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role.create_user
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    private String createUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role.create_date
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    private Date createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role.status
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    private Integer status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role.dept_code
     *
     * @return the value of role.dept_code
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public String getDeptCode() {
        return deptCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role.dept_code
     *
     * @param deptCode the value for role.dept_code
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role.name
     *
     * @return the value of role.name
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role.name
     *
     * @param name the value for role.name
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role.create_user
     *
     * @return the value of role.create_user
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role.create_user
     *
     * @param createUser the value for role.create_user
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role.create_date
     *
     * @return the value of role.create_date
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role.create_date
     *
     * @param createDate the value for role.create_date
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role.status
     *
     * @return the value of role.status
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role.status
     *
     * @param status the value for role.status
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}