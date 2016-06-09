package com.istart.framework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.istart.framework.domain.Pictrues;

/**
 * Spring Data JPA repository for the Pictrues entity.
 */
@SuppressWarnings("unused")
public interface PictruesRepository extends JpaRepository<Pictrues, Long> {

	@Modifying
	@Query(" delete from Pictrues as p where p.pno= ?1")
	void deleteByPno(String pno);

}
