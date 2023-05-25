package com.otc.kws.fishsix.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.fishsix.lib.domain.entity.StudyClassReservation;

@Repository
public interface JpaStudyClassReservationRepository extends KwsFishsixJpaRepository<StudyClassReservation, String>
{

}