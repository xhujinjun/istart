package com.istart.framework.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.istart.framework.domain.ScenicArea;
import com.istart.framework.service.ScenicAreaService;
import com.istart.framework.web.rest.util.HeaderUtil;
import com.istart.framework.web.rest.util.PaginationUtil;
import com.istart.framework.web.rest.base.BaseResource;
import com.istart.framework.web.rest.base.Pager;
import com.istart.framework.web.rest.dto.DicTypeDTO;
import com.istart.framework.web.rest.dto.ScenicAreaDTO;
import com.istart.framework.web.rest.mapper.ScenicAreaMapper;
import com.istart.framework.web.rest.search.SearchDicType;
import com.istart.framework.web.rest.search.SearchScenicArea;

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
 * REST controller for managing ScenicArea.
 */
@RestController
@RequestMapping("/api")
public class ScenicAreaResource extends BaseResource{

    private final Logger log = LoggerFactory.getLogger(ScenicAreaResource.class);
        
    @Inject
    private ScenicAreaService scenicAreaService;
    
    @Inject
    private ScenicAreaMapper scenicAreaMapper;
    
    /**
     * POST  /scenic-areas : Create a new scenicArea.
     *
     * @param scenicAreaDTO the scenicAreaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new scenicAreaDTO, or with status 400 (Bad Request) if the scenicArea has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/scenic-areas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ScenicAreaDTO> createScenicArea(@RequestBody ScenicAreaDTO scenicAreaDTO) throws URISyntaxException {
        log.debug("REST request to save ScenicArea : {}", scenicAreaDTO);
        if (scenicAreaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("scenicArea", "idexists", "A new scenicArea cannot already have an ID")).body(null);
        }
        ScenicAreaDTO result = scenicAreaService.save(scenicAreaDTO);
        return ResponseEntity.created(new URI("/api/scenic-areas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("scenicArea", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /scenic-areas : Updates an existing scenicArea.
     *
     * @param scenicAreaDTO the scenicAreaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated scenicAreaDTO,
     * or with status 400 (Bad Request) if the scenicAreaDTO is not valid,
     * or with status 500 (Internal Server Error) if the scenicAreaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/scenic-areas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ScenicAreaDTO> updateScenicArea(@RequestBody ScenicAreaDTO scenicAreaDTO) throws URISyntaxException {
        log.debug("REST request to update ScenicArea : {}", scenicAreaDTO);
        if (scenicAreaDTO.getId() == null) {
            return createScenicArea(scenicAreaDTO);
        }
        ScenicAreaDTO result = scenicAreaService.save(scenicAreaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("scenicArea", scenicAreaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /scenic-areas : get all the scenicAreas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of scenicAreas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/scenic-areas/get",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ScenicAreaDTO>> getAllScenicAreas(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ScenicAreas");
        Page<ScenicArea> page = scenicAreaService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/scenic-areas");
        return new ResponseEntity<>(scenicAreaMapper.scenicAreasToScenicAreaDTOs(page.getContent()), headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/scenic-areas",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
        @Timed
        @Transactional(readOnly = true)
        public ResponseEntity<Pager<ScenicAreaDTO>> getAllScenicAreas(int limit,int offset,String sort,String order,SearchScenicArea searchScenicArea)
            throws URISyntaxException {
            log.debug("REST request to get a page of ScenicAreas");
            Pageable pageable = this.toPageable(limit, offset, sort, order);
            Page<ScenicArea> page = scenicAreaService.findByPageSearch(searchScenicArea, pageable); 
            Pager<ScenicAreaDTO> pageDto = new Pager<>(scenicAreaMapper.scenicAreasToScenicAreaDTOs(page.getContent()),
    				page.getTotalElements());
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/scenic-areas");
            return new ResponseEntity<>(pageDto, headers, HttpStatus.OK);
        }
    /**
     * GET  /scenic-areas/:id : get the "id" scenicArea.
     *
     * @param id the id of the scenicAreaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the scenicAreaDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/scenic-areas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ScenicAreaDTO> getScenicArea(@PathVariable Long id) {
        log.debug("REST request to get ScenicArea : {}", id);
        ScenicAreaDTO scenicAreaDTO = scenicAreaService.findOne(id);
        return Optional.ofNullable(scenicAreaDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /scenic-areas/:id : delete the "id" scenicArea.
     *
     * @param id the id of the scenicAreaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/scenic-areas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteScenicArea(@PathVariable Long id) {
        log.debug("REST request to delete ScenicArea : {}", id);
        scenicAreaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("scenicArea", id.toString())).build();
    }

    /**
     * SEARCH  /_search/scenic-areas?query=:query : search for the scenicArea corresponding
     * to the query.
     *
     * @param query the query of the scenicArea search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/scenic-areas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ScenicAreaDTO>> searchScenicAreas(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of ScenicAreas for query {}", query);
        Page<ScenicArea> page = scenicAreaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/scenic-areas");
        return new ResponseEntity<>(scenicAreaMapper.scenicAreasToScenicAreaDTOs(page.getContent()), headers, HttpStatus.OK);
    }

}
