package com.otc.kws.core.domain.service.education.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.entity.School;
import com.otc.kws.core.domain.exception.EntityNotFoundException;
import com.otc.kws.core.domain.repository.jpa.JpaSchoolRepository;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;

import lombok.Data;

@Component
public class SchoolRemoveCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaSchoolRepository schoolRepository;
	
	
	public Response remove(Request request) throws EntityNotFoundException
	{
		School removedSchool = schoolRepository.findById(request.getSchoolId()).orElse(null);
		
		if(removedSchool == null) {
			throw new EntityNotFoundException("School id ["+request.getSchoolId()+"] Not Found");
		}
		
		schoolRepository.deleteById(request.getSchoolId());
		logger.info("remove school ["+removedSchool+"]");
		
		Response response = new Response();
		response.setRemovedSchool(removedSchool);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String schoolId;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected School removedSchool;
	}
}