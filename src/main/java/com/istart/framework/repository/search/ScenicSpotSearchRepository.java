package com.istart.framework.repository.search;

import com.istart.framework.domain.ScenicSpot;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ScenicSpot entity.
 */
public interface ScenicSpotSearchRepository extends ElasticsearchRepository<ScenicSpot, Long> {
}
