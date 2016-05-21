package com.istart.framework.web.rest;

import com.istart.framework.IstartApp;
import com.istart.framework.domain.DicType;
import com.istart.framework.repository.DicTypeRepository;
import com.istart.framework.service.DicTypeService;
import com.istart.framework.repository.search.DicTypeSearchRepository;
import com.istart.framework.web.rest.dto.DicTypeDTO;
import com.istart.framework.web.rest.mapper.DicTypeMapper;

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
 * Test class for the DicTypeResource REST controller.
 *
 * @see DicTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstartApp.class)
@WebAppConfiguration
@IntegrationTest
public class DicTypeResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_DIC_TYPE_CODE = "AAAAA";
    private static final String UPDATED_DIC_TYPE_CODE = "BBBBB";
    private static final String DEFAULT_DIC_TYPE_NAME = "AAAAA";
    private static final String UPDATED_DIC_TYPE_NAME = "BBBBB";
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
    private DicTypeRepository dicTypeRepository;

    @Inject
    private DicTypeMapper dicTypeMapper;

    @Inject
    private DicTypeService dicTypeService;

    @Inject
    private DicTypeSearchRepository dicTypeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDicTypeMockMvc;

    private DicType dicType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DicTypeResource dicTypeResource = new DicTypeResource();
        ReflectionTestUtils.setField(dicTypeResource, "dicTypeService", dicTypeService);
        ReflectionTestUtils.setField(dicTypeResource, "dicTypeMapper", dicTypeMapper);
        this.restDicTypeMockMvc = MockMvcBuilders.standaloneSetup(dicTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dicTypeSearchRepository.deleteAll();
        dicType = new DicType();
        dicType.setDicTypeCode(DEFAULT_DIC_TYPE_CODE);
        dicType.setDicTypeName(DEFAULT_DIC_TYPE_NAME);
        dicType.setDataCreator(DEFAULT_DATA_CREATOR);
        dicType.setDataUpdater(DEFAULT_DATA_UPDATER);
        dicType.setDataCreateDatetime(DEFAULT_DATA_CREATE_DATETIME);
        dicType.setDataUpdateDatetime(DEFAULT_DATA_UPDATE_DATETIME);
        dicType.setDataStatus(DEFAULT_DATA_STATUS);
    }

    @Test
    @Transactional
    public void createDicType() throws Exception {
        int databaseSizeBeforeCreate = dicTypeRepository.findAll().size();

        // Create the DicType
        DicTypeDTO dicTypeDTO = dicTypeMapper.dicTypeToDicTypeDTO(dicType);

        restDicTypeMockMvc.perform(post("/api/dic-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dicTypeDTO)))
                .andExpect(status().isCreated());

        // Validate the DicType in the database
        List<DicType> dicTypes = dicTypeRepository.findAll();
        assertThat(dicTypes).hasSize(databaseSizeBeforeCreate + 1);
        DicType testDicType = dicTypes.get(dicTypes.size() - 1);
        assertThat(testDicType.getDicTypeCode()).isEqualTo(DEFAULT_DIC_TYPE_CODE);
        assertThat(testDicType.getDicTypeName()).isEqualTo(DEFAULT_DIC_TYPE_NAME);
        assertThat(testDicType.getDataCreator()).isEqualTo(DEFAULT_DATA_CREATOR);
        assertThat(testDicType.getDataUpdater()).isEqualTo(DEFAULT_DATA_UPDATER);
        assertThat(testDicType.getDataCreateDatetime()).isEqualTo(DEFAULT_DATA_CREATE_DATETIME);
        assertThat(testDicType.getDataUpdateDatetime()).isEqualTo(DEFAULT_DATA_UPDATE_DATETIME);
        assertThat(testDicType.getDataStatus()).isEqualTo(DEFAULT_DATA_STATUS);

        // Validate the DicType in ElasticSearch
        DicType dicTypeEs = dicTypeSearchRepository.findOne(testDicType.getId());
        assertThat(dicTypeEs).isEqualToComparingFieldByField(testDicType);
    }

    @Test
    @Transactional
    public void getAllDicTypes() throws Exception {
        // Initialize the database
        dicTypeRepository.saveAndFlush(dicType);

        // Get all the dicTypes
        restDicTypeMockMvc.perform(get("/api/dic-types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dicType.getId().intValue())))
                .andExpect(jsonPath("$.[*].dicTypeCode").value(hasItem(DEFAULT_DIC_TYPE_CODE.toString())))
                .andExpect(jsonPath("$.[*].dicTypeName").value(hasItem(DEFAULT_DIC_TYPE_NAME.toString())))
                .andExpect(jsonPath("$.[*].dataCreator").value(hasItem(DEFAULT_DATA_CREATOR.toString())))
                .andExpect(jsonPath("$.[*].dataUpdater").value(hasItem(DEFAULT_DATA_UPDATER.toString())))
                .andExpect(jsonPath("$.[*].dataCreateDatetime").value(hasItem(DEFAULT_DATA_CREATE_DATETIME_STR)))
                .andExpect(jsonPath("$.[*].dataUpdateDatetime").value(hasItem(DEFAULT_DATA_UPDATE_DATETIME_STR)))
                .andExpect(jsonPath("$.[*].dataStatus").value(hasItem(DEFAULT_DATA_STATUS)));
    }

    @Test
    @Transactional
    public void getDicType() throws Exception {
        // Initialize the database
        dicTypeRepository.saveAndFlush(dicType);

        // Get the dicType
        restDicTypeMockMvc.perform(get("/api/dic-types/{id}", dicType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dicType.getId().intValue()))
            .andExpect(jsonPath("$.dicTypeCode").value(DEFAULT_DIC_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.dicTypeName").value(DEFAULT_DIC_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.dataCreator").value(DEFAULT_DATA_CREATOR.toString()))
            .andExpect(jsonPath("$.dataUpdater").value(DEFAULT_DATA_UPDATER.toString()))
            .andExpect(jsonPath("$.dataCreateDatetime").value(DEFAULT_DATA_CREATE_DATETIME_STR))
            .andExpect(jsonPath("$.dataUpdateDatetime").value(DEFAULT_DATA_UPDATE_DATETIME_STR))
            .andExpect(jsonPath("$.dataStatus").value(DEFAULT_DATA_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingDicType() throws Exception {
        // Get the dicType
        restDicTypeMockMvc.perform(get("/api/dic-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDicType() throws Exception {
        // Initialize the database
        dicTypeRepository.saveAndFlush(dicType);
        dicTypeSearchRepository.save(dicType);
        int databaseSizeBeforeUpdate = dicTypeRepository.findAll().size();

        // Update the dicType
        DicType updatedDicType = new DicType();
        updatedDicType.setId(dicType.getId());
        updatedDicType.setDicTypeCode(UPDATED_DIC_TYPE_CODE);
        updatedDicType.setDicTypeName(UPDATED_DIC_TYPE_NAME);
        updatedDicType.setDataCreator(UPDATED_DATA_CREATOR);
        updatedDicType.setDataUpdater(UPDATED_DATA_UPDATER);
        updatedDicType.setDataCreateDatetime(UPDATED_DATA_CREATE_DATETIME);
        updatedDicType.setDataUpdateDatetime(UPDATED_DATA_UPDATE_DATETIME);
        updatedDicType.setDataStatus(UPDATED_DATA_STATUS);
        DicTypeDTO dicTypeDTO = dicTypeMapper.dicTypeToDicTypeDTO(updatedDicType);

        restDicTypeMockMvc.perform(put("/api/dic-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dicTypeDTO)))
                .andExpect(status().isOk());

        // Validate the DicType in the database
        List<DicType> dicTypes = dicTypeRepository.findAll();
        assertThat(dicTypes).hasSize(databaseSizeBeforeUpdate);
        DicType testDicType = dicTypes.get(dicTypes.size() - 1);
        assertThat(testDicType.getDicTypeCode()).isEqualTo(UPDATED_DIC_TYPE_CODE);
        assertThat(testDicType.getDicTypeName()).isEqualTo(UPDATED_DIC_TYPE_NAME);
        assertThat(testDicType.getDataCreator()).isEqualTo(UPDATED_DATA_CREATOR);
        assertThat(testDicType.getDataUpdater()).isEqualTo(UPDATED_DATA_UPDATER);
        assertThat(testDicType.getDataCreateDatetime()).isEqualTo(UPDATED_DATA_CREATE_DATETIME);
        assertThat(testDicType.getDataUpdateDatetime()).isEqualTo(UPDATED_DATA_UPDATE_DATETIME);
        assertThat(testDicType.getDataStatus()).isEqualTo(UPDATED_DATA_STATUS);

        // Validate the DicType in ElasticSearch
        DicType dicTypeEs = dicTypeSearchRepository.findOne(testDicType.getId());
        assertThat(dicTypeEs).isEqualToComparingFieldByField(testDicType);
    }

    @Test
    @Transactional
    public void deleteDicType() throws Exception {
        // Initialize the database
        dicTypeRepository.saveAndFlush(dicType);
        dicTypeSearchRepository.save(dicType);
        int databaseSizeBeforeDelete = dicTypeRepository.findAll().size();

        // Get the dicType
        restDicTypeMockMvc.perform(delete("/api/dic-types/{id}", dicType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean dicTypeExistsInEs = dicTypeSearchRepository.exists(dicType.getId());
        assertThat(dicTypeExistsInEs).isFalse();

        // Validate the database is empty
        List<DicType> dicTypes = dicTypeRepository.findAll();
        assertThat(dicTypes).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDicType() throws Exception {
        // Initialize the database
        dicTypeRepository.saveAndFlush(dicType);
        dicTypeSearchRepository.save(dicType);

        // Search the dicType
        restDicTypeMockMvc.perform(get("/api/_search/dic-types?query=id:" + dicType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dicType.getId().intValue())))
            .andExpect(jsonPath("$.[*].dicTypeCode").value(hasItem(DEFAULT_DIC_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].dicTypeName").value(hasItem(DEFAULT_DIC_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].dataCreator").value(hasItem(DEFAULT_DATA_CREATOR.toString())))
            .andExpect(jsonPath("$.[*].dataUpdater").value(hasItem(DEFAULT_DATA_UPDATER.toString())))
            .andExpect(jsonPath("$.[*].dataCreateDatetime").value(hasItem(DEFAULT_DATA_CREATE_DATETIME_STR)))
            .andExpect(jsonPath("$.[*].dataUpdateDatetime").value(hasItem(DEFAULT_DATA_UPDATE_DATETIME_STR)))
            .andExpect(jsonPath("$.[*].dataStatus").value(hasItem(DEFAULT_DATA_STATUS)));
    }
}
