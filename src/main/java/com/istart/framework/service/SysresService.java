package com.istart.framework.service;

import com.istart.framework.domain.Sysres;
import com.istart.framework.web.rest.dto.SysresDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Sysres.
 */
public interface SysresService {

    /**
     * Save a sysres.
     * 
     * @param sysresDTO the entity to save
     * @return the persisted entity
     */
    SysresDTO save(SysresDTO sysresDTO);

    /**
     *  Get all the sysres.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Sysres> findAll(Pageable pageable);

    /**
     *  Get the "id" sysres.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    SysresDTO findOne(Long id);

    /**
     *  Delete the "id" sysres.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the sysres corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    Page<Sysres> search(String query, Pageable pageable);
}
