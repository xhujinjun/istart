package com.istart.framework.repository.search;

import com.istart.framework.domain.Dic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Dic entity.
 */
public interface DicSearchRepository extends ElasticsearchRepository<Dic, Long> {
}
