package com.istart.framework.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.istart.framework.domain.DicType;
import com.istart.framework.service.DicTypeService;
import com.istart.framework.web.rest.util.HeaderUtil;
import com.istart.framework.web.rest.util.PaginationUtil;
import com.istart.framework.web.rest.dto.DicTypeDTO;
import com.istart.framework.web.rest.mapper.DicTypeMapper;
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
 * REST controller for managing DicType.
 */
@RestController
@RequestMapping("/api")
public class DicTypeResource {

    private final Logger log = LoggerFactory.getLogger(DicTypeResource.class);
        
    @Inject
    private DicTypeService dicTypeService;
    
    @Inject
    private DicTypeMapper dicTypeMapper;
    
    /**
     * POST  /dic-types : Create a new dicType.
     *
     * @param dicTypeDTO the dicTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dicTypeDTO, or with status 400 (Bad Request) if the dicType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/dic-types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DicTypeDTO> createDicType(@RequestBody DicTypeDTO dicTypeDTO) throws URISyntaxException {
        log.debug("REST request to save DicType : {}", dicTypeDTO);
        if (dicTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("dicType", "idexists", "A new dicType cannot already have an ID")).body(null);
        }
        DicTypeDTO result = dicTypeService.save(dicTypeDTO);
        return ResponseEntity.created(new URI("/api/dic-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dicType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dic-types : Updates an existing dicType.
     *
     * @param dicTypeDTO the dicTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dicTypeDTO,
     * or with status 400 (Bad Request) if the dicTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the dicTypeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/dic-types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DicTypeDTO> updateDicType(@RequestBody DicTypeDTO dicTypeDTO) throws URISyntaxException {
        log.debug("REST request to update DicType : {}", dicTypeDTO);
        if (dicTypeDTO.getId() == null) {
            return createDicType(dicTypeDTO);
        }
        DicTypeDTO result = dicTypeService.save(dicTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dicType", dicTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dic-types : get all the dicTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dicTypes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/dic-types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<DicTypeDTO>> getAllDicTypes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of DicTypes");
        Page<DicType> page = dicTypeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dic-types");
        return new ResponseEntity<>(dicTypeMapper.dicTypesToDicTypeDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /dic-types/:id : get the "id" dicType.
     *
     * @param id the id of the dicTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dicTypeDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/dic-types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DicTypeDTO> getDicType(@PathVariable Long id) {
        log.debug("REST request to get DicType : {}", id);
        DicTypeDTO dicTypeDTO = dicTypeService.findOne(id);
        return Optional.ofNullable(dicTypeDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dic-types/:id : delete the "id" dicType.
     *
     * @param id the id of the dicTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/dic-types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDicType(@PathVariable Long id) {
        log.debug("REST request to delete DicType : {}", id);
        dicTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dicType", id.toString())).build();
    }

    /**
     * SEARCH  /_search/dic-types?query=:query : search for the dicType corresponding
     * to the query.
     *
     * @param query the query of the dicType search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/dic-types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<DicTypeDTO>> searchDicTypes(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of DicTypes for query {}", query);
        Page<DicType> page = dicTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/dic-types");
        return new ResponseEntity<>(dicTypeMapper.dicTypesToDicTypeDTOs(page.getContent()), headers, HttpStatus.OK);
    }

}
