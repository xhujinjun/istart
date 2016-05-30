package com.istart.framework.service;

import com.istart.framework.domain.ScenicArea;
import com.istart.framework.web.rest.dto.ScenicAreaDTO;
import com.istart.framework.web.rest.search.SearchScenicArea;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing ScenicArea.
 */
public interface ScenicAreaService {

    /**
     * Save a scenicArea.
     * 
     * @param scenicAreaDTO the entity to save
     * @return the persisted entity
     */
    ScenicAreaDTO save(ScenicAreaDTO scenicAreaDTO);

    /**
     *  Get all the scenicAreas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ScenicArea> findAll(Pageable pageable);

    Page<ScenicArea> findByPageSearch(SearchScenicArea searchScenicArea, Pageable pageable);
    
    /**
     *  Get the "id" scenicArea.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    ScenicAreaDTO findOne(Long id);

    /**
     *  Delete the "id" scenicArea.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the scenicArea corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    Page<ScenicArea> search(String query, Pageable pageable);

	
}
