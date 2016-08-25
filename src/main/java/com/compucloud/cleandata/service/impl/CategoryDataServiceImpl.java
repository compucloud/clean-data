package com.compucloud.cleandata.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.compucloud.cleandata.domain.Category;
import com.compucloud.cleandata.domain.CategoryData;
import com.compucloud.cleandata.repository.CategoryDataRepository;
import com.compucloud.cleandata.repository.CategoryRepository;
import com.compucloud.cleandata.service.CategoryDataService;
import com.compucloud.cleandata.web.rest.dto.CategoryDataDTO;
import com.compucloud.cleandata.web.rest.mapper.CategoryDataMapper;
import com.compucloud.cleandata.web.rest.mapper.CategoryMapper;

/**
 * Service Implementation for managing CategoryData.
 */
@Service
@Transactional
public class CategoryDataServiceImpl implements CategoryDataService{

    private final Logger log = LoggerFactory.getLogger(CategoryDataServiceImpl.class);
    
    @Inject
    private CategoryDataRepository categoryDataRepository;
    
    @Inject
    private CategoryDataMapper categoryDataMapper;
    
    @Inject
    private CategoryRepository categoryRepository;
    
    @Inject
    private CategoryMapper categoryMapper;
    
    /**
     * Save a categoryData.
     * 
     * @param categoryDataDTO the entity to save
     * @return the persisted entity
     */
    public CategoryDataDTO save(CategoryDataDTO categoryDataDTO) {
        log.debug("Request to save CategoryData : {}", categoryDataDTO);
        CategoryData categoryData = categoryDataMapper.categoryDataDTOToCategoryData(categoryDataDTO);
        categoryData = categoryDataRepository.save(categoryData);
        CategoryDataDTO result = categoryDataMapper.categoryDataToCategoryDataDTO(categoryData);
        return result;
    }
    
    /**
     * Save a categoryDataList.
     * 
     * @param categoryDataDTOList the entity to save
     * @return the persisted entitiesQueryByExampleExecutor<T>
     */
    public List<CategoryDataDTO> saveList(List<CategoryDataDTO> categoryDataDTOList) {
        log.debug("Request to save CategoryData : {}", categoryDataDTOList);
        
        //#1 Filter duplicates
        List<CategoryDataDTO> filteredDTOList = new ArrayList<CategoryDataDTO>();
        List<CategoryDataDTO> alreadyAddedDTOList = new ArrayList<CategoryDataDTO>();
        for (CategoryDataDTO categoryDataDTO : categoryDataDTOList){
        	
        	if(!org.springframework.util.CollectionUtils.contains(alreadyAddedDTOList.iterator(),categoryDataDTO)){
        		filteredDTOList.add(categoryDataDTO);
        	}
        	if(Collections.frequency(categoryDataDTOList, categoryDataDTO) > 1){
        		alreadyAddedDTOList.add(categoryDataDTO);
        		log.debug("CategoryDataDTO is has dups, added to add to alreadyAddedDTOList : {}", categoryDataDTO);
        	}
        }              
        
        //#2 Create a filtered List, Only add items that have valid categories
        List<CategoryData> categoryDataList = categoryDataMapper.categoryDataDTOsToCategoryData(filteredDTOList);
        List<CategoryData> filteredList = new ArrayList<CategoryData>();
        List<Category> validCategories = categoryRepository.findAll();
        for (CategoryData categoryData : categoryDataList) {        	
        	for (Category validCategory : validCategories) {
        		if(validCategory.getName().toLowerCase().equals(categoryData.getCategory().toLowerCase())){
            		filteredList.add(categoryData);
            		log.debug("CategoryData has valid category, added to filteredList : {}", categoryData);
            		break;
            	}
            }        	       	
        }      
        
        List<CategoryDataDTO> results = categoryDataMapper.categoryDataToCategoryDataDTOs(categoryDataRepository.save(filteredList));
        return results;
    }   

    /**
     *  Get all the categoryData.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<CategoryData> findAll(Pageable pageable) {
        log.debug("Request to get all CategoryData");
        Page<CategoryData> result = categoryDataRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one categoryData by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public CategoryDataDTO findOne(Long id) {
        log.debug("Request to get CategoryData : {}", id);
        CategoryData categoryData = categoryDataRepository.findOne(id);
        CategoryDataDTO categoryDataDTO = categoryDataMapper.categoryDataToCategoryDataDTO(categoryData);
        return categoryDataDTO;
    }

    /**
     *  Delete the  categoryData by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CategoryData : {}", id);
        categoryDataRepository.delete(id);
    }
}
