package com.istart.framework.service;

import com.istart.framework.domain.Area;
import com.istart.framework.web.rest.dto.AreaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Area.
 */
public interface AreaService {

    /**
     * Save a area.
     * 
     * @param areaDTO the entity to save
     * @return the persisted entity
     */
    AreaDTO save(AreaDTO areaDTO);

    /**
     *  Get all the areas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Area> findAll(Pageable pageable);

    /**
     *  Get the "id" area.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    AreaDTO findOne(Long id);

    /**
     *  Delete the "id" area.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the area corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    Page<Area> search(String query, Pageable pageable);
}
