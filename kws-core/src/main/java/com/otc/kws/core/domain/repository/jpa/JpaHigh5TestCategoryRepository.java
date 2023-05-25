package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.High5TestCategory;

@Repository
public interface JpaHigh5TestCategoryRepository extends KwsJpaRepository<High5TestCategory, String>
{

}