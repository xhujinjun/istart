package com.istart.framework.repository;

import com.istart.framework.domain.DicType;
import com.istart.framework.repository.base.JpaSpecificationPagingRepository;

/**
 * Spring Data JPA repository for the DicType entity.
 */
public interface DicTypeRepository extends JpaSpecificationPagingRepository<DicType, Long> {

	// DIC.006.0001
	public DicType findByDicTypeCode(String dicTypeCode);
}
