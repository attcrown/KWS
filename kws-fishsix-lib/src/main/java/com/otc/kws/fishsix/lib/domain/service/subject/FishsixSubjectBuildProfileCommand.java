package com.otc.kws.fishsix.lib.domain.service.subject;

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
import com.otc.kws.fishsix.lib.domain.service.subject.file.FishsixSubjectChapterThumbnailImageService;
import com.otc.kws.fishsix.lib.domain.service.subject.file.FishsixSubjectThumbnailImageService;

import lombok.Data;

@Component
public class FishsixSubjectBuildProfileCommand extends BaseKwsCommand
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
	protected FishsixSubjectChapterThumbnailImageService subjectChapterThumbnailImageService;
	
	@Autowired
	protected FishsixSubjectChapterMediaFileService subjectChapterMediaFileService;
	
	
	public Response buildSubjectProfile(Request request)
	{
		logger.info("### {}.buildSubjectProfile ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		Subject subject = subjectRepository.findById(request.getSubjectId()).orElse(null);
		
		if(subject != null) {
			String subjectThumbnailImageFileURL = subjectThumbnailImageService.getFileURL(req -> {
				req.copyFrom(request);
				req.setSubject(subject);
			}).getFileURL();
			
			SubjectProfile subjectProfile = SubjectProfile.transform(subject);
			subjectProfile.setThumbnailImageFileURL(subjectThumbnailImageFileURL);
			
			List<SubjectChapter> subjectChapters = subjectChapterRepository.findBySubjectId(subject.getId());
			if(subjectChapters != null && !subjectChapters.isEmpty()) {
				List<SubjectProfile.SubjectChapter> chapters = subjectChapters.
						stream().
						map(e -> {
							SubjectProfile.SubjectChapter chapter = SubjectProfile.transform(e);
							
							if(e.getThumbnailImageFileURI() != null) {
								String thumbnailImageFileURL = subjectChapterThumbnailImageService.getFileURL(req -> {
									req.copyFrom(request);
									req.setSubjectChapter(e);
								}).getFileURL();
								
								chapter.setThumbnailImageFileURL(thumbnailImageFileURL);
							}
							
							return chapter;
						}).
						collect(Collectors.toList());
				
				subjectProfile.setChapters(chapters);
				
				if(chapters != null && !chapters.isEmpty()) {
					for(SubjectProfile.SubjectChapter chapter : chapters) {
						List<SubjectChapterMedia> subjectChapterMedias = subjectChapterMediaRepository.findByChapterId(chapter.getId());
						
						if(subjectChapterMedias != null && !subjectChapterMedias.isEmpty()) {
							List<SubjectProfile.SubjectChapterMedia> medias = subjectChapterMedias.stream().map(e -> {
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
							}).collect(Collectors.toList());
							
							chapter.setMedias(medias);
						}
					}
				}
			}
			
			response.setSubjectProfile(subjectProfile);
		}
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String subjectId;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected SubjectProfile subjectProfile;
	}
}