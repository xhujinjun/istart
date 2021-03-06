package com.istart.framework.service.impl;

import com.istart.framework.service.DicTypeService;
import com.istart.framework.domain.Dic;
import com.istart.framework.domain.DicType;
import com.istart.framework.repository.DicTypeRepository;
import com.istart.framework.repository.search.DicTypeSearchRepository;
import com.istart.framework.web.rest.dto.DicTypeDTO;
import com.istart.framework.web.rest.mapper.DicTypeMapper;
import com.istart.framework.web.rest.search.SearchDic;
import com.istart.framework.web.rest.search.SearchDicType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DicType.
 */
@Service
@Transactional
public class DicTypeServiceImpl implements DicTypeService{

    private final Logger log = LoggerFactory.getLogger(DicTypeServiceImpl.class);
    
    @Inject
    private DicTypeRepository dicTypeRepository;
    
    @Inject
    private DicTypeMapper dicTypeMapper;
    
    @Inject
    private DicTypeSearchRepository dicTypeSearchRepository;
    
    /**
     * Save a dicType.
     * 
     * @param dicTypeDTO the entity to save
     * @return the persisted entity
     */
    public DicTypeDTO save(DicTypeDTO dicTypeDTO) {
        log.debug("Request to save DicType : {}", dicTypeDTO);
        DicType dicType = dicTypeMapper.dicTypeDTOToDicType(dicTypeDTO);
        dicType = dicTypeRepository.save(dicType);
        DicTypeDTO result = dicTypeMapper.dicTypeToDicTypeDTO(dicType);
        dicTypeSearchRepository.save(dicType);
        return result;
    }

    /**
     *  Get all the dicTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<DicType> findAll(Pageable pageable) {
        log.debug("Request to get all DicTypes");
        Page<DicType> result = dicTypeRepository.findAll(pageable); 
        return result;
    }
    
    @Override
    @Transactional(readOnly = true) 
	public Page<DicType> findByPageSearch(SearchDicType searchDicType, Pageable pageable) {
    	log.debug("Request to get all DicTypes");
    	Page<DicType> result = dicTypeRepository.findAll(this.getSpecification(searchDicType), pageable);
        return result;
	}
    /**
     *  Get one dicType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public DicTypeDTO findOne(Long id) {
        log.debug("Request to get DicType : {}", id);
        DicType dicType = dicTypeRepository.findOne(id);
        DicTypeDTO dicTypeDTO = dicTypeMapper.dicTypeToDicTypeDTO(dicType);
        return dicTypeDTO;
    }

    /**
     *  Delete the  dicType by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DicType : {}", id);
        dicTypeRepository.delete(id);
        dicTypeSearchRepository.delete(id);
    }

    /**
     * Search for the dicType corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DicType> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DicTypes for query {}", query);
        return dicTypeSearchRepository.search(queryStringQuery(query), pageable);
    }

    private Specification<DicType> getSpecification(final SearchDicType searchDicType){
    	return new Specification<DicType>() {
			@Override
			public Predicate toPredicate(Root<DicType> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				String dicTypeCode = searchDicType.getDicTypeCode();
				if(StringUtils.isNoneBlank(dicTypeCode)){
					Path<String> expDicTypeCode = root.get("dicTypeCode");
					list.add(cb.like(expDicTypeCode, dicTypeCode + "%"));
				}
				String dicTypeName = searchDicType.getDicTypeName();
				if(StringUtils.isNoneBlank(dicTypeName)){
					Path<String> expDicTypeName = root.get("dicTypeName");
					list.add(cb.like(expDicTypeName, dicTypeName + "%"));
				} 
				list.add(cb.equal(root.get("dataStatus"), "1"));
				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}
		};
    }
}
