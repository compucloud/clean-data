package com.compucloud.cleandata.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.compucloud.cleandata.domain.CategoryData;
import com.compucloud.cleandata.service.CategoryDataService;
import com.compucloud.cleandata.web.rest.util.HeaderUtil;
import com.compucloud.cleandata.web.rest.util.PaginationUtil;
import com.compucloud.cleandata.web.rest.dto.CategoryDataDTO;
import com.compucloud.cleandata.web.rest.mapper.CategoryDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing CategoryData.
 */
@RestController
@RequestMapping("/api")
public class CategoryDataResource {

    private final Logger log = LoggerFactory.getLogger(CategoryDataResource.class);
        
    @Inject
    private CategoryDataService categoryDataService;
    
    @Inject
    private CategoryDataMapper categoryDataMapper;    
    
    /**
     * POST  /category-data : Create a new categoryData.
     *
     * @param categoryDataDTO the categoryDataDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new categoryDataDTO, or with status 400 (Bad Request) if the categoryData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/category-data",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CategoryDataDTO>> createCategoryDataList(@Valid @RequestBody List<CategoryDataDTO> categoryDataDTOList) throws URISyntaxException {
        log.debug("REST request to save CategoryData : {}", categoryDataDTOList);        
        List<CategoryDataDTO> resultList = categoryDataService.saveList(categoryDataDTOList);              
        return ResponseEntity.created(new URI("/api/category-data/"))
                       .body(resultList);
    }    

    /**
     * GET  /category-data : get all the categoryData.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of categoryData in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/category-data",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CategoryDataDTO>> getAllCategoryData(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of CategoryData");
        Page<CategoryData> page = categoryDataService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/category-data");
        return new ResponseEntity<>(categoryDataMapper.categoryDataToCategoryDataDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /category-data/:id : get the "id" categoryData.
     *
     * @param id the id of the categoryDataDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the categoryDataDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/category-data/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CategoryDataDTO> getCategoryData(@PathVariable Long id) {
        log.debug("REST request to get CategoryData : {}", id);
        CategoryDataDTO categoryDataDTO = categoryDataService.findOne(id);
        return Optional.ofNullable(categoryDataDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /category-data/:id : delete the "id" categoryData.
     *
     * @param id the id of the categoryDataDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/category-data/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCategoryData(@PathVariable Long id) {
        log.debug("REST request to delete CategoryData : {}", id);
        categoryDataService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("categoryData", id.toString())).build();
    }

}
