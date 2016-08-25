package com.compucloud.cleandata.web.rest.dto;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.compucloud.cleandata.CleandataApp;
import com.compucloud.cleandata.service.UserService;

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
public class CategoryDataDTOUnitTest {   

    @Test
    public void testEquals() {
    	
    	CategoryDataDTO data = new CategoryDataDTO();
    	data.setCategory("PERSON");
    	data.setSubcategory("Pam");
    	
    	CategoryDataDTO data2 = new CategoryDataDTO();
    	data2.setCategory("PERSON");
    	data2.setSubcategory("Pam");
    	assert(data.equals(data2));
    	
    	           
    }
    
    @Test
    public void testNotEqual() {
    	CategoryDataDTO data = new CategoryDataDTO();
    	data.setCategory("PERSON");
    	data.setSubcategory("Pam");
    	
    	CategoryDataDTO data2 = new CategoryDataDTO();
    	data2.setCategory("PERSON");
    	data2.setSubcategory("Pama");   
    	assertFalse(data.equals(data2)); 
    	CategoryDataDTO data3 = new CategoryDataDTO();
    	data3.setCategory("PERSONa");
    	data3.setSubcategory("Pam");  
    	assertFalse(data.equals(data3)); 
    	
    	CategoryDataDTO data4 = new CategoryDataDTO();
    	data4.setCategory("PERSONa");
    	data4.setSubcategory("Pama");  
    	assertFalse(data.equals(data4));
    	
    	CategoryDataDTO data5 = new CategoryDataDTO();    	
    	assertFalse(data.equals(data5));
    	
    	       
    }

}
