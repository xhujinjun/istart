package com.istart.framework.web.rest;

import com.istart.framework.IstartApp;
import com.istart.framework.domain.Sysres;
import com.istart.framework.repository.SysresRepository;
import com.istart.framework.service.SysresService;
import com.istart.framework.repository.search.SysresSearchRepository;
import com.istart.framework.web.rest.dto.SysresDTO;
import com.istart.framework.web.rest.mapper.SysresMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the SysresResource REST controller.
 *
 * @see SysresResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstartApp.class)
@WebAppConfiguration
@IntegrationTest
public class SysresResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;
    private static final String DEFAULT_URL = "AAAAA";
    private static final String UPDATED_URL = "BBBBB";
    private static final String DEFAULT_RESDESC = "AAAAA";
    private static final String UPDATED_RESDESC = "BBBBB";

    @Inject
    private SysresRepository sysresRepository;

    @Inject
    private SysresMapper sysresMapper;

    @Inject
    private SysresService sysresService;

    @Inject
    private SysresSearchRepository sysresSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSysresMockMvc;

    private Sysres sysres;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SysresResource sysresResource = new SysresResource();
        ReflectionTestUtils.setField(sysresResource, "sysresService", sysresService);
        ReflectionTestUtils.setField(sysresResource, "sysresMapper", sysresMapper);
        this.restSysresMockMvc = MockMvcBuilders.standaloneSetup(sysresResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        sysresSearchRepository.deleteAll();
        sysres = new Sysres();
        sysres.setName(DEFAULT_NAME);
        sysres.setType(DEFAULT_TYPE);
        sysres.setUrl(DEFAULT_URL);
        sysres.setResdesc(DEFAULT_RESDESC);
    }

    @Test
    @Transactional
    public void createSysres() throws Exception {
        int databaseSizeBeforeCreate = sysresRepository.findAll().size();

        // Create the Sysres
        SysresDTO sysresDTO = sysresMapper.sysresToSysresDTO(sysres);

        restSysresMockMvc.perform(post("/api/sysres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sysresDTO)))
                .andExpect(status().isCreated());

        // Validate the Sysres in the database
        List<Sysres> sysres = sysresRepository.findAll();
        assertThat(sysres).hasSize(databaseSizeBeforeCreate + 1);
        Sysres testSysres = sysres.get(sysres.size() - 1);
        assertThat(testSysres.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSysres.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSysres.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testSysres.getResdesc()).isEqualTo(DEFAULT_RESDESC);

        // Validate the Sysres in ElasticSearch
        Sysres sysresEs = sysresSearchRepository.findOne(testSysres.getId());
        assertThat(sysresEs).isEqualToComparingFieldByField(testSysres);
    }

    @Test
    @Transactional
    public void getAllSysres() throws Exception {
        // Initialize the database
        sysresRepository.saveAndFlush(sysres);

        // Get all the sysres
        restSysresMockMvc.perform(get("/api/sysres?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sysres.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
                .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
                .andExpect(jsonPath("$.[*].resdesc").value(hasItem(DEFAULT_RESDESC.toString())));
    }

    @Test
    @Transactional
    public void getSysres() throws Exception {
        // Initialize the database
        sysresRepository.saveAndFlush(sysres);

        // Get the sysres
        restSysresMockMvc.perform(get("/api/sysres/{id}", sysres.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(sysres.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.resdesc").value(DEFAULT_RESDESC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSysres() throws Exception {
        // Get the sysres
        restSysresMockMvc.perform(get("/api/sysres/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysres() throws Exception {
        // Initialize the database
        sysresRepository.saveAndFlush(sysres);
        sysresSearchRepository.save(sysres);
        int databaseSizeBeforeUpdate = sysresRepository.findAll().size();

        // Update the sysres
        Sysres updatedSysres = new Sysres();
        updatedSysres.setId(sysres.getId());
        updatedSysres.setName(UPDATED_NAME);
        updatedSysres.setType(UPDATED_TYPE);
        updatedSysres.setUrl(UPDATED_URL);
        updatedSysres.setResdesc(UPDATED_RESDESC);
        SysresDTO sysresDTO = sysresMapper.sysresToSysresDTO(updatedSysres);

        restSysresMockMvc.perform(put("/api/sysres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sysresDTO)))
                .andExpect(status().isOk());

        // Validate the Sysres in the database
        List<Sysres> sysres = sysresRepository.findAll();
        assertThat(sysres).hasSize(databaseSizeBeforeUpdate);
        Sysres testSysres = sysres.get(sysres.size() - 1);
        assertThat(testSysres.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSysres.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSysres.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testSysres.getResdesc()).isEqualTo(UPDATED_RESDESC);

        // Validate the Sysres in ElasticSearch
        Sysres sysresEs = sysresSearchRepository.findOne(testSysres.getId());
        assertThat(sysresEs).isEqualToComparingFieldByField(testSysres);
    }

    @Test
    @Transactional
    public void deleteSysres() throws Exception {
        // Initialize the database
        sysresRepository.saveAndFlush(sysres);
        sysresSearchRepository.save(sysres);
        int databaseSizeBeforeDelete = sysresRepository.findAll().size();

        // Get the sysres
        restSysresMockMvc.perform(delete("/api/sysres/{id}", sysres.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean sysresExistsInEs = sysresSearchRepository.exists(sysres.getId());
        assertThat(sysresExistsInEs).isFalse();

        // Validate the database is empty
        List<Sysres> sysres = sysresRepository.findAll();
        assertThat(sysres).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSysres() throws Exception {
        // Initialize the database
        sysresRepository.saveAndFlush(sysres);
        sysresSearchRepository.save(sysres);

        // Search the sysres
        restSysresMockMvc.perform(get("/api/_search/sysres?query=id:" + sysres.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysres.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].resdesc").value(hasItem(DEFAULT_RESDESC.toString())));
    }
}
