package com.istart.framework.web.rest;

import com.istart.framework.IstartApp;
import com.istart.framework.domain.Area;
import com.istart.framework.repository.AreaRepository;
import com.istart.framework.service.AreaService;
import com.istart.framework.repository.search.AreaSearchRepository;
import com.istart.framework.web.rest.dto.AreaDTO;
import com.istart.framework.web.rest.mapper.AreaMapper;

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

import com.istart.framework.domain.enumeration.DataStatus;

/**
 * Test class for the AreaResource REST controller.
 *
 * @see AreaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstartApp.class)
@WebAppConfiguration
@IntegrationTest
public class AreaResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_AREAID = "AAAAA";
    private static final String UPDATED_AREAID = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_PARENTID = "AAAAA";
    private static final String UPDATED_PARENTID = "BBBBB";
    private static final String DEFAULT_PARENTNAME = "AAAAA";
    private static final String UPDATED_PARENTNAME = "BBBBB";
    private static final String DEFAULT_ADDR = "AAAAA";
    private static final String UPDATED_ADDR = "BBBBB";

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    private static final Boolean DEFAULT_ISLEAF = false;
    private static final Boolean UPDATED_ISLEAF = true;

    private static final ZonedDateTime DEFAULT_CREATE_DATETIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATETIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATETIME_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATETIME);

    private static final ZonedDateTime DEFAULT_MODIFIY_DATETIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_MODIFIY_DATETIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_MODIFIY_DATETIME_STR = dateTimeFormatter.format(DEFAULT_MODIFIY_DATETIME);

    private static final DataStatus DEFAULT_STATUS = DataStatus.NOVALIDATE;
    private static final DataStatus UPDATED_STATUS = DataStatus.VALIDATE;

    @Inject
    private AreaRepository areaRepository;

    @Inject
    private AreaMapper areaMapper;

    @Inject
    private AreaService areaService;

    @Inject
    private AreaSearchRepository areaSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAreaMockMvc;

    private Area area;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AreaResource areaResource = new AreaResource();
        ReflectionTestUtils.setField(areaResource, "areaService", areaService);
        ReflectionTestUtils.setField(areaResource, "areaMapper", areaMapper);
        this.restAreaMockMvc = MockMvcBuilders.standaloneSetup(areaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        areaSearchRepository.deleteAll();
        area = new Area();
        area.setAreaid(DEFAULT_AREAID);
        area.setName(DEFAULT_NAME);
        area.setParentid(DEFAULT_PARENTID);
        area.setParentname(DEFAULT_PARENTNAME);
        area.setAddr(DEFAULT_ADDR);
        area.setLevel(DEFAULT_LEVEL);
        area.setIsleaf(DEFAULT_ISLEAF);
        area.setCreateDatetime(DEFAULT_CREATE_DATETIME);
        area.setModifiyDatetime(DEFAULT_MODIFIY_DATETIME);
        area.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createArea() throws Exception {
        int databaseSizeBeforeCreate = areaRepository.findAll().size();

        // Create the Area
        AreaDTO areaDTO = areaMapper.areaToAreaDTO(area);

        restAreaMockMvc.perform(post("/api/areas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(areaDTO)))
                .andExpect(status().isCreated());

        // Validate the Area in the database
        List<Area> areas = areaRepository.findAll();
        assertThat(areas).hasSize(databaseSizeBeforeCreate + 1);
        Area testArea = areas.get(areas.size() - 1);
        assertThat(testArea.getAreaid()).isEqualTo(DEFAULT_AREAID);
        assertThat(testArea.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testArea.getParentid()).isEqualTo(DEFAULT_PARENTID);
        assertThat(testArea.getParentname()).isEqualTo(DEFAULT_PARENTNAME);
        assertThat(testArea.getAddr()).isEqualTo(DEFAULT_ADDR);
        assertThat(testArea.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testArea.isIsleaf()).isEqualTo(DEFAULT_ISLEAF);
        assertThat(testArea.getCreateDatetime()).isEqualTo(DEFAULT_CREATE_DATETIME);
        assertThat(testArea.getModifiyDatetime()).isEqualTo(DEFAULT_MODIFIY_DATETIME);
        assertThat(testArea.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Area in ElasticSearch
        Area areaEs = areaSearchRepository.findOne(testArea.getId());
        assertThat(areaEs).isEqualToComparingFieldByField(testArea);
    }

    @Test
    @Transactional
    public void getAllAreas() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areas
        restAreaMockMvc.perform(get("/api/areas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(area.getId().intValue())))
                .andExpect(jsonPath("$.[*].areaid").value(hasItem(DEFAULT_AREAID.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].parentid").value(hasItem(DEFAULT_PARENTID.toString())))
                .andExpect(jsonPath("$.[*].parentname").value(hasItem(DEFAULT_PARENTNAME.toString())))
                .andExpect(jsonPath("$.[*].addr").value(hasItem(DEFAULT_ADDR.toString())))
                .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
                .andExpect(jsonPath("$.[*].isleaf").value(hasItem(DEFAULT_ISLEAF.booleanValue())))
                .andExpect(jsonPath("$.[*].createDatetime").value(hasItem(DEFAULT_CREATE_DATETIME_STR)))
                .andExpect(jsonPath("$.[*].modifiyDatetime").value(hasItem(DEFAULT_MODIFIY_DATETIME_STR)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getArea() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get the area
        restAreaMockMvc.perform(get("/api/areas/{id}", area.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(area.getId().intValue()))
            .andExpect(jsonPath("$.areaid").value(DEFAULT_AREAID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.parentid").value(DEFAULT_PARENTID.toString()))
            .andExpect(jsonPath("$.parentname").value(DEFAULT_PARENTNAME.toString()))
            .andExpect(jsonPath("$.addr").value(DEFAULT_ADDR.toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.isleaf").value(DEFAULT_ISLEAF.booleanValue()))
            .andExpect(jsonPath("$.createDatetime").value(DEFAULT_CREATE_DATETIME_STR))
            .andExpect(jsonPath("$.modifiyDatetime").value(DEFAULT_MODIFIY_DATETIME_STR))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArea() throws Exception {
        // Get the area
        restAreaMockMvc.perform(get("/api/areas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArea() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);
        areaSearchRepository.save(area);
        int databaseSizeBeforeUpdate = areaRepository.findAll().size();

        // Update the area
        Area updatedArea = new Area();
        updatedArea.setId(area.getId());
        updatedArea.setAreaid(UPDATED_AREAID);
        updatedArea.setName(UPDATED_NAME);
        updatedArea.setParentid(UPDATED_PARENTID);
        updatedArea.setParentname(UPDATED_PARENTNAME);
        updatedArea.setAddr(UPDATED_ADDR);
        updatedArea.setLevel(UPDATED_LEVEL);
        updatedArea.setIsleaf(UPDATED_ISLEAF);
        updatedArea.setCreateDatetime(UPDATED_CREATE_DATETIME);
        updatedArea.setModifiyDatetime(UPDATED_MODIFIY_DATETIME);
        updatedArea.setStatus(UPDATED_STATUS);
        AreaDTO areaDTO = areaMapper.areaToAreaDTO(updatedArea);

        restAreaMockMvc.perform(put("/api/areas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(areaDTO)))
                .andExpect(status().isOk());

        // Validate the Area in the database
        List<Area> areas = areaRepository.findAll();
        assertThat(areas).hasSize(databaseSizeBeforeUpdate);
        Area testArea = areas.get(areas.size() - 1);
        assertThat(testArea.getAreaid()).isEqualTo(UPDATED_AREAID);
        assertThat(testArea.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testArea.getParentid()).isEqualTo(UPDATED_PARENTID);
        assertThat(testArea.getParentname()).isEqualTo(UPDATED_PARENTNAME);
        assertThat(testArea.getAddr()).isEqualTo(UPDATED_ADDR);
        assertThat(testArea.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testArea.isIsleaf()).isEqualTo(UPDATED_ISLEAF);
        assertThat(testArea.getCreateDatetime()).isEqualTo(UPDATED_CREATE_DATETIME);
        assertThat(testArea.getModifiyDatetime()).isEqualTo(UPDATED_MODIFIY_DATETIME);
        assertThat(testArea.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Area in ElasticSearch
        Area areaEs = areaSearchRepository.findOne(testArea.getId());
        assertThat(areaEs).isEqualToComparingFieldByField(testArea);
    }

    @Test
    @Transactional
    public void deleteArea() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);
        areaSearchRepository.save(area);
        int databaseSizeBeforeDelete = areaRepository.findAll().size();

        // Get the area
        restAreaMockMvc.perform(delete("/api/areas/{id}", area.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean areaExistsInEs = areaSearchRepository.exists(area.getId());
        assertThat(areaExistsInEs).isFalse();

        // Validate the database is empty
        List<Area> areas = areaRepository.findAll();
        assertThat(areas).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchArea() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);
        areaSearchRepository.save(area);

        // Search the area
        restAreaMockMvc.perform(get("/api/_search/areas?query=id:" + area.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(area.getId().intValue())))
            .andExpect(jsonPath("$.[*].areaid").value(hasItem(DEFAULT_AREAID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].parentid").value(hasItem(DEFAULT_PARENTID.toString())))
            .andExpect(jsonPath("$.[*].parentname").value(hasItem(DEFAULT_PARENTNAME.toString())))
            .andExpect(jsonPath("$.[*].addr").value(hasItem(DEFAULT_ADDR.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].isleaf").value(hasItem(DEFAULT_ISLEAF.booleanValue())))
            .andExpect(jsonPath("$.[*].createDatetime").value(hasItem(DEFAULT_CREATE_DATETIME_STR)))
            .andExpect(jsonPath("$.[*].modifiyDatetime").value(hasItem(DEFAULT_MODIFIY_DATETIME_STR)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
