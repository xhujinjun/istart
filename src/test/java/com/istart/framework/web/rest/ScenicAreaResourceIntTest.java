package com.istart.framework.web.rest;

import com.istart.framework.IstartApp;
import com.istart.framework.domain.ScenicArea;
import com.istart.framework.repository.ScenicAreaRepository;
import com.istart.framework.service.ScenicAreaService;
import com.istart.framework.repository.search.ScenicAreaSearchRepository;
import com.istart.framework.web.rest.dto.ScenicAreaDTO;
import com.istart.framework.web.rest.mapper.ScenicAreaMapper;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ScenicAreaResource REST controller.
 *
 * @see ScenicAreaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstartApp.class)
@WebAppConfiguration
@IntegrationTest
public class ScenicAreaResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_SCENIC_STAR = "AAAAA";
    private static final String UPDATED_SCENIC_STAR = "BBBBB";

    private static final BigDecimal DEFAULT_SCORE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SCORE = new BigDecimal(2);

    private static final Integer DEFAULT_INTEREST_NUM = 1;
    private static final Integer UPDATED_INTEREST_NUM = 2;

    private static final BigDecimal DEFAULT_TICKET = new BigDecimal(1);
    private static final BigDecimal UPDATED_TICKET = new BigDecimal(2);
    private static final String DEFAULT_TICKET_DESC = "AAAAA";
    private static final String UPDATED_TICKET_DESC = "BBBBB";

    private static final ZonedDateTime DEFAULT_OPEN_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_OPEN_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_OPEN_START_TIME_STR = dateTimeFormatter.format(DEFAULT_OPEN_START_TIME);

    private static final ZonedDateTime DEFAULT_OPEN_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_OPEN_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_OPEN_END_TIME_STR = dateTimeFormatter.format(DEFAULT_OPEN_END_TIME);
    private static final String DEFAULT_OPEN_DESC = "AAAAA";
    private static final String UPDATED_OPEN_DESC = "BBBBB";

    private static final BigDecimal DEFAULT_PLAY_TIME_NUM = new BigDecimal(1);
    private static final BigDecimal UPDATED_PLAY_TIME_NUM = new BigDecimal(2);
    private static final String DEFAULT_CONTACT = "AAAAA";
    private static final String UPDATED_CONTACT = "BBBBB";
    private static final String DEFAULT_WEBSITE = "AAAAA";
    private static final String UPDATED_WEBSITE = "BBBBB";

    private static final String DEFAULT_OVERVIEW = "AAAAA";
    private static final String UPDATED_OVERVIEW = "BBBBB";
    private static final String DEFAULT_IMAGE_PATH = "AAAAA";
    private static final String UPDATED_IMAGE_PATH = "BBBBB";
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
    private ScenicAreaRepository scenicAreaRepository;

    @Inject
    private ScenicAreaMapper scenicAreaMapper;

    @Inject
    private ScenicAreaService scenicAreaService;

    @Inject
    private ScenicAreaSearchRepository scenicAreaSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restScenicAreaMockMvc;

    private ScenicArea scenicArea;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ScenicAreaResource scenicAreaResource = new ScenicAreaResource();
        ReflectionTestUtils.setField(scenicAreaResource, "scenicAreaService", scenicAreaService);
        ReflectionTestUtils.setField(scenicAreaResource, "scenicAreaMapper", scenicAreaMapper);
        this.restScenicAreaMockMvc = MockMvcBuilders.standaloneSetup(scenicAreaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        scenicAreaSearchRepository.deleteAll();
        scenicArea = new ScenicArea();
        scenicArea.setName(DEFAULT_NAME);
        scenicArea.setScenicStar(DEFAULT_SCENIC_STAR);
        scenicArea.setScore(DEFAULT_SCORE);
        scenicArea.setInterestNum(DEFAULT_INTEREST_NUM);
        scenicArea.setTicket(DEFAULT_TICKET);
        scenicArea.setTicketDesc(DEFAULT_TICKET_DESC);
        scenicArea.setOpenStartTime(DEFAULT_OPEN_START_TIME);
        scenicArea.setOpenEndTime(DEFAULT_OPEN_END_TIME);
        scenicArea.setOpenDesc(DEFAULT_OPEN_DESC);
        scenicArea.setPlayTimeNum(DEFAULT_PLAY_TIME_NUM);
        scenicArea.setContact(DEFAULT_CONTACT);
        scenicArea.setWebsite(DEFAULT_WEBSITE);
        scenicArea.setOverview(DEFAULT_OVERVIEW);
        scenicArea.setImagePath(DEFAULT_IMAGE_PATH);
        scenicArea.setDataCreator(DEFAULT_DATA_CREATOR);
        scenicArea.setDataUpdater(DEFAULT_DATA_UPDATER);
        scenicArea.setDataCreateDatetime(DEFAULT_DATA_CREATE_DATETIME);
        scenicArea.setDataUpdateDatetime(DEFAULT_DATA_UPDATE_DATETIME);
        scenicArea.setDataStatus(DEFAULT_DATA_STATUS);
    }

    @Test
    @Transactional
    public void createScenicArea() throws Exception {
        int databaseSizeBeforeCreate = scenicAreaRepository.findAll().size();

        // Create the ScenicArea
        ScenicAreaDTO scenicAreaDTO = scenicAreaMapper.scenicAreaToScenicAreaDTO(scenicArea);

        restScenicAreaMockMvc.perform(post("/api/scenic-areas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(scenicAreaDTO)))
                .andExpect(status().isCreated());

        // Validate the ScenicArea in the database
        List<ScenicArea> scenicAreas = scenicAreaRepository.findAll();
        assertThat(scenicAreas).hasSize(databaseSizeBeforeCreate + 1);
        ScenicArea testScenicArea = scenicAreas.get(scenicAreas.size() - 1);
        assertThat(testScenicArea.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testScenicArea.getScenicStar()).isEqualTo(DEFAULT_SCENIC_STAR);
        assertThat(testScenicArea.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testScenicArea.getInterestNum()).isEqualTo(DEFAULT_INTEREST_NUM);
        assertThat(testScenicArea.getTicket()).isEqualTo(DEFAULT_TICKET);
        assertThat(testScenicArea.getTicketDesc()).isEqualTo(DEFAULT_TICKET_DESC);
        assertThat(testScenicArea.getOpenStartTime()).isEqualTo(DEFAULT_OPEN_START_TIME);
        assertThat(testScenicArea.getOpenEndTime()).isEqualTo(DEFAULT_OPEN_END_TIME);
        assertThat(testScenicArea.getOpenDesc()).isEqualTo(DEFAULT_OPEN_DESC);
        assertThat(testScenicArea.getPlayTimeNum()).isEqualTo(DEFAULT_PLAY_TIME_NUM);
        assertThat(testScenicArea.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testScenicArea.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testScenicArea.getOverview()).isEqualTo(DEFAULT_OVERVIEW);
        assertThat(testScenicArea.getImagePath()).isEqualTo(DEFAULT_IMAGE_PATH);
        assertThat(testScenicArea.getDataCreator()).isEqualTo(DEFAULT_DATA_CREATOR);
        assertThat(testScenicArea.getDataUpdater()).isEqualTo(DEFAULT_DATA_UPDATER);
        assertThat(testScenicArea.getDataCreateDatetime()).isEqualTo(DEFAULT_DATA_CREATE_DATETIME);
        assertThat(testScenicArea.getDataUpdateDatetime()).isEqualTo(DEFAULT_DATA_UPDATE_DATETIME);
        assertThat(testScenicArea.getDataStatus()).isEqualTo(DEFAULT_DATA_STATUS);

        // Validate the ScenicArea in ElasticSearch
        ScenicArea scenicAreaEs = scenicAreaSearchRepository.findOne(testScenicArea.getId());
        assertThat(scenicAreaEs).isEqualToComparingFieldByField(testScenicArea);
    }

    @Test
    @Transactional
    public void getAllScenicAreas() throws Exception {
        // Initialize the database
        scenicAreaRepository.saveAndFlush(scenicArea);

        // Get all the scenicAreas
        restScenicAreaMockMvc.perform(get("/api/scenic-areas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(scenicArea.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].scenicStar").value(hasItem(DEFAULT_SCENIC_STAR.toString())))
                .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.intValue())))
                .andExpect(jsonPath("$.[*].interestNum").value(hasItem(DEFAULT_INTEREST_NUM)))
                .andExpect(jsonPath("$.[*].ticket").value(hasItem(DEFAULT_TICKET.intValue())))
                .andExpect(jsonPath("$.[*].ticketDesc").value(hasItem(DEFAULT_TICKET_DESC.toString())))
                .andExpect(jsonPath("$.[*].openStartTime").value(hasItem(DEFAULT_OPEN_START_TIME_STR)))
                .andExpect(jsonPath("$.[*].openEndTime").value(hasItem(DEFAULT_OPEN_END_TIME_STR)))
                .andExpect(jsonPath("$.[*].openDesc").value(hasItem(DEFAULT_OPEN_DESC.toString())))
                .andExpect(jsonPath("$.[*].playTimeNum").value(hasItem(DEFAULT_PLAY_TIME_NUM.intValue())))
                .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())))
                .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
                .andExpect(jsonPath("$.[*].overview").value(hasItem(DEFAULT_OVERVIEW.toString())))
                .andExpect(jsonPath("$.[*].imagePath").value(hasItem(DEFAULT_IMAGE_PATH.toString())))
                .andExpect(jsonPath("$.[*].dataCreator").value(hasItem(DEFAULT_DATA_CREATOR.toString())))
                .andExpect(jsonPath("$.[*].dataUpdater").value(hasItem(DEFAULT_DATA_UPDATER.toString())))
                .andExpect(jsonPath("$.[*].dataCreateDatetime").value(hasItem(DEFAULT_DATA_CREATE_DATETIME_STR)))
                .andExpect(jsonPath("$.[*].dataUpdateDatetime").value(hasItem(DEFAULT_DATA_UPDATE_DATETIME_STR)))
                .andExpect(jsonPath("$.[*].dataStatus").value(hasItem(DEFAULT_DATA_STATUS)));
    }

    @Test
    @Transactional
    public void getScenicArea() throws Exception {
        // Initialize the database
        scenicAreaRepository.saveAndFlush(scenicArea);

        // Get the scenicArea
        restScenicAreaMockMvc.perform(get("/api/scenic-areas/{id}", scenicArea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(scenicArea.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.scenicStar").value(DEFAULT_SCENIC_STAR.toString()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.intValue()))
            .andExpect(jsonPath("$.interestNum").value(DEFAULT_INTEREST_NUM))
            .andExpect(jsonPath("$.ticket").value(DEFAULT_TICKET.intValue()))
            .andExpect(jsonPath("$.ticketDesc").value(DEFAULT_TICKET_DESC.toString()))
            .andExpect(jsonPath("$.openStartTime").value(DEFAULT_OPEN_START_TIME_STR))
            .andExpect(jsonPath("$.openEndTime").value(DEFAULT_OPEN_END_TIME_STR))
            .andExpect(jsonPath("$.openDesc").value(DEFAULT_OPEN_DESC.toString()))
            .andExpect(jsonPath("$.playTimeNum").value(DEFAULT_PLAY_TIME_NUM.intValue()))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()))
            .andExpect(jsonPath("$.overview").value(DEFAULT_OVERVIEW.toString()))
            .andExpect(jsonPath("$.imagePath").value(DEFAULT_IMAGE_PATH.toString()))
            .andExpect(jsonPath("$.dataCreator").value(DEFAULT_DATA_CREATOR.toString()))
            .andExpect(jsonPath("$.dataUpdater").value(DEFAULT_DATA_UPDATER.toString()))
            .andExpect(jsonPath("$.dataCreateDatetime").value(DEFAULT_DATA_CREATE_DATETIME_STR))
            .andExpect(jsonPath("$.dataUpdateDatetime").value(DEFAULT_DATA_UPDATE_DATETIME_STR))
            .andExpect(jsonPath("$.dataStatus").value(DEFAULT_DATA_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingScenicArea() throws Exception {
        // Get the scenicArea
        restScenicAreaMockMvc.perform(get("/api/scenic-areas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScenicArea() throws Exception {
        // Initialize the database
        scenicAreaRepository.saveAndFlush(scenicArea);
        scenicAreaSearchRepository.save(scenicArea);
        int databaseSizeBeforeUpdate = scenicAreaRepository.findAll().size();

        // Update the scenicArea
        ScenicArea updatedScenicArea = new ScenicArea();
        updatedScenicArea.setId(scenicArea.getId());
        updatedScenicArea.setName(UPDATED_NAME);
        updatedScenicArea.setScenicStar(UPDATED_SCENIC_STAR);
        updatedScenicArea.setScore(UPDATED_SCORE);
        updatedScenicArea.setInterestNum(UPDATED_INTEREST_NUM);
        updatedScenicArea.setTicket(UPDATED_TICKET);
        updatedScenicArea.setTicketDesc(UPDATED_TICKET_DESC);
        updatedScenicArea.setOpenStartTime(UPDATED_OPEN_START_TIME);
        updatedScenicArea.setOpenEndTime(UPDATED_OPEN_END_TIME);
        updatedScenicArea.setOpenDesc(UPDATED_OPEN_DESC);
        updatedScenicArea.setPlayTimeNum(UPDATED_PLAY_TIME_NUM);
        updatedScenicArea.setContact(UPDATED_CONTACT);
        updatedScenicArea.setWebsite(UPDATED_WEBSITE);
        updatedScenicArea.setOverview(UPDATED_OVERVIEW);
        updatedScenicArea.setImagePath(UPDATED_IMAGE_PATH);
        updatedScenicArea.setDataCreator(UPDATED_DATA_CREATOR);
        updatedScenicArea.setDataUpdater(UPDATED_DATA_UPDATER);
        updatedScenicArea.setDataCreateDatetime(UPDATED_DATA_CREATE_DATETIME);
        updatedScenicArea.setDataUpdateDatetime(UPDATED_DATA_UPDATE_DATETIME);
        updatedScenicArea.setDataStatus(UPDATED_DATA_STATUS);
        ScenicAreaDTO scenicAreaDTO = scenicAreaMapper.scenicAreaToScenicAreaDTO(updatedScenicArea);

        restScenicAreaMockMvc.perform(put("/api/scenic-areas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(scenicAreaDTO)))
                .andExpect(status().isOk());

        // Validate the ScenicArea in the database
        List<ScenicArea> scenicAreas = scenicAreaRepository.findAll();
        assertThat(scenicAreas).hasSize(databaseSizeBeforeUpdate);
        ScenicArea testScenicArea = scenicAreas.get(scenicAreas.size() - 1);
        assertThat(testScenicArea.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testScenicArea.getScenicStar()).isEqualTo(UPDATED_SCENIC_STAR);
        assertThat(testScenicArea.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testScenicArea.getInterestNum()).isEqualTo(UPDATED_INTEREST_NUM);
        assertThat(testScenicArea.getTicket()).isEqualTo(UPDATED_TICKET);
        assertThat(testScenicArea.getTicketDesc()).isEqualTo(UPDATED_TICKET_DESC);
        assertThat(testScenicArea.getOpenStartTime()).isEqualTo(UPDATED_OPEN_START_TIME);
        assertThat(testScenicArea.getOpenEndTime()).isEqualTo(UPDATED_OPEN_END_TIME);
        assertThat(testScenicArea.getOpenDesc()).isEqualTo(UPDATED_OPEN_DESC);
        assertThat(testScenicArea.getPlayTimeNum()).isEqualTo(UPDATED_PLAY_TIME_NUM);
        assertThat(testScenicArea.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testScenicArea.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testScenicArea.getOverview()).isEqualTo(UPDATED_OVERVIEW);
        assertThat(testScenicArea.getImagePath()).isEqualTo(UPDATED_IMAGE_PATH);
        assertThat(testScenicArea.getDataCreator()).isEqualTo(UPDATED_DATA_CREATOR);
        assertThat(testScenicArea.getDataUpdater()).isEqualTo(UPDATED_DATA_UPDATER);
        assertThat(testScenicArea.getDataCreateDatetime()).isEqualTo(UPDATED_DATA_CREATE_DATETIME);
        assertThat(testScenicArea.getDataUpdateDatetime()).isEqualTo(UPDATED_DATA_UPDATE_DATETIME);
        assertThat(testScenicArea.getDataStatus()).isEqualTo(UPDATED_DATA_STATUS);

        // Validate the ScenicArea in ElasticSearch
        ScenicArea scenicAreaEs = scenicAreaSearchRepository.findOne(testScenicArea.getId());
        assertThat(scenicAreaEs).isEqualToComparingFieldByField(testScenicArea);
    }

    @Test
    @Transactional
    public void deleteScenicArea() throws Exception {
        // Initialize the database
        scenicAreaRepository.saveAndFlush(scenicArea);
        scenicAreaSearchRepository.save(scenicArea);
        int databaseSizeBeforeDelete = scenicAreaRepository.findAll().size();

        // Get the scenicArea
        restScenicAreaMockMvc.perform(delete("/api/scenic-areas/{id}", scenicArea.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean scenicAreaExistsInEs = scenicAreaSearchRepository.exists(scenicArea.getId());
        assertThat(scenicAreaExistsInEs).isFalse();

        // Validate the database is empty
        List<ScenicArea> scenicAreas = scenicAreaRepository.findAll();
        assertThat(scenicAreas).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchScenicArea() throws Exception {
        // Initialize the database
        scenicAreaRepository.saveAndFlush(scenicArea);
        scenicAreaSearchRepository.save(scenicArea);

        // Search the scenicArea
        restScenicAreaMockMvc.perform(get("/api/_search/scenic-areas?query=id:" + scenicArea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scenicArea.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].scenicStar").value(hasItem(DEFAULT_SCENIC_STAR.toString())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.intValue())))
            .andExpect(jsonPath("$.[*].interestNum").value(hasItem(DEFAULT_INTEREST_NUM)))
            .andExpect(jsonPath("$.[*].ticket").value(hasItem(DEFAULT_TICKET.intValue())))
            .andExpect(jsonPath("$.[*].ticketDesc").value(hasItem(DEFAULT_TICKET_DESC.toString())))
            .andExpect(jsonPath("$.[*].openStartTime").value(hasItem(DEFAULT_OPEN_START_TIME_STR)))
            .andExpect(jsonPath("$.[*].openEndTime").value(hasItem(DEFAULT_OPEN_END_TIME_STR)))
            .andExpect(jsonPath("$.[*].openDesc").value(hasItem(DEFAULT_OPEN_DESC.toString())))
            .andExpect(jsonPath("$.[*].playTimeNum").value(hasItem(DEFAULT_PLAY_TIME_NUM.intValue())))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
            .andExpect(jsonPath("$.[*].overview").value(hasItem(DEFAULT_OVERVIEW.toString())))
            .andExpect(jsonPath("$.[*].imagePath").value(hasItem(DEFAULT_IMAGE_PATH.toString())))
            .andExpect(jsonPath("$.[*].dataCreator").value(hasItem(DEFAULT_DATA_CREATOR.toString())))
            .andExpect(jsonPath("$.[*].dataUpdater").value(hasItem(DEFAULT_DATA_UPDATER.toString())))
            .andExpect(jsonPath("$.[*].dataCreateDatetime").value(hasItem(DEFAULT_DATA_CREATE_DATETIME_STR)))
            .andExpect(jsonPath("$.[*].dataUpdateDatetime").value(hasItem(DEFAULT_DATA_UPDATE_DATETIME_STR)))
            .andExpect(jsonPath("$.[*].dataStatus").value(hasItem(DEFAULT_DATA_STATUS)));
    }
}
