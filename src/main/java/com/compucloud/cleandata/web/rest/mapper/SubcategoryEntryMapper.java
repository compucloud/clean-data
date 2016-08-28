package com.compucloud.cleandata.web.rest.mapper;

import com.compucloud.cleandata.domain.*;
import com.compucloud.cleandata.web.rest.dto.SubcategoryEntryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity SubcategoryEntry and its DTO SubcategoryEntryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SubcategoryEntryMapper {

    SubcategoryEntryDTO subcategoryEntryToSubcategoryEntryDTO(SubcategoryEntry subcategoryEntry);

    List<SubcategoryEntryDTO> subcategoryEntriesToSubcategoryEntryDTOs(List<SubcategoryEntry> subcategoryEntries);

    SubcategoryEntry subcategoryEntryDTOToSubcategoryEntry(SubcategoryEntryDTO subcategoryEntryDTO);

    List<SubcategoryEntry> subcategoryEntryDTOsToSubcategoryEntries(List<SubcategoryEntryDTO> subcategoryEntryDTOs);
}
