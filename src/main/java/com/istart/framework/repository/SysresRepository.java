package com.istart.framework.repository;

import com.istart.framework.domain.Sysres;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Sysres entity.
 */
public interface SysresRepository extends JpaRepository<Sysres,Long> {

}
