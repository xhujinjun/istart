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

import com.istart.framework.domain.Product;
import com.istart.framework.domain.search.ProductSearch;
import com.istart.framework.repository.ProductRepository;
import com.istart.framework.repository.search.ProductSearchRepository;
import com.istart.framework.service.ProductService;
import com.istart.framework.web.rest.dto.ProductDTO;
import com.istart.framework.web.rest.mapper.ProductMapper;

/**
 * Service Implementation for managing Product.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Inject
	private ProductRepository productRepository;

	@Inject
	private ProductMapper productMapper;

	@Inject
	private ProductSearchRepository productSearchRepository;

	/**
	 * Save a product.
	 * 
	 * @param productDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	public ProductDTO save(ProductDTO productDTO) {
		log.debug("Request to save Product : {}", productDTO);
		Product product = productMapper.productDTOToProduct(productDTO);
		product = productRepository.save(product);
		ProductDTO result = productMapper.productToProductDTO(product);
		productSearchRepository.save(product);
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
	public Page<Product> findAll(Pageable pageable) {
		log.debug("Request to get all Products");
		Page<Product> result = productRepository.findAll(pageable);
		return result;
	}

	/**
	 * Get one product by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public ProductDTO findOne(Long id) {
		log.debug("Request to get Product : {}", id);
		Product product = productRepository.findOne(id);
		ProductDTO productDTO = productMapper.productToProductDTO(product);
		return productDTO;
	}

	/**
	 * Delete the product by id.
	 * 
	 * @param id
	 *            the id of the entity
	 */
	public void delete(Long id) {
		log.debug("Request to delete Product : {}", id);
		productRepository.delete(id);
		productSearchRepository.delete(id);
	}

	/**
	 * Search for the product corresponding to the query.
	 *
	 * @param query
	 *            the query of the search
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<Product> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of Products for query {}", query);
		return productSearchRepository.search(queryStringQuery(query), pageable);
	}

	@Override
	public Page<Product> findByProductSearch(ProductSearch ps, Pageable pageable) {
		return productRepository.findAll(getSpecification(ps), pageable);
	}

	private Specification<Product> getSpecification(ProductSearch productSearch) {
		return new Specification<Product>() {
			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				// private Integer days;
				// private LocalDate startdate;
				// private BigDecimal price;
				// private String start;
				String start = productSearch.getStart();
				if (StringUtils.isNotBlank(start)) {
					Expression<String> expStart = root.get("start").as(String.class);
					list.add(cb.like(expStart, start));
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
}
