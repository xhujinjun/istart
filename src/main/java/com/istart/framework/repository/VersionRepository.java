package com.istart.framework.repository;

import com.istart.framework.domain.Version;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Version entity.
 */
public interface VersionRepository extends JpaRepository<Version,Long> {

}
