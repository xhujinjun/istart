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
import com.istart.framework.domain.Pictrues;
import com.istart.framework.domain.search.ProductSearch;
import com.istart.framework.service.PictruesService;
import com.istart.framework.web.rest.base.Pager;
import com.istart.framework.web.rest.dto.PictruesDTO;
import com.istart.framework.web.rest.dto.ProductsDTO;
import com.istart.framework.web.rest.mapper.PictruesMapper;
import com.istart.framework.web.rest.util.HeaderUtil;
import com.istart.framework.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Pictrues.
 */
@RestController
@RequestMapping("/api")
public class PictruesResource {

	private final Logger log = LoggerFactory.getLogger(PictruesResource.class);

	@Inject
	private PictruesService pictruesService;

	@Inject
	private PictruesMapper pictruesMapper;

	/**
	 * POST /pictrues : Create a new pictrues.
	 *
	 * @param pictruesDTO
	 *            the pictruesDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new pictruesDTO, or with status 400 (Bad Request) if the pictrues
	 *         has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/pictrues", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<PictruesDTO> createPictrues(@RequestBody PictruesDTO pictruesDTO) throws URISyntaxException {
		log.debug("REST request to save Pictrues : {}", pictruesDTO);
		if (pictruesDTO.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert("pictrues", "idexists", "A new pictrues cannot already have an ID"))
					.body(null);
		}
		PictruesDTO result = pictruesService.save(pictruesDTO);
		return ResponseEntity.created(new URI("/api/pictrues/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("pictrues", result.getId().toString())).body(result);
	}

	/**
	 * PUT /pictrues : Updates an existing pictrues.
	 *
	 * @param pictruesDTO
	 *            the pictruesDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         pictruesDTO, or with status 400 (Bad Request) if the pictruesDTO
	 *         is not valid, or with status 500 (Internal Server Error) if the
	 *         pictruesDTO couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/pictrues", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<PictruesDTO> updatePictrues(@RequestBody PictruesDTO pictruesDTO) throws URISyntaxException {
		log.debug("REST request to update Pictrues : {}", pictruesDTO);
		if (pictruesDTO.getId() == null) {
			return createPictrues(pictruesDTO);
		}
		PictruesDTO result = pictruesService.save(pictruesDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("pictrues", pictruesDTO.getId().toString())).body(result);
	}

	/**
	 * GET /pictrues : get all the pictrues.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of pictrues
	 *         in body
	 * @throws URISyntaxException
	 *             if there is an error to generate the pagination HTTP headers
	 */
	@RequestMapping(value = "/pictrues", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@Transactional(readOnly = true)
	public ResponseEntity<List<PictruesDTO>> getAllPictrues(Pageable pageable) throws URISyntaxException {
		log.debug("REST request to get a page of Pictrues");
		Page<Pictrues> page = pictruesService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pictrues");
		return new ResponseEntity<>(pictruesMapper.pictruesToPictruesDTOs(page.getContent()), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/products/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@Transactional(readOnly = true)
	public ResponseEntity<Pager<ProductsDTO>> getAllProducts(int limit, int offset, String sort, String order,
			ProductSearch ps) throws URISyntaxException {
		log.debug("REST request to get a page of Products");
		return null;
	}

	/**
	 * GET /pictrues/:id : get the "id" pictrues.
	 *
	 * @param id
	 *            the id of the pictruesDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         pictruesDTO, or with status 404 (Not Found)
	 */
	@RequestMapping(value = "/pictrues/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<PictruesDTO> getPictrues(@PathVariable Long id) {
		log.debug("REST request to get Pictrues : {}", id);
		PictruesDTO pictruesDTO = pictruesService.findOne(id);
		return Optional.ofNullable(pictruesDTO).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /pictrues/:id : delete the "id" pictrues.
	 *
	 * @param id
	 *            the id of the pictruesDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/pictrues/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deletePictrues(@PathVariable Long id) {
		log.debug("REST request to delete Pictrues : {}", id);
		pictruesService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pictrues", id.toString())).build();
	}

	/**
	 * SEARCH /_search/pictrues?query=:query : search for the pictrues
	 * corresponding to the query.
	 *
	 * @param query
	 *            the query of the pictrues search
	 * @return the result of the search
	 */
	@RequestMapping(value = "/_search/pictrues", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@Transactional(readOnly = true)
	public ResponseEntity<List<PictruesDTO>> searchPictrues(@RequestParam String query, Pageable pageable)
			throws URISyntaxException {
		log.debug("REST request to search for a page of Pictrues for query {}", query);
		Page<Pictrues> page = pictruesService.search(query, pageable);
		HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/pictrues");
		return new ResponseEntity<>(pictruesMapper.pictruesToPictruesDTOs(page.getContent()), headers, HttpStatus.OK);
	}

}
