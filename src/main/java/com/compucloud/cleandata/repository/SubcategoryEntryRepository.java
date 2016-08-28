package com.compucloud.cleandata.repository;

import com.compucloud.cleandata.domain.SubcategoryEntry;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SubcategoryEntry entity.
 */
@SuppressWarnings("unused")
public interface SubcategoryEntryRepository extends JpaRepository<SubcategoryEntry,Long> {

}
