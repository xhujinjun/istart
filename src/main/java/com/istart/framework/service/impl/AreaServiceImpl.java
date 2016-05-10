package com.istart.framework.service.impl;

import com.istart.framework.service.AreaService;
import com.istart.framework.domain.Area;
import com.istart.framework.repository.AreaRepository;
import com.istart.framework.repository.search.AreaSearchRepository;
import com.istart.framework.web.rest.dto.AreaDTO;
import com.istart.framework.web.rest.mapper.AreaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Area.
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService{

    private final Logger log = LoggerFactory.getLogger(AreaServiceImpl.class);
    
    @Inject
    private AreaRepository areaRepository;
    
    @Inject
    private AreaMapper areaMapper;
    
    @Inject
    private AreaSearchRepository areaSearchRepository;
    
    /**
     * Save a area.
     * 
     * @param areaDTO the entity to save
     * @return the persisted entity
     */
    public AreaDTO save(AreaDTO areaDTO) {
        log.debug("Request to save Area : {}", areaDTO);
        Area area = areaMapper.areaDTOToArea(areaDTO);
        area = areaRepository.save(area);
        AreaDTO result = areaMapper.areaToAreaDTO(area);
        areaSearchRepository.save(area);
        return result;
    }

    /**
     *  Get all the areas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Area> findAll(Pageable pageable) {
        log.debug("Request to get all Areas");
        Page<Area> result = areaRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one area by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public AreaDTO findOne(Long id) {
        log.debug("Request to get Area : {}", id);
        Area area = areaRepository.findOne(id);
        AreaDTO areaDTO = areaMapper.areaToAreaDTO(area);
        return areaDTO;
    }

    /**
     *  Delete the  area by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Area : {}", id);
        areaRepository.delete(id);
        areaSearchRepository.delete(id);
    }

    /**
     * Search for the area corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Area> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Areas for query {}", query);
        return areaSearchRepository.search(queryStringQuery(query), pageable);
    }
}
