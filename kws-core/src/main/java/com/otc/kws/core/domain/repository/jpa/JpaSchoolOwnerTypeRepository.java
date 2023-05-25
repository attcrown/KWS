package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.SchoolOwnerType;

@Repository
public interface JpaSchoolOwnerTypeRepository extends KwsJpaRepository<SchoolOwnerType, String>
{

}