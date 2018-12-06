/**
 * 
 */
package com.banry.pscm.web.mvc.model;

import java.util.ArrayList;

/**
 * @author Xu Dingkui
 * @date 2017年7月16日
 */
public class TechDisclosureModel {
	
	private String disdivsncode;	//划分序号编码
	private String divname;		//划分名称
	private String divlevel;	//划分级别
	private String disclosurecode;//交底编码
	private String disrole;		//交底者角色
	private String disclosurer;	//交底者姓名
	private String disdate;		//交底时间
	private String accepttime;	//接受时间
	private String disclocontent;	//交底内容
	private String discloinclude;	//交底包括项
	private String disclosurestatus;//是否接受交底 1:待接受交底 2:已经接受交底
	private ArrayList attachment;
	/**
	 * @return the disdivsncode
	 */
	public String getDisdivsncode() {
		return disdivsncode;
	}
	/**
	 * @param disdivsncode the disdivsncode to set
	 */
	public void setDisdivsncode(String disdivsncode) {
		this.disdivsncode = disdivsncode;
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
	 * @return the disclosurecode
	 */
	public String getDisclosurecode() {
		return disclosurecode;
	}
	/**
	 * @param disclosurecode the disclosurecode to set
	 */
	public void setDisclosurecode(String disclosurecode) {
		this.disclosurecode = disclosurecode;
	}
	/**
	 * @return the disrole
	 */
	public String getDisrole() {
		return disrole;
	}
	/**
	 * @param disrole the disrole to set
	 */
	public void setDisrole(String disrole) {
		this.disrole = disrole;
	}
	/**
	 * @return the disclosurer
	 */
	public String getDisclosurer() {
		return disclosurer;
	}
	/**
	 * @param disclosurer the disclosurer to set
	 */
	public void setDisclosurer(String disclosurer) {
		this.disclosurer = disclosurer;
	}
	/**
	 * @return the disdate
	 */
	public String getDisdate() {
		return disdate;
	}
	/**
	 * @param disdate the disdate to set
	 */
	public void setDisdate(String disdate) {
		this.disdate = disdate;
	}
	/**
	 * @return the accepttime
	 */
	public String getAccepttime() {
		return accepttime;
	}
	/**
	 * @param accepttime the accepttime to set
	 */
	public void setAccepttime(String accepttime) {
		this.accepttime = accepttime;
	}
	/**
	 * @return the disclocontent
	 */
	public String getDisclocontent() {
		return disclocontent;
	}
	/**
	 * @param disclocontent the disclocontent to set
	 */
	public void setDisclocontent(String disclocontent) {
		this.disclocontent = disclocontent;
	}
	/**
	 * @return the discloinclude
	 */
	public String getDiscloinclude() {
		return discloinclude;
	}
	/**
	 * @param discloinclude the discloinclude to set
	 */
	public void setDiscloinclude(String discloinclude) {
		this.discloinclude = discloinclude;
	}
	/**
	 * @return the disclosurestatus
	 */
	public String getDisclosurestatus() {
		return disclosurestatus;
	}
	/**
	 * @param disclosurestatus the disclosurestatus to set
	 */
	public void setDisclosurestatus(String disclosurestatus) {
		this.disclosurestatus = disclosurestatus;
	}
	/**
	 * @return the attachment
	 */
	public ArrayList getAttachment() {
		return attachment;
	}
	/**
	 * @param attachment the attachment to set
	 */
	public void setAttachment(ArrayList attachment) {
		this.attachment = attachment;
	}
}
