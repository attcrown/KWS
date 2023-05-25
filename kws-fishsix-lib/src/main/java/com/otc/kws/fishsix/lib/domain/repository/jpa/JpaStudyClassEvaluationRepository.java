package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.fishsix.lib.domain.entity.StudyClassEvaluation;

@Repository
public interface JpaStudyClassEvaluationRepository extends KwsFishsixJpaRepository<StudyClassEvaluation, String>
{
	public List<StudyClassEvaluation> findByClassScheduleId(String classScheduleId);
	
	public List<StudyClassEvaluation> findByClassScheduleIdIn(Collection<String> classScheduleIds);
	
	public default List<StudyClassEvaluation> findByClassScheduleIds(Collection<String> classScheduleIds)
	{
		return findByClassScheduleIdIn(classScheduleIds);
	}
}