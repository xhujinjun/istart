package com.istart.framework.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.istart.framework.domain.Hotel;
import com.istart.framework.repository.HotelRepository;
import com.istart.framework.repository.search.HotelSearchRepository;
import com.istart.framework.service.HotelService;
import com.istart.framework.web.rest.dto.HotelDTO;
import com.istart.framework.web.rest.mapper.HotelMapper;

/**
 * Service Implementation for managing Book.
 */
@Service
@Transactional
public class HotelServiceImpl implements HotelService{

    private final Logger log = LoggerFactory.getLogger(HotelServiceImpl.class);
    
    @Inject
    private HotelRepository hotelRepository;
    
    @Inject
    private HotelMapper hotelMapper;
    
    @Inject
    private HotelSearchRepository hotelSearchRepository;
    
    /**
     * Save a book.
     * 
     * @param bookDTO the entity to save
     * @return the persisted entity
     */
    public HotelDTO save(HotelDTO hotelDTO) {
        log.debug("Request to save Book : {}", hotelDTO);
        Hotel hotel = hotelMapper.HotelDTOToHotel(hotelDTO);
        hotel=hotelRepository.save(hotel);
        HotelDTO result = hotelMapper.hotelToHotelDTO(hotel);
        hotelSearchRepository.save(hotel);
        return result;
    }

    /**
     *  Get all the books.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Hotel> findAll(Pageable pageable) {
        log.debug("Request to get all Hotels");
        Page<Hotel> result = hotelRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one book by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public HotelDTO findOne(Long id) {
        log.debug("Request to get Book : {}", id);
        Hotel hotel = hotelRepository.findOne(id);
        HotelDTO hotelDTO = hotelMapper.hotelToHotelDTO(hotel);
        return hotelDTO;
    }

    /**
     *  Delete the  book by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Hotel : {}", id);
        hotelRepository.delete(id);
        hotelSearchRepository.delete(id);
    }

    /**
     * Search for the book corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Hotel> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Books for query {}", query);
        return hotelSearchRepository.search(queryStringQuery(query), pageable);
    }

	
}
