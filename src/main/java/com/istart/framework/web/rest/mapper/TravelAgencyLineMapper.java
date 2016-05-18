package com.istart.framework.web.rest.mapper;

import com.istart.framework.domain.*;
import com.istart.framework.web.rest.dto.TravelAgencyLineDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TravelAgencyLine and its DTO TravelAgencyLineDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TravelAgencyLineMapper {

    TravelAgencyLineDTO travelAgencyLineToTravelAgencyLineDTO(TravelAgencyLine travelAgencyLine);

    List<TravelAgencyLineDTO> travelAgencyLinesToTravelAgencyLineDTOs(List<TravelAgencyLine> travelAgencyLines);

    TravelAgencyLine travelAgencyLineDTOToTravelAgencyLine(TravelAgencyLineDTO travelAgencyLineDTO);

    List<TravelAgencyLine> travelAgencyLineDTOsToTravelAgencyLines(List<TravelAgencyLineDTO> travelAgencyLineDTOs);
}
