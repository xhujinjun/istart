package com.istart.framework.repository.search;

import com.istart.framework.domain.Pictrues;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Pictrues entity.
 */
public interface PictruesSearchRepository extends ElasticsearchRepository<Pictrues, Long> {
}
