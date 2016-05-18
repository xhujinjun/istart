package com.istart.framework.service;

import com.istart.framework.domain.TravelAgencyLine;
import com.istart.framework.web.rest.dto.TravelAgencyLineDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing TravelAgencyLine.
 */
public interface TravelAgencyLineService {

    /**
     * Save a travelAgencyLine.
     * 
     * @param travelAgencyLineDTO the entity to save
     * @return the persisted entity
     */
    TravelAgencyLineDTO save(TravelAgencyLineDTO travelAgencyLineDTO);

    /**
     *  Get all the travelAgencyLines.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TravelAgencyLine> findAll(Pageable pageable);

    /**
     *  Get the "id" travelAgencyLine.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    TravelAgencyLineDTO findOne(Long id);

    /**
     *  Delete the "id" travelAgencyLine.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the travelAgencyLine corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    Page<TravelAgencyLine> search(String query, Pageable pageable);
}
