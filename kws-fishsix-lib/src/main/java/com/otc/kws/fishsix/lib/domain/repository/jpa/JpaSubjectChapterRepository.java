package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.fishsix.lib.domain.entity.SubjectChapter;

@Repository
public interface JpaSubjectChapterRepository extends KwsFishsixJpaRepository<SubjectChapter, String>
{
	public List<SubjectChapter> findBySubjectId(String subjectId);
	
	public List<SubjectChapter> findBySubjectIdIn(Collection<String> subjectIds);
	
	public default List<SubjectChapter> findBySubjectIds(Collection<String> subjectIds)
	{
		return findBySubjectIdIn(subjectIds);
	}
}