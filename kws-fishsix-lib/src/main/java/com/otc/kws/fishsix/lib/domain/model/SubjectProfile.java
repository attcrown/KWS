package com.otc.kws.fishsix.lib.domain.model;

import java.util.List;

import com.otc.kws.core.domain.model.FileData;

import lombok.Data;

@Data
public class SubjectProfile extends com.otc.kws.fishsix.lib.domain.entity.Subject
{
	protected String thumbnailImageFileURL;
	protected FileData thumbnailImageFileData;
	
	protected List<SubjectChapter> chapters;
	
	public static SubjectProfile transform(com.otc.kws.fishsix.lib.domain.entity.Subject subject)
	{
		SubjectProfile subjectProfile = new SubjectProfile();
		
		subjectProfile.setId(subject.getId());
		subjectProfile.setSubjectCategoryId(subject.getSubjectCategoryId());
		subjectProfile.setName(subject.getName());
		subjectProfile.setTitle(subject.getTitle());
		subjectProfile.setDescription(subject.getDescription());
		subjectProfile.setEducationClassId(subject.getEducationClassId());
		subjectProfile.setEducationLevelId(subject.getEducationLevelId());
		subjectProfile.setClassLevel(subject.getClassLevel());
		subjectProfile.setThumbnailImageFileURI(subject.getThumbnailImageFileURI());
		subjectProfile.setRemark(subject.getRemark());
		subjectProfile.setStatus(subject.getStatus());
		subjectProfile.setCreatedBy(subject.getCreatedBy());
		subjectProfile.setCreatedAt(subject.getCreatedAt());
		subjectProfile.setLastModifiedBy(subject.getLastModifiedBy());
		subjectProfile.setLastModifiedAt(subject.getLastModifiedAt());
		
		return subjectProfile;
	}
	
	public static com.otc.kws.fishsix.lib.domain.entity.Subject transform(SubjectProfile subjectProfile)
	{
		com.otc.kws.fishsix.lib.domain.entity.Subject subject = new com.otc.kws.fishsix.lib.domain.entity.Subject();
		
		subject.setId(subjectProfile.getId());
		subject.setName(subjectProfile.getName());
		subject.setTitle(subjectProfile.getTitle());
		subject.setDescription(subjectProfile.getDescription());
		subject.setEducationLevelId(subjectProfile.getEducationLevelId());
		subject.setClassLevel(subjectProfile.getClassLevel());
		subject.setThumbnailImageFileURI(subjectProfile.getThumbnailImageFileURI());
		subject.setRemark(subjectProfile.getRemark());
		subject.setStatus(subjectProfile.getStatus());
		subject.setCreatedBy(subjectProfile.getCreatedBy());
		subject.setCreatedAt(subjectProfile.getCreatedAt());
		subject.setLastModifiedBy(subjectProfile.getLastModifiedBy());
		subject.setLastModifiedAt(subjectProfile.getLastModifiedAt());
		
		return subject;
		//return (com.otc.kws.fishsix.lib.domain.entity.Subject) subjectProfile;
	}
	// ****************************** Subject ****************************** //
	
	
	// ****************************** SubjectChapter ****************************** //
	@Data
	public static class SubjectChapter extends com.otc.kws.fishsix.lib.domain.entity.SubjectChapter
	{
		protected String thumbnailImageFileURL;
		protected FileData thumbnailImageFileData;
		protected List<SubjectChapterMedia> medias;
	}
	
	public static SubjectChapter transform(com.otc.kws.fishsix.lib.domain.entity.SubjectChapter subjectChapter)
	{
		SubjectChapter _subjectChapter = new SubjectChapter();
		_subjectChapter.setId(subjectChapter.getId());
		_subjectChapter.setSubjectId(subjectChapter.getSubjectId());
		_subjectChapter.setSeqNo(subjectChapter.getSeqNo());
		_subjectChapter.setName(subjectChapter.getName());
		_subjectChapter.setDescription(subjectChapter.getDescription());
		_subjectChapter.setSemaster(subjectChapter.getSemaster());
		_subjectChapter.setThumbnailImageFileURI(subjectChapter.getThumbnailImageFileURI());
		_subjectChapter.setRemark(subjectChapter.getRemark());
		_subjectChapter.setStatus(subjectChapter.getStatus());
		_subjectChapter.setCreatedBy(subjectChapter.getCreatedBy());
		_subjectChapter.setCreatedAt(subjectChapter.getCreatedAt());
		_subjectChapter.setLastModifiedBy(subjectChapter.getLastModifiedBy());
		_subjectChapter.setLastModifiedAt(subjectChapter.getLastModifiedAt());
		
		return _subjectChapter;
	}
	
	public static com.otc.kws.fishsix.lib.domain.entity.SubjectChapter transform(SubjectChapter subjectChapter)
	{
		return subjectChapter;
	}
	// ****************************** SubjectChapter ****************************** //
	
	
	@Data
	public static class SubjectChapterMedia extends com.otc.kws.fishsix.lib.domain.entity.SubjectChapterMedia
	{
		protected String mediaSourceURL;
		protected FileData mediaSourceFileData;
	}
	
	public static SubjectChapterMedia transform(com.otc.kws.fishsix.lib.domain.entity.SubjectChapterMedia subjectChapterMedia)
	{
		SubjectChapterMedia _subjectChapterMedia = new SubjectChapterMedia();
		_subjectChapterMedia.setId(subjectChapterMedia.getId());
		_subjectChapterMedia.setSubjectId(subjectChapterMedia.getSubjectId());
		_subjectChapterMedia.setChapterId(subjectChapterMedia.getChapterId());
		_subjectChapterMedia.setMediaType(subjectChapterMedia.getMediaType());
		_subjectChapterMedia.setMediaSourceURI(subjectChapterMedia.getMediaSourceURI());
		_subjectChapterMedia.setTitle(subjectChapterMedia.getTitle());
		_subjectChapterMedia.setDescription(subjectChapterMedia.getDescription());
		_subjectChapterMedia.setSeqNo(subjectChapterMedia.getSeqNo());
		_subjectChapterMedia.setRemark(subjectChapterMedia.getRemark());
		_subjectChapterMedia.setStatus(subjectChapterMedia.getStatus());
		_subjectChapterMedia.setCreatedBy(subjectChapterMedia.getCreatedBy());
		_subjectChapterMedia.setCreatedAt(subjectChapterMedia.getCreatedAt());
		_subjectChapterMedia.setLastModifiedBy(subjectChapterMedia.getLastModifiedBy());
		_subjectChapterMedia.setLastModifiedAt(subjectChapterMedia.getLastModifiedAt());
		
		return _subjectChapterMedia;
	}
	
	public static com.otc.kws.fishsix.lib.domain.entity.SubjectChapterMedia transform(SubjectChapterMedia subjectChapterMedia)
	{
		return subjectChapterMedia;
	}
}