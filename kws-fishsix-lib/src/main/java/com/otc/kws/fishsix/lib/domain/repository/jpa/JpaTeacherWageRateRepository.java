package com.otc.kws.fishsix.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.fishsix.lib.domain.entity.TeacherWageRate;

@Repository
public interface JpaTeacherWageRateRepository extends KwsFishsixJpaRepository<TeacherWageRate, String>
{

}