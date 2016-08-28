package com.compucloud.cleandata.web.rest.dto;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the CategoryCount entity.
 */
public class CategoryDataCompositeDTO implements Serializable {
	
	
	
	public CategoryDataCompositeDTO(List<CategoryCountDTO> categoryCountDTOList,
			List<CategoryDataDTO> categoryDataDTOList) {
		super();
		this.categoryCountDTOList = categoryCountDTOList;
		this.categoryDataDTOList = categoryDataDTOList;
	}
	public CategoryDataCompositeDTO() {
		super();
	}	
	
	List<CategoryCountDTO> categoryCountDTOList;        
    List<CategoryDataDTO> categoryDataDTOList;
    
	public List<CategoryCountDTO> getCategoryCountDTOList() {
		return categoryCountDTOList;
	}
	public void setCategoryCountDTOList(List<CategoryCountDTO> categoryCountDTOList) {
		this.categoryCountDTOList = categoryCountDTOList;
	}
	public List<CategoryDataDTO> getCategoryDataDTOList() {
		return categoryDataDTOList;
	}
	public void setCategoryDataDTOList(List<CategoryDataDTO> categoryDataDTOList) {
		this.categoryDataDTOList = categoryDataDTOList;
	} 

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryCountDTOList == null) ? 0 : categoryCountDTOList.hashCode());
		result = prime * result + ((categoryDataDTOList == null) ? 0 : categoryDataDTOList.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoryDataCompositeDTO other = (CategoryDataCompositeDTO) obj;
		if (categoryCountDTOList == null) {
			if (other.categoryCountDTOList != null)
				return false;
		} else if (!categoryCountDTOList.equals(other.categoryCountDTOList))
			return false;
		if (categoryDataDTOList == null) {
			if (other.categoryDataDTOList != null)
				return false;
		} else if (!categoryDataDTOList.equals(other.categoryDataDTOList))
			return false;
		return true;
	}
	
}
