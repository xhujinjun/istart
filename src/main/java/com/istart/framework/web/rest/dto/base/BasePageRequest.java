/**
 * Project Name:istart
 * File Name:Pager.java
 * Package Name:com.istart.framework.web.rest.dto.base
 * Date:2016年5月26日下午2:02:54
 * Copyright (c) 2016, xhujinjun@163.com All Rights Reserved.
 *
 */

package com.istart.framework.web.rest.dto.base;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;


/**
 * ClassName: Pager <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月26日 下午2:02:54 <br/>
 *
 * @author xiejinjun
 * @version 
 * @since JDK 1.6
 */
public class BasePageRequest implements Pageable{
	/**
	 * 每页显示多少条
	 */
	private Integer pageSize = 10;
	/**
	 * 当前页
	 */
	private Integer page = 1;
	/**
	 * 排序字段
	 */
	private String sort="id";
	
	private Direction order;
	
	private Long totalPage;
	
	private Long total = 0L;
	
//	private List<T> rows = new ArrayList<>();
	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see org.springframework.data.domain.Pageable#getPageNumber()
	 */
	@Override
	public int getPageNumber() {
		
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see org.springframework.data.domain.Pageable#getPageSize()
	 */
	@Override
	public int getPageSize() {
		
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see org.springframework.data.domain.Pageable#getOffset()
	 */
	@Override
	public int getOffset() {
		
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see org.springframework.data.domain.Pageable#getSort()
	 */
	@Override
	public Sort getSort() {
		
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see org.springframework.data.domain.Pageable#next()
	 */
	@Override
	public Pageable next() {
		
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see org.springframework.data.domain.Pageable#previousOrFirst()
	 */
	@Override
	public Pageable previousOrFirst() {
		
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see org.springframework.data.domain.Pageable#first()
	 */
	@Override
	public Pageable first() {
		
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see org.springframework.data.domain.Pageable#hasPrevious()
	 */
	@Override
	public boolean hasPrevious() {
		
		// TODO Auto-generated method stub
		return false;
	}

}

