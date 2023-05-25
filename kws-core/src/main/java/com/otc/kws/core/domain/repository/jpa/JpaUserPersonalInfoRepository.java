package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.UserPersonalInfo;

@Repository
public interface JpaUserPersonalInfoRepository extends KwsJpaRepository<UserPersonalInfo, String>
{

}