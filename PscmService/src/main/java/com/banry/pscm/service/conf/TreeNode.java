/**
 * 
 * @auther chenshiyu
 */
package com.banry.pscm.service.conf;

import java.util.List;

/**
 * @author chenshiyu
 *
 */
public class TreeNode {
	
	private String id;//divisionSnCode
	private String groupid;//divItemCode
	private String name;//divName
	private String skill;//skill
	private String divLevel;//divLevel
	private String pId;//parentDivSnCode
	

	private boolean open;
	private boolean draggable;
	private String isLeaf;
	private String url;
	private String target;
	private String dataUrl;
	

	private List<TreeNode> children;//子类

	
	public String getDataUrl() {
		return dataUrl;
	}
	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getDivLevel() {
		return divLevel;
	}

	public void setDivLevel(String divLevel) {
		this.divLevel = divLevel;
	}

	

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isDraggable() {
		return draggable;
	}

	public void setDraggable(boolean draggable) {
		this.draggable = draggable;
	}

	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	
	
	
}
