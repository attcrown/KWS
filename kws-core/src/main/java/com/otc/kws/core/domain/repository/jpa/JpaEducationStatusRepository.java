package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.EducationStatus;

@Repository
public interface JpaEducationStatusRepository extends KwsJpaRepository<EducationStatus, String>
{

}