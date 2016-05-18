package com.istart.framework.service.impl;

import com.istart.framework.service.TravelAgencyLineService;
import com.istart.framework.domain.TravelAgencyLine;
import com.istart.framework.repository.TravelAgencyLineRepository;
import com.istart.framework.repository.search.TravelAgencyLineSearchRepository;
import com.istart.framework.web.rest.dto.TravelAgencyLineDTO;
import com.istart.framework.web.rest.mapper.TravelAgencyLineMapper;
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
 * Service Implementation for managing TravelAgencyLine.
 */
@Service
@Transactional
public class TravelAgencyLineServiceImpl implements TravelAgencyLineService{

    private final Logger log = LoggerFactory.getLogger(TravelAgencyLineServiceImpl.class);
    
    @Inject
    private TravelAgencyLineRepository travelAgencyLineRepository;
    
    @Inject
    private TravelAgencyLineMapper travelAgencyLineMapper;
    
    @Inject
    private TravelAgencyLineSearchRepository travelAgencyLineSearchRepository;
    
    /**
     * Save a travelAgencyLine.
     * 
     * @param travelAgencyLineDTO the entity to save
     * @return the persisted entity
     */
    public TravelAgencyLineDTO save(TravelAgencyLineDTO travelAgencyLineDTO) {
        log.debug("Request to save TravelAgencyLine : {}", travelAgencyLineDTO);
        TravelAgencyLine travelAgencyLine = travelAgencyLineMapper.travelAgencyLineDTOToTravelAgencyLine(travelAgencyLineDTO);
        travelAgencyLine = travelAgencyLineRepository.save(travelAgencyLine);
        TravelAgencyLineDTO result = travelAgencyLineMapper.travelAgencyLineToTravelAgencyLineDTO(travelAgencyLine);
        travelAgencyLineSearchRepository.save(travelAgencyLine);
        return result;
    }

    /**
     *  Get all the travelAgencyLines.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<TravelAgencyLine> findAll(Pageable pageable) {
        log.debug("Request to get all TravelAgencyLines");
        Page<TravelAgencyLine> result = travelAgencyLineRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one travelAgencyLine by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TravelAgencyLineDTO findOne(Long id) {
        log.debug("Request to get TravelAgencyLine : {}", id);
        TravelAgencyLine travelAgencyLine = travelAgencyLineRepository.findOne(id);
        TravelAgencyLineDTO travelAgencyLineDTO = travelAgencyLineMapper.travelAgencyLineToTravelAgencyLineDTO(travelAgencyLine);
        return travelAgencyLineDTO;
    }

    /**
     *  Delete the  travelAgencyLine by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TravelAgencyLine : {}", id);
        travelAgencyLineRepository.delete(id);
        travelAgencyLineSearchRepository.delete(id);
    }

    /**
     * Search for the travelAgencyLine corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TravelAgencyLine> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TravelAgencyLines for query {}", query);
        return travelAgencyLineSearchRepository.search(queryStringQuery(query), pageable);
    }
}
