package com.istart.framework.web.rest;

import com.istart.framework.IstartApp;
import com.istart.framework.domain.ScenicSpot;
import com.istart.framework.repository.ScenicSpotRepository;
import com.istart.framework.service.ScenicSpotService;
import com.istart.framework.repository.search.ScenicSpotSearchRepository;
import com.istart.framework.web.rest.dto.ScenicSpotDTO;
import com.istart.framework.web.rest.mapper.ScenicSpotMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ScenicSpotResource REST controller.
 *
 * @see ScenicSpotResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstartApp.class)
@WebAppConfiguration
@IntegrationTest
public class ScenicSpotResourceIntTest {


    private static final Integer DEFAULT_SCENIC_AREAS_ID = 1;
    private static final Integer UPDATED_SCENIC_AREAS_ID = 2;
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final String DEFAULT_INTRODUCE = "AAAAA";
    private static final String UPDATED_INTRODUCE = "BBBBB";

    @Inject
    private ScenicSpotRepository scenicSpotRepository;

    @Inject
    private ScenicSpotMapper scenicSpotMapper;

    @Inject
    private ScenicSpotService scenicSpotService;

    @Inject
    private ScenicSpotSearchRepository scenicSpotSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restScenicSpotMockMvc;

    private ScenicSpot scenicSpot;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ScenicSpotResource scenicSpotResource = new ScenicSpotResource();
        ReflectionTestUtils.setField(scenicSpotResource, "scenicSpotService", scenicSpotService);
        ReflectionTestUtils.setField(scenicSpotResource, "scenicSpotMapper", scenicSpotMapper);
        this.restScenicSpotMockMvc = MockMvcBuilders.standaloneSetup(scenicSpotResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        scenicSpotSearchRepository.deleteAll();
        scenicSpot = new ScenicSpot();
        scenicSpot.setScenicAreasId(DEFAULT_SCENIC_AREAS_ID);
        scenicSpot.setName(DEFAULT_NAME);
        scenicSpot.setIntroduce(DEFAULT_INTRODUCE);
    }

    @Test
    @Transactional
    public void createScenicSpot() throws Exception {
        int databaseSizeBeforeCreate = scenicSpotRepository.findAll().size();

        // Create the ScenicSpot
        ScenicSpotDTO scenicSpotDTO = scenicSpotMapper.scenicSpotToScenicSpotDTO(scenicSpot);

        restScenicSpotMockMvc.perform(post("/api/scenic-spots")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(scenicSpotDTO)))
                .andExpect(status().isCreated());

        // Validate the ScenicSpot in the database
        List<ScenicSpot> scenicSpots = scenicSpotRepository.findAll();
        assertThat(scenicSpots).hasSize(databaseSizeBeforeCreate + 1);
        ScenicSpot testScenicSpot = scenicSpots.get(scenicSpots.size() - 1);
        assertThat(testScenicSpot.getScenicAreasId()).isEqualTo(DEFAULT_SCENIC_AREAS_ID);
        assertThat(testScenicSpot.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testScenicSpot.getIntroduce()).isEqualTo(DEFAULT_INTRODUCE);

        // Validate the ScenicSpot in ElasticSearch
        ScenicSpot scenicSpotEs = scenicSpotSearchRepository.findOne(testScenicSpot.getId());
        assertThat(scenicSpotEs).isEqualToComparingFieldByField(testScenicSpot);
    }

    @Test
    @Transactional
    public void getAllScenicSpots() throws Exception {
        // Initialize the database
        scenicSpotRepository.saveAndFlush(scenicSpot);

        // Get all the scenicSpots
        restScenicSpotMockMvc.perform(get("/api/scenic-spots?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(scenicSpot.getId().intValue())))
                .andExpect(jsonPath("$.[*].scenicAreasId").value(hasItem(DEFAULT_SCENIC_AREAS_ID)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].introduce").value(hasItem(DEFAULT_INTRODUCE.toString())));
    }

    @Test
    @Transactional
    public void getScenicSpot() throws Exception {
        // Initialize the database
        scenicSpotRepository.saveAndFlush(scenicSpot);

        // Get the scenicSpot
        restScenicSpotMockMvc.perform(get("/api/scenic-spots/{id}", scenicSpot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(scenicSpot.getId().intValue()))
            .andExpect(jsonPath("$.scenicAreasId").value(DEFAULT_SCENIC_AREAS_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.introduce").value(DEFAULT_INTRODUCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingScenicSpot() throws Exception {
        // Get the scenicSpot
        restScenicSpotMockMvc.perform(get("/api/scenic-spots/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScenicSpot() throws Exception {
        // Initialize the database
        scenicSpotRepository.saveAndFlush(scenicSpot);
        scenicSpotSearchRepository.save(scenicSpot);
        int databaseSizeBeforeUpdate = scenicSpotRepository.findAll().size();

        // Update the scenicSpot
        ScenicSpot updatedScenicSpot = new ScenicSpot();
        updatedScenicSpot.setId(scenicSpot.getId());
        updatedScenicSpot.setScenicAreasId(UPDATED_SCENIC_AREAS_ID);
        updatedScenicSpot.setName(UPDATED_NAME);
        updatedScenicSpot.setIntroduce(UPDATED_INTRODUCE);
        ScenicSpotDTO scenicSpotDTO = scenicSpotMapper.scenicSpotToScenicSpotDTO(updatedScenicSpot);

        restScenicSpotMockMvc.perform(put("/api/scenic-spots")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(scenicSpotDTO)))
                .andExpect(status().isOk());

        // Validate the ScenicSpot in the database
        List<ScenicSpot> scenicSpots = scenicSpotRepository.findAll();
        assertThat(scenicSpots).hasSize(databaseSizeBeforeUpdate);
        ScenicSpot testScenicSpot = scenicSpots.get(scenicSpots.size() - 1);
        assertThat(testScenicSpot.getScenicAreasId()).isEqualTo(UPDATED_SCENIC_AREAS_ID);
        assertThat(testScenicSpot.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testScenicSpot.getIntroduce()).isEqualTo(UPDATED_INTRODUCE);

        // Validate the ScenicSpot in ElasticSearch
        ScenicSpot scenicSpotEs = scenicSpotSearchRepository.findOne(testScenicSpot.getId());
        assertThat(scenicSpotEs).isEqualToComparingFieldByField(testScenicSpot);
    }

    @Test
    @Transactional
    public void deleteScenicSpot() throws Exception {
        // Initialize the database
        scenicSpotRepository.saveAndFlush(scenicSpot);
        scenicSpotSearchRepository.save(scenicSpot);
        int databaseSizeBeforeDelete = scenicSpotRepository.findAll().size();

        // Get the scenicSpot
        restScenicSpotMockMvc.perform(delete("/api/scenic-spots/{id}", scenicSpot.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean scenicSpotExistsInEs = scenicSpotSearchRepository.exists(scenicSpot.getId());
        assertThat(scenicSpotExistsInEs).isFalse();

        // Validate the database is empty
        List<ScenicSpot> scenicSpots = scenicSpotRepository.findAll();
        assertThat(scenicSpots).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchScenicSpot() throws Exception {
        // Initialize the database
        scenicSpotRepository.saveAndFlush(scenicSpot);
        scenicSpotSearchRepository.save(scenicSpot);

        // Search the scenicSpot
        restScenicSpotMockMvc.perform(get("/api/_search/scenic-spots?query=id:" + scenicSpot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scenicSpot.getId().intValue())))
            .andExpect(jsonPath("$.[*].scenicAreasId").value(hasItem(DEFAULT_SCENIC_AREAS_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].introduce").value(hasItem(DEFAULT_INTRODUCE.toString())));
    }
}
