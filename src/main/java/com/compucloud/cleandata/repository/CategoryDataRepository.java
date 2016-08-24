package com.compucloud.cleandata.repository;

import com.compucloud.cleandata.domain.CategoryData;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CategoryData entity.
 */
@SuppressWarnings("unused")
public interface CategoryDataRepository extends JpaRepository<CategoryData,Long> {

}
