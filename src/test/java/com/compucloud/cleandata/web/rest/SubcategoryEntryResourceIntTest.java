package com.compucloud.cleandata.web.rest;

import com.compucloud.cleandata.CleandataApp;
import com.compucloud.cleandata.domain.SubcategoryEntry;
import com.compucloud.cleandata.repository.SubcategoryEntryRepository;
import com.compucloud.cleandata.service.SubcategoryEntryService;
import com.compucloud.cleandata.web.rest.dto.SubcategoryEntryDTO;
import com.compucloud.cleandata.web.rest.mapper.SubcategoryEntryMapper;

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
 * Test class for the SubcategoryEntryResource REST controller.
 *
 * @see SubcategoryEntryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CleandataApp.class)
@WebAppConfiguration
@IntegrationTest
public class SubcategoryEntryResourceIntTest {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_SUBCATEGORY = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_SUBCATEGORY = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private SubcategoryEntryRepository subcategoryEntryRepository;

    @Inject
    private SubcategoryEntryMapper subcategoryEntryMapper;

    @Inject
    private SubcategoryEntryService subcategoryEntryService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSubcategoryEntryMockMvc;

    private SubcategoryEntry subcategoryEntry;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SubcategoryEntryResource subcategoryEntryResource = new SubcategoryEntryResource();
        ReflectionTestUtils.setField(subcategoryEntryResource, "subcategoryEntryService", subcategoryEntryService);
        ReflectionTestUtils.setField(subcategoryEntryResource, "subcategoryEntryMapper", subcategoryEntryMapper);
        this.restSubcategoryEntryMockMvc = MockMvcBuilders.standaloneSetup(subcategoryEntryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        subcategoryEntry = new SubcategoryEntry();
        subcategoryEntry.setCategory(DEFAULT_CATEGORY);
        subcategoryEntry.setSubcategory(DEFAULT_SUBCATEGORY);
    }

    @Test
    @Transactional
    public void createSubcategoryEntry() throws Exception {
        int databaseSizeBeforeCreate = subcategoryEntryRepository.findAll().size();

        // Create the SubcategoryEntry
        SubcategoryEntryDTO subcategoryEntryDTO = subcategoryEntryMapper.subcategoryEntryToSubcategoryEntryDTO(subcategoryEntry);

        restSubcategoryEntryMockMvc.perform(post("/api/subcategory-entries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subcategoryEntryDTO)))
                .andExpect(status().isCreated());

        // Validate the SubcategoryEntry in the database
        List<SubcategoryEntry> subcategoryEntries = subcategoryEntryRepository.findAll();
        assertThat(subcategoryEntries).hasSize(databaseSizeBeforeCreate + 1);
        SubcategoryEntry testSubcategoryEntry = subcategoryEntries.get(subcategoryEntries.size() - 1);
        assertThat(testSubcategoryEntry.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testSubcategoryEntry.getSubcategory()).isEqualTo(DEFAULT_SUBCATEGORY);
    }

    @Test
    @Transactional
    public void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = subcategoryEntryRepository.findAll().size();
        // set the field null
        subcategoryEntry.setCategory(null);

        // Create the SubcategoryEntry, which fails.
        SubcategoryEntryDTO subcategoryEntryDTO = subcategoryEntryMapper.subcategoryEntryToSubcategoryEntryDTO(subcategoryEntry);

        restSubcategoryEntryMockMvc.perform(post("/api/subcategory-entries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subcategoryEntryDTO)))
                .andExpect(status().isBadRequest());

        List<SubcategoryEntry> subcategoryEntries = subcategoryEntryRepository.findAll();
        assertThat(subcategoryEntries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubcategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = subcategoryEntryRepository.findAll().size();
        // set the field null
        subcategoryEntry.setSubcategory(null);

        // Create the SubcategoryEntry, which fails.
        SubcategoryEntryDTO subcategoryEntryDTO = subcategoryEntryMapper.subcategoryEntryToSubcategoryEntryDTO(subcategoryEntry);

        restSubcategoryEntryMockMvc.perform(post("/api/subcategory-entries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subcategoryEntryDTO)))
                .andExpect(status().isBadRequest());

        List<SubcategoryEntry> subcategoryEntries = subcategoryEntryRepository.findAll();
        assertThat(subcategoryEntries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubcategoryEntries() throws Exception {
        // Initialize the database
        subcategoryEntryRepository.saveAndFlush(subcategoryEntry);

        // Get all the subcategoryEntries
        restSubcategoryEntryMockMvc.perform(get("/api/subcategory-entries?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(subcategoryEntry.getId().intValue())))
                .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
                .andExpect(jsonPath("$.[*].subcategory").value(hasItem(DEFAULT_SUBCATEGORY.toString())));
    }

    @Test
    @Transactional
    public void getSubcategoryEntry() throws Exception {
        // Initialize the database
        subcategoryEntryRepository.saveAndFlush(subcategoryEntry);

        // Get the subcategoryEntry
        restSubcategoryEntryMockMvc.perform(get("/api/subcategory-entries/{id}", subcategoryEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(subcategoryEntry.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.subcategory").value(DEFAULT_SUBCATEGORY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSubcategoryEntry() throws Exception {
        // Get the subcategoryEntry
        restSubcategoryEntryMockMvc.perform(get("/api/subcategory-entries/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubcategoryEntry() throws Exception {
        // Initialize the database
        subcategoryEntryRepository.saveAndFlush(subcategoryEntry);
        int databaseSizeBeforeUpdate = subcategoryEntryRepository.findAll().size();

        // Update the subcategoryEntry
        SubcategoryEntry updatedSubcategoryEntry = new SubcategoryEntry();
        updatedSubcategoryEntry.setId(subcategoryEntry.getId());
        updatedSubcategoryEntry.setCategory(UPDATED_CATEGORY);
        updatedSubcategoryEntry.setSubcategory(UPDATED_SUBCATEGORY);
        SubcategoryEntryDTO subcategoryEntryDTO = subcategoryEntryMapper.subcategoryEntryToSubcategoryEntryDTO(updatedSubcategoryEntry);

        restSubcategoryEntryMockMvc.perform(put("/api/subcategory-entries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subcategoryEntryDTO)))
                .andExpect(status().isOk());

        // Validate the SubcategoryEntry in the database
        List<SubcategoryEntry> subcategoryEntries = subcategoryEntryRepository.findAll();
        assertThat(subcategoryEntries).hasSize(databaseSizeBeforeUpdate);
        SubcategoryEntry testSubcategoryEntry = subcategoryEntries.get(subcategoryEntries.size() - 1);
        assertThat(testSubcategoryEntry.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testSubcategoryEntry.getSubcategory()).isEqualTo(UPDATED_SUBCATEGORY);
    }

    @Test
    @Transactional
    public void deleteSubcategoryEntry() throws Exception {
        // Initialize the database
        subcategoryEntryRepository.saveAndFlush(subcategoryEntry);
        int databaseSizeBeforeDelete = subcategoryEntryRepository.findAll().size();

        // Get the subcategoryEntry
        restSubcategoryEntryMockMvc.perform(delete("/api/subcategory-entries/{id}", subcategoryEntry.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SubcategoryEntry> subcategoryEntries = subcategoryEntryRepository.findAll();
        assertThat(subcategoryEntries).hasSize(databaseSizeBeforeDelete - 1);
    }
}
