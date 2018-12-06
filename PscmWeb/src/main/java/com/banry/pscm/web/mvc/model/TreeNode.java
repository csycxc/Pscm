package com.banry.pscm.web.mvc.model;

import java.util.List;

/**
 * @author Xu Dingkui
 * @date 2017年7月2日
 */
public class TreeNode {

	private String id;
	private String pId;
	private String name;
	private boolean open;
	private String url;
	private String target;
	private boolean draggable;
	private String dataUrl;
	private String divlevel;
	private String isLeaf;
	private List<TreeNode> children;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the pId
	 */
	public String getpId() {
		return pId;
	}
	/**
	 * @param pId the pId to set
	 */
	public void setpId(String pId) {
		this.pId = pId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the open
	 */
	public boolean isOpen() {
		return open;
	}
	/**
	 * @param open the open to set
	 */
	public void setOpen(boolean open) {
		this.open = open;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}
	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}
	/**
	 * @return the draggable
	 */
	public boolean isDraggable() {
		return draggable;
	}
	/**
	 * @param draggable the draggable to set
	 */
	public void setDraggable(boolean draggable) {
		this.draggable = draggable;
	}
	/**
	 * @return the dataUrl
	 */
	public String getDataUrl() {
		return dataUrl;
	}
	/**
	 * @param dataUrl the dataUrl to set
	 */
	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
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
	 * @return the isLeaf
	 */
	public String getIsLeaf() {
		return isLeaf;
	}
	/**
	 * @param isLeaf the isLeaf to set
	 */
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	/**
	 * @return the children
	 */
	public List<TreeNode> getChildren() {
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
}
