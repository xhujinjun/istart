package com.istart.framework.repository;

import com.istart.framework.domain.Dic;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Dic entity.
 */
public interface DicRepository extends JpaRepository<Dic,Long> {

}
