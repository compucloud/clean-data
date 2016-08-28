package com.compucloud.cleandata.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.compucloud.cleandata.domain.SubcategoryEntry;
import com.compucloud.cleandata.service.SubcategoryEntryService;
import com.compucloud.cleandata.web.rest.util.HeaderUtil;
import com.compucloud.cleandata.web.rest.util.PaginationUtil;
import com.compucloud.cleandata.web.rest.dto.SubcategoryCompositeDTO;
import com.compucloud.cleandata.web.rest.dto.SubcategoryEntryDTO;
import com.compucloud.cleandata.web.rest.mapper.SubcategoryEntryMapper;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SubcategoryEntry.
 */
@RestController
@RequestMapping("/api")
public class SubcategoryEntryResource {

    private final Logger log = LoggerFactory.getLogger(SubcategoryEntryResource.class);
        
    @Inject
    private SubcategoryEntryService subcategoryEntryService;
    
    @Inject
    private SubcategoryEntryMapper subcategoryEntryMapper;
    
    
    /**
     * POST  /subcategory-entries : Create a new Subcategory entries.
     *
     * @param SubcategoryCompositeDTO the subcategoryEntryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subcategoryEntryDTO, or with status 400 (Bad Request) if the categoryData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/subcategory-entries",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SubcategoryCompositeDTO> createSubcategoryEntryList(@Valid @RequestBody List<SubcategoryEntryDTO> categoryDataDTOList) throws URISyntaxException {
        log.debug("REST request to save SubcategoryEntry : {}", categoryDataDTOList);        
        SubcategoryCompositeDTO result = subcategoryEntryService.saveList(categoryDataDTOList);              
        return ResponseEntity.created(new URI("/api/subcategory-entries/"))
                       .body(result);
    }       

    /**
     * GET  /subcategory-entries : get all the subcategoryEntries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of subcategoryEntries in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/subcategory-entries",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SubcategoryEntryDTO>> getAllSubcategoryEntries(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SubcategoryEntries");
        Page<SubcategoryEntry> page = subcategoryEntryService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/subcategory-entries");
        return new ResponseEntity<>(subcategoryEntryMapper.subcategoryEntriesToSubcategoryEntryDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /subcategory-entries/:id : get the "id" subcategoryEntry.
     *
     * @param id the id of the subcategoryEntryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subcategoryEntryDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/subcategory-entries/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SubcategoryEntryDTO> getSubcategoryEntry(@PathVariable Long id) {
        log.debug("REST request to get SubcategoryEntry : {}", id);
        SubcategoryEntryDTO subcategoryEntryDTO = subcategoryEntryService.findOne(id);
        return Optional.ofNullable(subcategoryEntryDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /subcategory-entries/:id : delete the "id" subcategoryEntry.
     *
     * @param id the id of the subcategoryEntryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/subcategory-entries/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSubcategoryEntry(@PathVariable Long id) {
        log.debug("REST request to delete SubcategoryEntry : {}", id);
        subcategoryEntryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("subcategoryEntry", id.toString())).build();
    }

}
