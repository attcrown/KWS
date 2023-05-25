package com.otc.kws.fishsix.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.fishsix.lib.domain.entity.StudyClassCancellation;

@Repository
public interface JpaStudyClassCancellationRepository extends KwsFishsixJpaRepository<StudyClassCancellation, String>
{

}