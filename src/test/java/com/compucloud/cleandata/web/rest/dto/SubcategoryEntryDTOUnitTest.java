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
public class SubcategoryEntryDTOUnitTest {

    @Test
    public void testEquals() {

    	SubcategoryEntryDTO data = new SubcategoryEntryDTO();
    	data.setCategory("PERSON");
    	data.setSubcategory("Pam");

    	SubcategoryEntryDTO data2 = new SubcategoryEntryDTO();
    	data2.setCategory("PERSON");
    	data2.setSubcategory("Pam");
    	assert(data.equals(data2));


    }

    @Test
    public void testNotEqual() {
    	SubcategoryEntryDTO data = new SubcategoryEntryDTO();
    	data.setCategory("PERSON");
    	data.setSubcategory("Pam");

    	SubcategoryEntryDTO data2 = new SubcategoryEntryDTO();
    	data2.setCategory("PERSON");
    	data2.setSubcategory("Pama");
    	assertFalse(data.equals(data2));
    	SubcategoryEntryDTO data3 = new SubcategoryEntryDTO();
    	data3.setCategory("PERSONa");
    	data3.setSubcategory("Pam");
    	assertFalse(data.equals(data3));

    	SubcategoryEntryDTO data4 = new SubcategoryEntryDTO();
    	data4.setCategory("PERSONa");
    	data4.setSubcategory("Pama");
    	assertFalse(data.equals(data4));

    	SubcategoryEntryDTO data5 = new SubcategoryEntryDTO();
    	assertFalse(data.equals(data5));


    }

}
