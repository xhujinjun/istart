package com.istart.framework.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.istart.framework.domain.Trip;
import com.istart.framework.web.rest.dto.TripDTO;

/**
 * Service Interface for managing Trip.
 */
public interface TripService {

	/**
	 * Save a trip.
	 * 
	 * @param tripDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	TripDTO save(TripDTO tripDTO);

	/**
	 * Get all the trips.
	 * 
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	Page<Trip> findAll(Pageable pageable);

	/**
	 * Get the "id" trip.
	 * 
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	TripDTO findOne(Long id);

	/**
	 * Delete the "id" trip.
	 * 
	 * @param id
	 *            the id of the entity
	 */
	void delete(Long id);

	/**
	 * Search for the trip corresponding to the query.
	 * 
	 * @param query
	 *            the query of the search
	 * @return the list of entities
	 */
	Page<Trip> search(String query, Pageable pageable);

	public boolean deleteByPno(String pno);
}
