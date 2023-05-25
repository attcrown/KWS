package com.otc.kws.fishsix.lib.domain.service.subject.chapter.media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.service.filestore.FileStoreService;
import com.otc.kws.fishsix.lib.domain.entity.SubjectChapterMedia;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectChapterMediaRepository;


import lombok.Data;

@Component
public class FishsixSubjectChapterMediaRemoveCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaSubjectChapterMediaRepository subjectChapterMediaRepository;
	
	@Autowired
	protected FileStoreService fileStoreService;
	
	
	public Response removeSubjectChapterMedia(Request request)
	{
		logger.info("### {}.removeSubjectChapterMedia ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		SubjectChapterMedia subjectChapterMedia = subjectChapterMediaRepository.findById(request.getSubjectChapterMediaId()).orElse(null);
		
		if(subjectChapterMedia == null) {
			throw new KwsRuntimeException("SubjectChapterMedia not found");
		}
		
		if(subjectChapterMedia.getMediaSourceURI() != null) {
			fileStoreService.removeFile(req -> {
				req.copyFrom(request);
				req.setFileURI(subjectChapterMedia.getMediaSourceURI());
			});
		}
		
		subjectChapterMediaRepository.delete(subjectChapterMedia);
		logger.info("remove subjectChapterMedia => {}", subjectChapterMedia);
		
		response.setRemovedSubjectChapterMedia(subjectChapterMedia);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String subjectChapterMediaId;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected SubjectChapterMedia removedSubjectChapterMedia;
	}
}