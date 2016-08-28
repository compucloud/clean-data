package com.compucloud.cleandata.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compucloud.cleandata.domain.Category;
import com.compucloud.cleandata.domain.SubcategoryEntry;
import com.compucloud.cleandata.repository.CategoryRepository;
import com.compucloud.cleandata.repository.SubcategoryEntryRepository;
import com.compucloud.cleandata.service.SubcategoryEntryService;
import com.compucloud.cleandata.web.rest.dto.CategoryCountDTO;
import com.compucloud.cleandata.web.rest.dto.SubcategoryCompositeDTO;
import com.compucloud.cleandata.web.rest.dto.SubcategoryEntryDTO;
import com.compucloud.cleandata.web.rest.mapper.SubcategoryEntryMapper;

/**
 * Service Implementation for managing SubcategoryEntry.
 */
@Service
@Transactional
public class SubcategoryEntryServiceImpl implements SubcategoryEntryService{

    private final Logger log = LoggerFactory.getLogger(SubcategoryEntryServiceImpl.class);
    
    @Inject
    private CategoryRepository categoryRepository;
    
    @Inject
    private SubcategoryEntryRepository subcategoryEntryRepository;
    
    @Inject
    private SubcategoryEntryMapper subcategoryEntryMapper;
    
    /**
     * Save a subcategoryEntry.
     * 
     * @param subcategoryEntryDTO the entity to save
     * @return the persisted entity
     */
    public SubcategoryEntryDTO save(SubcategoryEntryDTO subcategoryEntryDTO) {
        log.debug("Request to save SubcategoryEntry : {}", subcategoryEntryDTO);
        SubcategoryEntry subcategoryEntry = subcategoryEntryMapper.subcategoryEntryDTOToSubcategoryEntry(subcategoryEntryDTO);
        subcategoryEntry = subcategoryEntryRepository.save(subcategoryEntry);
        SubcategoryEntryDTO result = subcategoryEntryMapper.subcategoryEntryToSubcategoryEntryDTO(subcategoryEntry);
        return result;
    }

    /**
     *  Get all the subcategoryEntries.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SubcategoryEntry> findAll(Pageable pageable) {
        log.debug("Request to get all SubcategoryEntries");
        Page<SubcategoryEntry> result = subcategoryEntryRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one subcategoryEntry by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SubcategoryEntryDTO findOne(Long id) {
        log.debug("Request to get SubcategoryEntry : {}", id);
        SubcategoryEntry subcategoryEntry = subcategoryEntryRepository.findOne(id);
        SubcategoryEntryDTO subcategoryEntryDTO = subcategoryEntryMapper.subcategoryEntryToSubcategoryEntryDTO(subcategoryEntry);
        return subcategoryEntryDTO;
    }

    /**
     *  Delete the  subcategoryEntry by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SubcategoryEntry : {}", id);
        subcategoryEntryRepository.delete(id);
    }
    
    @Transactional(readOnly = true) 
	private List<CategoryCountDTO> findCategoryCounts(List<SubcategoryEntry> subcategoryEntryList) {
		
		//#1 Load categories into one dimensional array
		List<String> categoriesList = new ArrayList<String>();
		for (SubcategoryEntry categoryData : subcategoryEntryList) {
			categoriesList.add(categoryData.getCategory());
		}
		//#2 Check if the count is 0 the return empty
		List<CategoryCountDTO> countDTOList = new ArrayList<CategoryCountDTO>();
		if(subcategoryEntryList.size() == 0){
			return countDTOList;
		}
		//#3 Use frequency method to get the counts for each valid category, store in countDTOList 
		List<Category> validCategories = categoryRepository.findAll();		
		for (Category category : validCategories) {
			countDTOList.add(new CategoryCountDTO(category.getName(),Collections.frequency(categoriesList, category.getName())));
		}	
		
	   Comparator<CategoryCountDTO> byCount = new Comparator<CategoryCountDTO>() {		
			@Override
			public int compare(CategoryCountDTO c1, CategoryCountDTO c2) {				
				if (c1.getCount() < c2.getCount()) {
			           return 1;
		       } else if (c1.getCount() > c2.getCount()){
		           return -1;
		       } else {
		           return 0;
		       }
			}
		};
		countDTOList.sort(byCount);			
		return countDTOList;
	}

	@Override
	public SubcategoryCompositeDTO saveList(List<SubcategoryEntryDTO> subcategoryEntryDTOList) {
		log.debug("Request to save CategoryData : {}", subcategoryEntryDTOList);
        
        //#1 Filter duplicates
        List<SubcategoryEntryDTO> filteredDTOList = new ArrayList<SubcategoryEntryDTO>();
        List<SubcategoryEntryDTO> alreadyAddedDTOList = new ArrayList<SubcategoryEntryDTO>();
        for (SubcategoryEntryDTO categoryDataDTO : subcategoryEntryDTOList){
        	
        	if(!org.springframework.util.CollectionUtils.contains(alreadyAddedDTOList.iterator(),categoryDataDTO)){
        		filteredDTOList.add(categoryDataDTO);
        	}
        	if(Collections.frequency(subcategoryEntryDTOList, categoryDataDTO) > 1){
        		alreadyAddedDTOList.add(categoryDataDTO);
        		log.debug("SubcategoryEntryDTO is has dups, added to add to alreadyAddedDTOList : {}", categoryDataDTO);
        	}
        }              
        
        //#2 Create a filtered List, Only add items that have valid categories
        List<SubcategoryEntry> categoryDataList = subcategoryEntryMapper.subcategoryEntryDTOsToSubcategoryEntries(filteredDTOList);
        List<SubcategoryEntry> filteredList = new ArrayList<SubcategoryEntry>();
        List<Category> validCategories = categoryRepository.findAll();
        for (SubcategoryEntry categoryData : categoryDataList) {        	
        	for (Category validCategory : validCategories) {
        		if(validCategory.getName().toLowerCase().equals(categoryData.getCategory().toLowerCase())){
            		filteredList.add(categoryData);
            		log.debug("SubcategoryEntry has valid category, added to filteredList : {}", categoryData);
            		break;
            	}
            }        	       	
        }   
        
        List<CategoryCountDTO> categoryCountDTOList = findCategoryCounts(filteredList);        
        List<SubcategoryEntryDTO> results = subcategoryEntryMapper.subcategoryEntriesToSubcategoryEntryDTOs(subcategoryEntryRepository.save(filteredList));
        
        return new SubcategoryCompositeDTO(categoryCountDTOList,results);
	}

	@Transactional(readOnly = true) 
	public List<CategoryCountDTO> findCategoryCounts() {
    	List<SubcategoryEntry> categoryDataList = subcategoryEntryRepository.findAll();
    	return findCategoryCounts(categoryDataList);
    }
}
