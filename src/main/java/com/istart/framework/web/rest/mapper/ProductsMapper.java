package com.istart.framework.web.rest.mapper;

import com.istart.framework.domain.*;
import com.istart.framework.web.rest.dto.ProductsDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Products and its DTO ProductsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductsMapper {

    ProductsDTO productsToProductsDTO(Products products);

    List<ProductsDTO> productsToProductsDTOs(List<Products> products);

    @Mapping(target = "trips", ignore = true)
    @Mapping(target = "pictrues", ignore = true)
    Products productsDTOToProducts(ProductsDTO productsDTO);

    List<Products> productsDTOsToProducts(List<ProductsDTO> productsDTOs);
}
