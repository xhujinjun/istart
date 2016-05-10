package com.istart.framework.web.rest.mapper;

import com.istart.framework.domain.*;
import com.istart.framework.web.rest.dto.SysresDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Sysres and its DTO SysresDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysresMapper {

    SysresDTO sysresToSysresDTO(Sysres sysres);

    List<SysresDTO> sysresToSysresDTOs(List<Sysres> sysres);

    Sysres sysresDTOToSysres(SysresDTO sysresDTO);

    List<Sysres> sysresDTOsToSysres(List<SysresDTO> sysresDTOs);
}
