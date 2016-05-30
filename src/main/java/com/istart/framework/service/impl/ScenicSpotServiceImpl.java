package com.istart.framework.service.impl;

import com.istart.framework.service.ScenicSpotService;
import com.istart.framework.domain.ScenicSpot;
import com.istart.framework.repository.ScenicSpotRepository;
import com.istart.framework.repository.search.ScenicSpotSearchRepository;
import com.istart.framework.web.rest.dto.ScenicSpotDTO;
import com.istart.framework.web.rest.mapper.ScenicSpotMapper;
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
 * Service Implementation for managing ScenicSpot.
 */
@Service
@Transactional
public class ScenicSpotServiceImpl implements ScenicSpotService{

    private final Logger log = LoggerFactory.getLogger(ScenicSpotServiceImpl.class);
    
    @Inject
    private ScenicSpotRepository scenicSpotRepository;
    
    @Inject
    private ScenicSpotMapper scenicSpotMapper;
    
    @Inject
    private ScenicSpotSearchRepository scenicSpotSearchRepository;
    
    /**
     * Save a scenicSpot.
     * 
     * @param scenicSpotDTO the entity to save
     * @return the persisted entity
     */
    public ScenicSpotDTO save(ScenicSpotDTO scenicSpotDTO) {
        log.debug("Request to save ScenicSpot : {}", scenicSpotDTO);
        ScenicSpot scenicSpot = scenicSpotMapper.scenicSpotDTOToScenicSpot(scenicSpotDTO);
        scenicSpot = scenicSpotRepository.save(scenicSpot);
        ScenicSpotDTO result = scenicSpotMapper.scenicSpotToScenicSpotDTO(scenicSpot);
        scenicSpotSearchRepository.save(scenicSpot);
        return result;
    }

    /**
     *  Get all the scenicSpots.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ScenicSpot> findAll(Pageable pageable) {
        log.debug("Request to get all ScenicSpots");
        Page<ScenicSpot> result = scenicSpotRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one scenicSpot by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ScenicSpotDTO findOne(Long id) {
        log.debug("Request to get ScenicSpot : {}", id);
        ScenicSpot scenicSpot = scenicSpotRepository.findOne(id);
        ScenicSpotDTO scenicSpotDTO = scenicSpotMapper.scenicSpotToScenicSpotDTO(scenicSpot);
        return scenicSpotDTO;
    }

    /**
     *  Delete the  scenicSpot by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ScenicSpot : {}", id);
        scenicSpotRepository.delete(id);
        scenicSpotSearchRepository.delete(id);
    }

    /**
     * Search for the scenicSpot corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ScenicSpot> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ScenicSpots for query {}", query);
        return scenicSpotSearchRepository.search(queryStringQuery(query), pageable);
    }
}
