package com.istart.framework.repository;

import com.istart.framework.domain.Role;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Role entity.
 */
public interface RoleRepository extends JpaRepository<Role,Long> {
	public Role findByName(String name);
}
