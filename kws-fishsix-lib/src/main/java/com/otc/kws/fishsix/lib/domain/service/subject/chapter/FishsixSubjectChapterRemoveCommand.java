package com.otc.kws.fishsix.lib.domain.service.subject.chapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.service.filestore.FileStoreService;
import com.otc.kws.fishsix.lib.domain.entity.SubjectChapter;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectChapterRepository;
import com.otc.kws.fishsix.lib.domain.service.subject.FishsixSubjectService;
import com.otc.kws.fishsix.lib.domain.service.subject.file.FishsixSubjectChapterThumbnailImageService;

import lombok.Data;

@Component
public class FishsixSubjectChapterRemoveCommand extends BaseKwsCommand
{
	@Autowired
	protected FishsixSubjectService subjectService;
	
	@Autowired
	protected FishsixSubjectChapterThumbnailImageService thumbnailImageService;
	
	@Autowired
	protected JpaSubjectChapterRepository subjectChapterRepository;
	
	@Autowired
	protected FileStoreService fileStoreService;
	
	
	public Response removeSubjectChapter(Request request)
	{
		logger.info("### {}.removeSubjectChapter ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		SubjectChapter subjectChapter = subjectChapterRepository.findById(request.getSubjectChapterId()).orElse(null);
		
		if(subjectChapter == null) {
			throw new KwsRuntimeException("SubjectChapter not found");
		}
		
		if(subjectChapter.getThumbnailImageFileURI() != null) {
			fileStoreService.removeFile(req -> {
				req.copyFrom(request);
				req.setFileURI(subjectChapter.getThumbnailImageFileURI());
			});
		}
		
		subjectChapterRepository.delete(subjectChapter);
		logger.info("remove subjectChapter => {}", subjectChapter);
		
		response.setRemovedSubjectChapter(subjectChapter);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String subjectChapterId;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected SubjectChapter removedSubjectChapter;
	}
}