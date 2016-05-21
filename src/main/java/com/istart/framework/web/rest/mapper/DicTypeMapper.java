package com.istart.framework.web.rest.mapper;

import com.istart.framework.domain.*;
import com.istart.framework.web.rest.dto.DicTypeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity DicType and its DTO DicTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DicTypeMapper {

    DicTypeDTO dicTypeToDicTypeDTO(DicType dicType);

    List<DicTypeDTO> dicTypesToDicTypeDTOs(List<DicType> dicTypes);

    DicType dicTypeDTOToDicType(DicTypeDTO dicTypeDTO);

    List<DicType> dicTypeDTOsToDicTypes(List<DicTypeDTO> dicTypeDTOs);
}
