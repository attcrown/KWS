package com.otc.kws.fishsix.lib.domain.service.subject.chapter.media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.exception.KwsRuntimeException;
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
public class FishsixSubjectChapterMediaUpdateCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaSubjectChapterMediaRepository subjectChapterMediaRepository;
	
	@Autowired
	protected FishsixSubjectChapterMediaFileService mediaFileService;
	
	
	public Response updateSubjectChapterMedia(Request request)
	{
		logger.info("### {}.updateSubjectChapterMedia ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		SubjectChapterMedia subjectChapterMedia = request.getSubjectChapterMedia();
		
		if(subjectChapterMedia == null) {
			throw new KwsRuntimeException("SubjectChapterMedia is null");
		}
		
		SubjectChapterMedia oldSubjectChapterMedia = subjectChapterMediaRepository.findById(subjectChapterMedia.getId()).orElse(null);
		
		if(oldSubjectChapterMedia == null) {
			throw new KwsRuntimeException("SubjectChapterMedia not found");
		}
		
		if(subjectChapterMedia.getStatus() == null) {
			subjectChapterMedia.setStatus(oldSubjectChapterMedia.getStatus());
		}
		
		if(subjectChapterMedia.getCreatedBy() == null) {
			subjectChapterMedia.setCreatedBy(oldSubjectChapterMedia.getCreatedBy());
		}
		
		if(subjectChapterMedia.getCreatedAt() == null) {
			subjectChapterMedia.setCreatedAt(oldSubjectChapterMedia.getCreatedAt());
		}
		
		subjectChapterMedia.setLastModifiedBy(request.getPerformedBy());
		subjectChapterMedia.setLastModifiedAt(request.getPerformedAt());
		
		String mediaSourceURL = subjectChapterMedia.getMediaSourceURI();
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
		logger.info("update subjectChapterMedia => {}", subjectChapterMedia);
		
		SubjectProfile.SubjectChapterMedia updatedSubjectChapterMedia = SubjectProfile.transform(subjectChapterMedia);
		updatedSubjectChapterMedia.setMediaSourceURL(mediaSourceURL);
		
		response.setUpdatedSubjectChapterMedia(updatedSubjectChapterMedia);
		
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
		protected SubjectProfile.SubjectChapterMedia updatedSubjectChapterMedia;
	}
}