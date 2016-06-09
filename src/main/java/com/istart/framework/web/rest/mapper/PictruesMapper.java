package com.istart.framework.web.rest.mapper;

import com.istart.framework.domain.*;
import com.istart.framework.web.rest.dto.PictruesDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Pictrues and its DTO PictruesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PictruesMapper {

    PictruesDTO pictruesToPictruesDTO(Pictrues pictrues);

    List<PictruesDTO> pictruesToPictruesDTOs(List<Pictrues> pictrues);

    Pictrues pictruesDTOToPictrues(PictruesDTO pictruesDTO);

    List<Pictrues> pictruesDTOsToPictrues(List<PictruesDTO> pictruesDTOs);
}
