package com.istart.framework.service;

import com.istart.framework.domain.Product;
import com.istart.framework.domain.search.ProductSearch;
import com.istart.framework.web.rest.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Product.
 */
public interface ProductService {

    /**
     * Save a product.
     * 
     * @param productDTO the entity to save
     * @return the persisted entity
     */
    ProductDTO save(ProductDTO productDTO);

    /**
     *  Get all the products.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Product> findAll(Pageable pageable);

    /**
     *  Get the "id" product.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    ProductDTO findOne(Long id);

    /**
     *  Delete the "id" product.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the product corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    Page<Product> search(String query, Pageable pageable);
    
    /**
     * 分页查询
     * @param ps
     * @param pageable
     * @return
     */
    Page<Product> findByProductSearch(ProductSearch ps,Pageable pageable);
}
