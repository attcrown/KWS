package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.fishsix.lib.domain.entity.CourseSubjectCollection;

@Repository
public interface JpaCourseSubjectCollectionRepository extends KwsFishsixJpaRepository<CourseSubjectCollection, String>
{
	public CourseSubjectCollection findByCourseIdAndSubjectId(String courseId, String subjectId);
	
	public List<CourseSubjectCollection> findByCourseIdIn(Collection<String> courseIds);
	
	public List<CourseSubjectCollection> removeByCourseId(String courseId);
}