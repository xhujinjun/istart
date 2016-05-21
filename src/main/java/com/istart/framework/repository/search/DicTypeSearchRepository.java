package com.istart.framework.repository.search;

import com.istart.framework.domain.DicType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DicType entity.
 */
public interface DicTypeSearchRepository extends ElasticsearchRepository<DicType, Long> {
}
