package com.istart.framework.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.istart.framework.domain.Products;

/**
 * Spring Data JPA repository for the Products entity.
 */
@SuppressWarnings("unused")
public interface ProductsRepository extends JpaRepository<Products, Long> {

	Page<Products> findAll(Specification<Products> specification, Pageable pageable);

}
