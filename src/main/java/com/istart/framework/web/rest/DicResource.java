package com.istart.framework.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
import com.istart.framework.domain.Dic;
import com.istart.framework.service.DicService;
import com.istart.framework.web.rest.base.BaseResource;
import com.istart.framework.web.rest.base.Pager;
import com.istart.framework.web.rest.dto.DicDTO;
import com.istart.framework.web.rest.mapper.DicMapper;
import com.istart.framework.web.rest.search.SearchDic;
import com.istart.framework.web.rest.util.HeaderUtil;
import com.istart.framework.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Dic.
 */
@RestController
@RequestMapping("/api")
public class DicResource extends BaseResource {

	private final Logger log = LoggerFactory.getLogger(DicResource.class);

	@Inject
	private DicService dicService;

	@Inject
	private DicMapper dicMapper;

	/**
	 * POST /dics : Create a new dic.
	 *
	 * @param dicDTO
	 *            the dicDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new dicDTO, or with status 400 (Bad Request) if the dic has
	 *         already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/dics", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<DicDTO> createDic(@RequestBody DicDTO dicDTO) throws URISyntaxException {
		log.debug("REST request to save Dic : {}", dicDTO);
		if (dicDTO.getId() != null) {
			return ResponseEntity.badRequest()
					.headers(HeaderUtil.createFailureAlert("dic", "idexists", "A new dic cannot already have an ID"))
					.body(null);
		}
		DicDTO result = dicService.save(dicDTO);
		return ResponseEntity.created(new URI("/api/dics/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("dic", result.getId().toString())).body(result);
	}

	/**
	 * PUT /dics : Updates an existing dic.
	 *
	 * @param dicDTO
	 *            the dicDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         dicDTO, or with status 400 (Bad Request) if the dicDTO is not
	 *         valid, or with status 500 (Internal Server Error) if the dicDTO
	 *         couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/dics", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<DicDTO> updateDic(@RequestBody DicDTO dicDTO) throws URISyntaxException {
		log.debug("REST request to update Dic : {}", dicDTO);
		if (dicDTO.getId() == null) {
			return createDic(dicDTO);
		}
		DicDTO result = dicService.save(dicDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("dic", dicDTO.getId().toString()))
				.body(result);
	}

	/**
	 * GET /dics : get all the dics.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of dics in
	 *         body
	 * @throws URISyntaxException
	 *             if there is an error to generate the pagination HTTP headers
	 */
	@RequestMapping(value = "/dics/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@Transactional(readOnly = true)
	public ResponseEntity<List<DicDTO>> getAllDics(SearchDic searchDic, Pageable pageable) throws URISyntaxException {
		log.debug("REST request to get a page of Dics");
		Page<Dic> page = dicService.findByPageSearch(searchDic, null);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dics");
		return new ResponseEntity<>(dicMapper.dicsToDicDTOs(page.getContent()), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/dics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@Transactional(readOnly = true)
	public ResponseEntity<Pager<DicDTO>> getAllDics(int limit, int offset, String sort, String order,
			SearchDic searchDic) throws URISyntaxException {
		log.debug("REST request to get a page of Dics");
		Pageable pageable = this.toPageable(limit, offset, sort, order);
		log.debug("REST request to get a page of Dics");
		Page<Dic> page = dicService.findByPageSearch(searchDic, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dics");

		Pager<DicDTO> pagerDto = new Pager<>(dicMapper.dicsToDicDTOs(page.getContent()), page.getTotalElements());
		return new ResponseEntity<>(pagerDto, headers, HttpStatus.OK);
	}

	/**
	 * GET /dics/:id : get the "id" dic.
	 *
	 * @param id
	 *            the id of the dicDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the dicDTO,
	 *         or with status 404 (Not Found)
	 */
	@RequestMapping(value = "/dics/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<DicDTO> getDic(@PathVariable Long id) {
		log.debug("REST request to get Dic : {}", id);
		DicDTO dicDTO = dicService.findOne(id);
		return Optional.ofNullable(dicDTO).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /dics/:id : delete the "id" dic.
	 *
	 * @param id
	 *            the id of the dicDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/dics/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteDic(@PathVariable Long id) {
		log.debug("REST request to delete Dic : {}", id);
		dicService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dic", id.toString())).build();
	}

	/**
	 * SEARCH /_search/dics?query=:query : search for the dic corresponding to
	 * the query.
	 *
	 * @param query
	 *            the query of the dic search
	 * @return the result of the search
	 */
	@RequestMapping(value = "/_search/dics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@Transactional(readOnly = true)
	public ResponseEntity<List<DicDTO>> searchDicsByEla(@RequestParam String query, Pageable pageable)
			throws URISyntaxException {
		log.debug("REST request to search for a page of Dics for query {}", query);
		Page<Dic> page = dicService.search(query, pageable);
		HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/dics");
		return new ResponseEntity<>(dicMapper.dicsToDicDTOs(page.getContent()), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/_searchDB/dics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@Transactional(readOnly = true)
	public ResponseEntity<DicDTO> searchDics(SearchDic searchDic, Pageable pageable) throws URISyntaxException {
		log.debug("REST request to search for a page of Dics for query {}", searchDic);
		Dic dic = dicService.searchByDb(searchDic);
		return new ResponseEntity<>(dicMapper.dicToDicDTO(dic), null, HttpStatus.OK);
	}

	@RequestMapping(value = "/getDiclist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@Transactional(readOnly = true)
	public ResponseEntity<List<Dic>> getDics(String dicTypeCode) throws URISyntaxException {
		log.debug("获取" + dicTypeCode + "对应的字典");
		List<Dic> dic = dicService.searchByDicTypeCode(dicTypeCode);
		return new ResponseEntity<>(dic, null, HttpStatus.OK);
	}

	@RequestMapping(value = "/getDicDtolist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@Transactional(readOnly = true)
	public ResponseEntity<List<DicDTO>> getAllDicDtos(String dicTypeCode) throws URISyntaxException {
		log.debug("获取" + dicTypeCode + "对应的字典");
		List<Dic> dic = dicService.searchByDicTypeCode(dicTypeCode);
		List<DicDTO> ddto = new ArrayList<>();
		for (Dic dic2 : dic) {
			DicDTO dd = new DicDTO();
			dd.setDicCode(dic2.getDicCode());
			dd.setId(dic2.getId());
			dd.setDicName(dic2.getDicName());
			ddto.add(dd);
		}

		return new ResponseEntity<>(ddto, null, HttpStatus.OK);
	}
}
