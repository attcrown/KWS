package com.otc.kws.fishsix.lib.domain.service.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.model.FileData;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.Subject;
import com.otc.kws.fishsix.lib.domain.model.SubjectProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectRepository;
import com.otc.kws.fishsix.lib.domain.service.subject.file.FishsixSubjectThumbnailImageService;

import lombok.Data;

@Component
public class FishsixSubjectUpdateCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaSubjectRepository subjectRepository;
	
	@Autowired
	protected FishsixSubjectService subjectService;
	
	@Autowired
	protected FishsixSubjectThumbnailImageService subjectThumbnailImageService;
	
	
	public Response updateSubject(Request request)
	{
		logger.info("### {}.updateSubject ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		Subject subject = request.getSubject();
		
		Subject oldSubject = subjectRepository.findById(subject.getId()).orElse(null);
		if(oldSubject == null) {
			throw new KwsRuntimeException("Subject not found");
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
			subject.setStatus(oldSubject.getStatus());
		}
		
		if(subject.getCreatedBy() == null) {
			subject.setCreatedBy(oldSubject.getCreatedBy());
		}
		
		if(subject.getCreatedAt() == null) {
			subject.setCreatedAt(oldSubject.getCreatedAt());
		}
		
		subject.setLastModifiedBy(request.getPerformedBy());
		subject.setLastModifiedAt(request.getPerformedAt());
		subjectRepository.save(subject);
		logger.info("update subject => {}", subject);
		
		SubjectProfile updatedSubjectProfile = subjectService.buildSubjectProfile(req -> {
			req.copyFrom(request);
			req.setSubjectId(subject.getId());
		}).getSubjectProfile();
		
		response.setUpdatedSubjectProfile(updatedSubjectProfile);
		
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
		protected SubjectProfile updatedSubjectProfile;
	}
}