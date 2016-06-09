package com.istart.framework.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.istart.framework.domain.Product;
import com.istart.framework.domain.search.ProductPage;
import com.istart.framework.domain.search.ProductSearch;
import com.istart.framework.service.ProductService;
import com.istart.framework.web.rest.dto.ProductDTO;
import com.istart.framework.web.rest.mapper.ProductMapper;
import com.istart.framework.web.rest.util.HeaderUtil;
import com.istart.framework.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Product.
 */
@RestController
@RequestMapping("/api")
public class ProductResource {

	private final Logger log = LoggerFactory.getLogger(ProductResource.class);

	@Inject
	private ProductService productService;

	@Inject
	private ProductMapper productMapper;

	/**
	 * POST /products : Create a new product.
	 *
	 * @param productDTO
	 *            the productDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new productDTO, or with status 400 (Bad Request) if the product
	 *         has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/productss", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) throws URISyntaxException {
		log.debug("REST request to save Product : {}", productDTO);
		if (productDTO.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert("product", "idexists", "A new product cannot already have an ID"))
					.body(null);
		}
		ProductDTO result = productService.save(productDTO);
		return ResponseEntity.created(new URI("/api/products/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("product", result.getId().toString())).body(result);
	}

	/**
	 * PUT /products : Updates an existing product.
	 *
	 * @param productDTO
	 *            the productDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         productDTO, or with status 400 (Bad Request) if the productDTO is
	 *         not valid, or with status 500 (Internal Server Error) if the
	 *         productDTO couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/productss", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) throws URISyntaxException {
		log.debug("REST request to update Product : {}", productDTO);
		if (productDTO.getId() == null) {
			return createProduct(productDTO);
		}
		ProductDTO result = productService.save(productDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("product", productDTO.getId().toString()))
				.body(result);
	}

	/**
	 * GET /products : get all the products.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of products
	 *         in body
	 * @throws URISyntaxException
	 *             if there is an error to generate the pagination HTTP headers
	 */
	@RequestMapping(value = "/productss", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@Transactional(readOnly = true)
	public ResponseEntity<List<ProductDTO>> getAllProducts(Pageable pageable) throws URISyntaxException {
		log.debug("REST request to get a page of Products");
		Page<Product> page = productService.findAll(pageable);
		System.out.println(pageable.getSort());
		Sort sort = pageable.getSort();
		System.out.println("---------- " + sort.spliterator());
		System.out.println("pageable = " + pageable);
		Sort s = new Sort("id: ASC");
		System.out.println(" -       ----  " + s.toString());
		System.out.println("----- 000 -- " + new Sort("asc", "id"));
		System.out.println(new Sort("id", "ASC"));
		System.out.println(new Sort("id"));

		Order order = new Order(Direction.fromString("asc"), "id");
		System.out.println("new ---=-  " + new Sort(order));
		// String result = String.format("%s: %s", property, direction);
		System.out.println(String.format("%s: %s", "id", "asc"));
		System.out.println(String.format("%s: %s", "asc", "id"));
		ProductSearch ps = new ProductSearch();
		ps.setDays(1);

		Page<Product> page1 = productService.findByProductSearch(ps, pageable);
		System.out.println("page1.getContent()= " + page1.getContent());
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/products");
		return new ResponseEntity<>(productMapper.productsToProductDTOs(page.getContent()), headers, HttpStatus.OK);
	}

	/**
	 * GET /products/:id : get the "id" product.
	 *
	 * @param id
	 *            the id of the productDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         productDTO, or with status 404 (Not Found)
	 */
	@RequestMapping(value = "/productss/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
		log.debug("REST request to get Product : {}", id);
		ProductDTO productDTO = productService.findOne(id);
		return Optional.ofNullable(productDTO).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /products/:id : delete the "id" product.
	 *
	 * @param id
	 *            the id of the productDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/productss/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		log.debug("REST request to delete Product : {}", id);
		productService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("product", id.toString())).build();
	}

	/**
	 * SEARCH /_search/products?query=:query : search for the product
	 * corresponding to the query.
	 *
	 * @param query
	 *            the query of the product search
	 * @return the result of the search
	 */
	@RequestMapping(value = "/_search/productss", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@Transactional(readOnly = true)
	public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam String query, @RequestParam String name,
			Pageable pageable) throws URISyntaxException {
		log.debug("REST request to search for a page of Products for query {}", query);
		System.out.println("name = " + name);
		Page<Product> page = productService.search(query, pageable);
		HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/products");
		return new ResponseEntity<>(productMapper.productsToProductDTOs(page.getContent()), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/pagesearch/products", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@Transactional(readOnly = true)
	public ResponseEntity<List<ProductDTO>> pageSearch(@RequestBody ProductSearch ps) throws URISyntaxException {
		log.debug("REST request to search for a page of Products for query {}");

		ProductPage pp = new ProductPage();
		pp.setPageNumber(ps.getPage());
		pp.setPageSize(ps.getSize());
		Sort sort = new Sort(new Order(Direction.fromString(ps.getDirection()), ps.getProperty()));
		pp.setSort(sort);
		Page<Product> page = productService.findByProductSearch(ps, pp);
		System.out.println("结果 = " + page.getContent() + "  ");
		// System.out.println("name = " + query);
		// Page<Product> page = productService.search(query, pageable);
		// HttpHeaders headers =
		// PaginationUtil.generateSearchPaginationHttpHeaders(query, page,
		// "/api/_search/products");
		// return new
		// ResponseEntity<>(productMapper.productsToProductDTOs(page.getContent()),
		// headers, HttpStatus.OK);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pagesearch/products");
		return new ResponseEntity<>(productMapper.productsToProductDTOs(page.getContent()), headers, HttpStatus.OK);
	}

}
