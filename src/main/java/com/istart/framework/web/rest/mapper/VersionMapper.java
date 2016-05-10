package com.istart.framework.web.rest.mapper;

import com.istart.framework.domain.*;
import com.istart.framework.web.rest.dto.VersionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Version and its DTO VersionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VersionMapper {

    VersionDTO versionToVersionDTO(Version version);

    List<VersionDTO> versionsToVersionDTOs(List<Version> versions);

    Version versionDTOToVersion(VersionDTO versionDTO);

    List<Version> versionDTOsToVersions(List<VersionDTO> versionDTOs);
}
