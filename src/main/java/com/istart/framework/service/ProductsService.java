package com.istart.framework.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.istart.framework.domain.Products;
import com.istart.framework.domain.search.ProductsSearch;
import com.istart.framework.web.rest.dto.ProductsDTO;

/**
 * Service Interface for managing Products.
 */
public interface ProductsService {

	/**
	 * Save a products.
	 * 
	 * @param productsDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	ProductsDTO save(ProductsDTO productsDTO);

	/**
	 * Get all the products.
	 * 
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	Page<Products> findAll(Pageable pageable);

	/**
	 * Get the "id" products.
	 * 
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	ProductsDTO findOne(Long id);

	/**
	 * Delete the "id" products.
	 * 
	 * @param id
	 *            the id of the entity
	 */
	void delete(Long id);

	/**
	 * Search for the products corresponding to the query.
	 * 
	 * @param query
	 *            the query of the search
	 * @return the list of entities
	 */
	Page<Products> search(String query, Pageable pageable);

	/**
	 * 分页查询
	 * 
	 * @param ps
	 * @param pageable
	 * @return
	 */
	Page<Products> findAllPro(ProductsSearch ps, Pageable pageable);

	/**
	 * 通过id查找
	 * 
	 * @param id
	 * @return
	 */
	Products findById(Long id);

	/**
	 * 新增
	 * 
	 * @param products
	 * @return
	 */
	boolean saveProduct(Products products);

}
