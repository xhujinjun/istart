package com.istart.framework.web.rest.mapper;

import com.istart.framework.domain.*;
import com.istart.framework.web.rest.dto.DicDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Dic and its DTO DicDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DicMapper {

    @Mapping(source = "dicType.id", target = "dicTypeId")
    @Mapping(source = "dicType.dicTypeName", target = "dicTypeName")
    @Mapping(source = "dicType.dicTypeCode", target = "dicTypeCode")
    DicDTO dicToDicDTO(Dic dic);

    List<DicDTO> dicsToDicDTOs(List<Dic> dics);

    @Mapping(source = "dicTypeId", target = "dicType")
    Dic dicDTOToDic(DicDTO dicDTO);

    List<Dic> dicDTOsToDics(List<DicDTO> dicDTOs);

    default DicType dicTypeFromId(Long id) {
        if (id == null) {
            return null;
        }
        DicType dicType = new DicType();
        dicType.setId(id);
        return dicType;
    }
}
