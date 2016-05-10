package com.istart.framework.service;

import com.istart.framework.domain.Role;
import com.istart.framework.web.rest.dto.RoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Role.
 */
public interface RoleService {

    /**
     * Save a role.
     * 
     * @param roleDTO the entity to save
     * @return the persisted entity
     */
    RoleDTO save(RoleDTO roleDTO);

    /**
     *  Get all the roles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Role> findAll(Pageable pageable);

    /**
     *  Get the "id" role.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    RoleDTO findOne(Long id);

    /**
     *  Delete the "id" role.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the role corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    Page<Role> search(String query, Pageable pageable);
}
