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
import com.istart.framework.domain.TravelAgency;
import com.istart.framework.service.TravelAgencyService;
import com.istart.framework.web.rest.base.BaseResource;
import com.istart.framework.web.rest.base.Pager;
import com.istart.framework.web.rest.dto.TravelAgencyDTO;
import com.istart.framework.web.rest.mapper.TravelAgencyMapper;
import com.istart.framework.web.rest.search.SearchTravelAgency;
import com.istart.framework.web.rest.util.HeaderUtil;
import com.istart.framework.web.rest.util.PaginationUtil;

/**
 * REST controller for managing TravelAgency.
 */
@RestController
@RequestMapping("/api")
public class TravelAgencyResource extends BaseResource{

	private final Logger log = LoggerFactory.getLogger(TravelAgencyResource.class);

	@Inject
	private TravelAgencyService travelAgencyService;

	@Inject
	private TravelAgencyMapper travelAgencyMapper;

	/**
	 * POST /travel-agencies : Create a new travelAgency.
	 *
	 * @param travelAgencyDTO
	 *            the travelAgencyDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new travelAgencyDTO, or with status 400 (Bad Request) if the
	 *         travelAgency has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/travel-agencies", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<TravelAgencyDTO> createTravelAgency(@RequestBody TravelAgencyDTO travelAgencyDTO)
			throws URISyntaxException {
		log.debug("REST request to save TravelAgency : {}", travelAgencyDTO);
		if (travelAgencyDTO.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("travelAgency", "idexists",
					"A new travelAgency cannot already have an ID")).body(null);
		}
		TravelAgencyDTO result = travelAgencyService.save(travelAgencyDTO);
		return ResponseEntity.created(new URI("/api/travel-agencies/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("travelAgency", result.getId().toString())).body(result);
	}

	/**
	 * PUT /travel-agencies : Updates an existing travelAgency.
	 *
	 * @param travelAgencyDTO
	 *            the travelAgencyDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         travelAgencyDTO, or with status 400 (Bad Request) if the
	 *         travelAgencyDTO is not valid, or with status 500 (Internal Server
	 *         Error) if the travelAgencyDTO couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/travel-agencies", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<TravelAgencyDTO> updateTravelAgency(@RequestBody TravelAgencyDTO travelAgencyDTO)
			throws URISyntaxException {
		log.debug("REST request to update TravelAgency : {}", travelAgencyDTO);
		if (travelAgencyDTO.getId() == null) {
			return createTravelAgency(travelAgencyDTO);
		}
		TravelAgencyDTO result = travelAgencyService.save(travelAgencyDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("travelAgency", travelAgencyDTO.getId().toString()))
				.body(result);
	}

	/**
	 * GET /travel-agencies : get all the travelAgencies.
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         travelAgencies in body
	 * @throws URISyntaxException
	 *             if there is an error to generate the pagination HTTP headers
	 */
	@RequestMapping(value = "/travel-agencies/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@Transactional(readOnly = true)
	public ResponseEntity<List<TravelAgencyDTO>> getAllTravelAgencies(Pageable pageable) throws URISyntaxException {
		log.debug("REST request to get a page of TravelAgencies");
		Page<TravelAgency> page = travelAgencyService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/travel-agencies");
		return new ResponseEntity<>(travelAgencyMapper.travelAgenciesToTravelAgencyDTOs(page.getContent()), headers,
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/travel-agencies", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@Transactional(readOnly = true)
	public ResponseEntity<Pager<TravelAgencyDTO>> getAllTravelAgencies(int limit,int offset,String sort,String order,SearchTravelAgency searchTravelAgency) throws URISyntaxException {
		log.debug("REST request to get a page of TravelAgencies");
		Pageable pageable = this.toPageable(limit, offset, sort, order);
		Page<TravelAgency> page = travelAgencyService.findByPageSearch(searchTravelAgency, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/travel-agencies");
		
		Pager<TravelAgencyDTO> pageDto = new Pager<TravelAgencyDTO>(travelAgencyMapper.travelAgenciesToTravelAgencyDTOs(page.getContent()),
				page.getTotalElements());
		return new ResponseEntity<>(pageDto, headers,
				HttpStatus.OK);
	}

	/**
	 * GET /travel-agencies/:id : get the "id" travelAgency.
	 *
	 * @param id
	 *            the id of the travelAgencyDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         travelAgencyDTO, or with status 404 (Not Found)
	 */
	@RequestMapping(value = "/travel-agencies/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<TravelAgencyDTO> getTravelAgency(@PathVariable Long id) {
		log.debug("REST request to get TravelAgency : {}", id);
		TravelAgencyDTO travelAgencyDTO = travelAgencyService.findOne(id);
		return Optional.ofNullable(travelAgencyDTO).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /travel-agencies/:id : delete the "id" travelAgency.
	 *
	 * @param id
	 *            the id of the travelAgencyDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/travel-agencies/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteTravelAgency(@PathVariable Long id) {
		log.debug("REST request to delete TravelAgency : {}", id);
		travelAgencyService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("travelAgency", id.toString())).build();
	}

	/**
	 * SEARCH /_search/travel-agencies?query=:query : search for the
	 * travelAgency corresponding to the query.
	 *
	 * @param query
	 *            the query of the travelAgency search
	 * @return the result of the search
	 */
	@RequestMapping(value = "/_search/travel-agencies", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@Transactional(readOnly = true)
	public ResponseEntity<List<TravelAgencyDTO>> searchTravelAgencies(@RequestParam String query, Pageable pageable)
			throws URISyntaxException {
		log.debug("REST request to search for a page of TravelAgencies for query {}", query);
		Page<TravelAgency> page = travelAgencyService.search(query, pageable);
		HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page,
				"/api/_search/travel-agencies");
		return new ResponseEntity<>(travelAgencyMapper.travelAgenciesToTravelAgencyDTOs(page.getContent()), headers,
				HttpStatus.OK);
	}

}
