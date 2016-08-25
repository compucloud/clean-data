package com.compucloud.cleandata.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CategoryCount entity.
 */
public class CategoryCountDTO implements Serializable {

    public CategoryCountDTO(String name, int count) {
		super();
		this.name = name;
		this.count = count;
	}	
    
    public CategoryCountDTO() {
		super();		
	}

	private Long id;

    @NotNull
    @Size(max = 256)
    private String name;
    
    private int count;


    public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategoryCountDTO categoryDTO = (CategoryCountDTO) o;

        if ( ! Objects.equals(id, categoryDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
