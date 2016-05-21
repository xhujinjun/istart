package com.istart.framework.service;

import org.springframework.data.domain.Page;

import com.istart.framework.domain.DicType;
import com.istart.framework.web.rest.search.SearchDicType;

public interface DicTypeService {

	/**
	 * 
	 * @param page
	 * @param SearchDicType
	 * @return
	 */
	public Page<DicType> findDicTypeByPageSearch(Page<DicType> page,final SearchDicType SearchDicType);
}
