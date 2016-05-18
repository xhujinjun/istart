package com.istart.framework.repository;

import com.istart.framework.domain.TravelAgency;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TravelAgency entity.
 */
public interface TravelAgencyRepository extends JpaRepository<TravelAgency,Long> {

}
