package com.istart.framework.repository.search;

import com.istart.framework.domain.Version;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Version entity.
 */
public interface VersionSearchRepository extends ElasticsearchRepository<Version, Long> {
}
