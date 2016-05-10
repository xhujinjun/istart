package com.istart.framework.repository.search;

import com.istart.framework.domain.Area;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Area entity.
 */
public interface AreaSearchRepository extends ElasticsearchRepository<Area, Long> {
}
