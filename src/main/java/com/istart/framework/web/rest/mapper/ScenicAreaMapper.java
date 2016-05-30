package com.istart.framework.web.rest.mapper;

import com.istart.framework.domain.*;
import com.istart.framework.web.rest.dto.ScenicAreaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ScenicArea and its DTO ScenicAreaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ScenicAreaMapper {

    ScenicAreaDTO scenicAreaToScenicAreaDTO(ScenicArea scenicArea);

    List<ScenicAreaDTO> scenicAreasToScenicAreaDTOs(List<ScenicArea> scenicAreas);

    @Mapping(target = "scenicSpots", ignore = true)
    ScenicArea scenicAreaDTOToScenicArea(ScenicAreaDTO scenicAreaDTO);

    List<ScenicArea> scenicAreaDTOsToScenicAreas(List<ScenicAreaDTO> scenicAreaDTOs);
}
