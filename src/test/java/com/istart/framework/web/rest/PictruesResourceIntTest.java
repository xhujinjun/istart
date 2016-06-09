package com.istart.framework.web.rest;

import com.istart.framework.IstartApp;
import com.istart.framework.domain.Pictrues;
import com.istart.framework.repository.PictruesRepository;
import com.istart.framework.service.PictruesService;
import com.istart.framework.repository.search.PictruesSearchRepository;
import com.istart.framework.web.rest.dto.PictruesDTO;
import com.istart.framework.web.rest.mapper.PictruesMapper;

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
 * Test class for the PictruesResource REST controller.
 *
 * @see PictruesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstartApp.class)
@WebAppConfiguration
@IntegrationTest
public class PictruesResourceIntTest {

    private static final String DEFAULT_PNO = "AAAAA";
    private static final String UPDATED_PNO = "BBBBB";
    private static final String DEFAULT_PIC_PATH = "AAAAA";
    private static final String UPDATED_PIC_PATH = "BBBBB";

    @Inject
    private PictruesRepository pictruesRepository;

    @Inject
    private PictruesMapper pictruesMapper;

    @Inject
    private PictruesService pictruesService;

    @Inject
    private PictruesSearchRepository pictruesSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPictruesMockMvc;

    private Pictrues pictrues;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PictruesResource pictruesResource = new PictruesResource();
        ReflectionTestUtils.setField(pictruesResource, "pictruesService", pictruesService);
        ReflectionTestUtils.setField(pictruesResource, "pictruesMapper", pictruesMapper);
        this.restPictruesMockMvc = MockMvcBuilders.standaloneSetup(pictruesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pictruesSearchRepository.deleteAll();
        pictrues = new Pictrues();
        pictrues.setPno(DEFAULT_PNO);
        pictrues.setPicPath(DEFAULT_PIC_PATH);
    }

    @Test
    @Transactional
    public void createPictrues() throws Exception {
        int databaseSizeBeforeCreate = pictruesRepository.findAll().size();

        // Create the Pictrues
        PictruesDTO pictruesDTO = pictruesMapper.pictruesToPictruesDTO(pictrues);

        restPictruesMockMvc.perform(post("/api/pictrues")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pictruesDTO)))
                .andExpect(status().isCreated());

        // Validate the Pictrues in the database
        List<Pictrues> pictrues = pictruesRepository.findAll();
        assertThat(pictrues).hasSize(databaseSizeBeforeCreate + 1);
        Pictrues testPictrues = pictrues.get(pictrues.size() - 1);
        assertThat(testPictrues.getPno()).isEqualTo(DEFAULT_PNO);
        assertThat(testPictrues.getPicPath()).isEqualTo(DEFAULT_PIC_PATH);

        // Validate the Pictrues in ElasticSearch
        Pictrues pictruesEs = pictruesSearchRepository.findOne(testPictrues.getId());
        assertThat(pictruesEs).isEqualToComparingFieldByField(testPictrues);
    }

    @Test
    @Transactional
    public void getAllPictrues() throws Exception {
        // Initialize the database
        pictruesRepository.saveAndFlush(pictrues);

        // Get all the pictrues
        restPictruesMockMvc.perform(get("/api/pictrues?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pictrues.getId().intValue())))
                .andExpect(jsonPath("$.[*].pno").value(hasItem(DEFAULT_PNO.toString())))
                .andExpect(jsonPath("$.[*].picPath").value(hasItem(DEFAULT_PIC_PATH.toString())));
    }

    @Test
    @Transactional
    public void getPictrues() throws Exception {
        // Initialize the database
        pictruesRepository.saveAndFlush(pictrues);

        // Get the pictrues
        restPictruesMockMvc.perform(get("/api/pictrues/{id}", pictrues.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pictrues.getId().intValue()))
            .andExpect(jsonPath("$.pno").value(DEFAULT_PNO.toString()))
            .andExpect(jsonPath("$.picPath").value(DEFAULT_PIC_PATH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPictrues() throws Exception {
        // Get the pictrues
        restPictruesMockMvc.perform(get("/api/pictrues/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePictrues() throws Exception {
        // Initialize the database
        pictruesRepository.saveAndFlush(pictrues);
        pictruesSearchRepository.save(pictrues);
        int databaseSizeBeforeUpdate = pictruesRepository.findAll().size();

        // Update the pictrues
        Pictrues updatedPictrues = new Pictrues();
        updatedPictrues.setId(pictrues.getId());
        updatedPictrues.setPno(UPDATED_PNO);
        updatedPictrues.setPicPath(UPDATED_PIC_PATH);
        PictruesDTO pictruesDTO = pictruesMapper.pictruesToPictruesDTO(updatedPictrues);

        restPictruesMockMvc.perform(put("/api/pictrues")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pictruesDTO)))
                .andExpect(status().isOk());

        // Validate the Pictrues in the database
        List<Pictrues> pictrues = pictruesRepository.findAll();
        assertThat(pictrues).hasSize(databaseSizeBeforeUpdate);
        Pictrues testPictrues = pictrues.get(pictrues.size() - 1);
        assertThat(testPictrues.getPno()).isEqualTo(UPDATED_PNO);
        assertThat(testPictrues.getPicPath()).isEqualTo(UPDATED_PIC_PATH);

        // Validate the Pictrues in ElasticSearch
        Pictrues pictruesEs = pictruesSearchRepository.findOne(testPictrues.getId());
        assertThat(pictruesEs).isEqualToComparingFieldByField(testPictrues);
    }

    @Test
    @Transactional
    public void deletePictrues() throws Exception {
        // Initialize the database
        pictruesRepository.saveAndFlush(pictrues);
        pictruesSearchRepository.save(pictrues);
        int databaseSizeBeforeDelete = pictruesRepository.findAll().size();

        // Get the pictrues
        restPictruesMockMvc.perform(delete("/api/pictrues/{id}", pictrues.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean pictruesExistsInEs = pictruesSearchRepository.exists(pictrues.getId());
        assertThat(pictruesExistsInEs).isFalse();

        // Validate the database is empty
        List<Pictrues> pictrues = pictruesRepository.findAll();
        assertThat(pictrues).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPictrues() throws Exception {
        // Initialize the database
        pictruesRepository.saveAndFlush(pictrues);
        pictruesSearchRepository.save(pictrues);

        // Search the pictrues
        restPictruesMockMvc.perform(get("/api/_search/pictrues?query=id:" + pictrues.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pictrues.getId().intValue())))
            .andExpect(jsonPath("$.[*].pno").value(hasItem(DEFAULT_PNO.toString())))
            .andExpect(jsonPath("$.[*].picPath").value(hasItem(DEFAULT_PIC_PATH.toString())));
    }
}
