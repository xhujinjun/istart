package com.istart.framework.repository.search;

import com.istart.framework.domain.TravelAgency;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TravelAgency entity.
 */
public interface TravelAgencySearchRepository extends ElasticsearchRepository<TravelAgency, Long> {
}
