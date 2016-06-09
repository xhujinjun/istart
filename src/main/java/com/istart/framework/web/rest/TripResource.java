package com.istart.framework.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.istart.framework.domain.Trip;
import com.istart.framework.service.TripService;
import com.istart.framework.web.rest.util.HeaderUtil;
import com.istart.framework.web.rest.util.PaginationUtil;
import com.istart.framework.web.rest.dto.TripDTO;
import com.istart.framework.web.rest.mapper.TripMapper;
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
 * REST controller for managing Trip.
 */
@RestController
@RequestMapping("/api")
public class TripResource {

    private final Logger log = LoggerFactory.getLogger(TripResource.class);
        
    @Inject
    private TripService tripService;
    
    @Inject
    private TripMapper tripMapper;
    
    /**
     * POST  /trips : Create a new trip.
     *
     * @param tripDTO the tripDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tripDTO, or with status 400 (Bad Request) if the trip has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/trips",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TripDTO> createTrip(@RequestBody TripDTO tripDTO) throws URISyntaxException {
        log.debug("REST request to save Trip : {}", tripDTO);
        if (tripDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("trip", "idexists", "A new trip cannot already have an ID")).body(null);
        }
        TripDTO result = tripService.save(tripDTO);
        return ResponseEntity.created(new URI("/api/trips/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("trip", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trips : Updates an existing trip.
     *
     * @param tripDTO the tripDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tripDTO,
     * or with status 400 (Bad Request) if the tripDTO is not valid,
     * or with status 500 (Internal Server Error) if the tripDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/trips",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TripDTO> updateTrip(@RequestBody TripDTO tripDTO) throws URISyntaxException {
        log.debug("REST request to update Trip : {}", tripDTO);
        if (tripDTO.getId() == null) {
            return createTrip(tripDTO);
        }
        TripDTO result = tripService.save(tripDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("trip", tripDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trips : get all the trips.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of trips in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/trips",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<TripDTO>> getAllTrips(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Trips");
        Page<Trip> page = tripService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trips");
        return new ResponseEntity<>(tripMapper.tripsToTripDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /trips/:id : get the "id" trip.
     *
     * @param id the id of the tripDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tripDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/trips/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TripDTO> getTrip(@PathVariable Long id) {
        log.debug("REST request to get Trip : {}", id);
        TripDTO tripDTO = tripService.findOne(id);
        return Optional.ofNullable(tripDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /trips/:id : delete the "id" trip.
     *
     * @param id the id of the tripDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/trips/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        log.debug("REST request to delete Trip : {}", id);
        tripService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("trip", id.toString())).build();
    }

    /**
     * SEARCH  /_search/trips?query=:query : search for the trip corresponding
     * to the query.
     *
     * @param query the query of the trip search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/trips",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<TripDTO>> searchTrips(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Trips for query {}", query);
        Page<Trip> page = tripService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/trips");
        return new ResponseEntity<>(tripMapper.tripsToTripDTOs(page.getContent()), headers, HttpStatus.OK);
    }

}
