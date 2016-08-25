package com.compucloud.cleandata.web.rest.mapper;

import org.mapstruct.*;

import com.compucloud.cleandata.domain.CategoryData;
import com.compucloud.cleandata.web.rest.dto.*;

import java.util.List;

/**
 * Mapper for the entity CategoryData and its DTO CategoryDataDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryDataMapper {

    CategoryDataDTO categoryDataToCategoryDataDTO(CategoryData categoryData);

    List<CategoryDataDTO> categoryDataToCategoryDataDTOs(List<CategoryData> categoryData);

    CategoryData categoryDataDTOToCategoryData(CategoryDataDTO categoryDataDTO);

    List<CategoryData> categoryDataDTOsToCategoryData(List<CategoryDataDTO> categoryDataDTOs);
}
