/**
 * 
 */
package com.banry.pscm.web.mvc.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Xu Dingkui
 * @date 2017年7月5日
 */
public class GanttTask implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String text;
    private String color;
    private String start_date;
    private Long duration;
    private float progress;
    private Long sortorder;
    private String parent;
    private String version;
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    /**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	public String getStart_date() {
        return start_date;
    }
    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }
    public Long getDuration() {
        return duration;
    }
    public void setDuration(Long duration) {
        this.duration = duration;
    }
    public float getProgress() {
        return progress;
    }
    public void setProgress(float progress) {
        this.progress = progress;
    }
    public Long getSortorder() {
        return sortorder;
    }
    public void setSortorder(Long sortorder) {
        this.sortorder = sortorder;
    }
    public String getParent() {
        return parent;
    }
    public void setParent(String parent) {
        this.parent = parent;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof GanttTask)) {
            return false;
        }
        GanttTask other = (GanttTask) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}