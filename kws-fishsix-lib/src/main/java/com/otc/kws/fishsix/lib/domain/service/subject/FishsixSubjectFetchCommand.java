package com.otc.kws.fishsix.lib.domain.service.subject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.Subject;
import com.otc.kws.fishsix.lib.domain.entity.SubjectChapter;
import com.otc.kws.fishsix.lib.domain.entity.SubjectChapterMedia;
import com.otc.kws.fishsix.lib.domain.model.SubjectProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectChapterMediaRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectChapterRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectRepository;
import com.otc.kws.fishsix.lib.domain.service.subject.file.FishsixSubjectChapterMediaFileService;
import com.otc.kws.fishsix.lib.domain.service.subject.file.FishsixSubjectThumbnailImageService;

import lombok.Data;

@Component
public class FishsixSubjectFetchCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaSubjectRepository subjectRepository;
	
	@Autowired
	protected JpaSubjectChapterRepository subjectChapterRepository;
	
	@Autowired
	protected JpaSubjectChapterMediaRepository subjectChapterMediaRepository;
	
	@Autowired
	protected FishsixSubjectThumbnailImageService subjectThumbnailImageService;
	
	@Autowired
	protected FishsixSubjectChapterMediaFileService subjectChapterMediaFileService;
	
	
	public Response fetchSubject(Request request)
	{
		logger.info("### {}.fetchSubject ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		List<Subject> subjects = request.getSubjects();
		
		if(subjects == null) {
			subjects = subjectRepository.findAll();
		}
		
		if(subjects != null && !subjects.isEmpty()) {
			List<SubjectChapter> subjectChapters = null;
			List<SubjectChapterMedia> subjectChapterMedias = null;
			
			List<String> subjectIds = subjects.stream().map(Subject::getId).distinct().collect(Collectors.toList());
			
			subjectChapters = subjectChapterRepository.findBySubjectIds(subjectIds);
			
			if(subjectChapters != null && !subjectChapters.isEmpty()) {
				List<String> subjectChapterIds = subjectChapters.stream().map(SubjectChapter::getId).distinct().collect(Collectors.toList());
				subjectChapterMedias = subjectChapterMediaRepository.findByChapterIds(subjectChapterIds);
			}
			
			List<SubjectProfile> subjectProfiles = new ArrayList<>();
			
			for(Subject subject : subjects) {
				String subjectThumbnailImageFileURL = subjectThumbnailImageService.getFileURL(req -> {
					req.copyFrom(request);
					req.setSubject(subject);
				}).getFileURL();
				
				SubjectProfile subjectProfile = SubjectProfile.transform(subject);
				subjectProfile.setThumbnailImageFileURL(subjectThumbnailImageFileURL);
				
				List<SubjectProfile.SubjectChapter> chapters = new ArrayList<>();
				
				if(subjectChapters != null && !subjectChapters.isEmpty()) {
					chapters = subjectChapters.
							stream().
							filter(subjectChapter -> subjectChapter.getSubjectId().equals(subject.getId())).
							map(subjectChapter -> {
								SubjectProfile.SubjectChapter _subjectChapter = SubjectProfile.transform(subjectChapter);
								return _subjectChapter;
							}).
							sorted((chapter1, chapter2) -> chapter1.getSeqNo() - chapter2.getSeqNo()).
							collect(Collectors.toList());
					
					subjectProfile.setChapters(chapters);
				}
				
				if(chapters != null && !chapters.isEmpty() && subjectChapterMedias != null && !subjectChapterMedias.isEmpty()) {
					for(SubjectProfile.SubjectChapter chapter : chapters) {
						List<SubjectProfile.SubjectChapterMedia> medias = subjectChapterMedias.
								stream().
								filter(e -> e.getChapterId().equals(chapter.getId())).
								map(e -> {
									String mediaSourceFileURL = e.getMediaSourceURI();
									
									if(e.getMediaType() == SubjectChapterMedia.MediaType.Document) {
										mediaSourceFileURL = subjectChapterMediaFileService.getMediaSourceFileURL(req -> {
											req.copyFrom(request);
											req.setMedia(e);
										}).getFileURL();
									}
									
									SubjectProfile.SubjectChapterMedia media = SubjectProfile.transform(e);
									media.setMediaSourceURL(mediaSourceFileURL);
									
									return media;
								}).
								sorted((media1, media2) -> media1.getSeqNo() - media2.getSeqNo()).
								collect(Collectors.toList());
						
						chapter.setMedias(medias);
					}
				}
				
				subjectProfiles.add(subjectProfile);
			}
			
			response.setSubjectProfiles(subjectProfiles);
		}
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected List<Subject> subjects;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected List<SubjectProfile> subjectProfiles;
	}
}