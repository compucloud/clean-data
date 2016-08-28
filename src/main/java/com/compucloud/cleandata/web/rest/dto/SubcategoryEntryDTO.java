package com.compucloud.cleandata.web.rest.dto;

import javax.validation.constraints.*;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SubcategoryEntry entity.
 */
public class SubcategoryEntryDTO implements Serializable {
    
    public SubcategoryEntryDTO(String category, String subcategory) {
		super();		
		this.category = category;
		this.subcategory = subcategory;
	}
    
    public SubcategoryEntryDTO() {
		super();		
	}

	private Long id;

    @NotNull
    @Size(max = 256)
    private String category;

    @NotNull
    @Size(max = 256)
    private String subcategory;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SubcategoryEntryDTO categoryDataDTO = (SubcategoryEntryDTO) o;
        if(StringUtils.isBlank(this.category) || StringUtils.isBlank(categoryDataDTO.category)
        	|| StringUtils.isBlank(this.subcategory) || StringUtils.isBlank(categoryDataDTO.subcategory)){
        	return false;
        }
        
        if((!categoryDataDTO.getCategory().toLowerCase().equals(this.category.toLowerCase()))
        		|| (!categoryDataDTO.getSubcategory().equals(this.subcategory))){
        	return false;
        }        

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SubcategoryEntryDTO{" +
            "id=" + id +
            ", category='" + category + "'" +
            ", subcategory='" + subcategory + "'" +
            '}';
    }
}
