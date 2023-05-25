package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.fishsix.lib.domain.entity.SubjectChapterMedia;

@Repository
public interface JpaSubjectChapterMediaRepository extends KwsFishsixJpaRepository<SubjectChapterMedia, String>
{
	public List<SubjectChapterMedia> findByChapterId(String chapterId);
	
	public List<SubjectChapterMedia> findByChapterIdIn(Collection<String> chapterIds);
	
	public default List<SubjectChapterMedia> findByChapterIds(Collection<String> chapterIds)
	{
		return findByChapterIdIn(chapterIds);
	}
}