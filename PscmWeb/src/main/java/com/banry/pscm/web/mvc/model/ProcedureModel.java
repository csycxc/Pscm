/**
 * 
 */
package com.banry.pscm.web.mvc.model;

/**
 * @author Xu Dingkui
 * @date 2017年7月16日
 */
public class ProcedureModel {
	
	private String workArea;
	private String procedureId;
	private String procedureItem;	//划分项
	private String procedureName;
	private String divclass;
	private String status;
	/**
	 * @return the workArea
	 */
	public String getWorkArea() {
		return workArea;
	}
	/**
	 * @param workArea the workArea to set
	 */
	public void setWorkArea(String workArea) {
		this.workArea = workArea;
	}
	/**
	 * @return the procedureId
	 */
	public String getProcedureId() {
		return procedureId;
	}
	/**
	 * @param procedureId the procedureId to set
	 */
	public void setProcedureId(String procedureId) {
		this.procedureId = procedureId;
	}
	/**
	 * @return the procedureName
	 */
	public String getProcedureName() {
		return procedureName;
	}
	/**
	 * @param procedureName the procedureName to set
	 */
	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}
	/**
	 * @return the divclass
	 */
	public String getDivclass() {
		return divclass;
	}
	/**
	 * @param divclass the divclass to set
	 */
	public void setDivclass(String divclass) {
		this.divclass = divclass;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the procedureItem
	 */
	public String getProcedureItem() {
		return procedureItem;
	}
	/**
	 * @param procedureItem the procedureItem to set
	 */
	public void setProcedureItem(String procedureItem) {
		this.procedureItem = procedureItem;
	}
}
