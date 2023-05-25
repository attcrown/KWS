package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.fishsix.lib.domain.entity.StudyClassEvaluationDetail;

@Repository
public interface JpaStudyClassEvaluationDetailRepository extends KwsFishsixJpaRepository<StudyClassEvaluationDetail, String>
{
	public List<StudyClassEvaluationDetail> findByClassEvaluationId(String classEvaluationId);
	
	public List<StudyClassEvaluationDetail> findByClassEvaluationIdIn(Collection<String> classEvaluationIds);
	
	public default List<StudyClassEvaluationDetail> findByClassEvaluationIds(Collection<String> classEvaluationIds)
	{
		return findByClassEvaluationIdIn(classEvaluationIds);
	}
	
	public List<StudyClassEvaluationDetail> removeByClassEvaluationIdIn(Collection<String> classEvaluationIds);
	
	public default List<StudyClassEvaluationDetail> removeByClassEvaluationIds(Collection<String> classEvaluationIds)
	{
		return removeByClassEvaluationIdIn(classEvaluationIds);
	}
}