package com.istart.framework.web.rest.mapper;

import com.istart.framework.domain.*;
import com.istart.framework.web.rest.dto.TripDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Trip and its DTO TripDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TripMapper {

    TripDTO tripToTripDTO(Trip trip);

    List<TripDTO> tripsToTripDTOs(List<Trip> trips);

    Trip tripDTOToTrip(TripDTO tripDTO);

    List<Trip> tripDTOsToTrips(List<TripDTO> tripDTOs);
}
