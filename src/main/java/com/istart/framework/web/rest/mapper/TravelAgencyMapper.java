package com.istart.framework.web.rest.mapper;

import com.istart.framework.domain.*;
import com.istart.framework.web.rest.dto.TravelAgencyDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TravelAgency and its DTO TravelAgencyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TravelAgencyMapper {

    TravelAgencyDTO travelAgencyToTravelAgencyDTO(TravelAgency travelAgency);

    List<TravelAgencyDTO> travelAgenciesToTravelAgencyDTOs(List<TravelAgency> travelAgencies);

    TravelAgency travelAgencyDTOToTravelAgency(TravelAgencyDTO travelAgencyDTO);

    List<TravelAgency> travelAgencyDTOsToTravelAgencies(List<TravelAgencyDTO> travelAgencyDTOs);
}
