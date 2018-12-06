package com.banry.pscm.service.account;

import java.util.Date;

public class SysUsers extends SysUsersKey {
	
	public SysUsers() {
		
	}
	
    public SysUsers(String userCode, String tenantCode) {
		super(userCode, tenantCode);
	}

	/**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_users.user_name
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    private String userName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_users.user_password
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    private String userPassword;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_users.encryption
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    private String encryption;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_users.create_user
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    private String createUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_users.create_date
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    private Date createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_users.status
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_users.dept_code
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    private String deptCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_users.position_code
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    private String positionCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_users.alias
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    private String alias;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_users.last_login_time
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    private Date lastLoginTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_users.login_number
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    private Integer loginNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_users.description
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    private String description;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_users.telephone
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    private String telephone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_users.email
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    private String email;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_users.user_name
     *
     * @return the value of sys_users.user_name
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_users.user_name
     *
     * @param userName the value for sys_users.user_name
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_users.user_password
     *
     * @return the value of sys_users.user_password
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_users.user_password
     *
     * @param userPassword the value for sys_users.user_password
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_users.encryption
     *
     * @return the value of sys_users.encryption
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public String getEncryption() {
        return encryption;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_users.encryption
     *
     * @param encryption the value for sys_users.encryption
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public void setEncryption(String encryption) {
        this.encryption = encryption;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_users.create_user
     *
     * @return the value of sys_users.create_user
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_users.create_user
     *
     * @param createUser the value for sys_users.create_user
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_users.create_date
     *
     * @return the value of sys_users.create_date
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_users.create_date
     *
     * @param createDate the value for sys_users.create_date
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_users.status
     *
     * @return the value of sys_users.status
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_users.status
     *
     * @param status the value for sys_users.status
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_users.dept_code
     *
     * @return the value of sys_users.dept_code
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public String getDeptCode() {
        return deptCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_users.dept_code
     *
     * @param deptCode the value for sys_users.dept_code
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_users.position_code
     *
     * @return the value of sys_users.position_code
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public String getPositionCode() {
        return positionCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_users.position_code
     *
     * @param positionCode the value for sys_users.position_code
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_users.alias
     *
     * @return the value of sys_users.alias
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public String getAlias() {
        return alias;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_users.alias
     *
     * @param alias the value for sys_users.alias
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_users.last_login_time
     *
     * @return the value of sys_users.last_login_time
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_users.last_login_time
     *
     * @param lastLoginTime the value for sys_users.last_login_time
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_users.login_number
     *
     * @return the value of sys_users.login_number
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public Integer getLoginNumber() {
        return loginNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_users.login_number
     *
     * @param loginNumber the value for sys_users.login_number
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public void setLoginNumber(Integer loginNumber) {
        this.loginNumber = loginNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_users.description
     *
     * @return the value of sys_users.description
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_users.description
     *
     * @param description the value for sys_users.description
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_users.telephone
     *
     * @return the value of sys_users.telephone
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_users.telephone
     *
     * @param telephone the value for sys_users.telephone
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_users.email
     *
     * @return the value of sys_users.email
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_users.email
     *
     * @param email the value for sys_users.email
     *
     * @mbg.generated Fri Jul 13 09:30:09 CST 2018
     */
    public void setEmail(String email) {
        this.email = email;
    }
}