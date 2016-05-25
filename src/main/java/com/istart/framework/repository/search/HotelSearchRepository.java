package com.istart.framework.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.istart.framework.domain.Hotel;

/**
 * Spring Data ElasticSearch repository for the DicType entity.
 */
public interface HotelSearchRepository extends ElasticsearchRepository<Hotel, Long> {
}
