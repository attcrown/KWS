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
import com.otc.kws.fishsix.lib.domain.entity.Subject;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectRepository;

import lombok.Data;

@Service
public class FishsixSubjectThumbnailImageService extends BaseKwsService
{
	public static final String FILE_CATEGORY = "subject";
	public static final String FILE_GROUP = "thumbnail_image";
	
	
	@Autowired
	protected JpaSubjectRepository subjectRepository;
	
	@Autowired
	protected FileStoreService fileStoreService;
	
	
	// ****************************** getFileURL ****************************** //
	public GetFileURLResponse getFileURL(Consumer<GetFileURLRequest> consumer)
	{
		GetFileURLRequest request = new GetFileURLRequest();
		consumer.accept(request);
		return getFileURL(request);
	}
	
	public GetFileURLResponse getFileURL(GetFileURLRequest request)
	{
		logger.info("### {}.getFileURL ###", getClass().getSimpleName());
		
		GetFileURLResponse response = new GetFileURLResponse();
		
		Subject subject = request.getSubject();
		
		if(subject == null) {
			subject = subjectRepository.findById(request.getSubjectId()).orElse(null);
		}
		
		String fileURI = subject != null ? subject.getThumbnailImageFileURI() : null;
		
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
	public static class GetFileURLRequest extends BaseKwsCommandRequest
	{
		protected String subjectId;
		protected Subject subject;
	}
	
	@Data
	public static class GetFileURLResponse extends BaseKwsCommandResponse
	{
		protected String fileURL;
	}
	// ****************************** getFileURL ****************************** //
	
	
	
	// ****************************** putFile ****************************** //
	@Transactional
	public PutFileResponse putFile(Consumer<PutFileRequest> consumer)
	{
		PutFileRequest request = new PutFileRequest();
		consumer.accept(request);
		return putFile(request);
	}
	
	@Transactional
	public PutFileResponse putFile(PutFileRequest request)
	{
		logger.info("### {}.putFile ###", getClass().getSimpleName());
		
		PutFileResponse response = new PutFileResponse();
		
		FileStorePutFileCommand.Response putFileResponse = fileStoreService.putFile(req -> {
			req.copyFrom(request);
			
			Map<String, String> variables = new HashMap<>();
			variables.put("subject_id", request.getSubjectId());
			
			req.setModule(KwsFishsixConst.MODULE_NAME);
			req.setFileCategory(FILE_CATEGORY);
			req.setFileGroup(FILE_GROUP);
			req.setSeqNo(1);
			req.setVariables(variables);
			req.setFileData(request.getThumbnailImageFileData());
		});
		
		response.setFileURI(putFileResponse.getUri());
		response.setFileURL(putFileResponse.getUrl());
		
		return response;
	}
	
	@Data
	public static class PutFileRequest extends BaseKwsCommandRequest
	{
		protected String subjectId;
		protected FileData thumbnailImageFileData;
	}
	
	@Data
	public static class PutFileResponse extends BaseKwsCommandResponse
	{
		protected String fileURI;
		protected String fileURL;
	}
	// ****************************** putFile ****************************** //
}