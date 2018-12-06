package com.banry.pscm.web.mvc.model;

import java.util.List;
import java.util.Map;

public class DataTableModel {
	private Long draw;
	private Long recordsTotal;
	private Long recordsFiltered;
	private List<?> data;
	private Map<?, ?> options;

	public Long getDraw() {
		return draw;
	}

	public void setDraw(Long draw) {
		this.draw = draw;
	}

	public Long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	public Map<?, ?> getOptions() {
		return options;
	}

	public void setOptions(Map<?, ?> options) {
		this.options = options;
	}

}
