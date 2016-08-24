package com.compucloud.cleandata.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the CategoryData entity.
 */
public class CategoryDataDTO implements Serializable {

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

        CategoryDataDTO categoryDataDTO = (CategoryDataDTO) o;

        if ( ! Objects.equals(id, categoryDataDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CategoryDataDTO{" +
            "id=" + id +
            ", category='" + category + "'" +
            ", subcategory='" + subcategory + "'" +
            '}';
    }
}
