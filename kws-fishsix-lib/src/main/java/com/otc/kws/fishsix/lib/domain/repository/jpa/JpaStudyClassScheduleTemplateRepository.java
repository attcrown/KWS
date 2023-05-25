package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.fishsix.lib.domain.entity.StudyClassScheduleTemplate;

@Repository
public interface JpaStudyClassScheduleTemplateRepository extends KwsFishsixJpaRepository<StudyClassScheduleTemplate, String>
{
	public List<StudyClassScheduleTemplate> findByClassChannelId(String classChannelId);
	
	public List<StudyClassScheduleTemplate> findByClassTypeId(String classTypeId);
	
	public List<StudyClassScheduleTemplate> findByClassLocationId(String classLocationId);
}