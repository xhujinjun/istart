package com.istart.framework.service.impl;

import com.istart.framework.service.RoleService;
import com.istart.framework.domain.Role;
import com.istart.framework.repository.RoleRepository;
import com.istart.framework.repository.search.RoleSearchRepository;
import com.istart.framework.web.rest.dto.RoleDTO;
import com.istart.framework.web.rest.mapper.RoleMapper;
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
 * Service Implementation for managing Role.
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    private final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);
    
    @Inject
    private RoleRepository roleRepository;
    
    @Inject
    private RoleMapper roleMapper;
    
    @Inject
    private RoleSearchRepository roleSearchRepository;
    
    /**
     * Save a role.
     * 
     * @param roleDTO the entity to save
     * @return the persisted entity
     */
    public RoleDTO save(RoleDTO roleDTO) {
        log.debug("Request to save Role : {}", roleDTO);
        Role role = roleMapper.roleDTOToRole(roleDTO);
        role = roleRepository.save(role);
        RoleDTO result = roleMapper.roleToRoleDTO(role);
        roleSearchRepository.save(role);
        return result;
    }

    /**
     *  Get all the roles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Role> findAll(Pageable pageable) {
        log.debug("Request to get all Roles");
        Page<Role> result = roleRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one role by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public RoleDTO findOne(Long id) {
        log.debug("Request to get Role : {}", id);
        Role role = roleRepository.findOne(id);
        RoleDTO roleDTO = roleMapper.roleToRoleDTO(role);
        return roleDTO;
    }

    /**
     *  Delete the  role by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Role : {}", id);
        roleRepository.delete(id);
        roleSearchRepository.delete(id);
    }

    /**
     * Search for the role corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Role> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Roles for query {}", query);
        return roleSearchRepository.search(queryStringQuery(query), pageable);
    }
}
