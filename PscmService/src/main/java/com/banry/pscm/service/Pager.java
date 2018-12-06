package com.banry.pscm.service;

import java.io.Serializable;
import java.util.List;
import java.util.Date;

public class Pager implements Serializable{

	private static final long serialVersionUID = 1058885625968296224L;
	protected List<Date> datas;
	protected long totalCount;
	
	
	public Pager(List<Date> datas, long totalCount) {
		super();
		this.datas = datas;
		this.totalCount = totalCount;
	}
	
	public List<Date> getDatas() {
		return datas;
	}
	public void setDatas(List<Date> datas) {
		this.datas = datas;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
}
