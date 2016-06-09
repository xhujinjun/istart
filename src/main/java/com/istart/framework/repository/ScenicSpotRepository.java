package com.istart.framework.repository;

import com.istart.framework.domain.ScenicSpot;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ScenicSpot entity.
 */
public interface ScenicSpotRepository extends JpaRepository<ScenicSpot,Long> {

}
