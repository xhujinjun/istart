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
import com.istart.framework.domain.Products;
import com.istart.framework.repository.ProductsRepository;
import com.istart.framework.repository.search.ProductsSearchRepository;
import com.istart.framework.service.ProductsService;
import com.istart.framework.web.rest.dto.ProductsDTO;
import com.istart.framework.web.rest.mapper.ProductsMapper;

/**
 * Test class for the ProductsResource REST controller.
 *
 * @see ProductsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstartApp.class)
@WebAppConfiguration
@IntegrationTest
public class ProductsResourceIntTest {

	private static final String DEFAULT_PNO = "AAAAA";
	private static final String UPDATED_PNO = "BBBBB";

	private static final Long DEFAULT_TRAVEL_AGENT_ID = 1L;
	private static final Long UPDATED_TRAVEL_AGENT_ID = 2L;
	private static final String DEFAULT_PHONE = "AAAAA";
	private static final String UPDATED_PHONE = "BBBBB";
	private static final String DEFAULT_TITLE = "AAAAA";
	private static final String UPDATED_TITLE = "BBBBB";

	private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
	private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
	private static final String DEFAULT_PRICEDESC = "AAAAA";
	private static final String UPDATED_PRICEDESC = "BBBBB";
	private static final String DEFAULT_PREFERENTIAL = "AAAAA";
	private static final String UPDATED_PREFERENTIAL = "BBBBB";

	private static final LocalDate DEFAULT_STARTDATE = LocalDate.ofEpochDay(0L);
	private static final LocalDate UPDATED_STARTDATE = LocalDate.now(ZoneId.systemDefault());
	private static final String DEFAULT_STARTADDERSS = "AAAAA";
	private static final String UPDATED_STARTADDERSS = "BBBBB";
	private static final String DEFAULT_ENDADDERSS = "AAAAA";
	private static final String UPDATED_ENDADDERSS = "BBBBB";

	private static final Integer DEFAULT_DAYS = 1;
	private static final Integer UPDATED_DAYS = 2;
	private static final String DEFAULT_COSTDESC = "AAAAA";
	private static final String UPDATED_COSTDESC = "BBBBB";
	private static final String DEFAULT_ROUTE = "AAAAA";
	private static final String UPDATED_ROUTE = "BBBBB";
	private static final String DEFAULT_DETAILDESC = "AAAAA";
	private static final String UPDATED_DETAILDESC = "BBBBB";
	private static final String DEFAULT_BOOK_NOTICE = "AAAAA";
	private static final String UPDATED_BOOK_NOTICE = "BBBBB";
	private static final String DEFAULT_RATE = "AAAAA";
	private static final String UPDATED_RATE = "BBBBB";

	private static final Long DEFAULT_TOURISM_TYPES_ID = 1L;
	private static final Long UPDATED_TOURISM_TYPES_ID = 2L;

	private static final Long DEFAULT_DETAIL_TYPE_ID = 1L;
	private static final Long UPDATED_DETAIL_TYPE_ID = 2L;
	private static final String DEFAULT_DATA_CREATOR = "AAAAA";
	private static final String UPDATED_DATA_CREATOR = "BBBBB";
	private static final String DEFAULT_DATA_UPDATER = "AAAAA";
	private static final String UPDATED_DATA_UPDATER = "BBBBB";

	private static final LocalDate DEFAULT_DATA_CREATE_DATETIME = LocalDate.ofEpochDay(0L);
	private static final LocalDate UPDATED_DATA_CREATE_DATETIME = LocalDate.now(ZoneId.systemDefault());

	private static final LocalDate DEFAULT_DATA_UPDATE_DATETIME = LocalDate.ofEpochDay(0L);
	private static final LocalDate UPDATED_DATA_UPDATE_DATETIME = LocalDate.now(ZoneId.systemDefault());

	private static final Integer DEFAULT_DATA_STATUS = 1;
	private static final Integer UPDATED_DATA_STATUS = 2;

	@Inject
	private ProductsRepository productsRepository;

	@Inject
	private ProductsMapper productsMapper;

	@Inject
	private ProductsService productsService;

	@Inject
	private ProductsSearchRepository productsSearchRepository;

	@Inject
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Inject
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	private MockMvc restProductsMockMvc;

	private Products products;

	@PostConstruct
	public void setup() {
		MockitoAnnotations.initMocks(this);
		ProductsResource productsResource = new ProductsResource();
		ReflectionTestUtils.setField(productsResource, "productsService", productsService);
		ReflectionTestUtils.setField(productsResource, "productsMapper", productsMapper);
		this.restProductsMockMvc = MockMvcBuilders.standaloneSetup(productsResource)
				.setCustomArgumentResolvers(pageableArgumentResolver).setMessageConverters(jacksonMessageConverter)
				.build();
	}

	@Before
	public void initTest() {
		productsSearchRepository.deleteAll();
		products = new Products();
		products.setPno(DEFAULT_PNO);
		products.setTravelAgentId(DEFAULT_TRAVEL_AGENT_ID);
		products.setPhone(DEFAULT_PHONE);
		products.setTitle(DEFAULT_TITLE);
		products.setPrice(DEFAULT_PRICE);
		products.setPricedesc(DEFAULT_PRICEDESC);
		products.setPreferential(DEFAULT_PREFERENTIAL);
		products.setStartdate(DEFAULT_STARTDATE);
		products.setStartadderss(DEFAULT_STARTADDERSS);
		products.setEndadderss(DEFAULT_ENDADDERSS);
		products.setDays(DEFAULT_DAYS);
		products.setCostdesc(DEFAULT_COSTDESC);
		products.setRoute(DEFAULT_ROUTE);
		products.setDetaildesc(DEFAULT_DETAILDESC);
		products.setBookNotice(DEFAULT_BOOK_NOTICE);
		products.setRate(DEFAULT_RATE);
		products.setTourismTypesId(DEFAULT_TOURISM_TYPES_ID);
		products.setDetailTypeId(DEFAULT_DETAIL_TYPE_ID);
		products.setDataCreator(DEFAULT_DATA_CREATOR);
		products.setDataUpdater(DEFAULT_DATA_UPDATER);
		products.setDataCreateDatetime(DEFAULT_DATA_CREATE_DATETIME);
		products.setDataUpdateDatetime(DEFAULT_DATA_UPDATE_DATETIME);
		products.setDataStatus(DEFAULT_DATA_STATUS);
	}

	@Test
	@Transactional
	public void createProducts() throws Exception {
		int databaseSizeBeforeCreate = productsRepository.findAll().size();

		// Create the Products
		ProductsDTO productsDTO = productsMapper.productsToProductsDTO(products);

		restProductsMockMvc.perform(post("/api/products").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(productsDTO))).andExpect(status().isCreated());

		// Validate the Products in the database
		List<Products> products = productsRepository.findAll();
		assertThat(products).hasSize(databaseSizeBeforeCreate + 1);
		Products testProducts = products.get(products.size() - 1);
		assertThat(testProducts.getPno()).isEqualTo(DEFAULT_PNO);
		assertThat(testProducts.getTravelAgentId()).isEqualTo(DEFAULT_TRAVEL_AGENT_ID);
		assertThat(testProducts.getPhone()).isEqualTo(DEFAULT_PHONE);
		assertThat(testProducts.getTitle()).isEqualTo(DEFAULT_TITLE);
		assertThat(testProducts.getPrice()).isEqualTo(DEFAULT_PRICE);
		assertThat(testProducts.getPricedesc()).isEqualTo(DEFAULT_PRICEDESC);
		assertThat(testProducts.getPreferential()).isEqualTo(DEFAULT_PREFERENTIAL);
		assertThat(testProducts.getStartdate()).isEqualTo(DEFAULT_STARTDATE);
		assertThat(testProducts.getStartadderss()).isEqualTo(DEFAULT_STARTADDERSS);
		assertThat(testProducts.getEndadderss()).isEqualTo(DEFAULT_ENDADDERSS);
		assertThat(testProducts.getDays()).isEqualTo(DEFAULT_DAYS);
		assertThat(testProducts.getCostdesc()).isEqualTo(DEFAULT_COSTDESC);
		assertThat(testProducts.getRoute()).isEqualTo(DEFAULT_ROUTE);
		assertThat(testProducts.getDetaildesc()).isEqualTo(DEFAULT_DETAILDESC);
		assertThat(testProducts.getBookNotice()).isEqualTo(DEFAULT_BOOK_NOTICE);
		assertThat(testProducts.getRate()).isEqualTo(DEFAULT_RATE);
		assertThat(testProducts.getTourismTypesId()).isEqualTo(DEFAULT_TOURISM_TYPES_ID);
		assertThat(testProducts.getDetailTypeId()).isEqualTo(DEFAULT_DETAIL_TYPE_ID);
		assertThat(testProducts.getDataCreator()).isEqualTo(DEFAULT_DATA_CREATOR);
		assertThat(testProducts.getDataUpdater()).isEqualTo(DEFAULT_DATA_UPDATER);
		assertThat(testProducts.getDataCreateDatetime()).isEqualTo(DEFAULT_DATA_CREATE_DATETIME);
		assertThat(testProducts.getDataUpdateDatetime()).isEqualTo(DEFAULT_DATA_UPDATE_DATETIME);
		assertThat(testProducts.getDataStatus()).isEqualTo(DEFAULT_DATA_STATUS);

		// Validate the Products in ElasticSearch
		Products productsEs = productsSearchRepository.findOne(testProducts.getId());
		assertThat(productsEs).isEqualToComparingFieldByField(testProducts);
	}

	@Test
	@Transactional
	public void getAllProducts() throws Exception {
		// Initialize the database
		productsRepository.saveAndFlush(products);

		// Get all the products
		restProductsMockMvc.perform(get("/api/products?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.[*].id").value(hasItem(products.getId().intValue())))
				.andExpect(jsonPath("$.[*].pno").value(hasItem(DEFAULT_PNO.toString())))
				.andExpect(jsonPath("$.[*].travelAgentId").value(hasItem(DEFAULT_TRAVEL_AGENT_ID.intValue())))
				.andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
				.andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
				.andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
				.andExpect(jsonPath("$.[*].pricedesc").value(hasItem(DEFAULT_PRICEDESC.toString())))
				.andExpect(jsonPath("$.[*].preferential").value(hasItem(DEFAULT_PREFERENTIAL.toString())))
				.andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
				.andExpect(jsonPath("$.[*].startadderss").value(hasItem(DEFAULT_STARTADDERSS.toString())))
				.andExpect(jsonPath("$.[*].endadderss").value(hasItem(DEFAULT_ENDADDERSS.toString())))
				.andExpect(jsonPath("$.[*].days").value(hasItem(DEFAULT_DAYS)))
				.andExpect(jsonPath("$.[*].costdesc").value(hasItem(DEFAULT_COSTDESC.toString())))
				.andExpect(jsonPath("$.[*].route").value(hasItem(DEFAULT_ROUTE.toString())))
				.andExpect(jsonPath("$.[*].detaildesc").value(hasItem(DEFAULT_DETAILDESC.toString())))
				.andExpect(jsonPath("$.[*].bookNotice").value(hasItem(DEFAULT_BOOK_NOTICE.toString())))
				.andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.toString())))
				.andExpect(jsonPath("$.[*].tourismTypesId").value(hasItem(DEFAULT_TOURISM_TYPES_ID.intValue())))
				.andExpect(jsonPath("$.[*].detailTypeId").value(hasItem(DEFAULT_DETAIL_TYPE_ID.intValue())))
				.andExpect(jsonPath("$.[*].dataCreator").value(hasItem(DEFAULT_DATA_CREATOR.toString())))
				.andExpect(jsonPath("$.[*].dataUpdater").value(hasItem(DEFAULT_DATA_UPDATER.toString())))
				.andExpect(jsonPath("$.[*].dataCreateDatetime").value(hasItem(DEFAULT_DATA_CREATE_DATETIME.toString())))
				.andExpect(jsonPath("$.[*].dataUpdateDatetime").value(hasItem(DEFAULT_DATA_UPDATE_DATETIME.toString())))
				.andExpect(jsonPath("$.[*].dataStatus").value(hasItem(DEFAULT_DATA_STATUS)));
	}

	@Test
	@Transactional
	public void getProducts() throws Exception {
		// Initialize the database
		productsRepository.saveAndFlush(products);

		// Get the products
		restProductsMockMvc.perform(get("/api/products/{id}", products.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(products.getId().intValue()))
				.andExpect(jsonPath("$.pno").value(DEFAULT_PNO.toString()))
				.andExpect(jsonPath("$.travelAgentId").value(DEFAULT_TRAVEL_AGENT_ID.intValue()))
				.andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
				.andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
				.andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
				.andExpect(jsonPath("$.pricedesc").value(DEFAULT_PRICEDESC.toString()))
				.andExpect(jsonPath("$.preferential").value(DEFAULT_PREFERENTIAL.toString()))
				.andExpect(jsonPath("$.startdate").value(DEFAULT_STARTDATE.toString()))
				.andExpect(jsonPath("$.startadderss").value(DEFAULT_STARTADDERSS.toString()))
				.andExpect(jsonPath("$.endadderss").value(DEFAULT_ENDADDERSS.toString()))
				.andExpect(jsonPath("$.days").value(DEFAULT_DAYS))
				.andExpect(jsonPath("$.costdesc").value(DEFAULT_COSTDESC.toString()))
				.andExpect(jsonPath("$.route").value(DEFAULT_ROUTE.toString()))
				.andExpect(jsonPath("$.detaildesc").value(DEFAULT_DETAILDESC.toString()))
				.andExpect(jsonPath("$.bookNotice").value(DEFAULT_BOOK_NOTICE.toString()))
				.andExpect(jsonPath("$.rate").value(DEFAULT_RATE.toString()))
				.andExpect(jsonPath("$.tourismTypesId").value(DEFAULT_TOURISM_TYPES_ID.intValue()))
				.andExpect(jsonPath("$.detailTypeId").value(DEFAULT_DETAIL_TYPE_ID.intValue()))
				.andExpect(jsonPath("$.dataCreator").value(DEFAULT_DATA_CREATOR.toString()))
				.andExpect(jsonPath("$.dataUpdater").value(DEFAULT_DATA_UPDATER.toString()))
				.andExpect(jsonPath("$.dataCreateDatetime").value(DEFAULT_DATA_CREATE_DATETIME.toString()))
				.andExpect(jsonPath("$.dataUpdateDatetime").value(DEFAULT_DATA_UPDATE_DATETIME.toString()))
				.andExpect(jsonPath("$.dataStatus").value(DEFAULT_DATA_STATUS));
	}

	@Test
	@Transactional
	public void getNonExistingProducts() throws Exception {
		// Get the products
		restProductsMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateProducts() throws Exception {
		// Initialize the database
		productsRepository.saveAndFlush(products);
		productsSearchRepository.save(products);
		int databaseSizeBeforeUpdate = productsRepository.findAll().size();

		// Update the products
		Products updatedProducts = new Products();
		updatedProducts.setId(products.getId());
		updatedProducts.setPno(UPDATED_PNO);
		updatedProducts.setTravelAgentId(UPDATED_TRAVEL_AGENT_ID);
		updatedProducts.setPhone(UPDATED_PHONE);
		updatedProducts.setTitle(UPDATED_TITLE);
		updatedProducts.setPrice(UPDATED_PRICE);
		updatedProducts.setPricedesc(UPDATED_PRICEDESC);
		updatedProducts.setPreferential(UPDATED_PREFERENTIAL);
		updatedProducts.setStartdate(UPDATED_STARTDATE);
		updatedProducts.setStartadderss(UPDATED_STARTADDERSS);
		updatedProducts.setEndadderss(UPDATED_ENDADDERSS);
		updatedProducts.setDays(UPDATED_DAYS);
		updatedProducts.setCostdesc(UPDATED_COSTDESC);
		updatedProducts.setRoute(UPDATED_ROUTE);
		updatedProducts.setDetaildesc(UPDATED_DETAILDESC);
		updatedProducts.setBookNotice(UPDATED_BOOK_NOTICE);
		updatedProducts.setRate(UPDATED_RATE);
		updatedProducts.setTourismTypesId(UPDATED_TOURISM_TYPES_ID);
		updatedProducts.setDetailTypeId(UPDATED_DETAIL_TYPE_ID);
		updatedProducts.setDataCreator(UPDATED_DATA_CREATOR);
		updatedProducts.setDataUpdater(UPDATED_DATA_UPDATER);
		updatedProducts.setDataCreateDatetime(UPDATED_DATA_CREATE_DATETIME);
		updatedProducts.setDataUpdateDatetime(UPDATED_DATA_UPDATE_DATETIME);
		updatedProducts.setDataStatus(UPDATED_DATA_STATUS);
		ProductsDTO productsDTO = productsMapper.productsToProductsDTO(updatedProducts);

		restProductsMockMvc.perform(put("/api/products").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(productsDTO))).andExpect(status().isOk());

		// Validate the Products in the database
		List<Products> products = productsRepository.findAll();
		assertThat(products).hasSize(databaseSizeBeforeUpdate);
		Products testProducts = products.get(products.size() - 1);
		assertThat(testProducts.getPno()).isEqualTo(UPDATED_PNO);
		assertThat(testProducts.getTravelAgentId()).isEqualTo(UPDATED_TRAVEL_AGENT_ID);
		assertThat(testProducts.getPhone()).isEqualTo(UPDATED_PHONE);
		assertThat(testProducts.getTitle()).isEqualTo(UPDATED_TITLE);
		assertThat(testProducts.getPrice()).isEqualTo(UPDATED_PRICE);
		assertThat(testProducts.getPricedesc()).isEqualTo(UPDATED_PRICEDESC);
		assertThat(testProducts.getPreferential()).isEqualTo(UPDATED_PREFERENTIAL);
		assertThat(testProducts.getStartdate()).isEqualTo(UPDATED_STARTDATE);
		assertThat(testProducts.getStartadderss()).isEqualTo(UPDATED_STARTADDERSS);
		assertThat(testProducts.getEndadderss()).isEqualTo(UPDATED_ENDADDERSS);
		assertThat(testProducts.getDays()).isEqualTo(UPDATED_DAYS);
		assertThat(testProducts.getCostdesc()).isEqualTo(UPDATED_COSTDESC);
		assertThat(testProducts.getRoute()).isEqualTo(UPDATED_ROUTE);
		assertThat(testProducts.getDetaildesc()).isEqualTo(UPDATED_DETAILDESC);
		assertThat(testProducts.getBookNotice()).isEqualTo(UPDATED_BOOK_NOTICE);
		assertThat(testProducts.getRate()).isEqualTo(UPDATED_RATE);
		assertThat(testProducts.getTourismTypesId()).isEqualTo(UPDATED_TOURISM_TYPES_ID);
		assertThat(testProducts.getDetailTypeId()).isEqualTo(UPDATED_DETAIL_TYPE_ID);
		assertThat(testProducts.getDataCreator()).isEqualTo(UPDATED_DATA_CREATOR);
		assertThat(testProducts.getDataUpdater()).isEqualTo(UPDATED_DATA_UPDATER);
		assertThat(testProducts.getDataCreateDatetime()).isEqualTo(UPDATED_DATA_CREATE_DATETIME);
		assertThat(testProducts.getDataUpdateDatetime()).isEqualTo(UPDATED_DATA_UPDATE_DATETIME);
		assertThat(testProducts.getDataStatus()).isEqualTo(UPDATED_DATA_STATUS);

		// Validate the Products in ElasticSearch
		Products productsEs = productsSearchRepository.findOne(testProducts.getId());
		assertThat(productsEs).isEqualToComparingFieldByField(testProducts);
	}

	@Test
	@Transactional
	public void deleteProducts() throws Exception {
		// Initialize the database
		productsRepository.saveAndFlush(products);
		productsSearchRepository.save(products);
		int databaseSizeBeforeDelete = productsRepository.findAll().size();

		// Get the products
		restProductsMockMvc
				.perform(delete("/api/products/{id}", products.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Validate ElasticSearch is empty
		boolean productsExistsInEs = productsSearchRepository.exists(products.getId());
		assertThat(productsExistsInEs).isFalse();

		// Validate the database is empty
		List<Products> products = productsRepository.findAll();
		assertThat(products).hasSize(databaseSizeBeforeDelete - 1);
	}

	@Test
	@Transactional
	public void searchProducts() throws Exception {
		// Initialize the database
		productsRepository.saveAndFlush(products);
		productsSearchRepository.save(products);

		// Search the products
		restProductsMockMvc.perform(get("/api/_search/products?query=id:" + products.getId()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.[*].id").value(hasItem(products.getId().intValue())))
				.andExpect(jsonPath("$.[*].pno").value(hasItem(DEFAULT_PNO.toString())))
				.andExpect(jsonPath("$.[*].travelAgentId").value(hasItem(DEFAULT_TRAVEL_AGENT_ID.intValue())))
				.andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
				.andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
				.andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
				.andExpect(jsonPath("$.[*].pricedesc").value(hasItem(DEFAULT_PRICEDESC.toString())))
				.andExpect(jsonPath("$.[*].preferential").value(hasItem(DEFAULT_PREFERENTIAL.toString())))
				.andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
				.andExpect(jsonPath("$.[*].startadderss").value(hasItem(DEFAULT_STARTADDERSS.toString())))
				.andExpect(jsonPath("$.[*].endadderss").value(hasItem(DEFAULT_ENDADDERSS.toString())))
				.andExpect(jsonPath("$.[*].days").value(hasItem(DEFAULT_DAYS)))
				.andExpect(jsonPath("$.[*].costdesc").value(hasItem(DEFAULT_COSTDESC.toString())))
				.andExpect(jsonPath("$.[*].route").value(hasItem(DEFAULT_ROUTE.toString())))
				.andExpect(jsonPath("$.[*].detaildesc").value(hasItem(DEFAULT_DETAILDESC.toString())))
				.andExpect(jsonPath("$.[*].bookNotice").value(hasItem(DEFAULT_BOOK_NOTICE.toString())))
				.andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.toString())))
				.andExpect(jsonPath("$.[*].tourismTypesId").value(hasItem(DEFAULT_TOURISM_TYPES_ID.intValue())))
				.andExpect(jsonPath("$.[*].detailTypeId").value(hasItem(DEFAULT_DETAIL_TYPE_ID.intValue())))
				.andExpect(jsonPath("$.[*].dataCreator").value(hasItem(DEFAULT_DATA_CREATOR.toString())))
				.andExpect(jsonPath("$.[*].dataUpdater").value(hasItem(DEFAULT_DATA_UPDATER.toString())))
				.andExpect(jsonPath("$.[*].dataCreateDatetime").value(hasItem(DEFAULT_DATA_CREATE_DATETIME.toString())))
				.andExpect(jsonPath("$.[*].dataUpdateDatetime").value(hasItem(DEFAULT_DATA_UPDATE_DATETIME.toString())))
				.andExpect(jsonPath("$.[*].dataStatus").value(hasItem(DEFAULT_DATA_STATUS)));
	}
}
