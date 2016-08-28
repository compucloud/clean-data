package com.compucloud.cleandata.service.impl;

import com.compucloud.cleandata.CleandataApp;
import com.compucloud.cleandata.domain.CategoryData;
import com.compucloud.cleandata.domain.User;
import com.compucloud.cleandata.repository.CategoryDataRepository;
import com.compucloud.cleandata.repository.UserRepository;
import java.time.ZonedDateTime;

import com.compucloud.cleandata.service.CategoryDataService;
import com.compucloud.cleandata.service.CategoryService;
import com.compucloud.cleandata.service.util.RandomUtil;
import com.compucloud.cleandata.web.rest.dto.CategoryCountDTO;
import com.compucloud.cleandata.web.rest.dto.CategoryDTO;
import com.compucloud.cleandata.web.rest.dto.CategoryDataCompositeDTO;
import com.compucloud.cleandata.web.rest.dto.CategoryDataDTO;

import java.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the UserResource REST controller.
 *
 * @see UserService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CleandataApp.class)
@WebAppConfiguration
@IntegrationTest
@Transactional
public class CategoryDataServiceIntTest {

    @Inject
    private CategoryDataService categoryDataService;
    
    @Inject
    private CategoryService categoryService;

    @Test    
    public void testSave() {
    	
    	CategoryDataDTO data = new CategoryDataDTO();
    	data.setCategory("PERSON");
    	data.setSubcategory("Pam");    	
    	CategoryDataDTO cd = categoryDataService.save(data); 
    	assertNotNull(cd.getId());
    	
    }
    
    @Test
    public void testSaveList() {
    	//Store Test Valid Categories
    	categoryService.save(new CategoryDTO("PERSON"));
    	categoryService.save(new CategoryDTO("PLACE"));
    	categoryService.save(new CategoryDTO("ANIMAL"));
    	categoryService.save(new CategoryDTO("COMPUTER"));
    	categoryService.save(new CategoryDTO("OTHER"));
    	//Create Test Data
    	List<CategoryDataDTO> dataList = new ArrayList<CategoryDataDTO>();
    	dataList.add(new CategoryDataDTO("PERSON","Bob Jones"));
    	dataList.add(new CategoryDataDTO("PLACE","Washington"));
    	dataList.add(new CategoryDataDTO("PERSON","Mary"));
    	dataList.add(new CategoryDataDTO("COMPUTER","Mac"));
    	dataList.add(new CategoryDataDTO("PERSON","Bob Jones"));
    	dataList.add(new CategoryDataDTO("OTHER","Tree"));
    	dataList.add(new CategoryDataDTO("ANIMAL","Dog"));
    	dataList.add(new CategoryDataDTO("PLACE","Texas"));
    	dataList.add(new CategoryDataDTO("FOOD","Steak"));
    	dataList.add(new CategoryDataDTO("ANIMAL","Cat"));
    	dataList.add(new CategoryDataDTO("PERSON","Mac"));  	
    	//Perform operations and report results
    	CategoryDataCompositeDTO cdComposite = categoryDataService.saveList(dataList);      	
    	assertEquals(9,cdComposite.getCategoryDataDTOList().size());
    	
    	for(CategoryCountDTO dto: cdComposite.getCategoryCountDTOList()){
    		System.out.println(dto.getName() + " " + dto.getCount());
    	}
    	
    	assertEquals(5,cdComposite.getCategoryCountDTOList().size());
    	assertEquals("ANIMAL",cdComposite.getCategoryCountDTOList().get(2).getName());
    	assertEquals(2,cdComposite.getCategoryCountDTOList().get(2).getCount());
    	
    }
    
    @Test
    public void testFrequency() {
    	
    	//Create Test Data
    	List<CategoryDataDTO> dataList = new ArrayList<CategoryDataDTO>();
    	dataList.add(new CategoryDataDTO("PERSON","Bob Jones"));
    	dataList.add(new CategoryDataDTO("PLACE","Washington"));
    	dataList.add(new CategoryDataDTO("PERSON","Mary"));
    	dataList.add(new CategoryDataDTO("COMPUTER","Mac"));
    	dataList.add(new CategoryDataDTO("PERSON","Bob Jones"));
    	dataList.add(new CategoryDataDTO("OTHER","Tree"));
    	dataList.add(new CategoryDataDTO("ANIMAL","Dog"));
    	dataList.add(new CategoryDataDTO("PLACE","Texas"));
    	dataList.add(new CategoryDataDTO("FOOD","Steak"));
    	dataList.add(new CategoryDataDTO("ANIMAL","Cat"));
    	dataList.add(new CategoryDataDTO("PERSON","Mac"));  
    	dataList.add(new CategoryDataDTO("PERSON","Bob Jones"));
    	int frequency = Collections.frequency(dataList, new CategoryDataDTO("PERSON","Bob Jones"));
    	System.out.println(frequency);
    	assertEquals(3,frequency);
    	
    	boolean exist = CollectionUtils.contains(dataList.iterator(), new CategoryDataDTO("PERSON","Bob Jones"));
    	assertTrue(exist);
    }
    
    @Test
    public void testFindCountDTO() {
    	
    	assertEquals(0,categoryDataService.findCategoryCounts().size());
    	
    	//Store Test Valid Categories
    	categoryService.save(new CategoryDTO("PERSON"));
    	categoryService.save(new CategoryDTO("PLACE"));
    	categoryService.save(new CategoryDTO("ANIMAL"));
    	categoryService.save(new CategoryDTO("COMPUTER"));
    	categoryService.save(new CategoryDTO("OTHER"));
    	//Create Test Data
    	List<CategoryDataDTO> dataList = new ArrayList<CategoryDataDTO>();
    	dataList.add(new CategoryDataDTO("PERSON","Bob Jones"));
    	dataList.add(new CategoryDataDTO("PLACE","Washington"));
    	dataList.add(new CategoryDataDTO("PERSON","Mary"));
    	dataList.add(new CategoryDataDTO("COMPUTER","Mac"));
    	dataList.add(new CategoryDataDTO("PERSON","Bob Jones"));
    	dataList.add(new CategoryDataDTO("OTHER","Tree"));
    	dataList.add(new CategoryDataDTO("ANIMAL","Dog"));
    	dataList.add(new CategoryDataDTO("PLACE","Texas"));
    	dataList.add(new CategoryDataDTO("FOOD","Steak"));
    	dataList.add(new CategoryDataDTO("ANIMAL","Cat"));
    	dataList.add(new CategoryDataDTO("PERSON","Mac"));  	
    	//Perform operations and report results
    	CategoryDataCompositeDTO cdComposite = categoryDataService.saveList(dataList);     	
    	assertEquals(9,cdComposite.getCategoryDataDTOList().size());
    	
    	List<CategoryCountDTO> counts = categoryDataService.findCategoryCounts();
    	for(CategoryCountDTO dto: counts){
    		System.out.println(dto.getName() + " " + dto.getCount());
    	}
    	assertEquals(5,counts.size());
    	
    }
    
    
    


}
