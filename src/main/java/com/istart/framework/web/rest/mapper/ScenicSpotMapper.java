package com.istart.framework.web.rest.mapper;

import com.istart.framework.domain.*;
import com.istart.framework.web.rest.dto.ScenicSpotDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ScenicSpot and its DTO ScenicSpotDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ScenicSpotMapper {

    ScenicSpotDTO scenicSpotToScenicSpotDTO(ScenicSpot scenicSpot);

    List<ScenicSpotDTO> scenicSpotsToScenicSpotDTOs(List<ScenicSpot> scenicSpots);

    ScenicSpot scenicSpotDTOToScenicSpot(ScenicSpotDTO scenicSpotDTO);

    List<ScenicSpot> scenicSpotDTOsToScenicSpots(List<ScenicSpotDTO> scenicSpotDTOs);
}
