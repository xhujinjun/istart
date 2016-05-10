package com.istart.framework.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.istart.framework.domain.Sysres;
import com.istart.framework.service.SysresService;
import com.istart.framework.web.rest.util.HeaderUtil;
import com.istart.framework.web.rest.util.PaginationUtil;
import com.istart.framework.web.rest.dto.SysresDTO;
import com.istart.framework.web.rest.mapper.SysresMapper;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Sysres.
 */
@RestController
@RequestMapping("/api")
public class SysresResource {

    private final Logger log = LoggerFactory.getLogger(SysresResource.class);
        
    @Inject
    private SysresService sysresService;
    
    @Inject
    private SysresMapper sysresMapper;
    
    /**
     * POST  /sysres : Create a new sysres.
     *
     * @param sysresDTO the sysresDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sysresDTO, or with status 400 (Bad Request) if the sysres has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/sysres",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SysresDTO> createSysres(@RequestBody SysresDTO sysresDTO) throws URISyntaxException {
        log.debug("REST request to save Sysres : {}", sysresDTO);
        if (sysresDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sysres", "idexists", "A new sysres cannot already have an ID")).body(null);
        }
        SysresDTO result = sysresService.save(sysresDTO);
        return ResponseEntity.created(new URI("/api/sysres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sysres", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sysres : Updates an existing sysres.
     *
     * @param sysresDTO the sysresDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sysresDTO,
     * or with status 400 (Bad Request) if the sysresDTO is not valid,
     * or with status 500 (Internal Server Error) if the sysresDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/sysres",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SysresDTO> updateSysres(@RequestBody SysresDTO sysresDTO) throws URISyntaxException {
        log.debug("REST request to update Sysres : {}", sysresDTO);
        if (sysresDTO.getId() == null) {
            return createSysres(sysresDTO);
        }
        SysresDTO result = sysresService.save(sysresDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sysres", sysresDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sysres : get all the sysres.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysres in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/sysres",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<SysresDTO>> getAllSysres(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Sysres");
        Page<Sysres> page = sysresService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sysres");
        return new ResponseEntity<>(sysresMapper.sysresToSysresDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /sysres/:id : get the "id" sysres.
     *
     * @param id the id of the sysresDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sysresDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/sysres/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SysresDTO> getSysres(@PathVariable Long id) {
        log.debug("REST request to get Sysres : {}", id);
        SysresDTO sysresDTO = sysresService.findOne(id);
        return Optional.ofNullable(sysresDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sysres/:id : delete the "id" sysres.
     *
     * @param id the id of the sysresDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/sysres/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSysres(@PathVariable Long id) {
        log.debug("REST request to delete Sysres : {}", id);
        sysresService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sysres", id.toString())).build();
    }

    /**
     * SEARCH  /_search/sysres?query=:query : search for the sysres corresponding
     * to the query.
     *
     * @param query the query of the sysres search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/sysres",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<SysresDTO>> searchSysres(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Sysres for query {}", query);
        Page<Sysres> page = sysresService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sysres");
        return new ResponseEntity<>(sysresMapper.sysresToSysresDTOs(page.getContent()), headers, HttpStatus.OK);
    }

}
