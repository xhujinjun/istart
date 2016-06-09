package com.istart.framework.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.istart.framework.domain.Trip;
import com.istart.framework.repository.TripRepository;
import com.istart.framework.repository.search.TripSearchRepository;
import com.istart.framework.service.TripService;
import com.istart.framework.web.rest.dto.TripDTO;
import com.istart.framework.web.rest.mapper.TripMapper;

/**
 * Service Implementation for managing Trip.
 */
@Service
@Transactional
public class TripServiceImpl implements TripService {

	private final Logger log = LoggerFactory.getLogger(TripServiceImpl.class);

	@Inject
	private TripRepository tripRepository;

	@Inject
	private TripMapper tripMapper;

	@Inject
	private TripSearchRepository tripSearchRepository;

	/**
	 * Save a trip.
	 * 
	 * @param tripDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	public TripDTO save(TripDTO tripDTO) {
		log.debug("Request to save Trip : {}", tripDTO);
		Trip trip = tripMapper.tripDTOToTrip(tripDTO);
		trip = tripRepository.save(trip);
		TripDTO result = tripMapper.tripToTripDTO(trip);
		tripSearchRepository.save(trip);
		return result;
	}

	/**
	 * Get all the trips.
	 * 
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<Trip> findAll(Pageable pageable) {
		log.debug("Request to get all Trips");
		Page<Trip> result = tripRepository.findAll(pageable);
		return result;
	}

	/**
	 * Get one trip by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public TripDTO findOne(Long id) {
		log.debug("Request to get Trip : {}", id);
		Trip trip = tripRepository.findOne(id);
		TripDTO tripDTO = tripMapper.tripToTripDTO(trip);
		return tripDTO;
	}

	/**
	 * Delete the trip by id.
	 * 
	 * @param id
	 *            the id of the entity
	 */
	public void delete(Long id) {
		log.debug("Request to delete Trip : {}", id);
		tripRepository.delete(id);
		tripSearchRepository.delete(id);
	}

	/**
	 * Search for the trip corresponding to the query.
	 *
	 * @param query
	 *            the query of the search
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<Trip> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of Trips for query {}", query);
		return tripSearchRepository.search(queryStringQuery(query), pageable);
	}

	@Override
	public boolean deleteByPno(String pno) {
		try {
			tripRepository.deleteByPno(pno);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
