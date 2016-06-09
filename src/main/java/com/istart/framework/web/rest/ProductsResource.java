package com.istart.framework.web.rest;

import java.io.File;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.codahale.metrics.annotation.Timed;
import com.istart.framework.domain.Products;
import com.istart.framework.domain.search.ProductsSearch;
import com.istart.framework.service.PictruesService;
import com.istart.framework.service.ProductsService;
import com.istart.framework.service.TripService;
import com.istart.framework.web.rest.base.BaseResource;
import com.istart.framework.web.rest.base.Pager;
import com.istart.framework.web.rest.dto.PictruesDTO;
import com.istart.framework.web.rest.dto.ProductsDTO;
import com.istart.framework.web.rest.mapper.ProductsMapper;
import com.istart.framework.web.rest.util.HeaderUtil;
import com.istart.framework.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Products.
 */
@RestController
@RequestMapping("/api")
public class ProductsResource extends BaseResource {

	private final Logger log = LoggerFactory.getLogger(ProductsResource.class);

	@Inject
	private ProductsService productsService;

	@Inject
	private PictruesService pictruesService;

	@Inject
	private TripService tripService;

	@Inject
	private ProductsMapper productsMapper;

	/**
	 * POST /products : Create a new products.
	 *
	 * @param productsDTO
	 *            the productsDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new productsDTO, or with status 400 (Bad Request) if the products
	 *         has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/products", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<ProductsDTO> createProducts(@RequestBody ProductsDTO productsDTO) throws URISyntaxException {
		log.debug("REST request to save Products : {}", productsDTO);
		if (productsDTO.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert("products", "idexists", "A new products cannot already have an ID"))
					.body(null);
		}
		ProductsDTO result = productsService.save(productsDTO);
		return ResponseEntity.created(new URI("/api/products/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("products", result.getId().toString())).body(result);
	}

	/**
	 * PUT /products : Updates an existing products.
	 *
	 * @param productsDTO
	 *            the productsDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         productsDTO, or with status 400 (Bad Request) if the productsDTO
	 *         is not valid, or with status 500 (Internal Server Error) if the
	 *         productsDTO couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/products", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<ProductsDTO> updateProducts(@RequestBody ProductsDTO productsDTO) throws URISyntaxException {
		log.debug("REST request to update Products : {}", productsDTO);
		if (productsDTO.getId() == null) {
			return createProducts(productsDTO);
		}
		ProductsDTO result = productsService.save(productsDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("products", productsDTO.getId().toString())).body(result);
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
	@RequestMapping(value = "/products", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@Transactional(readOnly = true)
	public ResponseEntity<List<ProductsDTO>> getAllProducts(Pageable pageable) throws URISyntaxException {
		log.debug("REST request to get a page of Products");
		Page<Products> page = productsService.findAll(pageable);
		System.out.println("取数据1！！！！！！！！！！！！！！");
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/products");
		return new ResponseEntity<>(productsMapper.productsToProductsDTOs(page.getContent()), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/getproducts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@Transactional(readOnly = true)
	public ResponseEntity<Pager<Products>> getAllProducts(int limit, int offset, String sort, String order,
			ProductsSearch pss) throws URISyntaxException {
		log.debug("REST request to get a page of Products");
		System.out.println("取数据！！！！！！！！！！！！！！！！！！！");
		Pageable pageable = this.toPageable(limit, offset, sort, order);
		Page<Products> page = productsService.findAllPro(pss, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/products");
		Pager<Products> pagerDto = new Pager<>(page.getContent(), page.getTotalElements());
		return new ResponseEntity<>(pagerDto, headers, HttpStatus.OK);
	}

	/**
	 * GET /products/:id : get the "id" products.
	 *
	 * @param id
	 *            the id of the productsDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         productsDTO, or with status 404 (Not Found)
	 */
	@RequestMapping(value = "/oldproducts/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<ProductsDTO> getProducts(@PathVariable Long id) {
		log.debug("REST request to get Products : {}", id);
		ProductsDTO productsDTO = productsService.findOne(id);
		return Optional.ofNullable(productsDTO).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Products> getProductsByid(@PathVariable Long id) {
		log.debug("REST request to get Products : {}", id);
		// ProductsDTO productsDTO = productsService.findOne(id);
		Products products = productsService.findById(id);
		return Optional.ofNullable(products).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /products/:id : delete the "id" products.
	 *
	 * @param id
	 *            the id of the productsDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteProducts(@PathVariable Long id) {
		log.debug("REST request to delete Products : {}", id);
		Products products = productsService.findById(id);
		// 删除pictrues
		boolean pictrueOk = pictruesService.deleteByPno(products.getPno());
		// 删除trip
		boolean tripOk = tripService.deleteByPno(products.getPno());

		if (pictrueOk && tripOk) {

			productsService.delete(id);
		}
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("products", id.toString())).build();
	}

	/**
	 * SEARCH /_search/products?query=:query : search for the products
	 * corresponding to the query.
	 *
	 * @param query
	 *            the query of the products search
	 * @return the result of the search
	 */
	@RequestMapping(value = "/_search/products", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@Transactional(readOnly = true)
	public ResponseEntity<List<ProductsDTO>> searchProducts(@RequestParam String query, Pageable pageable)
			throws URISyntaxException {
		log.debug("REST request to search for a page of Products for query {}", query);
		Page<Products> page = productsService.search(query, pageable);
		HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/products");
		return new ResponseEntity<>(productsMapper.productsToProductsDTOs(page.getContent()), headers, HttpStatus.OK);
	}

	@RequestMapping("/saveproduct")
	public ResponseEntity<Products> saveDate(HttpServletRequest req) {
		log.debug("发布旅游消息");
		try {
			// Long id = Long.valueOf(req.getParameter("id"));
			String pno = req.getParameter("field_pno");
			Long travelAgentId = Long.valueOf(req.getParameter("field_travelAgentId"));
			String phone = req.getParameter("field_phone");
			String title = req.getParameter("field_title");
			BigDecimal price = new BigDecimal(req.getParameter("field_price"));
			String pricedesc = req.getParameter("field_pricedesc");
			String preferential = req.getParameter("field_preferential");
			LocalDate startdate = LocalDate.parse(req.getParameter("field_startdate"));
			String startadderss = req.getParameter("field_startadderss");
			String endadderss = req.getParameter("field_endadderss");
			Integer days = Integer.valueOf(req.getParameter("field_days"));
			String costdesc = req.getParameter("field_costdesc");
			String route = req.getParameter("field_route");
			String detaildesc = req.getParameter("field_detaildesc");
			String bookNotice = req.getParameter("field_bookNotice");
			Long tourismTypesId = Long.valueOf(req.getParameter("dicId"));
			Long detailTypeId = Long.valueOf(req.getParameter("detaile"));
			String detailTrip = req.getParameter("detailTrip");

			Products products = new Products();
			products.setBookNotice(bookNotice);
			products.setCostdesc(costdesc);
			products.setDataCreateDatetime(UDateToLocalDate());
			products.setDataCreator("");
			products.setDataStatus(1);
			products.setDataUpdateDatetime(UDateToLocalDate());
			products.setDataUpdater("");
			products.setDays(days);
			products.setDetaildesc(detaildesc);
			products.setDetailTypeId(detailTypeId);
			products.setEndadderss(endadderss);
			// productsDTO.setId(id);
			products.setPhone(phone);
			products.setPno(pno);
			products.setPreferential(preferential);
			products.setPrice(price);
			products.setPricedesc(pricedesc);
			products.setRoute(route);
			products.setStartadderss(startadderss);
			products.setStartdate(startdate);
			products.setTitle(title);
			products.setTourismTypesId(tourismTypesId);
			products.setTravelAgentId(travelAgentId);
			products.setDetailTrip(detailTrip);

			productsService.saveProduct(products);
			uploadFiles(req, pno);

			return ResponseEntity.created(new URI("/api/products/" + products.getId()))
					.headers(HeaderUtil.createEntityCreationAlert("products", products.getId().toString()))
					.body(products);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 上传图片
	 * 
	 * @param req
	 * @param pno
	 */
	public void uploadFiles(HttpServletRequest req, String pno) {
		StandardMultipartHttpServletRequest smr = new StandardMultipartHttpServletRequest(req);
		Iterator<String> it = smr.getFileNames();
		while (it.hasNext()) {
			MultipartFile file = smr.getFile(it.next());
			String realPath = "d:/";
			String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
			String filePath = realPath + "upload/" + uuid + ".png";
			System.out.println("--- " + filePath);
			try {
				File execlFile = new File(filePath);
				FileUtils.copyInputStreamToFile(file.getInputStream(), execlFile);
			} catch (Exception e) {
				System.out.println("exe");
				e.printStackTrace();
			}
			PictruesDTO pictruesDTO = new PictruesDTO();
			pictruesDTO.setPicPath(filePath);
			pictruesDTO.setPno(pno);
			pictruesService.save(pictruesDTO);
		}

	}

	// 02. java.util.Date --> java.time.LocalDate
	public LocalDate UDateToLocalDate() {
		java.util.Date date = new java.util.Date();
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		LocalDate localDate = localDateTime.toLocalDate();
		return localDate;
	}

	// value = "/_search/products", method = RequestMethod.GET,
	@RequestMapping(value = "/saveproducts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Products> updateDate(@RequestBody Products products) {
		log.debug("更新旅游消息" + products);
		Products oldpro = productsService.findById(products.getId());
		// bookNotice:field_bookNotice
		oldpro.setBookNotice(products.getBookNotice());
		// costdesc
		oldpro.setCostdesc(products.getCostdesc());
		oldpro.setDataUpdateDatetime(UDateToLocalDate());
		// days
		oldpro.setDays(products.getDays());
		// detaildesc
		oldpro.setDetaildesc(products.getDetaildesc());
		// detailTypeId
		oldpro.setDetailTypeId(products.getDetailTypeId());
		// endadderss
		oldpro.setEndadderss(products.getEndadderss());
		// phone
		oldpro.setPhone(products.getPhone());
		// pno
		oldpro.setPno(products.getPno());
		// preferential
		oldpro.setPreferential(products.getPreferential());
		// price
		oldpro.setPrice(products.getPrice());
		// pricedesc
		oldpro.setPricedesc(products.getPricedesc());
		// route
		oldpro.setRoute(products.getRoute());
		// startadderss
		oldpro.setStartadderss(products.getStartadderss());
		// startdate
		oldpro.setStartdate(products.getStartdate());
		// title
		oldpro.setTitle(products.getTitle());
		// tourismTypesId
		oldpro.setTourismTypesId(products.getTourismTypesId());
		// travelAgentId
		oldpro.setTravelAgentId(products.getTravelAgentId());

		oldpro.setDetailTrip(products.getDetailTrip());
		productsService.saveProduct(oldpro);

		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("products", oldpro.getId().toString()))
				.body(products);

	}

}
