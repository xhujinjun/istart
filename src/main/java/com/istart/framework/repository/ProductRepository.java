package com.istart.framework.repository;

import com.istart.framework.domain.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Product entity.
 */
@SuppressWarnings("unused")
public interface ProductRepository extends JpaRepository<Product,Long> {

	Page<Product> findAll(Specification<Product> specification, Pageable pageable);

	
}
