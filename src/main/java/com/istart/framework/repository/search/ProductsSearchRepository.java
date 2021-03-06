package com.istart.framework.repository.search;

import com.istart.framework.domain.Products;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Products entity.
 */
public interface ProductsSearchRepository extends ElasticsearchRepository<Products, Long> {
}
