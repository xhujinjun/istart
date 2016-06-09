package com.istart.framework.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.istart.framework.domain.Products;
import com.istart.framework.domain.search.ProductsSearch;
import com.istart.framework.repository.ProductsRepository;
import com.istart.framework.repository.search.ProductsSearchRepository;
import com.istart.framework.service.ProductsService;
import com.istart.framework.web.rest.dto.ProductsDTO;
import com.istart.framework.web.rest.mapper.ProductsMapper;

/**
 * Service Implementation for managing Products.
 */
@Service
@Transactional
public class ProductsServiceImpl implements ProductsService {

	private final Logger log = LoggerFactory.getLogger(ProductsServiceImpl.class);

	@Inject
	private ProductsRepository productsRepository;

	@Inject
	private ProductsMapper productsMapper;

	@Inject
	private ProductsSearchRepository productsSearchRepository;

	/**
	 * Save a products.
	 * 
	 * @param productsDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	public ProductsDTO save(ProductsDTO productsDTO) {
		log.debug("Request to save Products : {}", productsDTO);
		Products products = productsMapper.productsDTOToProducts(productsDTO);
		products = productsRepository.save(products);
		ProductsDTO result = productsMapper.productsToProductsDTO(products);
		productsSearchRepository.save(products);
		return result;
	}

	/**
	 * Get all the products.
	 * 
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<Products> findAll(Pageable pageable) {
		log.debug("Request to get all Products");
		Page<Products> result = productsRepository.findAll(pageable);
		return result;
	}

	/**
	 * Get one products by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public ProductsDTO findOne(Long id) {
		log.debug("Request to get Products : {}", id);
		Products products = productsRepository.findOne(id);
		ProductsDTO productsDTO = productsMapper.productsToProductsDTO(products);
		return productsDTO;
	}

	/**
	 * Delete the products by id.
	 * 
	 * @param id
	 *            the id of the entity
	 */
	public void delete(Long id) {
		log.debug("Request to delete Products : {}", id);
		productsRepository.delete(id);
		productsSearchRepository.delete(id);
	}

	/**
	 * Search for the products corresponding to the query.
	 *
	 * @param query
	 *            the query of the search
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<Products> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of Products for query {}", query);
		return productsSearchRepository.search(queryStringQuery(query), pageable);
	}

	@Override
	public Page<Products> findAllPro(ProductsSearch ps, Pageable pageable) {
		return productsRepository.findAll(getSpecification(ps), pageable);
	}

	private Specification<Products> getSpecification(ProductsSearch productSearch) {
		return new Specification<Products>() {
			@Override
			public Predicate toPredicate(Root<Products> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				String startadderss = productSearch.getStartadderss();
				if (StringUtils.isNotBlank(startadderss)) {
					Expression<String> expStart = root.get("startadderss").as(String.class);
					list.add(cb.like(expStart, startadderss));
				}

				Integer days = productSearch.getDays();
				if (days != null) {
					Expression<Integer> expId = root.get("days").as(Integer.class);
					list.add(cb.equal(expId, days));
				}
				BigDecimal price = productSearch.getPrice();
				if (price != null) {
					Expression<BigDecimal> expPrice = root.get("price").as(BigDecimal.class);
					list.add(cb.equal(expPrice, price));
				}
				LocalDate startdate = productSearch.getStartdate();
				if (startdate != null) {
					Expression<LocalDate> expSdate = root.get("startdate").as(LocalDate.class);
					list.add(cb.equal(expSdate, startdate));
				}
				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}
		};
	}

	@Override
	public Products findById(Long id) {
		return productsRepository.findOne(id);
	}

	@Override
	public boolean saveProduct(Products products) {
		try {
			productsRepository.save(products);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
