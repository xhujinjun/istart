package com.istart.framework.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.istart.framework.IstartApp;
import com.istart.framework.domain.Product;
import com.istart.framework.repository.ProductRepository;
import com.istart.framework.repository.search.ProductSearchRepository;
import com.istart.framework.service.ProductService;
import com.istart.framework.web.rest.dto.ProductDTO;
import com.istart.framework.web.rest.mapper.ProductMapper;

/**
 * Test class for the ProductResource REST controller.
 *
 * @see ProductResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstartApp.class)
@WebAppConfiguration
@IntegrationTest
public class ProductResourceIntTest {

	private static final String DEFAULT_PNO = "AAAAA";
	private static final String UPDATED_PNO = "BBBBB";
	private static final String DEFAULT_TITLE = "AAAAA";
	private static final String UPDATED_TITLE = "BBBBB";

	private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
	private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
	private static final String DEFAULT_PRICEDESC = "AAAAA";
	private static final String UPDATED_PRICEDESC = "BBBBB";
	private static final String DEFAULT_PREFERENTIAL = "AAAAA";
	private static final String UPDATED_PREFERENTIAL = "BBBBB";
	private static final String DEFAULT_COSTDESC = "AAAAA";
	private static final String UPDATED_COSTDESC = "BBBBB";
	private static final String DEFAULT_TRIP = "AAAAA";
	private static final String UPDATED_TRIP = "BBBBB";
	private static final String DEFAULT_START = "AAAAA";
	private static final String UPDATED_START = "BBBBB";
	private static final String DEFAULT_ROUTE = "AAAAA";
	private static final String UPDATED_ROUTE = "BBBBB";
	private static final String DEFAULT_PRODESC = "AAAAA";
	private static final String UPDATED_PRODESC = "BBBBB";
	private static final String DEFAULT_RATE = "AAAAA";
	private static final String UPDATED_RATE = "BBBBB";
	private static final String DEFAULT_STATE = "AAAAA";
	private static final String UPDATED_STATE = "BBBBB";
	private static final String DEFAULT_TYPE = "AAAAA";
	private static final String UPDATED_TYPE = "BBBBB";

	private static final LocalDate DEFAULT_STARTDATE = LocalDate.ofEpochDay(0L);
	private static final LocalDate UPDATED_STARTDATE = LocalDate.now(ZoneId.systemDefault());
	private static final String DEFAULT_PICS = "AAAAA";
	private static final String UPDATED_PICS = "BBBBB";

	private static final Integer DEFAULT_DAYS = 1;
	private static final Integer UPDATED_DAYS = 2;

	@Inject
	private ProductRepository productRepository;

	@Inject
	private ProductMapper productMapper;

	@Inject
	private ProductService productService;

	@Inject
	private ProductSearchRepository productSearchRepository;

	@Inject
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Inject
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	private MockMvc restProductMockMvc;

	private Product product;

	@PostConstruct
	public void setup() {
		MockitoAnnotations.initMocks(this);
		ProductResource productResource = new ProductResource();
		ReflectionTestUtils.setField(productResource, "productService", productService);
		ReflectionTestUtils.setField(productResource, "productMapper", productMapper);
		this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productResource)
				.setCustomArgumentResolvers(pageableArgumentResolver).setMessageConverters(jacksonMessageConverter)
				.build();
	}

	@Before
	public void initTest() {
		productSearchRepository.deleteAll();
		product = new Product();
		product.setPno(DEFAULT_PNO);
		product.setTitle(DEFAULT_TITLE);
		product.setPrice(DEFAULT_PRICE);
		product.setPricedesc(DEFAULT_PRICEDESC);
		product.setPreferential(DEFAULT_PREFERENTIAL);
		product.setCostdesc(DEFAULT_COSTDESC);
		product.setTrip(DEFAULT_TRIP);
		product.setStart(DEFAULT_START);
		product.setRoute(DEFAULT_ROUTE);
		product.setProdesc(DEFAULT_PRODESC);
		product.setRate(DEFAULT_RATE);
		product.setState(DEFAULT_STATE);
		product.setType(DEFAULT_TYPE);
		product.setStartdate(DEFAULT_STARTDATE);
		product.setPics(DEFAULT_PICS);
		product.setDays(DEFAULT_DAYS);
	}

	@Test
	@Transactional
	public void createProduct() throws Exception {
		int databaseSizeBeforeCreate = productRepository.findAll().size();

		// Create the Product
		ProductDTO productDTO = productMapper.productToProductDTO(product);

		restProductMockMvc.perform(post("/api/products").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(productDTO))).andExpect(status().isCreated());

		// Validate the Product in the database
		List<Product> products = productRepository.findAll();
		assertThat(products).hasSize(databaseSizeBeforeCreate + 1);
		Product testProduct = products.get(products.size() - 1);
		assertThat(testProduct.getPno()).isEqualTo(DEFAULT_PNO);
		assertThat(testProduct.getTitle()).isEqualTo(DEFAULT_TITLE);
		assertThat(testProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
		assertThat(testProduct.getPricedesc()).isEqualTo(DEFAULT_PRICEDESC);
		assertThat(testProduct.getPreferential()).isEqualTo(DEFAULT_PREFERENTIAL);
		assertThat(testProduct.getCostdesc()).isEqualTo(DEFAULT_COSTDESC);
		assertThat(testProduct.getTrip()).isEqualTo(DEFAULT_TRIP);
		assertThat(testProduct.getStart()).isEqualTo(DEFAULT_START);
		assertThat(testProduct.getRoute()).isEqualTo(DEFAULT_ROUTE);
		assertThat(testProduct.getProdesc()).isEqualTo(DEFAULT_PRODESC);
		assertThat(testProduct.getRate()).isEqualTo(DEFAULT_RATE);
		assertThat(testProduct.getState()).isEqualTo(DEFAULT_STATE);
		assertThat(testProduct.getType()).isEqualTo(DEFAULT_TYPE);
		assertThat(testProduct.getStartdate()).isEqualTo(DEFAULT_STARTDATE);
		assertThat(testProduct.getPics()).isEqualTo(DEFAULT_PICS);
		assertThat(testProduct.getDays()).isEqualTo(DEFAULT_DAYS);

		// Validate the Product in ElasticSearch
		Product productEs = productSearchRepository.findOne(testProduct.getId());
		assertThat(productEs).isEqualToComparingFieldByField(testProduct);
	}

	@Test
	@Transactional
	public void getAllProducts() throws Exception {
		// Initialize the database
		productRepository.saveAndFlush(product);

		// Get all the products
		restProductMockMvc.perform(get("/api/products?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
				.andExpect(jsonPath("$.[*].pno").value(hasItem(DEFAULT_PNO.toString())))
				.andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
				.andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
				.andExpect(jsonPath("$.[*].pricedesc").value(hasItem(DEFAULT_PRICEDESC.toString())))
				.andExpect(jsonPath("$.[*].preferential").value(hasItem(DEFAULT_PREFERENTIAL.toString())))
				.andExpect(jsonPath("$.[*].costdesc").value(hasItem(DEFAULT_COSTDESC.toString())))
				.andExpect(jsonPath("$.[*].trip").value(hasItem(DEFAULT_TRIP.toString())))
				.andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
				.andExpect(jsonPath("$.[*].route").value(hasItem(DEFAULT_ROUTE.toString())))
				.andExpect(jsonPath("$.[*].prodesc").value(hasItem(DEFAULT_PRODESC.toString())))
				.andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.toString())))
				.andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
				.andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
				.andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
				.andExpect(jsonPath("$.[*].pics").value(hasItem(DEFAULT_PICS.toString())))
				.andExpect(jsonPath("$.[*].days").value(hasItem(DEFAULT_DAYS)));
	}

	@Test
	@Transactional
	public void getProduct() throws Exception {
		// Initialize the database
		productRepository.saveAndFlush(product);

		// Get the product
		restProductMockMvc.perform(get("/api/products/{id}", product.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(product.getId().intValue()))
				.andExpect(jsonPath("$.pno").value(DEFAULT_PNO.toString()))
				.andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
				.andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
				.andExpect(jsonPath("$.pricedesc").value(DEFAULT_PRICEDESC.toString()))
				.andExpect(jsonPath("$.preferential").value(DEFAULT_PREFERENTIAL.toString()))
				.andExpect(jsonPath("$.costdesc").value(DEFAULT_COSTDESC.toString()))
				.andExpect(jsonPath("$.trip").value(DEFAULT_TRIP.toString()))
				.andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
				.andExpect(jsonPath("$.route").value(DEFAULT_ROUTE.toString()))
				.andExpect(jsonPath("$.prodesc").value(DEFAULT_PRODESC.toString()))
				.andExpect(jsonPath("$.rate").value(DEFAULT_RATE.toString()))
				.andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
				.andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
				.andExpect(jsonPath("$.startdate").value(DEFAULT_STARTDATE.toString()))
				.andExpect(jsonPath("$.pics").value(DEFAULT_PICS.toString()))
				.andExpect(jsonPath("$.days").value(DEFAULT_DAYS));
	}

	@Test
	@Transactional
	public void getNonExistingProduct() throws Exception {
		// Get the product
		restProductMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateProduct() throws Exception {
		// Initialize the database
		productRepository.saveAndFlush(product);
		productSearchRepository.save(product);
		int databaseSizeBeforeUpdate = productRepository.findAll().size();

		// Update the product
		Product updatedProduct = new Product();
		updatedProduct.setId(product.getId());
		updatedProduct.setPno(UPDATED_PNO);
		updatedProduct.setTitle(UPDATED_TITLE);
		updatedProduct.setPrice(UPDATED_PRICE);
		updatedProduct.setPricedesc(UPDATED_PRICEDESC);
		updatedProduct.setPreferential(UPDATED_PREFERENTIAL);
		updatedProduct.setCostdesc(UPDATED_COSTDESC);
		updatedProduct.setTrip(UPDATED_TRIP);
		updatedProduct.setStart(UPDATED_START);
		updatedProduct.setRoute(UPDATED_ROUTE);
		updatedProduct.setProdesc(UPDATED_PRODESC);
		updatedProduct.setRate(UPDATED_RATE);
		updatedProduct.setState(UPDATED_STATE);
		updatedProduct.setType(UPDATED_TYPE);
		updatedProduct.setStartdate(UPDATED_STARTDATE);
		updatedProduct.setPics(UPDATED_PICS);
		updatedProduct.setDays(UPDATED_DAYS);
		ProductDTO productDTO = productMapper.productToProductDTO(updatedProduct);

		restProductMockMvc.perform(put("/api/products").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(productDTO))).andExpect(status().isOk());

		// Validate the Product in the database
		List<Product> products = productRepository.findAll();
		assertThat(products).hasSize(databaseSizeBeforeUpdate);
		Product testProduct = products.get(products.size() - 1);
		assertThat(testProduct.getPno()).isEqualTo(UPDATED_PNO);
		assertThat(testProduct.getTitle()).isEqualTo(UPDATED_TITLE);
		assertThat(testProduct.getPrice()).isEqualTo(UPDATED_PRICE);
		assertThat(testProduct.getPricedesc()).isEqualTo(UPDATED_PRICEDESC);
		assertThat(testProduct.getPreferential()).isEqualTo(UPDATED_PREFERENTIAL);
		assertThat(testProduct.getCostdesc()).isEqualTo(UPDATED_COSTDESC);
		assertThat(testProduct.getTrip()).isEqualTo(UPDATED_TRIP);
		assertThat(testProduct.getStart()).isEqualTo(UPDATED_START);
		assertThat(testProduct.getRoute()).isEqualTo(UPDATED_ROUTE);
		assertThat(testProduct.getProdesc()).isEqualTo(UPDATED_PRODESC);
		assertThat(testProduct.getRate()).isEqualTo(UPDATED_RATE);
		assertThat(testProduct.getState()).isEqualTo(UPDATED_STATE);
		assertThat(testProduct.getType()).isEqualTo(UPDATED_TYPE);
		assertThat(testProduct.getStartdate()).isEqualTo(UPDATED_STARTDATE);
		assertThat(testProduct.getPics()).isEqualTo(UPDATED_PICS);
		assertThat(testProduct.getDays()).isEqualTo(UPDATED_DAYS);

		// Validate the Product in ElasticSearch
		Product productEs = productSearchRepository.findOne(testProduct.getId());
		assertThat(productEs).isEqualToComparingFieldByField(testProduct);
	}

	@Test
	@Transactional
	public void deleteProduct() throws Exception {
		// Initialize the database
		productRepository.saveAndFlush(product);
		productSearchRepository.save(product);
		int databaseSizeBeforeDelete = productRepository.findAll().size();

		// Get the product
		restProductMockMvc.perform(delete("/api/products/{id}", product.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Validate ElasticSearch is empty
		boolean productExistsInEs = productSearchRepository.exists(product.getId());
		assertThat(productExistsInEs).isFalse();

		// Validate the database is empty
		List<Product> products = productRepository.findAll();
		assertThat(products).hasSize(databaseSizeBeforeDelete - 1);
	}

	@Test
	@Transactional
	public void searchProduct() throws Exception {
		// Initialize the database
		productRepository.saveAndFlush(product);
		productSearchRepository.save(product);

		// Search the product
		restProductMockMvc.perform(get("/api/_search/products?query=id:" + product.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
				.andExpect(jsonPath("$.[*].pno").value(hasItem(DEFAULT_PNO.toString())))
				.andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
				.andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
				.andExpect(jsonPath("$.[*].pricedesc").value(hasItem(DEFAULT_PRICEDESC.toString())))
				.andExpect(jsonPath("$.[*].preferential").value(hasItem(DEFAULT_PREFERENTIAL.toString())))
				.andExpect(jsonPath("$.[*].costdesc").value(hasItem(DEFAULT_COSTDESC.toString())))
				.andExpect(jsonPath("$.[*].trip").value(hasItem(DEFAULT_TRIP.toString())))
				.andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
				.andExpect(jsonPath("$.[*].route").value(hasItem(DEFAULT_ROUTE.toString())))
				.andExpect(jsonPath("$.[*].prodesc").value(hasItem(DEFAULT_PRODESC.toString())))
				.andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.toString())))
				.andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
				.andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
				.andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
				.andExpect(jsonPath("$.[*].pics").value(hasItem(DEFAULT_PICS.toString())))
				.andExpect(jsonPath("$.[*].days").value(hasItem(DEFAULT_DAYS)));
	}
}
