package com.istart.framework.web.rest;

import com.istart.framework.IstartApp;
import com.istart.framework.domain.TravelAgencyLine;
import com.istart.framework.repository.TravelAgencyLineRepository;
import com.istart.framework.service.TravelAgencyLineService;
import com.istart.framework.repository.search.TravelAgencyLineSearchRepository;
import com.istart.framework.web.rest.dto.TravelAgencyLineDTO;
import com.istart.framework.web.rest.mapper.TravelAgencyLineMapper;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the TravelAgencyLineResource REST controller.
 *
 * @see TravelAgencyLineResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstartApp.class)
@WebAppConfiguration
@IntegrationTest
public class TravelAgencyLineResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final Long DEFAULT_TRAVEL_AGENCY_ID = 1L;
    private static final Long UPDATED_TRAVEL_AGENCY_ID = 2L;
    private static final String DEFAULT_LINE_NUMBER = "AAAAA";
    private static final String UPDATED_LINE_NUMBER = "BBBBB";
    private static final String DEFAULT_LINE_NAME = "AAAAA";
    private static final String UPDATED_LINE_NAME = "BBBBB";
    private static final String DEFAULT_SPOT_INTRODUCE = "AAAAA";
    private static final String UPDATED_SPOT_INTRODUCE = "BBBBB";

    private static final ZonedDateTime DEFAULT_LINE_DATETIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_LINE_DATETIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_LINE_DATETIME_STR = dateTimeFormatter.format(DEFAULT_LINE_DATETIME);
    private static final String DEFAULT_LINE_CITY = "AAAAA";
    private static final String UPDATED_LINE_CITY = "BBBBB";
    private static final String DEFAULT_DATA_CREATOR = "AAAAA";
    private static final String UPDATED_DATA_CREATOR = "BBBBB";
    private static final String DEFAULT_DATA_UPDATER = "AAAAA";
    private static final String UPDATED_DATA_UPDATER = "BBBBB";

    private static final ZonedDateTime DEFAULT_DATA_CREATE_DATETIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATA_CREATE_DATETIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATA_CREATE_DATETIME_STR = dateTimeFormatter.format(DEFAULT_DATA_CREATE_DATETIME);

    private static final ZonedDateTime DEFAULT_DATA_UPDATE_DATETIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATA_UPDATE_DATETIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATA_UPDATE_DATETIME_STR = dateTimeFormatter.format(DEFAULT_DATA_UPDATE_DATETIME);

    private static final Integer DEFAULT_DATA_STATUS = 1;
    private static final Integer UPDATED_DATA_STATUS = 2;

    @Inject
    private TravelAgencyLineRepository travelAgencyLineRepository;

    @Inject
    private TravelAgencyLineMapper travelAgencyLineMapper;

    @Inject
    private TravelAgencyLineService travelAgencyLineService;

    @Inject
    private TravelAgencyLineSearchRepository travelAgencyLineSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTravelAgencyLineMockMvc;

    private TravelAgencyLine travelAgencyLine;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TravelAgencyLineResource travelAgencyLineResource = new TravelAgencyLineResource();
        ReflectionTestUtils.setField(travelAgencyLineResource, "travelAgencyLineService", travelAgencyLineService);
        ReflectionTestUtils.setField(travelAgencyLineResource, "travelAgencyLineMapper", travelAgencyLineMapper);
        this.restTravelAgencyLineMockMvc = MockMvcBuilders.standaloneSetup(travelAgencyLineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        travelAgencyLineSearchRepository.deleteAll();
        travelAgencyLine = new TravelAgencyLine();
        travelAgencyLine.setTravelAgencyId(DEFAULT_TRAVEL_AGENCY_ID);
        travelAgencyLine.setLine_number(DEFAULT_LINE_NUMBER);
        travelAgencyLine.setLineName(DEFAULT_LINE_NAME);
        travelAgencyLine.setSpotIntroduce(DEFAULT_SPOT_INTRODUCE);
        travelAgencyLine.setLineDatetime(DEFAULT_LINE_DATETIME);
        travelAgencyLine.setLineCity(DEFAULT_LINE_CITY);
        travelAgencyLine.setDataCreator(DEFAULT_DATA_CREATOR);
        travelAgencyLine.setDataUpdater(DEFAULT_DATA_UPDATER);
        travelAgencyLine.setDataCreateDatetime(DEFAULT_DATA_CREATE_DATETIME);
        travelAgencyLine.setDataUpdateDatetime(DEFAULT_DATA_UPDATE_DATETIME);
        travelAgencyLine.setDataStatus(DEFAULT_DATA_STATUS);
    }

    @Test
    @Transactional
    public void createTravelAgencyLine() throws Exception {
        int databaseSizeBeforeCreate = travelAgencyLineRepository.findAll().size();

        // Create the TravelAgencyLine
        TravelAgencyLineDTO travelAgencyLineDTO = travelAgencyLineMapper.travelAgencyLineToTravelAgencyLineDTO(travelAgencyLine);

        restTravelAgencyLineMockMvc.perform(post("/api/travel-agency-lines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(travelAgencyLineDTO)))
                .andExpect(status().isCreated());

        // Validate the TravelAgencyLine in the database
        List<TravelAgencyLine> travelAgencyLines = travelAgencyLineRepository.findAll();
        assertThat(travelAgencyLines).hasSize(databaseSizeBeforeCreate + 1);
        TravelAgencyLine testTravelAgencyLine = travelAgencyLines.get(travelAgencyLines.size() - 1);
        assertThat(testTravelAgencyLine.getTravelAgencyId()).isEqualTo(DEFAULT_TRAVEL_AGENCY_ID);
        assertThat(testTravelAgencyLine.getLine_number()).isEqualTo(DEFAULT_LINE_NUMBER);
        assertThat(testTravelAgencyLine.getLineName()).isEqualTo(DEFAULT_LINE_NAME);
        assertThat(testTravelAgencyLine.getSpotIntroduce()).isEqualTo(DEFAULT_SPOT_INTRODUCE);
        assertThat(testTravelAgencyLine.getLineDatetime()).isEqualTo(DEFAULT_LINE_DATETIME);
        assertThat(testTravelAgencyLine.getLineCity()).isEqualTo(DEFAULT_LINE_CITY);
        assertThat(testTravelAgencyLine.getDataCreator()).isEqualTo(DEFAULT_DATA_CREATOR);
        assertThat(testTravelAgencyLine.getDataUpdater()).isEqualTo(DEFAULT_DATA_UPDATER);
        assertThat(testTravelAgencyLine.getDataCreateDatetime()).isEqualTo(DEFAULT_DATA_CREATE_DATETIME);
        assertThat(testTravelAgencyLine.getDataUpdateDatetime()).isEqualTo(DEFAULT_DATA_UPDATE_DATETIME);
        assertThat(testTravelAgencyLine.getDataStatus()).isEqualTo(DEFAULT_DATA_STATUS);

        // Validate the TravelAgencyLine in ElasticSearch
        TravelAgencyLine travelAgencyLineEs = travelAgencyLineSearchRepository.findOne(testTravelAgencyLine.getId());
        assertThat(travelAgencyLineEs).isEqualToComparingFieldByField(testTravelAgencyLine);
    }

    @Test
    @Transactional
    public void getAllTravelAgencyLines() throws Exception {
        // Initialize the database
        travelAgencyLineRepository.saveAndFlush(travelAgencyLine);

        // Get all the travelAgencyLines
        restTravelAgencyLineMockMvc.perform(get("/api/travel-agency-lines?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(travelAgencyLine.getId().intValue())))
                .andExpect(jsonPath("$.[*].travelAgencyId").value(hasItem(DEFAULT_TRAVEL_AGENCY_ID.intValue())))
                .andExpect(jsonPath("$.[*].line_number").value(hasItem(DEFAULT_LINE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].lineName").value(hasItem(DEFAULT_LINE_NAME.toString())))
                .andExpect(jsonPath("$.[*].spotIntroduce").value(hasItem(DEFAULT_SPOT_INTRODUCE.toString())))
                .andExpect(jsonPath("$.[*].lineDatetime").value(hasItem(DEFAULT_LINE_DATETIME_STR)))
                .andExpect(jsonPath("$.[*].lineCity").value(hasItem(DEFAULT_LINE_CITY.toString())))
                .andExpect(jsonPath("$.[*].dataCreator").value(hasItem(DEFAULT_DATA_CREATOR.toString())))
                .andExpect(jsonPath("$.[*].dataUpdater").value(hasItem(DEFAULT_DATA_UPDATER.toString())))
                .andExpect(jsonPath("$.[*].dataCreateDatetime").value(hasItem(DEFAULT_DATA_CREATE_DATETIME_STR)))
                .andExpect(jsonPath("$.[*].dataUpdateDatetime").value(hasItem(DEFAULT_DATA_UPDATE_DATETIME_STR)))
                .andExpect(jsonPath("$.[*].dataStatus").value(hasItem(DEFAULT_DATA_STATUS)));
    }

    @Test
    @Transactional
    public void getTravelAgencyLine() throws Exception {
        // Initialize the database
        travelAgencyLineRepository.saveAndFlush(travelAgencyLine);

        // Get the travelAgencyLine
        restTravelAgencyLineMockMvc.perform(get("/api/travel-agency-lines/{id}", travelAgencyLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(travelAgencyLine.getId().intValue()))
            .andExpect(jsonPath("$.travelAgencyId").value(DEFAULT_TRAVEL_AGENCY_ID.intValue()))
            .andExpect(jsonPath("$.line_number").value(DEFAULT_LINE_NUMBER.toString()))
            .andExpect(jsonPath("$.lineName").value(DEFAULT_LINE_NAME.toString()))
            .andExpect(jsonPath("$.spotIntroduce").value(DEFAULT_SPOT_INTRODUCE.toString()))
            .andExpect(jsonPath("$.lineDatetime").value(DEFAULT_LINE_DATETIME_STR))
            .andExpect(jsonPath("$.lineCity").value(DEFAULT_LINE_CITY.toString()))
            .andExpect(jsonPath("$.dataCreator").value(DEFAULT_DATA_CREATOR.toString()))
            .andExpect(jsonPath("$.dataUpdater").value(DEFAULT_DATA_UPDATER.toString()))
            .andExpect(jsonPath("$.dataCreateDatetime").value(DEFAULT_DATA_CREATE_DATETIME_STR))
            .andExpect(jsonPath("$.dataUpdateDatetime").value(DEFAULT_DATA_UPDATE_DATETIME_STR))
            .andExpect(jsonPath("$.dataStatus").value(DEFAULT_DATA_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingTravelAgencyLine() throws Exception {
        // Get the travelAgencyLine
        restTravelAgencyLineMockMvc.perform(get("/api/travel-agency-lines/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTravelAgencyLine() throws Exception {
        // Initialize the database
        travelAgencyLineRepository.saveAndFlush(travelAgencyLine);
        travelAgencyLineSearchRepository.save(travelAgencyLine);
        int databaseSizeBeforeUpdate = travelAgencyLineRepository.findAll().size();

        // Update the travelAgencyLine
        TravelAgencyLine updatedTravelAgencyLine = new TravelAgencyLine();
        updatedTravelAgencyLine.setId(travelAgencyLine.getId());
        updatedTravelAgencyLine.setTravelAgencyId(UPDATED_TRAVEL_AGENCY_ID);
        updatedTravelAgencyLine.setLine_number(UPDATED_LINE_NUMBER);
        updatedTravelAgencyLine.setLineName(UPDATED_LINE_NAME);
        updatedTravelAgencyLine.setSpotIntroduce(UPDATED_SPOT_INTRODUCE);
        updatedTravelAgencyLine.setLineDatetime(UPDATED_LINE_DATETIME);
        updatedTravelAgencyLine.setLineCity(UPDATED_LINE_CITY);
        updatedTravelAgencyLine.setDataCreator(UPDATED_DATA_CREATOR);
        updatedTravelAgencyLine.setDataUpdater(UPDATED_DATA_UPDATER);
        updatedTravelAgencyLine.setDataCreateDatetime(UPDATED_DATA_CREATE_DATETIME);
        updatedTravelAgencyLine.setDataUpdateDatetime(UPDATED_DATA_UPDATE_DATETIME);
        updatedTravelAgencyLine.setDataStatus(UPDATED_DATA_STATUS);
        TravelAgencyLineDTO travelAgencyLineDTO = travelAgencyLineMapper.travelAgencyLineToTravelAgencyLineDTO(updatedTravelAgencyLine);

        restTravelAgencyLineMockMvc.perform(put("/api/travel-agency-lines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(travelAgencyLineDTO)))
                .andExpect(status().isOk());

        // Validate the TravelAgencyLine in the database
        List<TravelAgencyLine> travelAgencyLines = travelAgencyLineRepository.findAll();
        assertThat(travelAgencyLines).hasSize(databaseSizeBeforeUpdate);
        TravelAgencyLine testTravelAgencyLine = travelAgencyLines.get(travelAgencyLines.size() - 1);
        assertThat(testTravelAgencyLine.getTravelAgencyId()).isEqualTo(UPDATED_TRAVEL_AGENCY_ID);
        assertThat(testTravelAgencyLine.getLine_number()).isEqualTo(UPDATED_LINE_NUMBER);
        assertThat(testTravelAgencyLine.getLineName()).isEqualTo(UPDATED_LINE_NAME);
        assertThat(testTravelAgencyLine.getSpotIntroduce()).isEqualTo(UPDATED_SPOT_INTRODUCE);
        assertThat(testTravelAgencyLine.getLineDatetime()).isEqualTo(UPDATED_LINE_DATETIME);
        assertThat(testTravelAgencyLine.getLineCity()).isEqualTo(UPDATED_LINE_CITY);
        assertThat(testTravelAgencyLine.getDataCreator()).isEqualTo(UPDATED_DATA_CREATOR);
        assertThat(testTravelAgencyLine.getDataUpdater()).isEqualTo(UPDATED_DATA_UPDATER);
        assertThat(testTravelAgencyLine.getDataCreateDatetime()).isEqualTo(UPDATED_DATA_CREATE_DATETIME);
        assertThat(testTravelAgencyLine.getDataUpdateDatetime()).isEqualTo(UPDATED_DATA_UPDATE_DATETIME);
        assertThat(testTravelAgencyLine.getDataStatus()).isEqualTo(UPDATED_DATA_STATUS);

        // Validate the TravelAgencyLine in ElasticSearch
        TravelAgencyLine travelAgencyLineEs = travelAgencyLineSearchRepository.findOne(testTravelAgencyLine.getId());
        assertThat(travelAgencyLineEs).isEqualToComparingFieldByField(testTravelAgencyLine);
    }

    @Test
    @Transactional
    public void deleteTravelAgencyLine() throws Exception {
        // Initialize the database
        travelAgencyLineRepository.saveAndFlush(travelAgencyLine);
        travelAgencyLineSearchRepository.save(travelAgencyLine);
        int databaseSizeBeforeDelete = travelAgencyLineRepository.findAll().size();

        // Get the travelAgencyLine
        restTravelAgencyLineMockMvc.perform(delete("/api/travel-agency-lines/{id}", travelAgencyLine.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean travelAgencyLineExistsInEs = travelAgencyLineSearchRepository.exists(travelAgencyLine.getId());
        assertThat(travelAgencyLineExistsInEs).isFalse();

        // Validate the database is empty
        List<TravelAgencyLine> travelAgencyLines = travelAgencyLineRepository.findAll();
        assertThat(travelAgencyLines).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTravelAgencyLine() throws Exception {
        // Initialize the database
        travelAgencyLineRepository.saveAndFlush(travelAgencyLine);
        travelAgencyLineSearchRepository.save(travelAgencyLine);

        // Search the travelAgencyLine
        restTravelAgencyLineMockMvc.perform(get("/api/_search/travel-agency-lines?query=id:" + travelAgencyLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(travelAgencyLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].travelAgencyId").value(hasItem(DEFAULT_TRAVEL_AGENCY_ID.intValue())))
            .andExpect(jsonPath("$.[*].line_number").value(hasItem(DEFAULT_LINE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].lineName").value(hasItem(DEFAULT_LINE_NAME.toString())))
            .andExpect(jsonPath("$.[*].spotIntroduce").value(hasItem(DEFAULT_SPOT_INTRODUCE.toString())))
            .andExpect(jsonPath("$.[*].lineDatetime").value(hasItem(DEFAULT_LINE_DATETIME_STR)))
            .andExpect(jsonPath("$.[*].lineCity").value(hasItem(DEFAULT_LINE_CITY.toString())))
            .andExpect(jsonPath("$.[*].dataCreator").value(hasItem(DEFAULT_DATA_CREATOR.toString())))
            .andExpect(jsonPath("$.[*].dataUpdater").value(hasItem(DEFAULT_DATA_UPDATER.toString())))
            .andExpect(jsonPath("$.[*].dataCreateDatetime").value(hasItem(DEFAULT_DATA_CREATE_DATETIME_STR)))
            .andExpect(jsonPath("$.[*].dataUpdateDatetime").value(hasItem(DEFAULT_DATA_UPDATE_DATETIME_STR)))
            .andExpect(jsonPath("$.[*].dataStatus").value(hasItem(DEFAULT_DATA_STATUS)));
    }
}
