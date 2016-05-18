package com.istart.framework.web.rest;

import com.istart.framework.IstartApp;
import com.istart.framework.domain.TravelAgency;
import com.istart.framework.repository.TravelAgencyRepository;
import com.istart.framework.service.TravelAgencyService;
import com.istart.framework.repository.search.TravelAgencySearchRepository;
import com.istart.framework.web.rest.dto.TravelAgencyDTO;
import com.istart.framework.web.rest.mapper.TravelAgencyMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the TravelAgencyResource REST controller.
 *
 * @see TravelAgencyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstartApp.class)
@WebAppConfiguration
@IntegrationTest
public class TravelAgencyResourceIntTest {

    private static final String DEFAULT_AGENCY_CODE = "AAAAA";
    private static final String UPDATED_AGENCY_CODE = "BBBBB";
    private static final String DEFAULT_AGENCY_NAME = "AAAAA";
    private static final String UPDATED_AGENCY_NAME = "BBBBB";
    private static final String DEFAULT_AGENCY_INTRODUCE = "AAAAA";
    private static final String UPDATED_AGENCY_INTRODUCE = "BBBBB";
    private static final String DEFAULT_ADDR = "AAAAA";
    private static final String UPDATED_ADDR = "BBBBB";

    private static final LocalDate DEFAULT_BUILD_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BUILD_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_CONTACT_PHONE = "AAAAA";
    private static final String UPDATED_CONTACT_PHONE = "BBBBB";

    @Inject
    private TravelAgencyRepository travelAgencyRepository;

    @Inject
    private TravelAgencyMapper travelAgencyMapper;

    @Inject
    private TravelAgencyService travelAgencyService;

    @Inject
    private TravelAgencySearchRepository travelAgencySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTravelAgencyMockMvc;

    private TravelAgency travelAgency;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TravelAgencyResource travelAgencyResource = new TravelAgencyResource();
        ReflectionTestUtils.setField(travelAgencyResource, "travelAgencyService", travelAgencyService);
        ReflectionTestUtils.setField(travelAgencyResource, "travelAgencyMapper", travelAgencyMapper);
        this.restTravelAgencyMockMvc = MockMvcBuilders.standaloneSetup(travelAgencyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        travelAgencySearchRepository.deleteAll();
        travelAgency = new TravelAgency();
        travelAgency.setAgencyCode(DEFAULT_AGENCY_CODE);
        travelAgency.setAgencyName(DEFAULT_AGENCY_NAME);
        travelAgency.setAgencyIntroduce(DEFAULT_AGENCY_INTRODUCE);
        travelAgency.setAddr(DEFAULT_ADDR);
        travelAgency.setBuildDate(DEFAULT_BUILD_DATE);
        travelAgency.setContactPhone(DEFAULT_CONTACT_PHONE);
    }

    @Test
    @Transactional
    public void createTravelAgency() throws Exception {
        int databaseSizeBeforeCreate = travelAgencyRepository.findAll().size();

        // Create the TravelAgency
        TravelAgencyDTO travelAgencyDTO = travelAgencyMapper.travelAgencyToTravelAgencyDTO(travelAgency);

        restTravelAgencyMockMvc.perform(post("/api/travel-agencies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(travelAgencyDTO)))
                .andExpect(status().isCreated());

        // Validate the TravelAgency in the database
        List<TravelAgency> travelAgencies = travelAgencyRepository.findAll();
        assertThat(travelAgencies).hasSize(databaseSizeBeforeCreate + 1);
        TravelAgency testTravelAgency = travelAgencies.get(travelAgencies.size() - 1);
        assertThat(testTravelAgency.getAgencyCode()).isEqualTo(DEFAULT_AGENCY_CODE);
        assertThat(testTravelAgency.getAgencyName()).isEqualTo(DEFAULT_AGENCY_NAME);
        assertThat(testTravelAgency.getAgencyIntroduce()).isEqualTo(DEFAULT_AGENCY_INTRODUCE);
        assertThat(testTravelAgency.getAddr()).isEqualTo(DEFAULT_ADDR);
        assertThat(testTravelAgency.getBuildDate()).isEqualTo(DEFAULT_BUILD_DATE);
        assertThat(testTravelAgency.getContactPhone()).isEqualTo(DEFAULT_CONTACT_PHONE);

        // Validate the TravelAgency in ElasticSearch
        TravelAgency travelAgencyEs = travelAgencySearchRepository.findOne(testTravelAgency.getId());
        assertThat(travelAgencyEs).isEqualToComparingFieldByField(testTravelAgency);
    }

    @Test
    @Transactional
    public void getAllTravelAgencies() throws Exception {
        // Initialize the database
        travelAgencyRepository.saveAndFlush(travelAgency);

        // Get all the travelAgencies
        restTravelAgencyMockMvc.perform(get("/api/travel-agencies?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(travelAgency.getId().intValue())))
                .andExpect(jsonPath("$.[*].agencyCode").value(hasItem(DEFAULT_AGENCY_CODE.toString())))
                .andExpect(jsonPath("$.[*].agencyName").value(hasItem(DEFAULT_AGENCY_NAME.toString())))
                .andExpect(jsonPath("$.[*].agencyIntroduce").value(hasItem(DEFAULT_AGENCY_INTRODUCE.toString())))
                .andExpect(jsonPath("$.[*].addr").value(hasItem(DEFAULT_ADDR.toString())))
                .andExpect(jsonPath("$.[*].buildDate").value(hasItem(DEFAULT_BUILD_DATE.toString())))
                .andExpect(jsonPath("$.[*].contactPhone").value(hasItem(DEFAULT_CONTACT_PHONE.toString())));
    }

    @Test
    @Transactional
    public void getTravelAgency() throws Exception {
        // Initialize the database
        travelAgencyRepository.saveAndFlush(travelAgency);

        // Get the travelAgency
        restTravelAgencyMockMvc.perform(get("/api/travel-agencies/{id}", travelAgency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(travelAgency.getId().intValue()))
            .andExpect(jsonPath("$.agencyCode").value(DEFAULT_AGENCY_CODE.toString()))
            .andExpect(jsonPath("$.agencyName").value(DEFAULT_AGENCY_NAME.toString()))
            .andExpect(jsonPath("$.agencyIntroduce").value(DEFAULT_AGENCY_INTRODUCE.toString()))
            .andExpect(jsonPath("$.addr").value(DEFAULT_ADDR.toString()))
            .andExpect(jsonPath("$.buildDate").value(DEFAULT_BUILD_DATE.toString()))
            .andExpect(jsonPath("$.contactPhone").value(DEFAULT_CONTACT_PHONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTravelAgency() throws Exception {
        // Get the travelAgency
        restTravelAgencyMockMvc.perform(get("/api/travel-agencies/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTravelAgency() throws Exception {
        // Initialize the database
        travelAgencyRepository.saveAndFlush(travelAgency);
        travelAgencySearchRepository.save(travelAgency);
        int databaseSizeBeforeUpdate = travelAgencyRepository.findAll().size();

        // Update the travelAgency
        TravelAgency updatedTravelAgency = new TravelAgency();
        updatedTravelAgency.setId(travelAgency.getId());
        updatedTravelAgency.setAgencyCode(UPDATED_AGENCY_CODE);
        updatedTravelAgency.setAgencyName(UPDATED_AGENCY_NAME);
        updatedTravelAgency.setAgencyIntroduce(UPDATED_AGENCY_INTRODUCE);
        updatedTravelAgency.setAddr(UPDATED_ADDR);
        updatedTravelAgency.setBuildDate(UPDATED_BUILD_DATE);
        updatedTravelAgency.setContactPhone(UPDATED_CONTACT_PHONE);
        TravelAgencyDTO travelAgencyDTO = travelAgencyMapper.travelAgencyToTravelAgencyDTO(updatedTravelAgency);

        restTravelAgencyMockMvc.perform(put("/api/travel-agencies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(travelAgencyDTO)))
                .andExpect(status().isOk());

        // Validate the TravelAgency in the database
        List<TravelAgency> travelAgencies = travelAgencyRepository.findAll();
        assertThat(travelAgencies).hasSize(databaseSizeBeforeUpdate);
        TravelAgency testTravelAgency = travelAgencies.get(travelAgencies.size() - 1);
        assertThat(testTravelAgency.getAgencyCode()).isEqualTo(UPDATED_AGENCY_CODE);
        assertThat(testTravelAgency.getAgencyName()).isEqualTo(UPDATED_AGENCY_NAME);
        assertThat(testTravelAgency.getAgencyIntroduce()).isEqualTo(UPDATED_AGENCY_INTRODUCE);
        assertThat(testTravelAgency.getAddr()).isEqualTo(UPDATED_ADDR);
        assertThat(testTravelAgency.getBuildDate()).isEqualTo(UPDATED_BUILD_DATE);
        assertThat(testTravelAgency.getContactPhone()).isEqualTo(UPDATED_CONTACT_PHONE);

        // Validate the TravelAgency in ElasticSearch
        TravelAgency travelAgencyEs = travelAgencySearchRepository.findOne(testTravelAgency.getId());
        assertThat(travelAgencyEs).isEqualToComparingFieldByField(testTravelAgency);
    }

    @Test
    @Transactional
    public void deleteTravelAgency() throws Exception {
        // Initialize the database
        travelAgencyRepository.saveAndFlush(travelAgency);
        travelAgencySearchRepository.save(travelAgency);
        int databaseSizeBeforeDelete = travelAgencyRepository.findAll().size();

        // Get the travelAgency
        restTravelAgencyMockMvc.perform(delete("/api/travel-agencies/{id}", travelAgency.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean travelAgencyExistsInEs = travelAgencySearchRepository.exists(travelAgency.getId());
        assertThat(travelAgencyExistsInEs).isFalse();

        // Validate the database is empty
        List<TravelAgency> travelAgencies = travelAgencyRepository.findAll();
        assertThat(travelAgencies).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTravelAgency() throws Exception {
        // Initialize the database
        travelAgencyRepository.saveAndFlush(travelAgency);
        travelAgencySearchRepository.save(travelAgency);

        // Search the travelAgency
        restTravelAgencyMockMvc.perform(get("/api/_search/travel-agencies?query=id:" + travelAgency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(travelAgency.getId().intValue())))
            .andExpect(jsonPath("$.[*].agencyCode").value(hasItem(DEFAULT_AGENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].agencyName").value(hasItem(DEFAULT_AGENCY_NAME.toString())))
            .andExpect(jsonPath("$.[*].agencyIntroduce").value(hasItem(DEFAULT_AGENCY_INTRODUCE.toString())))
            .andExpect(jsonPath("$.[*].addr").value(hasItem(DEFAULT_ADDR.toString())))
            .andExpect(jsonPath("$.[*].buildDate").value(hasItem(DEFAULT_BUILD_DATE.toString())))
            .andExpect(jsonPath("$.[*].contactPhone").value(hasItem(DEFAULT_CONTACT_PHONE.toString())));
    }
}
