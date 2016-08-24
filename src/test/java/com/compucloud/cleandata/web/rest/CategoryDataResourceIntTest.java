package com.compucloud.cleandata.web.rest;

import com.compucloud.cleandata.CleandataApp;
import com.compucloud.cleandata.domain.CategoryData;
import com.compucloud.cleandata.repository.CategoryDataRepository;
import com.compucloud.cleandata.service.CategoryDataService;
import com.compucloud.cleandata.web.rest.dto.CategoryDataDTO;
import com.compucloud.cleandata.web.rest.mapper.CategoryDataMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CategoryDataResource REST controller.
 *
 * @see CategoryDataResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CleandataApp.class)
@WebAppConfiguration
@IntegrationTest
public class CategoryDataResourceIntTest {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_SUBCATEGORY = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_SUBCATEGORY = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private CategoryDataRepository categoryDataRepository;

    @Inject
    private CategoryDataMapper categoryDataMapper;

    @Inject
    private CategoryDataService categoryDataService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCategoryDataMockMvc;

    private CategoryData categoryData;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CategoryDataResource categoryDataResource = new CategoryDataResource();
        ReflectionTestUtils.setField(categoryDataResource, "categoryDataService", categoryDataService);
        ReflectionTestUtils.setField(categoryDataResource, "categoryDataMapper", categoryDataMapper);
        this.restCategoryDataMockMvc = MockMvcBuilders.standaloneSetup(categoryDataResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        categoryData = new CategoryData();
        categoryData.setCategory(DEFAULT_CATEGORY);
        categoryData.setSubcategory(DEFAULT_SUBCATEGORY);
    }

    @Test
    @Transactional
    public void createCategoryData() throws Exception {
        int databaseSizeBeforeCreate = categoryDataRepository.findAll().size();

        // Create the CategoryData
        CategoryDataDTO categoryDataDTO = categoryDataMapper.categoryDataToCategoryDataDTO(categoryData);

        restCategoryDataMockMvc.perform(post("/api/category-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(categoryDataDTO)))
                .andExpect(status().isCreated());

        // Validate the CategoryData in the database
        List<CategoryData> categoryData = categoryDataRepository.findAll();
        assertThat(categoryData).hasSize(databaseSizeBeforeCreate + 1);
        CategoryData testCategoryData = categoryData.get(categoryData.size() - 1);
        assertThat(testCategoryData.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testCategoryData.getSubcategory()).isEqualTo(DEFAULT_SUBCATEGORY);
    }

    @Test
    @Transactional
    public void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryDataRepository.findAll().size();
        // set the field null
        categoryData.setCategory(null);

        // Create the CategoryData, which fails.
        CategoryDataDTO categoryDataDTO = categoryDataMapper.categoryDataToCategoryDataDTO(categoryData);

        restCategoryDataMockMvc.perform(post("/api/category-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(categoryDataDTO)))
                .andExpect(status().isBadRequest());

        List<CategoryData> categoryData = categoryDataRepository.findAll();
        assertThat(categoryData).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubcategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryDataRepository.findAll().size();
        // set the field null
        categoryData.setSubcategory(null);

        // Create the CategoryData, which fails.
        CategoryDataDTO categoryDataDTO = categoryDataMapper.categoryDataToCategoryDataDTO(categoryData);

        restCategoryDataMockMvc.perform(post("/api/category-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(categoryDataDTO)))
                .andExpect(status().isBadRequest());

        List<CategoryData> categoryData = categoryDataRepository.findAll();
        assertThat(categoryData).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoryData() throws Exception {
        // Initialize the database
        categoryDataRepository.saveAndFlush(categoryData);

        // Get all the categoryData
        restCategoryDataMockMvc.perform(get("/api/category-data?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(categoryData.getId().intValue())))
                .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
                .andExpect(jsonPath("$.[*].subcategory").value(hasItem(DEFAULT_SUBCATEGORY.toString())));
    }

    @Test
    @Transactional
    public void getCategoryData() throws Exception {
        // Initialize the database
        categoryDataRepository.saveAndFlush(categoryData);

        // Get the categoryData
        restCategoryDataMockMvc.perform(get("/api/category-data/{id}", categoryData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(categoryData.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.subcategory").value(DEFAULT_SUBCATEGORY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCategoryData() throws Exception {
        // Get the categoryData
        restCategoryDataMockMvc.perform(get("/api/category-data/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoryData() throws Exception {
        // Initialize the database
        categoryDataRepository.saveAndFlush(categoryData);
        int databaseSizeBeforeUpdate = categoryDataRepository.findAll().size();

        // Update the categoryData
        CategoryData updatedCategoryData = new CategoryData();
        updatedCategoryData.setId(categoryData.getId());
        updatedCategoryData.setCategory(UPDATED_CATEGORY);
        updatedCategoryData.setSubcategory(UPDATED_SUBCATEGORY);
        CategoryDataDTO categoryDataDTO = categoryDataMapper.categoryDataToCategoryDataDTO(updatedCategoryData);

        restCategoryDataMockMvc.perform(put("/api/category-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(categoryDataDTO)))
                .andExpect(status().isOk());

        // Validate the CategoryData in the database
        List<CategoryData> categoryData = categoryDataRepository.findAll();
        assertThat(categoryData).hasSize(databaseSizeBeforeUpdate);
        CategoryData testCategoryData = categoryData.get(categoryData.size() - 1);
        assertThat(testCategoryData.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testCategoryData.getSubcategory()).isEqualTo(UPDATED_SUBCATEGORY);
    }

    @Test
    @Transactional
    public void deleteCategoryData() throws Exception {
        // Initialize the database
        categoryDataRepository.saveAndFlush(categoryData);
        int databaseSizeBeforeDelete = categoryDataRepository.findAll().size();

        // Get the categoryData
        restCategoryDataMockMvc.perform(delete("/api/category-data/{id}", categoryData.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CategoryData> categoryData = categoryDataRepository.findAll();
        assertThat(categoryData).hasSize(databaseSizeBeforeDelete - 1);
    }
}
