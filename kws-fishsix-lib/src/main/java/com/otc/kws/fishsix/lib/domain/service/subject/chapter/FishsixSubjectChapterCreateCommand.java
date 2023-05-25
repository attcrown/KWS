package com.otc.kws.fishsix.lib.domain.service.subject.chapter;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
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
public class FishsixSubjectChapterCreateCommand extends BaseKwsCommand
{
	@Autowired
	protected FishsixSubjectChapterThumbnailImageService thumbnailImageService;
	
	@Autowired
	protected JpaSubjectChapterRepository subjectChapterRepository;
	
	
	public Response createSubjectChapter(Request request)
	{
		logger.info("### {}.createSubjectChapter ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		SubjectChapter subjectChapter = request.getSubjectChapter();
		
		if(subjectChapter.getId() == null) {
			subjectChapter.setId(UUID.randomUUID().toString());
		}
		if(subjectChapter.getStatus() == null) {
			subjectChapter.setStatus(MasterStatusValue.Active);
		}
		subjectChapter.setCreatedBy(request.getPerformedBy());
		subjectChapter.setCreatedAt(request.getPerformedAt());
		subjectChapter.setLastModifiedBy(request.getPerformedBy());
		subjectChapter.setLastModifiedAt(request.getPerformedAt());
		
		String thumbnailImageFileURL = null;
		if(request.getThumbnailImageFileData() != null) {
			FishsixSubjectChapterThumbnailImageService.PutFileResponse putFileResponse = thumbnailImageService.putFile(req -> {
				req.copyFrom(request);
				req.setSubjectId(subjectChapter.getSubjectId());
				req.setSubjectChapterId(subjectChapter.getId());
				req.setThumbnailImageFileData(request.getThumbnailImageFileData());
			});
			
			subjectChapter.setThumbnailImageFileURI(putFileResponse.getFileURI());
			thumbnailImageFileURL = putFileResponse.getFileURL();
		}
		
		subjectChapterRepository.save(subjectChapter);
		logger.info("### create subjectChapter => {}", subjectChapter);
		
		SubjectProfile.SubjectChapter _createdSubjectChapter = SubjectProfile.transform(subjectChapter);
		_createdSubjectChapter.setThumbnailImageFileURL(thumbnailImageFileURL);
		
		response.setCreatedSubjectChapter(_createdSubjectChapter);
		
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
		protected SubjectProfile.SubjectChapter createdSubjectChapter;
	}
}