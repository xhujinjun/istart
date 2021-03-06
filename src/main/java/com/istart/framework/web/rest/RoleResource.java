package com.istart.framework.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.istart.framework.domain.Role;
import com.istart.framework.service.RoleService;
import com.istart.framework.web.rest.util.HeaderUtil;
import com.istart.framework.web.rest.util.PaginationUtil;
import com.istart.framework.web.rest.dto.RoleDTO;
import com.istart.framework.web.rest.mapper.RoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Role.
 */
@RestController
@RequestMapping("/api")
public class RoleResource {

    private final Logger log = LoggerFactory.getLogger(RoleResource.class);
        
    @Inject
    private RoleService roleService;
    
    @Inject
    private RoleMapper roleMapper;
    
    /**
     * POST  /roles : Create a new role.
     *
     * @param roleDTO the roleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new roleDTO, or with status 400 (Bad Request) if the role has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/roles",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RoleDTO> createRole(@Valid @RequestBody RoleDTO roleDTO) throws URISyntaxException {
        log.debug("REST request to save Role : {}", roleDTO);
        if (roleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("role", "idexists", "A new role cannot already have an ID")).body(null);
        }
        RoleDTO result = roleService.save(roleDTO);
        return ResponseEntity.created(new URI("/api/roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("role", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /roles : Updates an existing role.
     *
     * @param roleDTO the roleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated roleDTO,
     * or with status 400 (Bad Request) if the roleDTO is not valid,
     * or with status 500 (Internal Server Error) if the roleDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/roles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RoleDTO> updateRole(@Valid @RequestBody RoleDTO roleDTO) throws URISyntaxException {
        log.debug("REST request to update Role : {}", roleDTO);
        if (roleDTO.getId() == null) {
            return createRole(roleDTO);
        }
        RoleDTO result = roleService.save(roleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("role", roleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /roles : get all the roles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of roles in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/roles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<RoleDTO>> getAllRoles(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Roles");
        Page<Role> page = roleService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/roles");
        return new ResponseEntity<>(roleMapper.rolesToRoleDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /roles/:id : get the "id" role.
     *
     * @param id the id of the roleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the roleDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/roles/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RoleDTO> getRole(@PathVariable Long id) {
        log.debug("REST request to get Role : {}", id);
        RoleDTO roleDTO = roleService.findOne(id);
        return Optional.ofNullable(roleDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /roles/:id : delete the "id" role.
     *
     * @param id the id of the roleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/roles/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        log.debug("REST request to delete Role : {}", id);
        roleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("role", id.toString())).build();
    }

    /**
     * SEARCH  /_search/roles?query=:query : search for the role corresponding
     * to the query.
     *
     * @param query the query of the role search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/roles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<RoleDTO>> searchRoles(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Roles for query {}", query);
        Page<Role> page = roleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/roles");
        return new ResponseEntity<>(roleMapper.rolesToRoleDTOs(page.getContent()), headers, HttpStatus.OK);
    }

}
