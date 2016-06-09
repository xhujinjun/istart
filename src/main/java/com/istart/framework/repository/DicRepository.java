package com.istart.framework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.istart.framework.domain.Dic;
import com.istart.framework.domain.DicType;
import com.istart.framework.repository.base.JpaSpecificationPagingRepository;

/**
 * Spring Data JPA repository for the Dic entity.
 */
public interface DicRepository extends JpaSpecificationPagingRepository<Dic, Long> {

	@Query("select dicType from DicType dicType left outer join dicType.dics dic  where dicType.dicTypeCode = ?1 and dic.dicCode = ?2")
	public DicType findByDicTypeCodeAndDicCode(String dicTypeCode, String dicCode);

	@Query("select dic from Dic dic left outer join dic.dicType dicType where dic.id = ?1")
	public Dic findById(Long id);

	// dicTypeId
	public List<Dic> findByDicTypeId(Long dicTypeId);
}
