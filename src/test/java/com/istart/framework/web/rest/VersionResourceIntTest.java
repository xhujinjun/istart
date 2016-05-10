package com.istart.framework.web.rest;

import com.istart.framework.IstartApp;
import com.istart.framework.domain.Version;
import com.istart.framework.repository.VersionRepository;
import com.istart.framework.service.VersionService;
import com.istart.framework.repository.search.VersionSearchRepository;
import com.istart.framework.web.rest.dto.VersionDTO;
import com.istart.framework.web.rest.mapper.VersionMapper;

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
 * Test class for the VersionResource REST controller.
 *
 * @see VersionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstartApp.class)
@WebAppConfiguration
@IntegrationTest
public class VersionResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_URL = "AAAAA";
    private static final String UPDATED_URL = "BBBBB";

    private static final Boolean DEFAULT_DEPRECATED = false;
    private static final Boolean UPDATED_DEPRECATED = true;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_TIME_STR = dateTimeFormatter.format(DEFAULT_CREATE_TIME);
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private VersionRepository versionRepository;

    @Inject
    private VersionMapper versionMapper;

    @Inject
    private VersionService versionService;

    @Inject
    private VersionSearchRepository versionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restVersionMockMvc;

    private Version version;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VersionResource versionResource = new VersionResource();
        ReflectionTestUtils.setField(versionResource, "versionService", versionService);
        ReflectionTestUtils.setField(versionResource, "versionMapper", versionMapper);
        this.restVersionMockMvc = MockMvcBuilders.standaloneSetup(versionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        versionSearchRepository.deleteAll();
        version = new Version();
        version.setName(DEFAULT_NAME);
        version.setUrl(DEFAULT_URL);
        version.setDeprecated(DEFAULT_DEPRECATED);
        version.setCreateTime(DEFAULT_CREATE_TIME);
        version.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createVersion() throws Exception {
        int databaseSizeBeforeCreate = versionRepository.findAll().size();

        // Create the Version
        VersionDTO versionDTO = versionMapper.versionToVersionDTO(version);

        restVersionMockMvc.perform(post("/api/versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(versionDTO)))
                .andExpect(status().isCreated());

        // Validate the Version in the database
        List<Version> versions = versionRepository.findAll();
        assertThat(versions).hasSize(databaseSizeBeforeCreate + 1);
        Version testVersion = versions.get(versions.size() - 1);
        assertThat(testVersion.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVersion.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testVersion.isDeprecated()).isEqualTo(DEFAULT_DEPRECATED);
        assertThat(testVersion.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testVersion.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Version in ElasticSearch
        Version versionEs = versionSearchRepository.findOne(testVersion.getId());
        assertThat(versionEs).isEqualToComparingFieldByField(testVersion);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = versionRepository.findAll().size();
        // set the field null
        version.setName(null);

        // Create the Version, which fails.
        VersionDTO versionDTO = versionMapper.versionToVersionDTO(version);

        restVersionMockMvc.perform(post("/api/versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(versionDTO)))
                .andExpect(status().isBadRequest());

        List<Version> versions = versionRepository.findAll();
        assertThat(versions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVersions() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versions
        restVersionMockMvc.perform(get("/api/versions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(version.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
                .andExpect(jsonPath("$.[*].deprecated").value(hasItem(DEFAULT_DEPRECATED.booleanValue())))
                .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME_STR)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getVersion() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get the version
        restVersionMockMvc.perform(get("/api/versions/{id}", version.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(version.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.deprecated").value(DEFAULT_DEPRECATED.booleanValue()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME_STR))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVersion() throws Exception {
        // Get the version
        restVersionMockMvc.perform(get("/api/versions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVersion() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);
        versionSearchRepository.save(version);
        int databaseSizeBeforeUpdate = versionRepository.findAll().size();

        // Update the version
        Version updatedVersion = new Version();
        updatedVersion.setId(version.getId());
        updatedVersion.setName(UPDATED_NAME);
        updatedVersion.setUrl(UPDATED_URL);
        updatedVersion.setDeprecated(UPDATED_DEPRECATED);
        updatedVersion.setCreateTime(UPDATED_CREATE_TIME);
        updatedVersion.setDescription(UPDATED_DESCRIPTION);
        VersionDTO versionDTO = versionMapper.versionToVersionDTO(updatedVersion);

        restVersionMockMvc.perform(put("/api/versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(versionDTO)))
                .andExpect(status().isOk());

        // Validate the Version in the database
        List<Version> versions = versionRepository.findAll();
        assertThat(versions).hasSize(databaseSizeBeforeUpdate);
        Version testVersion = versions.get(versions.size() - 1);
        assertThat(testVersion.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVersion.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testVersion.isDeprecated()).isEqualTo(UPDATED_DEPRECATED);
        assertThat(testVersion.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testVersion.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Version in ElasticSearch
        Version versionEs = versionSearchRepository.findOne(testVersion.getId());
        assertThat(versionEs).isEqualToComparingFieldByField(testVersion);
    }

    @Test
    @Transactional
    public void deleteVersion() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);
        versionSearchRepository.save(version);
        int databaseSizeBeforeDelete = versionRepository.findAll().size();

        // Get the version
        restVersionMockMvc.perform(delete("/api/versions/{id}", version.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean versionExistsInEs = versionSearchRepository.exists(version.getId());
        assertThat(versionExistsInEs).isFalse();

        // Validate the database is empty
        List<Version> versions = versionRepository.findAll();
        assertThat(versions).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchVersion() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);
        versionSearchRepository.save(version);

        // Search the version
        restVersionMockMvc.perform(get("/api/_search/versions?query=id:" + version.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(version.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].deprecated").value(hasItem(DEFAULT_DEPRECATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME_STR)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
}
