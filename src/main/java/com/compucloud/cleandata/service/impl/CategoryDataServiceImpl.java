package com.compucloud.cleandata.service.impl;

import com.compucloud.cleandata.service.CategoryDataService;
import com.compucloud.cleandata.domain.CategoryData;
import com.compucloud.cleandata.repository.CategoryDataRepository;
import com.compucloud.cleandata.web.rest.dto.CategoryDataDTO;
import com.compucloud.cleandata.web.rest.mapper.CategoryDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
     * @return the persisted entities
     */
    public List<CategoryDataDTO> saveList(List<CategoryDataDTO> categoryDataDTOList) {
        log.debug("Request to save CategoryData : {}", categoryDataDTOList);
        List<CategoryData> categoryDataList = categoryDataMapper.categoryDataDTOsToCategoryData(categoryDataDTOList);
        categoryDataList = categoryDataRepository.save(categoryDataList);
        List<CategoryDataDTO> results = categoryDataMapper.categoryDataToCategoryDataDTOs(categoryDataList);
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
