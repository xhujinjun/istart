package com.istart.framework.web.rest.mapper;

import com.istart.framework.domain.*;
import com.istart.framework.web.rest.dto.AreaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Area and its DTO AreaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AreaMapper {

    AreaDTO areaToAreaDTO(Area area);

    List<AreaDTO> areasToAreaDTOs(List<Area> areas);

    Area areaDTOToArea(AreaDTO areaDTO);

    List<Area> areaDTOsToAreas(List<AreaDTO> areaDTOs);
}
