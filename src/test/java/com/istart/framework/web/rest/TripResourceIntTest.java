package com.istart.framework.web.rest;

import com.istart.framework.IstartApp;
import com.istart.framework.domain.Trip;
import com.istart.framework.repository.TripRepository;
import com.istart.framework.service.TripService;
import com.istart.framework.repository.search.TripSearchRepository;
import com.istart.framework.web.rest.dto.TripDTO;
import com.istart.framework.web.rest.mapper.TripMapper;

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
 * Test class for the TripResource REST controller.
 *
 * @see TripResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstartApp.class)
@WebAppConfiguration
@IntegrationTest
public class TripResourceIntTest {

    private static final String DEFAULT_PNO = "AAAAA";
    private static final String UPDATED_PNO = "BBBBB";
    private static final String DEFAULT_DAY = "AAAAA";
    private static final String UPDATED_DAY = "BBBBB";
    private static final String DEFAULT_DISCRIPE = "AAAAA";
    private static final String UPDATED_DISCRIPE = "BBBBB";

    @Inject
    private TripRepository tripRepository;

    @Inject
    private TripMapper tripMapper;

    @Inject
    private TripService tripService;

    @Inject
    private TripSearchRepository tripSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTripMockMvc;

    private Trip trip;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TripResource tripResource = new TripResource();
        ReflectionTestUtils.setField(tripResource, "tripService", tripService);
        ReflectionTestUtils.setField(tripResource, "tripMapper", tripMapper);
        this.restTripMockMvc = MockMvcBuilders.standaloneSetup(tripResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        tripSearchRepository.deleteAll();
        trip = new Trip();
        trip.setPno(DEFAULT_PNO);
        trip.setDay(DEFAULT_DAY);
        trip.setDiscripe(DEFAULT_DISCRIPE);
    }

    @Test
    @Transactional
    public void createTrip() throws Exception {
        int databaseSizeBeforeCreate = tripRepository.findAll().size();

        // Create the Trip
        TripDTO tripDTO = tripMapper.tripToTripDTO(trip);

        restTripMockMvc.perform(post("/api/trips")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tripDTO)))
                .andExpect(status().isCreated());

        // Validate the Trip in the database
        List<Trip> trips = tripRepository.findAll();
        assertThat(trips).hasSize(databaseSizeBeforeCreate + 1);
        Trip testTrip = trips.get(trips.size() - 1);
        assertThat(testTrip.getPno()).isEqualTo(DEFAULT_PNO);
        assertThat(testTrip.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testTrip.getDiscripe()).isEqualTo(DEFAULT_DISCRIPE);

        // Validate the Trip in ElasticSearch
        Trip tripEs = tripSearchRepository.findOne(testTrip.getId());
        assertThat(tripEs).isEqualToComparingFieldByField(testTrip);
    }

    @Test
    @Transactional
    public void getAllTrips() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the trips
        restTripMockMvc.perform(get("/api/trips?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(trip.getId().intValue())))
                .andExpect(jsonPath("$.[*].pno").value(hasItem(DEFAULT_PNO.toString())))
                .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
                .andExpect(jsonPath("$.[*].discripe").value(hasItem(DEFAULT_DISCRIPE.toString())));
    }

    @Test
    @Transactional
    public void getTrip() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get the trip
        restTripMockMvc.perform(get("/api/trips/{id}", trip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(trip.getId().intValue()))
            .andExpect(jsonPath("$.pno").value(DEFAULT_PNO.toString()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.discripe").value(DEFAULT_DISCRIPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTrip() throws Exception {
        // Get the trip
        restTripMockMvc.perform(get("/api/trips/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrip() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);
        tripSearchRepository.save(trip);
        int databaseSizeBeforeUpdate = tripRepository.findAll().size();

        // Update the trip
        Trip updatedTrip = new Trip();
        updatedTrip.setId(trip.getId());
        updatedTrip.setPno(UPDATED_PNO);
        updatedTrip.setDay(UPDATED_DAY);
        updatedTrip.setDiscripe(UPDATED_DISCRIPE);
        TripDTO tripDTO = tripMapper.tripToTripDTO(updatedTrip);

        restTripMockMvc.perform(put("/api/trips")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tripDTO)))
                .andExpect(status().isOk());

        // Validate the Trip in the database
        List<Trip> trips = tripRepository.findAll();
        assertThat(trips).hasSize(databaseSizeBeforeUpdate);
        Trip testTrip = trips.get(trips.size() - 1);
        assertThat(testTrip.getPno()).isEqualTo(UPDATED_PNO);
        assertThat(testTrip.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testTrip.getDiscripe()).isEqualTo(UPDATED_DISCRIPE);

        // Validate the Trip in ElasticSearch
        Trip tripEs = tripSearchRepository.findOne(testTrip.getId());
        assertThat(tripEs).isEqualToComparingFieldByField(testTrip);
    }

    @Test
    @Transactional
    public void deleteTrip() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);
        tripSearchRepository.save(trip);
        int databaseSizeBeforeDelete = tripRepository.findAll().size();

        // Get the trip
        restTripMockMvc.perform(delete("/api/trips/{id}", trip.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tripExistsInEs = tripSearchRepository.exists(trip.getId());
        assertThat(tripExistsInEs).isFalse();

        // Validate the database is empty
        List<Trip> trips = tripRepository.findAll();
        assertThat(trips).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTrip() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);
        tripSearchRepository.save(trip);

        // Search the trip
        restTripMockMvc.perform(get("/api/_search/trips?query=id:" + trip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trip.getId().intValue())))
            .andExpect(jsonPath("$.[*].pno").value(hasItem(DEFAULT_PNO.toString())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].discripe").value(hasItem(DEFAULT_DISCRIPE.toString())));
    }
}
