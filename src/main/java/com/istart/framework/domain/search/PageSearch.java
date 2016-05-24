package com.istart.framework.domain.search;

/**
 * 接受分页参数
 * 
 * @author Administrator
 *
 */
public class PageSearch {

	public int page;
	public int size;
	public String property;
	public String direction;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return "PageSearch [page=" + page + ", size=" + size + ", property=" + property + ", direction=" + direction
				+ "]";
	}

}
