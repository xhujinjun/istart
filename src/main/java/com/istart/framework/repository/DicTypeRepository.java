package com.istart.framework.repository;

import com.istart.framework.domain.DicType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DicType entity.
 */
public interface DicTypeRepository extends JpaRepository<DicType,Long> {

}
