package com.otc.kws.fishsix.lib.domain.service.subject.chapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.model.FileData;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.SubjectChapter;
import com.otc.kws.fishsix.lib.domain.model.SubjectProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectChapterRepository;
import com.otc.kws.fishsix.lib.domain.service.subject.file.FishsixSubjectChapterThumbnailImageService;

import lombok.Data;

@Component
public class FishsixSubjectChapterUpdateCommand extends BaseKwsCommand
{
	@Autowired
	protected FishsixSubjectChapterThumbnailImageService thumbnailImageService;
	
	@Autowired
	protected JpaSubjectChapterRepository subjectChapterRepository;
	
	
	public Response updateSubjectChapter(Request request)
	{
		logger.info("### {}.updateSubjectChapter ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		SubjectChapter subjectChapter = request.getSubjectChapter();
		
		if(subjectChapter == null) {
			throw new KwsRuntimeException("SubjectChapter is null");
		}
		
		SubjectChapter oldSubjectChapter = subjectChapterRepository.findById(subjectChapter.getId()).orElse(null);
		
		if(oldSubjectChapter == null) {
			throw new KwsRuntimeException("SubjectChapter not found");
		}
		
		if(subjectChapter.getStatus() == null) {
			subjectChapter.setStatus(oldSubjectChapter.getStatus());
		}
		
		if(subjectChapter.getCreatedBy() == null) {
			subjectChapter.setCreatedBy(oldSubjectChapter.getCreatedBy());
		}
		
		if(subjectChapter.getCreatedAt() == null) {
			subjectChapter.setCreatedAt(oldSubjectChapter.getCreatedAt());
		}
		
		subjectChapter.setLastModifiedBy(request.getPerformedBy());
		subjectChapter.setLastModifiedAt(request.getPerformedAt());
		
		String thumbnailImageFileURL = null;
		
		if(request.getThumbnailImageFileData() != null) {
			FishsixSubjectChapterThumbnailImageService.PutFileResponse putFileResponse = thumbnailImageService.putFile(req -> {
				req.copyFrom(request);
				req.setSubjectChapterId(subjectChapter.getId());
				req.setSubjectId(subjectChapter.getSubjectId());
				req.setThumbnailImageFileData(request.getThumbnailImageFileData());
			});
			
			subjectChapter.setThumbnailImageFileURI(putFileResponse.getFileURI());
			
			thumbnailImageFileURL = putFileResponse.getFileURL();
		}
		
		subjectChapterRepository.save(subjectChapter);
		logger.info("update subjectChapter => {}", subjectChapter);
		
		SubjectProfile.SubjectChapter updatedSubjectChapter = SubjectProfile.transform(subjectChapter);
		updatedSubjectChapter.setThumbnailImageFileURL(thumbnailImageFileURL);
		
		response.setUpdatedSubjectChapter(updatedSubjectChapter);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected SubjectChapter subjectChapter;
		protected FileData thumbnailImageFileData;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected SubjectProfile.SubjectChapter updatedSubjectChapter;
	}
}