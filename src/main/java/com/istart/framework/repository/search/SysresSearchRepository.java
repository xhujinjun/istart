package com.istart.framework.repository.search;

import com.istart.framework.domain.Sysres;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Sysres entity.
 */
public interface SysresSearchRepository extends ElasticsearchRepository<Sysres, Long> {
}
