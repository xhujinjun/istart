package com.istart.framework.service;

import com.istart.framework.domain.Hotel;
import com.istart.framework.web.rest.dto.HotelDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Hotel.
 */
public interface HotelService {

    /**
     * Save a book.
     * 
     * @param bookDTO the entity to save
     * @return the persisted entity
     */
    HotelDTO save(HotelDTO bookDTO);

    /**
     *  Get all the books.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Hotel> findAll(Pageable pageable);

    /**
     *  Get the "id" book.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    HotelDTO findOne(Long id);

    /**
     *  Delete the "id" book.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the book corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    Page<Hotel> search(String query, Pageable pageable);
}
