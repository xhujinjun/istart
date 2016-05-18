package com.istart.framework.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.istart.framework.domain.TravelAgencyLine;
import com.istart.framework.service.TravelAgencyLineService;
import com.istart.framework.web.rest.util.HeaderUtil;
import com.istart.framework.web.rest.util.PaginationUtil;
import com.istart.framework.web.rest.dto.TravelAgencyLineDTO;
import com.istart.framework.web.rest.mapper.TravelAgencyLineMapper;
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
 * REST controller for managing TravelAgencyLine.
 */
@RestController
@RequestMapping("/api")
public class TravelAgencyLineResource {

    private final Logger log = LoggerFactory.getLogger(TravelAgencyLineResource.class);
        
    @Inject
    private TravelAgencyLineService travelAgencyLineService;
    
    @Inject
    private TravelAgencyLineMapper travelAgencyLineMapper;
    
    /**
     * POST  /travel-agency-lines : Create a new travelAgencyLine.
     *
     * @param travelAgencyLineDTO the travelAgencyLineDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new travelAgencyLineDTO, or with status 400 (Bad Request) if the travelAgencyLine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/travel-agency-lines",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TravelAgencyLineDTO> createTravelAgencyLine(@RequestBody TravelAgencyLineDTO travelAgencyLineDTO) throws URISyntaxException {
        log.debug("REST request to save TravelAgencyLine : {}", travelAgencyLineDTO);
        if (travelAgencyLineDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("travelAgencyLine", "idexists", "A new travelAgencyLine cannot already have an ID")).body(null);
        }
        TravelAgencyLineDTO result = travelAgencyLineService.save(travelAgencyLineDTO);
        return ResponseEntity.created(new URI("/api/travel-agency-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("travelAgencyLine", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /travel-agency-lines : Updates an existing travelAgencyLine.
     *
     * @param travelAgencyLineDTO the travelAgencyLineDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated travelAgencyLineDTO,
     * or with status 400 (Bad Request) if the travelAgencyLineDTO is not valid,
     * or with status 500 (Internal Server Error) if the travelAgencyLineDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/travel-agency-lines",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TravelAgencyLineDTO> updateTravelAgencyLine(@RequestBody TravelAgencyLineDTO travelAgencyLineDTO) throws URISyntaxException {
        log.debug("REST request to update TravelAgencyLine : {}", travelAgencyLineDTO);
        if (travelAgencyLineDTO.getId() == null) {
            return createTravelAgencyLine(travelAgencyLineDTO);
        }
        TravelAgencyLineDTO result = travelAgencyLineService.save(travelAgencyLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("travelAgencyLine", travelAgencyLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /travel-agency-lines : get all the travelAgencyLines.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of travelAgencyLines in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/travel-agency-lines",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<TravelAgencyLineDTO>> getAllTravelAgencyLines(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TravelAgencyLines");
        Page<TravelAgencyLine> page = travelAgencyLineService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/travel-agency-lines");
        return new ResponseEntity<>(travelAgencyLineMapper.travelAgencyLinesToTravelAgencyLineDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /travel-agency-lines/:id : get the "id" travelAgencyLine.
     *
     * @param id the id of the travelAgencyLineDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the travelAgencyLineDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/travel-agency-lines/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TravelAgencyLineDTO> getTravelAgencyLine(@PathVariable Long id) {
        log.debug("REST request to get TravelAgencyLine : {}", id);
        TravelAgencyLineDTO travelAgencyLineDTO = travelAgencyLineService.findOne(id);
        return Optional.ofNullable(travelAgencyLineDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /travel-agency-lines/:id : delete the "id" travelAgencyLine.
     *
     * @param id the id of the travelAgencyLineDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/travel-agency-lines/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTravelAgencyLine(@PathVariable Long id) {
        log.debug("REST request to delete TravelAgencyLine : {}", id);
        travelAgencyLineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("travelAgencyLine", id.toString())).build();
    }

    /**
     * SEARCH  /_search/travel-agency-lines?query=:query : search for the travelAgencyLine corresponding
     * to the query.
     *
     * @param query the query of the travelAgencyLine search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/travel-agency-lines",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<TravelAgencyLineDTO>> searchTravelAgencyLines(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of TravelAgencyLines for query {}", query);
        Page<TravelAgencyLine> page = travelAgencyLineService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/travel-agency-lines");
        return new ResponseEntity<>(travelAgencyLineMapper.travelAgencyLinesToTravelAgencyLineDTOs(page.getContent()), headers, HttpStatus.OK);
    }

}
