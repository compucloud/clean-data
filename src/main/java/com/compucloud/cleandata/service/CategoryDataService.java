package com.compucloud.cleandata.service;

import com.compucloud.cleandata.domain.CategoryData;
import com.compucloud.cleandata.web.rest.dto.CategoryCountDTO;
import com.compucloud.cleandata.web.rest.dto.CategoryDataDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing CategoryData.
 */
public interface CategoryDataService {

    /**
     * Save a categoryData.
     * 
     * @param categoryDataDTO the entity to save
     * @return the persisted entity
     */
    CategoryDataDTO save(CategoryDataDTO categoryDataDTO);
    
    /**
     * Save a categoryData.
     * 
     * @param categoryDataDTO the entity to save
     * @return the persisted entity
     */
    List<CategoryDataDTO> saveList(List<CategoryDataDTO> categoryDataDTOList);

    /**
     *  Get all the categoryData.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CategoryData> findAll(Pageable pageable);

    /**
     *  Get the "id" categoryData.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    CategoryDataDTO findOne(Long id);

    /**
     *  Delete the "id" categoryData.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

	List<CategoryCountDTO> findCategoryCounts();
}
