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
public class EducationMajorRemoveCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaEducationMajorRepository educationMajorRepository;
	
	
	public Response remove(Request request) throws EntityNotFoundException
	{
		EducationMajor removedEducationMajor = educationMajorRepository.findById(request.getEducationMajorId()).orElse(null);
		
		if(removedEducationMajor == null) {
			throw new EntityNotFoundException("EducationMajor id ["+request.getEducationMajorId()+"] Not Found");
		}
		
		educationMajorRepository.deleteById(request.getEducationMajorId());
		logger.info("remove EducationMajor ["+removedEducationMajor+"]");
		
		Response response = new Response();
		response.setRemovedEducationMajor(removedEducationMajor);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String educationMajorId;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected EducationMajor removedEducationMajor;
	}
}