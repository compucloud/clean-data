package com.compucloud.cleandata.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.compucloud.cleandata.CleandataApp;
import com.compucloud.cleandata.service.CategoryService;
import com.compucloud.cleandata.service.SubcategoryEntryService;
import com.compucloud.cleandata.service.UserService;
import com.compucloud.cleandata.web.rest.dto.CategoryCountDTO;
import com.compucloud.cleandata.web.rest.dto.CategoryDTO;
import com.compucloud.cleandata.web.rest.dto.SubcategoryCompositeDTO;
import com.compucloud.cleandata.web.rest.dto.SubcategoryEntryDTO;

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
public class SubcategoryEntryServiceIntTest {

    @Inject
    private SubcategoryEntryService subcategoryEntryService;
    
    @Inject
    private CategoryService categoryService;

    @Test    
    public void testSave() {
    	
    	SubcategoryEntryDTO data = new SubcategoryEntryDTO();
    	data.setCategory("PERSON");
    	data.setSubcategory("Pam");    	
    	SubcategoryEntryDTO cd = subcategoryEntryService.save(data); 
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
    	List<SubcategoryEntryDTO> dataList = new ArrayList<SubcategoryEntryDTO>();
    	dataList.add(new SubcategoryEntryDTO("PERSON","Bob Jones"));
    	dataList.add(new SubcategoryEntryDTO("PLACE","Washington"));
    	dataList.add(new SubcategoryEntryDTO("PERSON","Mary"));
    	dataList.add(new SubcategoryEntryDTO("COMPUTER","Mac"));
    	dataList.add(new SubcategoryEntryDTO("PERSON","Bob Jones"));
    	dataList.add(new SubcategoryEntryDTO("OTHER","Tree"));
    	dataList.add(new SubcategoryEntryDTO("ANIMAL","Dog"));
    	dataList.add(new SubcategoryEntryDTO("PLACE","Texas"));
    	dataList.add(new SubcategoryEntryDTO("FOOD","Steak"));
    	dataList.add(new SubcategoryEntryDTO("ANIMAL","Cat"));
    	dataList.add(new SubcategoryEntryDTO("PERSON","Mac"));  	
    	//Perform operations and report results
    	SubcategoryCompositeDTO cdComposite = subcategoryEntryService.saveList(dataList);      	
    	assertEquals(9,cdComposite.getSubcategoryEntryDTOList().size());
    	
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
    	List<SubcategoryEntryDTO> dataList = new ArrayList<SubcategoryEntryDTO>();
    	dataList.add(new SubcategoryEntryDTO("PERSON","Bob Jones"));
    	dataList.add(new SubcategoryEntryDTO("PLACE","Washington"));
    	dataList.add(new SubcategoryEntryDTO("PERSON","Mary"));
    	dataList.add(new SubcategoryEntryDTO("COMPUTER","Mac"));
    	dataList.add(new SubcategoryEntryDTO("PERSON","Bob Jones"));
    	dataList.add(new SubcategoryEntryDTO("OTHER","Tree"));
    	dataList.add(new SubcategoryEntryDTO("ANIMAL","Dog"));
    	dataList.add(new SubcategoryEntryDTO("PLACE","Texas"));
    	dataList.add(new SubcategoryEntryDTO("FOOD","Steak"));
    	dataList.add(new SubcategoryEntryDTO("ANIMAL","Cat"));
    	dataList.add(new SubcategoryEntryDTO("PERSON","Mac"));  
    	dataList.add(new SubcategoryEntryDTO("PERSON","Bob Jones"));
    	int frequency = Collections.frequency(dataList, new SubcategoryEntryDTO("PERSON","Bob Jones"));
    	System.out.println(frequency);
    	assertEquals(3,frequency);
    	
    	boolean exist = CollectionUtils.contains(dataList.iterator(), new SubcategoryEntryDTO("PERSON","Bob Jones"));
    	assertTrue(exist);
    }
    
    @Test
    public void testFindCountDTO() {
    	
    	assertEquals(0,subcategoryEntryService.findCategoryCounts().size());
    	
    	//Store Test Valid Categories
    	categoryService.save(new CategoryDTO("PERSON"));
    	categoryService.save(new CategoryDTO("PLACE"));
    	categoryService.save(new CategoryDTO("ANIMAL"));
    	categoryService.save(new CategoryDTO("COMPUTER"));
    	categoryService.save(new CategoryDTO("OTHER"));
    	//Create Test Data
    	List<SubcategoryEntryDTO> dataList = new ArrayList<SubcategoryEntryDTO>();
    	dataList.add(new SubcategoryEntryDTO("PERSON","Bob Jones"));
    	dataList.add(new SubcategoryEntryDTO("PLACE","Washington"));
    	dataList.add(new SubcategoryEntryDTO("PERSON","Mary"));
    	dataList.add(new SubcategoryEntryDTO("COMPUTER","Mac"));
    	dataList.add(new SubcategoryEntryDTO("PERSON","Bob Jones"));
    	dataList.add(new SubcategoryEntryDTO("OTHER","Tree"));
    	dataList.add(new SubcategoryEntryDTO("ANIMAL","Dog"));
    	dataList.add(new SubcategoryEntryDTO("PLACE","Texas"));
    	dataList.add(new SubcategoryEntryDTO("FOOD","Steak"));
    	dataList.add(new SubcategoryEntryDTO("ANIMAL","Cat"));
    	dataList.add(new SubcategoryEntryDTO("PERSON","Mac"));  	
    	//Perform operations and report results
    	SubcategoryCompositeDTO cdComposite = subcategoryEntryService.saveList(dataList);     	
    	assertEquals(9,cdComposite.getSubcategoryEntryDTOList().size());
    	
    	List<CategoryCountDTO> counts = subcategoryEntryService.findCategoryCounts();
    	for(CategoryCountDTO dto: counts){
    		System.out.println(dto.getName() + " " + dto.getCount());
    	}
    	assertEquals(5,counts.size());
    	
    }

}
