package com.istart.framework.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.istart.framework.domain.Pictrues;
import com.istart.framework.repository.PictruesRepository;
import com.istart.framework.repository.search.PictruesSearchRepository;
import com.istart.framework.service.PictruesService;
import com.istart.framework.web.rest.dto.PictruesDTO;
import com.istart.framework.web.rest.mapper.PictruesMapper;

/**
 * Service Implementation for managing Pictrues.
 */
@Service
@Transactional
public class PictruesServiceImpl implements PictruesService {

	private final Logger log = LoggerFactory.getLogger(PictruesServiceImpl.class);

	@Inject
	private PictruesRepository pictruesRepository;

	@Inject
	private PictruesMapper pictruesMapper;

	@Inject
	private PictruesSearchRepository pictruesSearchRepository;

	/**
	 * Save a pictrues.
	 * 
	 * @param pictruesDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	public PictruesDTO save(PictruesDTO pictruesDTO) {
		log.debug("Request to save Pictrues : {}", pictruesDTO);
		Pictrues pictrues = pictruesMapper.pictruesDTOToPictrues(pictruesDTO);
		pictrues = pictruesRepository.save(pictrues);
		PictruesDTO result = pictruesMapper.pictruesToPictruesDTO(pictrues);
		pictruesSearchRepository.save(pictrues);
		return result;
	}

	/**
	 * Get all the pictrues.
	 * 
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<Pictrues> findAll(Pageable pageable) {
		log.debug("Request to get all Pictrues");
		Page<Pictrues> result = pictruesRepository.findAll(pageable);
		return result;
	}

	/**
	 * Get one pictrues by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public PictruesDTO findOne(Long id) {
		log.debug("Request to get Pictrues : {}", id);
		Pictrues pictrues = pictruesRepository.findOne(id);
		PictruesDTO pictruesDTO = pictruesMapper.pictruesToPictruesDTO(pictrues);
		return pictruesDTO;
	}

	/**
	 * Delete the pictrues by id.
	 * 
	 * @param id
	 *            the id of the entity
	 */
	public void delete(Long id) {
		log.debug("Request to delete Pictrues : {}", id);
		pictruesRepository.delete(id);
		pictruesSearchRepository.delete(id);
	}

	/**
	 * Search for the pictrues corresponding to the query.
	 *
	 * @param query
	 *            the query of the search
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<Pictrues> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of Pictrues for query {}", query);
		return pictruesSearchRepository.search(queryStringQuery(query), pageable);
	}

	@Override
	public boolean deleteByPno(String pno) {
		try {
			pictruesRepository.deleteByPno(pno);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
