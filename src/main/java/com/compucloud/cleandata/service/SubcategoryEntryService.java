package com.compucloud.cleandata.service;

import com.compucloud.cleandata.domain.SubcategoryEntry;
import com.compucloud.cleandata.web.rest.dto.CategoryCountDTO;
import com.compucloud.cleandata.web.rest.dto.CategoryDataCompositeDTO;
import com.compucloud.cleandata.web.rest.dto.CategoryDataDTO;
import com.compucloud.cleandata.web.rest.dto.SubcategoryCompositeDTO;
import com.compucloud.cleandata.web.rest.dto.SubcategoryEntryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing SubcategoryEntry.
 */
public interface SubcategoryEntryService {

    /**
     * Save a subcategoryEntry.
     * 
     * @param subcategoryEntryDTO the entity to save
     * @return the persisted entity
     */
    SubcategoryEntryDTO save(SubcategoryEntryDTO subcategoryEntryDTO);

    /**
     *  Get all the subcategoryEntries.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SubcategoryEntry> findAll(Pageable pageable);

    /**
     *  Get the "id" subcategoryEntry.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    SubcategoryEntryDTO findOne(Long id);

    /**
     *  Delete the "id" subcategoryEntry.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
    
    /**
     * Save a subcategory list.
     * 
     * @param categoryDataDTOList the entity to save
     * @return the persisted entity
     */
    SubcategoryCompositeDTO saveList(List<SubcategoryEntryDTO> subcategoryEntryDTOList);
    
    /**
     * Find category count list.
     * 
     * @return category count dto
     */
    List<CategoryCountDTO> findCategoryCounts();
}
