package com.istart.framework.service;

import com.istart.framework.domain.ScenicSpot;
import com.istart.framework.web.rest.dto.ScenicSpotDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing ScenicSpot.
 */
public interface ScenicSpotService {

    /**
     * Save a scenicSpot.
     * 
     * @param scenicSpotDTO the entity to save
     * @return the persisted entity
     */
    ScenicSpotDTO save(ScenicSpotDTO scenicSpotDTO);

    /**
     *  Get all the scenicSpots.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ScenicSpot> findAll(Pageable pageable);

    /**
     *  Get the "id" scenicSpot.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    ScenicSpotDTO findOne(Long id);

    /**
     *  Delete the "id" scenicSpot.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the scenicSpot corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    Page<ScenicSpot> search(String query, Pageable pageable);
}
