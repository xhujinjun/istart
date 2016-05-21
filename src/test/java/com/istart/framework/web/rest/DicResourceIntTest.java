package com.istart.framework.web.rest;

import com.istart.framework.IstartApp;
import com.istart.framework.domain.Dic;
import com.istart.framework.repository.DicRepository;
import com.istart.framework.service.DicService;
import com.istart.framework.repository.search.DicSearchRepository;
import com.istart.framework.web.rest.dto.DicDTO;
import com.istart.framework.web.rest.mapper.DicMapper;

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
 * Test class for the DicResource REST controller.
 *
 * @see DicResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstartApp.class)
@WebAppConfiguration
@IntegrationTest
public class DicResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final Long DEFAULT_DIC_TYPE_ID = 1L;
    private static final Long UPDATED_DIC_TYPE_ID = 2L;
    private static final String DEFAULT_DIC_CODE = "AAAAA";
    private static final String UPDATED_DIC_CODE = "BBBBB";
    private static final String DEFAULT_DIC_NAME = "AAAAA";
    private static final String UPDATED_DIC_NAME = "BBBBB";
    private static final String DEFAULT_DIC_NAME_DEFINITION = "AAAAA";
    private static final String UPDATED_DIC_NAME_DEFINITION = "BBBBB";
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
    private DicRepository dicRepository;

    @Inject
    private DicMapper dicMapper;

    @Inject
    private DicService dicService;

    @Inject
    private DicSearchRepository dicSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDicMockMvc;

    private Dic dic;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DicResource dicResource = new DicResource();
        ReflectionTestUtils.setField(dicResource, "dicService", dicService);
        ReflectionTestUtils.setField(dicResource, "dicMapper", dicMapper);
        this.restDicMockMvc = MockMvcBuilders.standaloneSetup(dicResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dicSearchRepository.deleteAll();
        dic = new Dic();
        dic.setDicTypeId(DEFAULT_DIC_TYPE_ID);
        dic.setDicCode(DEFAULT_DIC_CODE);
        dic.setDicName(DEFAULT_DIC_NAME);
        dic.setDicNameDefinition(DEFAULT_DIC_NAME_DEFINITION);
        dic.setDataCreator(DEFAULT_DATA_CREATOR);
        dic.setDataUpdater(DEFAULT_DATA_UPDATER);
        dic.setDataCreateDatetime(DEFAULT_DATA_CREATE_DATETIME);
        dic.setDataUpdateDatetime(DEFAULT_DATA_UPDATE_DATETIME);
        dic.setDataStatus(DEFAULT_DATA_STATUS);
    }

    @Test
    @Transactional
    public void createDic() throws Exception {
        int databaseSizeBeforeCreate = dicRepository.findAll().size();

        // Create the Dic
        DicDTO dicDTO = dicMapper.dicToDicDTO(dic);

        restDicMockMvc.perform(post("/api/dics")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dicDTO)))
                .andExpect(status().isCreated());

        // Validate the Dic in the database
        List<Dic> dics = dicRepository.findAll();
        assertThat(dics).hasSize(databaseSizeBeforeCreate + 1);
        Dic testDic = dics.get(dics.size() - 1);
        assertThat(testDic.getDicTypeId()).isEqualTo(DEFAULT_DIC_TYPE_ID);
        assertThat(testDic.getDicCode()).isEqualTo(DEFAULT_DIC_CODE);
        assertThat(testDic.getDicName()).isEqualTo(DEFAULT_DIC_NAME);
        assertThat(testDic.getDicNameDefinition()).isEqualTo(DEFAULT_DIC_NAME_DEFINITION);
        assertThat(testDic.getDataCreator()).isEqualTo(DEFAULT_DATA_CREATOR);
        assertThat(testDic.getDataUpdater()).isEqualTo(DEFAULT_DATA_UPDATER);
        assertThat(testDic.getDataCreateDatetime()).isEqualTo(DEFAULT_DATA_CREATE_DATETIME);
        assertThat(testDic.getDataUpdateDatetime()).isEqualTo(DEFAULT_DATA_UPDATE_DATETIME);
        assertThat(testDic.getDataStatus()).isEqualTo(DEFAULT_DATA_STATUS);

        // Validate the Dic in ElasticSearch
        Dic dicEs = dicSearchRepository.findOne(testDic.getId());
        assertThat(dicEs).isEqualToComparingFieldByField(testDic);
    }

    @Test
    @Transactional
    public void getAllDics() throws Exception {
        // Initialize the database
        dicRepository.saveAndFlush(dic);

        // Get all the dics
        restDicMockMvc.perform(get("/api/dics?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dic.getId().intValue())))
                .andExpect(jsonPath("$.[*].dicTypeId").value(hasItem(DEFAULT_DIC_TYPE_ID.intValue())))
                .andExpect(jsonPath("$.[*].dicCode").value(hasItem(DEFAULT_DIC_CODE.toString())))
                .andExpect(jsonPath("$.[*].dicName").value(hasItem(DEFAULT_DIC_NAME.toString())))
                .andExpect(jsonPath("$.[*].dicNameDefinition").value(hasItem(DEFAULT_DIC_NAME_DEFINITION.toString())))
                .andExpect(jsonPath("$.[*].dataCreator").value(hasItem(DEFAULT_DATA_CREATOR.toString())))
                .andExpect(jsonPath("$.[*].dataUpdater").value(hasItem(DEFAULT_DATA_UPDATER.toString())))
                .andExpect(jsonPath("$.[*].dataCreateDatetime").value(hasItem(DEFAULT_DATA_CREATE_DATETIME_STR)))
                .andExpect(jsonPath("$.[*].dataUpdateDatetime").value(hasItem(DEFAULT_DATA_UPDATE_DATETIME_STR)))
                .andExpect(jsonPath("$.[*].dataStatus").value(hasItem(DEFAULT_DATA_STATUS)));
    }

    @Test
    @Transactional
    public void getDic() throws Exception {
        // Initialize the database
        dicRepository.saveAndFlush(dic);

        // Get the dic
        restDicMockMvc.perform(get("/api/dics/{id}", dic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dic.getId().intValue()))
            .andExpect(jsonPath("$.dicTypeId").value(DEFAULT_DIC_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.dicCode").value(DEFAULT_DIC_CODE.toString()))
            .andExpect(jsonPath("$.dicName").value(DEFAULT_DIC_NAME.toString()))
            .andExpect(jsonPath("$.dicNameDefinition").value(DEFAULT_DIC_NAME_DEFINITION.toString()))
            .andExpect(jsonPath("$.dataCreator").value(DEFAULT_DATA_CREATOR.toString()))
            .andExpect(jsonPath("$.dataUpdater").value(DEFAULT_DATA_UPDATER.toString()))
            .andExpect(jsonPath("$.dataCreateDatetime").value(DEFAULT_DATA_CREATE_DATETIME_STR))
            .andExpect(jsonPath("$.dataUpdateDatetime").value(DEFAULT_DATA_UPDATE_DATETIME_STR))
            .andExpect(jsonPath("$.dataStatus").value(DEFAULT_DATA_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingDic() throws Exception {
        // Get the dic
        restDicMockMvc.perform(get("/api/dics/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDic() throws Exception {
        // Initialize the database
        dicRepository.saveAndFlush(dic);
        dicSearchRepository.save(dic);
        int databaseSizeBeforeUpdate = dicRepository.findAll().size();

        // Update the dic
        Dic updatedDic = new Dic();
        updatedDic.setId(dic.getId());
        updatedDic.setDicTypeId(UPDATED_DIC_TYPE_ID);
        updatedDic.setDicCode(UPDATED_DIC_CODE);
        updatedDic.setDicName(UPDATED_DIC_NAME);
        updatedDic.setDicNameDefinition(UPDATED_DIC_NAME_DEFINITION);
        updatedDic.setDataCreator(UPDATED_DATA_CREATOR);
        updatedDic.setDataUpdater(UPDATED_DATA_UPDATER);
        updatedDic.setDataCreateDatetime(UPDATED_DATA_CREATE_DATETIME);
        updatedDic.setDataUpdateDatetime(UPDATED_DATA_UPDATE_DATETIME);
        updatedDic.setDataStatus(UPDATED_DATA_STATUS);
        DicDTO dicDTO = dicMapper.dicToDicDTO(updatedDic);

        restDicMockMvc.perform(put("/api/dics")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dicDTO)))
                .andExpect(status().isOk());

        // Validate the Dic in the database
        List<Dic> dics = dicRepository.findAll();
        assertThat(dics).hasSize(databaseSizeBeforeUpdate);
        Dic testDic = dics.get(dics.size() - 1);
        assertThat(testDic.getDicTypeId()).isEqualTo(UPDATED_DIC_TYPE_ID);
        assertThat(testDic.getDicCode()).isEqualTo(UPDATED_DIC_CODE);
        assertThat(testDic.getDicName()).isEqualTo(UPDATED_DIC_NAME);
        assertThat(testDic.getDicNameDefinition()).isEqualTo(UPDATED_DIC_NAME_DEFINITION);
        assertThat(testDic.getDataCreator()).isEqualTo(UPDATED_DATA_CREATOR);
        assertThat(testDic.getDataUpdater()).isEqualTo(UPDATED_DATA_UPDATER);
        assertThat(testDic.getDataCreateDatetime()).isEqualTo(UPDATED_DATA_CREATE_DATETIME);
        assertThat(testDic.getDataUpdateDatetime()).isEqualTo(UPDATED_DATA_UPDATE_DATETIME);
        assertThat(testDic.getDataStatus()).isEqualTo(UPDATED_DATA_STATUS);

        // Validate the Dic in ElasticSearch
        Dic dicEs = dicSearchRepository.findOne(testDic.getId());
        assertThat(dicEs).isEqualToComparingFieldByField(testDic);
    }

    @Test
    @Transactional
    public void deleteDic() throws Exception {
        // Initialize the database
        dicRepository.saveAndFlush(dic);
        dicSearchRepository.save(dic);
        int databaseSizeBeforeDelete = dicRepository.findAll().size();

        // Get the dic
        restDicMockMvc.perform(delete("/api/dics/{id}", dic.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean dicExistsInEs = dicSearchRepository.exists(dic.getId());
        assertThat(dicExistsInEs).isFalse();

        // Validate the database is empty
        List<Dic> dics = dicRepository.findAll();
        assertThat(dics).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDic() throws Exception {
        // Initialize the database
        dicRepository.saveAndFlush(dic);
        dicSearchRepository.save(dic);

        // Search the dic
        restDicMockMvc.perform(get("/api/_search/dics?query=id:" + dic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dic.getId().intValue())))
            .andExpect(jsonPath("$.[*].dicTypeId").value(hasItem(DEFAULT_DIC_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].dicCode").value(hasItem(DEFAULT_DIC_CODE.toString())))
            .andExpect(jsonPath("$.[*].dicName").value(hasItem(DEFAULT_DIC_NAME.toString())))
            .andExpect(jsonPath("$.[*].dicNameDefinition").value(hasItem(DEFAULT_DIC_NAME_DEFINITION.toString())))
            .andExpect(jsonPath("$.[*].dataCreator").value(hasItem(DEFAULT_DATA_CREATOR.toString())))
            .andExpect(jsonPath("$.[*].dataUpdater").value(hasItem(DEFAULT_DATA_UPDATER.toString())))
            .andExpect(jsonPath("$.[*].dataCreateDatetime").value(hasItem(DEFAULT_DATA_CREATE_DATETIME_STR)))
            .andExpect(jsonPath("$.[*].dataUpdateDatetime").value(hasItem(DEFAULT_DATA_UPDATE_DATETIME_STR)))
            .andExpect(jsonPath("$.[*].dataStatus").value(hasItem(DEFAULT_DATA_STATUS)));
    }
}
