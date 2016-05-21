package com.istart.framework.service;

import com.istart.framework.domain.DicType;
import com.istart.framework.web.rest.dto.DicTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing DicType.
 */
public interface DicTypeService {

    /**
     * Save a dicType.
     * 
     * @param dicTypeDTO the entity to save
     * @return the persisted entity
     */
    DicTypeDTO save(DicTypeDTO dicTypeDTO);

    /**
     *  Get all the dicTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DicType> findAll(Pageable pageable);

    /**
     *  Get the "id" dicType.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    DicTypeDTO findOne(Long id);

    /**
     *  Delete the "id" dicType.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the dicType corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    Page<DicType> search(String query, Pageable pageable);
}
