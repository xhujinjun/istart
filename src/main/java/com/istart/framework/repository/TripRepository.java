package com.istart.framework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.istart.framework.domain.Trip;

/**
 * Spring Data JPA repository for the Trip entity.
 */
@SuppressWarnings("unused")
public interface TripRepository extends JpaRepository<Trip, Long> {

	@Modifying
	@Query(" delete from  Trip as t where t.pno= ?1")
	void deleteByPno(String pno);

}
