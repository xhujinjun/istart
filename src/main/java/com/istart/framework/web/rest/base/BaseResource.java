package com.istart.framework.web.rest.base;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public abstract class BaseResource {

	protected Pageable toPageable(int limit,int offset) {
		return toPageable(limit,offset,null,null);
	}
	
	protected Pageable toPageable(int limit,int offset,String sort,String order) {
		Sort _sort = null;
		if (StringUtils.isNotBlank(order)) {
			if(StringUtils.isBlank(sort)){
				sort = "id";
			}
			if (order.equalsIgnoreCase("asc")) {
				_sort = new Sort(new Order(Direction.ASC, sort));
			}else if (order.equalsIgnoreCase("desc")) {
				_sort = new Sort(new Order(Direction.DESC, sort));
			}
		}
		return new PageRequest(offset/limit, limit,_sort);
	}
}
