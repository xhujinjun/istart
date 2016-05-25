package com.istart.framework.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.istart.framework.domain.Hotel;
import com.istart.framework.service.HotelService;
import com.istart.framework.web.rest.dto.HotelDTO;
import com.istart.framework.web.rest.mapper.HotelMapper;
import com.istart.framework.web.rest.util.HeaderUtil;
import com.istart.framework.web.rest.util.PaginationUtil;

/**
 * @Description: 酒店管理API
 * @Version: 1.0
 */
@RestController
@RequestMapping("/api")
public class HotelResource {

    private final Logger log = LoggerFactory.getLogger(HotelResource.class);
        
    @Inject
    private HotelService hotelService;
    
    @Inject
    private HotelMapper hotelMapper;
    
    /**
     * POST  /Hotels : Create a new Hotel.
     *
     * @param HotelDTO the HotelDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new HotelDTO, or with status 400 (Bad Request) if the Hotel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/hotels",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HotelDTO> createHotel(@RequestBody HotelDTO hotelDTO) throws URISyntaxException {
        log.debug("REST request to save Hotel : {}", hotelDTO);
        if (hotelDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("Hotel", "idexists", "A new Hotel cannot already have an ID")).body(null);
        }
        HotelDTO result = hotelService.save(hotelDTO);
        return ResponseEntity.created(new URI("/api/hotels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hotel", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /Hotels : Updates an existing Hotel.
     *
     * @param HotelDTO the HotelDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated HotelDTO,
     * or with status 400 (Bad Request) if the HotelDTO is not valid,
     * or with status 500 (Internal Server Error) if the HotelDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/hotels",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HotelDTO> updateHotel(@RequestBody HotelDTO hotelDTO) throws URISyntaxException {
        log.debug("REST request to update Hotel : {}", hotelDTO);
        if (hotelDTO.getId() == null) {
            return createHotel(hotelDTO);
        }
        HotelDTO result = hotelService.save(hotelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hotel", hotelDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /Hotels : get all the Hotels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of Hotels in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/hotels",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<HotelDTO>> getAllHotels(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Hotels");
        Page<Hotel> page = hotelService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hotels");
        return new ResponseEntity<>(hotelMapper.hotelsToHotelDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /Hotels/:id : get the "id" Hotel.
     *
     * @param id the id of the HotelDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the HotelDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/hotels/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HotelDTO> getHotel(@PathVariable Long id) {
        log.debug("REST request to get Hotel : {}", id);
        HotelDTO hotelDTO = hotelService.findOne(id);
        return Optional.ofNullable(hotelDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /Hotels/:id : delete the "id" Hotel.
     *
     * @param id the id of the HotelDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/hotels/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        log.debug("REST request to delete Hotel : {}", id);
        hotelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hotel", id.toString())).build();
    }

    /**
     * SEARCH  /_search/Hotels?query=:query : search for the Hotel corresponding
     * to the query.
     *
     * @param query the query of the Hotel search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/hotels",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<HotelDTO>> searchHotels(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Hotels for query {}", query);
        Page<Hotel> page = hotelService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/hotels");
        return new ResponseEntity<>(hotelMapper.hotelsToHotelDTOs(page.getContent()), headers, HttpStatus.OK);
    }

}
