package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.fishsix.lib.domain.entity.StudyClass;

@Repository
public interface JpaStudyClassRepository extends KwsFishsixJpaRepository<StudyClass, String>
{
	public List<StudyClass> findByStudyDate(Date studyDate);
}