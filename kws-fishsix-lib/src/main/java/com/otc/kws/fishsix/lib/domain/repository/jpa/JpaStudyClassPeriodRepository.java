package com.otc.kws.fishsix.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.fishsix.lib.domain.entity.StudyClassPeriod;

@Repository
public interface JpaStudyClassPeriodRepository extends KwsFishsixJpaRepository<StudyClassPeriod, String>
{

}