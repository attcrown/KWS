package com.otc.kws.fishsix.lib.domain.service.subject.file;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.kws.core.domain.model.FileData;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.service.BaseKwsService;
import com.otc.kws.core.domain.service.filestore.FileStorePutFileCommand;
import com.otc.kws.core.domain.service.filestore.FileStoreService;
import com.otc.kws.fishsix.lib.domain.constant.KwsFishsixConst;
import com.otc.kws.fishsix.lib.domain.entity.SubjectChapterMedia;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectChapterMediaRepository;

import lombok.Data;

@Service
public class FishsixSubjectChapterMediaFileService extends BaseKwsService
{
	public static final String FILE_CATEGORY = "subject_chapter_media";
	public static final String FILE_GROUP = "media_source";
	
	
	@Autowired
	protected JpaSubjectChapterMediaRepository subjectChapterMediaRepository;
	
	@Autowired
	protected FileStoreService fileStoreService;
	
	
	// ****************************** getMediaSourceFileURL ****************************** //
	public GetMediaSourceFileURLResponse getMediaSourceFileURL(Consumer<GetMediaSourceFileURLRequest> consumer)
	{
		GetMediaSourceFileURLRequest request = new GetMediaSourceFileURLRequest();
		consumer.accept(request);
		return getMediaSourceFileURL(request);
	}
	
	public GetMediaSourceFileURLResponse getMediaSourceFileURL(GetMediaSourceFileURLRequest request)
	{
		logger.info("### {}.getMediaSourceFileURL ###", getClass().getSimpleName());
		
		GetMediaSourceFileURLResponse response = new GetMediaSourceFileURLResponse();
		
		SubjectChapterMedia media = request.getMedia();
		
		if(media == null) {
			media = subjectChapterMediaRepository.findById(request.getMediaId()).orElse(null);
		}
		
		String fileURI = media != null ? media.getMediaSourceURI() : null;
		
		if(fileURI != null) {
			String fileURL = fileStoreService.getURL(req -> {
				req.copyFrom(request);
				req.setFileURI(fileURI);
			}).getUrl();
			
			response.setFileURL(fileURL);
		}
		
		return response;
	}
	
	@Data
	public static class GetMediaSourceFileURLRequest extends BaseKwsCommandRequest
	{
		protected String mediaId;
		protected SubjectChapterMedia media;
	}
	
	@Data
	public static class GetMediaSourceFileURLResponse extends BaseKwsCommandResponse
	{
		protected String fileURL;
	}
	// ****************************** getMediaSourceFileURL ****************************** //
	
	
	// ****************************** putMediaSourceFile ****************************** //
	@Transactional
	public PutMediaSourceFileURLResponse putMediaSourceFile(Consumer<PutMediaSourceFileURLRequest> consumer)
	{
		PutMediaSourceFileURLRequest request = new PutMediaSourceFileURLRequest();
		consumer.accept(request);
		return putMediaSourceFile(request);
	}
	
	@Transactional
	public PutMediaSourceFileURLResponse putMediaSourceFile(PutMediaSourceFileURLRequest request)
	{
		logger.info("### {}.putMediaSourceFile ###", getClass().getSimpleName());
		
		PutMediaSourceFileURLResponse response = new PutMediaSourceFileURLResponse();
		
		FileStorePutFileCommand.Response putFileResponse = fileStoreService.putFile(req -> {
			req.copyFrom(request);
			
			Map<String, String> variables = new HashMap<>();
			variables.put("subject_id", request.getSubjectId());
			variables.put("subject_chapter_id", request.getSubjectChapterId());
			variables.put("subject_chapter_media_id", request.getSubjectChapterMediaId());
			
			req.setModule(KwsFishsixConst.MODULE_NAME);
			req.setFileCategory(FILE_CATEGORY);
			req.setFileGroup(FILE_GROUP);
			req.setSeqNo(1);
			req.setVariables(variables);
			req.setFileData(request.getMediaSourceFileData());
		});
		
		response.setFileURI(putFileResponse.getUri());
		response.setFileURL(putFileResponse.getUrl());
		
		return response;
	}
	
	@Data
	public static class PutMediaSourceFileURLRequest extends BaseKwsCommandRequest
	{
		protected String subjectId;
		protected String subjectChapterId;
		protected String subjectChapterMediaId;
		protected FileData mediaSourceFileData;
	}
	
	@Data
	public static class PutMediaSourceFileURLResponse extends BaseKwsCommandResponse
	{
		protected String fileURI;
		protected String fileURL;
	}
	// ****************************** putMediaSourceFile ****************************** //
}