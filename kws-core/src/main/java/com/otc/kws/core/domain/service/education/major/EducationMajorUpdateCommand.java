package com.otc.kws.core.domain.service.education.major;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.entity.EducationMajor;
import com.otc.kws.core.domain.exception.EntityNotFoundException;
import com.otc.kws.core.domain.repository.jpa.JpaEducationMajorRepository;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;

import lombok.Data;

@Component
public class EducationMajorUpdateCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaEducationMajorRepository educationMajorRepository;
	
	
	public Response update(Request request) throws EntityNotFoundException
	{
		String educationMajorId = request.getEducationMajorId();
		EducationMajor educationMajor = request.getEducationMajor();
		
		if(educationMajor.getId() == null) {
			educationMajor.setId(educationMajorId);
		}
		
		if(educationMajorId == null || educationMajorId.trim().isEmpty()) {
			throw new EntityNotFoundException("EducationMajor id ["+educationMajorId+"] Not Found");
		}
		
		if(educationMajorRepository.findById(educationMajorId).isEmpty()) {
			throw new EntityNotFoundException("EducationMajor id ["+educationMajorId+"] Not Found");
		}
		
		educationMajor.setLastModifiedBy(request.getPerformedBy());
		educationMajor.setLastModifiedAt(request.getPerformedAt());
		
		EducationMajor updatedEducationMajor = educationMajorRepository.save(educationMajor);
		logger.info("update educationMajor ["+updatedEducationMajor+"]");
		
		Response response = new Response();
		response.setUpdatedEducationMajor(updatedEducationMajor);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String educationMajorId;
		protected EducationMajor educationMajor;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected EducationMajor updatedEducationMajor;
	}
}