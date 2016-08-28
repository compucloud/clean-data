package com.compucloud.cleandata.web.rest.dto;

import java.io.Serializable;
import java.util.List;

import com.compucloud.cleandata.domain.SubcategoryEntry;

/**
 * A DTO for the CategoryCount entity.
 */
public class SubcategoryCompositeDTO implements Serializable {
	
	List<CategoryCountDTO> categoryCountDTOList; 
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	
	List<SubcategoryEntryDTO> subcategoryEntryDTOList;
	 
	public SubcategoryCompositeDTO(List<CategoryCountDTO> categoryCountDTOList,
			List<SubcategoryEntryDTO> subcategoryEntryDTOList) {
		super();
		this.categoryCountDTOList = categoryCountDTOList;
		this.subcategoryEntryDTOList = subcategoryEntryDTOList;
	}
	
	public SubcategoryCompositeDTO() {
		super();		
	}
	/**
	 * @return the categoryCountDTOList
	 */
	public List<CategoryCountDTO> getCategoryCountDTOList() {
		return categoryCountDTOList;
	}
	/**
	 * @param categoryCountDTOList the categoryCountDTOList to set
	 */
	public void setCategoryCountDTOList(List<CategoryCountDTO> categoryCountDTOList) {
		this.categoryCountDTOList = categoryCountDTOList;
	}
	/**
	 * @return the subcategoryEntryDTOList
	 */
	public List<SubcategoryEntryDTO> getSubcategoryEntryDTOList() {
		return subcategoryEntryDTOList;
	}
	/**
	 * @param subcategoryEntryDTOList the subcategoryEntryDTOList to set
	 */
	public void setSubcategoryEntryDTOList(List<SubcategoryEntryDTO> subcategoryEntryDTOList) {
		this.subcategoryEntryDTOList = subcategoryEntryDTOList;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryCountDTOList == null) ? 0 : categoryCountDTOList.hashCode());
		result = prime * result + ((subcategoryEntryDTOList == null) ? 0 : subcategoryEntryDTOList.hashCode());
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
		SubcategoryCompositeDTO other = (SubcategoryCompositeDTO) obj;
		if (categoryCountDTOList == null) {
			if (other.categoryCountDTOList != null)
				return false;
		} else if (!categoryCountDTOList.equals(other.categoryCountDTOList))
			return false;
		if (subcategoryEntryDTOList == null) {
			if (other.subcategoryEntryDTOList != null)
				return false;
		} else if (!subcategoryEntryDTOList.equals(other.subcategoryEntryDTOList))
			return false;
		return true;
	}

	
}
