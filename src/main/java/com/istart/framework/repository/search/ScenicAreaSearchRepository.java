package com.istart.framework.repository.search;

import com.istart.framework.domain.ScenicArea;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ScenicArea entity.
 */
public interface ScenicAreaSearchRepository extends ElasticsearchRepository<ScenicArea, Long> {
}
