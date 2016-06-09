package com.istart.framework.service.impl;

import com.istart.framework.service.ScenicAreaService;
import com.istart.framework.domain.DicType;
import com.istart.framework.domain.ScenicArea;
import com.istart.framework.repository.ScenicAreaRepository;
import com.istart.framework.repository.search.ScenicAreaSearchRepository;
import com.istart.framework.web.rest.dto.ScenicAreaDTO;
import com.istart.framework.web.rest.mapper.ScenicAreaMapper;
import com.istart.framework.web.rest.search.SearchDicType;
import com.istart.framework.web.rest.search.SearchScenicArea;

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
 * Service Implementation for managing ScenicArea.
 */
@Service
@Transactional
public class ScenicAreaServiceImpl implements ScenicAreaService{

    private final Logger log = LoggerFactory.getLogger(ScenicAreaServiceImpl.class);
    
    @Inject
    private ScenicAreaRepository scenicAreaRepository;
    
    @Inject
    private ScenicAreaMapper scenicAreaMapper;
    
    @Inject
    private ScenicAreaSearchRepository scenicAreaSearchRepository;
    
    /**
     * Save a scenicArea.
     * 
     * @param scenicAreaDTO the entity to save
     * @return the persisted entity
     */
    public ScenicAreaDTO save(ScenicAreaDTO scenicAreaDTO) {
        log.debug("Request to save ScenicArea : {}", scenicAreaDTO);
        ScenicArea scenicArea = scenicAreaMapper.scenicAreaDTOToScenicArea(scenicAreaDTO);
        scenicArea = scenicAreaRepository.save(scenicArea);
        ScenicAreaDTO result = scenicAreaMapper.scenicAreaToScenicAreaDTO(scenicArea);
        scenicAreaSearchRepository.save(scenicArea);
        return result;
    }

    /**
     *  Get all the scenicAreas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ScenicArea> findAll(Pageable pageable) {
        log.debug("Request to get all ScenicAreas");
        Page<ScenicArea> result = scenicAreaRepository.findAll(pageable); 
        return result;
    }

    @Transactional(readOnly = true)
    @Override
	public Page<ScenicArea> findByPageSearch(SearchScenicArea searchScenicArea, Pageable pageable) {
    	log.debug("Request to get all ScenicAreas");
    	Page<ScenicArea> result = scenicAreaRepository.findAll(this.getSpecification(searchScenicArea), pageable);
        return result;
	}
    
    /**
     *  Get one scenicArea by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ScenicAreaDTO findOne(Long id) {
        log.debug("Request to get ScenicArea : {}", id);
        ScenicArea scenicArea = scenicAreaRepository.findOne(id);
        ScenicAreaDTO scenicAreaDTO = scenicAreaMapper.scenicAreaToScenicAreaDTO(scenicArea);
        return scenicAreaDTO;
    }

    /**
     *  Delete the  scenicArea by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ScenicArea : {}", id);
        scenicAreaRepository.delete(id);
        scenicAreaSearchRepository.delete(id);
    }

    /**
     * Search for the scenicArea corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ScenicArea> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ScenicAreas for query {}", query);
        return scenicAreaSearchRepository.search(queryStringQuery(query), pageable);
    }
    private Specification<ScenicArea> getSpecification(final SearchScenicArea searchScenicArea){
    	return new Specification<ScenicArea>() {
			@Override
			public Predicate toPredicate(Root<ScenicArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				String name = searchScenicArea.getName();
				if(StringUtils.isNoneBlank(name)){
					Path<String> expName = root.get("name");
					list.add(cb.like(expName, name + "%"));
				}
				list.add(cb.equal(root.get("dataStatus"), "1"));
				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}
		};
    }
}
