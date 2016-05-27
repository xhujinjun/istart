package com.istart.framework.repository;

import com.istart.framework.domain.TravelAgency;
import com.istart.framework.repository.base.JpaSpecificationPagingRepository;

/**
 * Spring Data JPA repository for the TravelAgency entity.
 */
public interface TravelAgencyRepository extends JpaSpecificationPagingRepository<TravelAgency,Long> {

}
