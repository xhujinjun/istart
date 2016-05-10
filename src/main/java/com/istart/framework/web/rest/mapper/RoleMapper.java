package com.istart.framework.web.rest.mapper;

import com.istart.framework.domain.*;
import com.istart.framework.web.rest.dto.RoleDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Role and its DTO RoleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RoleMapper {

    RoleDTO roleToRoleDTO(Role role);

    List<RoleDTO> rolesToRoleDTOs(List<Role> roles);

    Role roleDTOToRole(RoleDTO roleDTO);

    List<Role> roleDTOsToRoles(List<RoleDTO> roleDTOs);
}
