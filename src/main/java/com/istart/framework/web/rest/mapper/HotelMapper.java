package com.istart.framework.web.rest.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.istart.framework.domain.Author;
import com.istart.framework.domain.Hotel;
import com.istart.framework.web.rest.dto.HotelDTO;

/**
 * Mapper for the entity Hotel and its DTO HotelDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HotelMapper {

//    @Mapping(source = "author.id", target = "authorId")
    HotelDTO hotelToHotelDTO(Hotel hotel);

    List<HotelDTO> hotelsToHotelDTOs(List<Hotel> Hotels);

//    @Mapping(source = "authorId", target = "author")
    Hotel HotelDTOToHotel(HotelDTO HotelDTO);

    List<Hotel> HotelDTOsToHotels(List<HotelDTO> HotelDTOs);

    default Author authorFromId(Long id) {
        if (id == null) {
            return null;
        }
        Author author = new Author();
        author.setId(id);
        return author;
    }
}
