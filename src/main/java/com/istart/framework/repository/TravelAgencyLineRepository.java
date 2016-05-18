package com.istart.framework.repository;

import com.istart.framework.domain.TravelAgencyLine;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TravelAgencyLine entity.
 */
public interface TravelAgencyLineRepository extends JpaRepository<TravelAgencyLine,Long> {

}
