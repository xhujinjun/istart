package com.istart.framework.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.istart.framework.domain.Pictrues;
import com.istart.framework.web.rest.dto.PictruesDTO;

/**
 * Service Interface for managing Pictrues.
 */
public interface PictruesService {

	/**
	 * Save a pictrues.
	 * 
	 * @param pictruesDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	PictruesDTO save(PictruesDTO pictruesDTO);

	/**
	 * Get all the pictrues.
	 * 
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	Page<Pictrues> findAll(Pageable pageable);

	/**
	 * Get the "id" pictrues.
	 * 
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	PictruesDTO findOne(Long id);

	/**
	 * Delete the "id" pictrues.
	 * 
	 * @param id
	 *            the id of the entity
	 */
	void delete(Long id);

	/**
	 * Search for the pictrues corresponding to the query.
	 * 
	 * @param query
	 *            the query of the search
	 * @return the list of entities
	 */
	Page<Pictrues> search(String query, Pageable pageable);

	boolean deleteByPno(String pno);
}
