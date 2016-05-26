package com.istart.framework.web.rest.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hp
 *
 * @param <T>
 */
public class Pager<T> implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long total = 0L;
	
	private List<T> rows = new ArrayList<T>();
	
	public Pager() {
	}
	
	public Pager(List<T> rows,Long total) {
		this.total = total;
		this.rows = rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
