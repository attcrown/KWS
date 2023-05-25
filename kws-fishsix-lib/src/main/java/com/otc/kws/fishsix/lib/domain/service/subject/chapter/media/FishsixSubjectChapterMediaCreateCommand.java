package com.otc.kws.fishsix.lib.domain.service.subject.chapter.media;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.model.FileData;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.SubjectChapterMedia;
import com.otc.kws.fishsix.lib.domain.entity.SubjectChapterMedia.MediaType;
import com.otc.kws.fishsix.lib.domain.model.SubjectProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectChapterMediaRepository;
import com.otc.kws.fishsix.lib.domain.service.subject.file.FishsixSubjectChapterMediaFileService;

import lombok.Data;

@Component
public class FishsixSubjectChapterMediaCreateCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaSubjectChapterMediaRepository subjectChapterMediaRepository;
	
	@Autowired
	protected FishsixSubjectChapterMediaFileService mediaFileService;
	
	
	public Response createSubjectChapterMedia(Request request)
	{
		logger.info("### {}.createSubjectChapterMedia ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		SubjectChapterMedia subjectChapterMedia = request.getSubjectChapterMedia();
		if(subjectChapterMedia.getId() == null) {
			subjectChapterMedia.setId(UUID.randomUUID().toString());
		}
		if(subjectChapterMedia.getStatus() == null) {
			subjectChapterMedia.setStatus(MasterStatusValue.Active);
		}
		subjectChapterMedia.setCreatedBy(request.getPerformedBy());
		subjectChapterMedia.setCreatedAt(request.getPerformedAt());
		subjectChapterMedia.setLastModifiedBy(request.getPerformedBy());
		subjectChapterMedia.setLastModifiedAt(request.getPerformedAt());
		
		String mediaSourceURL = null;
		if(subjectChapterMedia.getMediaType() == MediaType.Document && request.getMediaSourceFileData() != null) {
			FishsixSubjectChapterMediaFileService.PutMediaSourceFileURLResponse putFileResponse = mediaFileService.putMediaSourceFile(req -> {
				req.copyFrom(request);
				req.setSubjectId(subjectChapterMedia.getSubjectId());
				req.setSubjectChapterId(subjectChapterMedia.getChapterId());
				req.setSubjectChapterMediaId(subjectChapterMedia.getId());
				req.setMediaSourceFileData(request.getMediaSourceFileData());
			});
			
			subjectChapterMedia.setMediaSourceURI(putFileResponse.getFileURI());
			mediaSourceURL = putFileResponse.getFileURL();
		}
		
		subjectChapterMediaRepository.save(subjectChapterMedia);
		logger.info("create subjectChapterMedia => {}", subjectChapterMedia);
		
		SubjectProfile.SubjectChapterMedia createdSubjectChapterMedia = SubjectProfile.transform(subjectChapterMedia);
		createdSubjectChapterMedia.setMediaSourceURL(mediaSourceURL);
		
		response.setCreatedSubjectChapterMedia(createdSubjectChapterMedia);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected SubjectChapterMedia subjectChapterMedia;
		protected FileData mediaSourceFileData;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected SubjectProfile.SubjectChapterMedia createdSubjectChapterMedia;
	}
}