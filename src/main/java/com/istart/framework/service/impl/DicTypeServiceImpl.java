package com.istart.framework.service.impl;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.istart.framework.domain.DicType;
import com.istart.framework.repository.DicTypeRepository;
import com.istart.framework.repository.search.AuthorSearchRepository;
import com.istart.framework.service.DicTypeService;
import com.istart.framework.web.rest.search.SearchDicType;
/**
 * Service Implementation for managing DicType.
 */
@Service
@Transactional
public class DicTypeServiceImpl implements DicTypeService{
	@Inject
    private DicTypeRepository dicTypeRepository;
	
	@Override
	public Page<DicType> findDicTypeByPageSearch(Page<DicType> page, SearchDicType SearchDicType) {
//		dicTypeRepository.findAll(spec, pageable);
		return null;
	}
	
	public Specification<DicType> getSpecification(final SearchDicType searchDicType){
		
		return new Specification<DicType>() {
			@Override
			public Predicate toPredicate(Root<DicType> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return null;
			}
		};
	}

	
}
