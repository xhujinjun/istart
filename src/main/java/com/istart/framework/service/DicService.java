package com.istart.framework.service;

import com.istart.framework.domain.Dic;
import com.istart.framework.web.rest.dto.DicDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Dic.
 */
public interface DicService {

    /**
     * Save a dic.
     * 
     * @param dicDTO the entity to save
     * @return the persisted entity
     */
    DicDTO save(DicDTO dicDTO);

    /**
     *  Get all the dics.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Dic> findAll(Pageable pageable);

    /**
     *  Get the "id" dic.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    DicDTO findOne(Long id);

    /**
     *  Delete the "id" dic.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the dic corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    Page<Dic> search(String query, Pageable pageable);
}
