package com.istart.framework.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.istart.framework.domain.Version;
import com.istart.framework.service.VersionService;
import com.istart.framework.web.rest.util.HeaderUtil;
import com.istart.framework.web.rest.util.PaginationUtil;
import com.istart.framework.web.rest.dto.VersionDTO;
import com.istart.framework.web.rest.mapper.VersionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
 * REST controller for managing Version.
 */
@RestController
@RequestMapping("/api")
public class VersionResource {

    private final Logger log = LoggerFactory.getLogger(VersionResource.class);
    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private String serverPort;
    
    @Inject
    private VersionService versionService;
    
    @Inject
    private VersionMapper versionMapper;
    
    /**
     * POST  /versions : Create a new version.
     *
     * @param versionDTO the versionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new versionDTO, or with status 400 (Bad Request) if the version has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/versions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VersionDTO> createVersion(@Valid @RequestBody VersionDTO versionDTO) throws URISyntaxException {
        log.debug("REST request to save Version : {}", versionDTO);
        if (versionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("version", "idexists", "A new version cannot already have an ID")).body(null);
        }
        VersionDTO result = versionService.save(versionDTO);
        return ResponseEntity.created(new URI("/api/versions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("version", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /versions : Updates an existing version.
     *
     * @param versionDTO the versionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated versionDTO,
     * or with status 400 (Bad Request) if the versionDTO is not valid,
     * or with status 500 (Internal Server Error) if the versionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/versions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VersionDTO> updateVersion(@Valid @RequestBody VersionDTO versionDTO) throws URISyntaxException {
        log.debug("REST request to update Version : {}", versionDTO);
        if (versionDTO.getId() == null) {
            return createVersion(versionDTO);
        }
        VersionDTO result = versionService.save(versionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("version", versionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /versions : get all the versions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of versions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/versions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<VersionDTO>> getAllVersions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Versions");
        Page<Version> page = versionService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/versions");
        List<Version> reuslt = page.getContent();
        for(Version version : reuslt){
        	version.setUrl("http://" + serverAddress + ":" + serverPort + version.getUrl());
        }
        log.info("服务IP:{}, 服务端口:{}",serverAddress,serverPort);
        return new ResponseEntity<>(versionMapper.versionsToVersionDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /versions/:id : get the "id" version.
     *
     * @param id the id of the versionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the versionDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/versions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VersionDTO> getVersion(@PathVariable Long id) {
        log.debug("REST request to get Version : {}", id);
        VersionDTO versionDTO = versionService.findOne(id);
        return Optional.ofNullable(versionDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /versions/:id : delete the "id" version.
     *
     * @param id the id of the versionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/versions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteVersion(@PathVariable Long id) {
        log.debug("REST request to delete Version : {}", id);
        versionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("version", id.toString())).build();
    }

    /**
     * SEARCH  /_search/versions?query=:query : search for the version corresponding
     * to the query.
     *
     * @param query the query of the version search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/versions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<VersionDTO>> searchVersions(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Versions for query {}", query);
        Page<Version> page = versionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/versions");
        return new ResponseEntity<>(versionMapper.versionsToVersionDTOs(page.getContent()), headers, HttpStatus.OK);
    }

}
