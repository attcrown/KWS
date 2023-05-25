package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.SchoolType;

@Repository
public interface JpaSchoolTypeRepository extends KwsJpaRepository<SchoolType, String>
{

}