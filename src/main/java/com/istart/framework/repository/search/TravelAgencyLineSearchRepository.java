package com.istart.framework.repository.search;

import com.istart.framework.domain.TravelAgencyLine;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TravelAgencyLine entity.
 */
public interface TravelAgencyLineSearchRepository extends ElasticsearchRepository<TravelAgencyLine, Long> {
}
