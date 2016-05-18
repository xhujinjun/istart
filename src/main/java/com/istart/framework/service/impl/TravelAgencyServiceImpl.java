package com.istart.framework.service.impl;

import com.istart.framework.service.TravelAgencyService;
import com.istart.framework.domain.TravelAgency;
import com.istart.framework.repository.TravelAgencyRepository;
import com.istart.framework.repository.search.TravelAgencySearchRepository;
import com.istart.framework.web.rest.dto.TravelAgencyDTO;
import com.istart.framework.web.rest.mapper.TravelAgencyMapper;
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
 * Service Implementation for managing TravelAgency.
 */
@Service
@Transactional
public class TravelAgencyServiceImpl implements TravelAgencyService{

    private final Logger log = LoggerFactory.getLogger(TravelAgencyServiceImpl.class);
    
    @Inject
    private TravelAgencyRepository travelAgencyRepository;
    
    @Inject
    private TravelAgencyMapper travelAgencyMapper;
    
    @Inject
    private TravelAgencySearchRepository travelAgencySearchRepository;
    
    /**
     * Save a travelAgency.
     * 
     * @param travelAgencyDTO the entity to save
     * @return the persisted entity
     */
    public TravelAgencyDTO save(TravelAgencyDTO travelAgencyDTO) {
        log.debug("Request to save TravelAgency : {}", travelAgencyDTO);
        TravelAgency travelAgency = travelAgencyMapper.travelAgencyDTOToTravelAgency(travelAgencyDTO);
        travelAgency = travelAgencyRepository.save(travelAgency);
        TravelAgencyDTO result = travelAgencyMapper.travelAgencyToTravelAgencyDTO(travelAgency);
        travelAgencySearchRepository.save(travelAgency);
        return result;
    }

    /**
     *  Get all the travelAgencies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<TravelAgency> findAll(Pageable pageable) {
        log.debug("Request to get all TravelAgencies");
        Page<TravelAgency> result = travelAgencyRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one travelAgency by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TravelAgencyDTO findOne(Long id) {
        log.debug("Request to get TravelAgency : {}", id);
        TravelAgency travelAgency = travelAgencyRepository.findOne(id);
        TravelAgencyDTO travelAgencyDTO = travelAgencyMapper.travelAgencyToTravelAgencyDTO(travelAgency);
        return travelAgencyDTO;
    }

    /**
     *  Delete the  travelAgency by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TravelAgency : {}", id);
        travelAgencyRepository.delete(id);
        travelAgencySearchRepository.delete(id);
    }

    /**
     * Search for the travelAgency corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TravelAgency> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TravelAgencies for query {}", query);
        return travelAgencySearchRepository.search(queryStringQuery(query), pageable);
    }
}
