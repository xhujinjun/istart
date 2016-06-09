package com.istart.framework.repository.search;

import com.istart.framework.domain.Trip;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Trip entity.
 */
public interface TripSearchRepository extends ElasticsearchRepository<Trip, Long> {
}
