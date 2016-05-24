package com.istart.framework.domain.search;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 实现Pageable，作为分页条件之一
 * 
 * @author Administrator
 *
 */
public class ProductPage implements Pageable {

	private int pageNumber;
	private int pageSize;
	private Sort sort;

	@Override
	public int getOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	@Override
	public Pageable next() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pageable previousOrFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pageable first() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPrevious() {
		// TODO Auto-generated method stub
		return false;
	}

}
