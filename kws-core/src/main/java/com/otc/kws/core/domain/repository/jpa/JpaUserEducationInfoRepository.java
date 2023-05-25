package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.UserEducationInfo;

@Repository
public interface JpaUserEducationInfoRepository extends KwsJpaRepository<UserEducationInfo, String>
{

}