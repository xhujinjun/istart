package com.istart.framework.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.istart.framework.domain.ScenicSpot;
import com.istart.framework.service.ScenicSpotService;
import com.istart.framework.web.rest.util.HeaderUtil;
import com.istart.framework.web.rest.util.PaginationUtil;
import com.istart.framework.web.rest.dto.ScenicSpotDTO;
import com.istart.framework.web.rest.mapper.ScenicSpotMapper;
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
 * REST controller for managing ScenicSpot.
 */
@RestController
@RequestMapping("/api")
public class ScenicSpotResource {

    private final Logger log = LoggerFactory.getLogger(ScenicSpotResource.class);
        
    @Inject
    private ScenicSpotService scenicSpotService;
    
    @Inject
    private ScenicSpotMapper scenicSpotMapper;
    
    /**
     * POST  /scenic-spots : Create a new scenicSpot.
     *
     * @param scenicSpotDTO the scenicSpotDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new scenicSpotDTO, or with status 400 (Bad Request) if the scenicSpot has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/scenic-spots",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ScenicSpotDTO> createScenicSpot(@RequestBody ScenicSpotDTO scenicSpotDTO) throws URISyntaxException {
        log.debug("REST request to save ScenicSpot : {}", scenicSpotDTO);
        if (scenicSpotDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("scenicSpot", "idexists", "A new scenicSpot cannot already have an ID")).body(null);
        }
        ScenicSpotDTO result = scenicSpotService.save(scenicSpotDTO);
        return ResponseEntity.created(new URI("/api/scenic-spots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("scenicSpot", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /scenic-spots : Updates an existing scenicSpot.
     *
     * @param scenicSpotDTO the scenicSpotDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated scenicSpotDTO,
     * or with status 400 (Bad Request) if the scenicSpotDTO is not valid,
     * or with status 500 (Internal Server Error) if the scenicSpotDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/scenic-spots",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ScenicSpotDTO> updateScenicSpot(@RequestBody ScenicSpotDTO scenicSpotDTO) throws URISyntaxException {
        log.debug("REST request to update ScenicSpot : {}", scenicSpotDTO);
        if (scenicSpotDTO.getId() == null) {
            return createScenicSpot(scenicSpotDTO);
        }
        ScenicSpotDTO result = scenicSpotService.save(scenicSpotDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("scenicSpot", scenicSpotDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /scenic-spots : get all the scenicSpots.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of scenicSpots in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/scenic-spots",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ScenicSpotDTO>> getAllScenicSpots(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ScenicSpots");
        Page<ScenicSpot> page = scenicSpotService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/scenic-spots");
        return new ResponseEntity<>(scenicSpotMapper.scenicSpotsToScenicSpotDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /scenic-spots/:id : get the "id" scenicSpot.
     *
     * @param id the id of the scenicSpotDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the scenicSpotDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/scenic-spots/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ScenicSpotDTO> getScenicSpot(@PathVariable Long id) {
        log.debug("REST request to get ScenicSpot : {}", id);
        ScenicSpotDTO scenicSpotDTO = scenicSpotService.findOne(id);
        return Optional.ofNullable(scenicSpotDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /scenic-spots/:id : delete the "id" scenicSpot.
     *
     * @param id the id of the scenicSpotDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/scenic-spots/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteScenicSpot(@PathVariable Long id) {
        log.debug("REST request to delete ScenicSpot : {}", id);
        scenicSpotService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("scenicSpot", id.toString())).build();
    }

    /**
     * SEARCH  /_search/scenic-spots?query=:query : search for the scenicSpot corresponding
     * to the query.
     *
     * @param query the query of the scenicSpot search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/scenic-spots",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ScenicSpotDTO>> searchScenicSpots(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of ScenicSpots for query {}", query);
        Page<ScenicSpot> page = scenicSpotService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/scenic-spots");
        return new ResponseEntity<>(scenicSpotMapper.scenicSpotsToScenicSpotDTOs(page.getContent()), headers, HttpStatus.OK);
    }

}
