package com.istart.framework.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.istart.framework.domain.Hotel;

/**
 * Spring Data JPA repository for the DicType entity.
 */
public interface HotelRepository extends JpaRepository<Hotel,Long> {

}
