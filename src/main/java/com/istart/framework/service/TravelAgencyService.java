package com.istart.framework.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.istart.framework.domain.TravelAgency;
import com.istart.framework.web.rest.dto.TravelAgencyDTO;
import com.istart.framework.web.rest.search.SearchTravelAgency;

/**
 * Service Interface for managing TravelAgency.
 */
public interface TravelAgencyService {

    /**
     * Save a travelAgency.
     * 
     * @param travelAgencyDTO the entity to save
     * @return the persisted entity
     */
    TravelAgencyDTO save(TravelAgencyDTO travelAgencyDTO);

    /**
     *  Get all the travelAgencies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TravelAgency> findAll(Pageable pageable);

    Page<TravelAgency> findByPageSearch(SearchTravelAgency searchTravelAgency,Pageable pageable);
    /**
     *  Get the "id" travelAgency.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    TravelAgencyDTO findOne(Long id);

    /**
     *  Delete the "id" travelAgency.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the travelAgency corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    Page<TravelAgency> search(String query, Pageable pageable);
}
