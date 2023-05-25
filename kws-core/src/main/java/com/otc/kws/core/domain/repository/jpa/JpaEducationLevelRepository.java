package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.EducationLevel;

@Repository
public interface JpaEducationLevelRepository extends KwsJpaRepository<EducationLevel, String>
{

}