package com.otc.kws.fishsix.lib.domain.service.subject;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.model.FileData;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.Subject;
import com.otc.kws.fishsix.lib.domain.model.SubjectProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectChapterMediaRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectChapterRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectRepository;
import com.otc.kws.fishsix.lib.domain.service.subject.file.FishsixSubjectChapterMediaFileService;
import com.otc.kws.fishsix.lib.domain.service.subject.file.FishsixSubjectThumbnailImageService;

import lombok.Data;

@Component
public class FishsixSubjectCreateCommand extends BaseKwsCommand
{
	@Autowired
	protected FishsixSubjectService subjectService;
	
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
	
	
	public Response createSubject(Request request)
	{
		logger.info("### {}.createSubject ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		Subject subject = request.getSubject();
		if(subject.getId() == null) {
			subject.setId(UUID.randomUUID().toString());
		}
		if(request.getThumbnailImageFileData() != null) {
			String fileURI = subjectThumbnailImageService.putFile(req -> {
				req.copyFrom(request);
				req.setSubjectId(subject.getId());
				req.setThumbnailImageFileData(request.getThumbnailImageFileData());
			}).getFileURI();
			
			subject.setThumbnailImageFileURI(fileURI);
		}
		if(subject.getStatus() == null) {
			subject.setStatus(MasterStatusValue.Active);
		}
		subject.setCreatedBy(request.getPerformedBy());
		subject.setCreatedAt(request.getPerformedAt());
		subject.setLastModifiedBy(request.getPerformedBy());
		subject.setLastModifiedAt(request.getPerformedAt());
		subjectRepository.save(subject);
		logger.info("create subject => {}", subject);
		
		SubjectProfile createdSubjectProfile = subjectService.buildSubjectProfile(req -> {
			req.copyFrom(request);
			req.setSubjectId(subject.getId());
		}).getSubjectProfile();
		
		response.setCreatedSubjectProfile(createdSubjectProfile);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected Subject subject;
		protected FileData thumbnailImageFileData;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected SubjectProfile createdSubjectProfile;
	}
}