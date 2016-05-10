package com.istart.framework.repository;

import com.istart.framework.domain.Area;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Area entity.
 */
public interface AreaRepository extends JpaRepository<Area,Long> {

}
