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
public class SchoolUpdateCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaSchoolRepository schoolRepository;
	
	
	public Response update(Request request) throws EntityNotFoundException
	{
		String schoolId = request.getSchoolId();
		School school = request.getSchool();
		
		if(school.getId() == null) {
			school.setId(schoolId);
		}
		
		if(schoolId == null || schoolId.trim().isEmpty()) {
			throw new EntityNotFoundException("School id ["+schoolId+"] Not Found");
		}
		
		if(schoolRepository.findById(schoolId).isEmpty()) {
			throw new EntityNotFoundException("School id ["+schoolId+"] Not Found");
		}
		
		school.setLastModifiedBy(request.getPerformedBy());
		school.setLastModifiedAt(request.getPerformedAt());
		
		School updatedSchool = schoolRepository.save(school);
		logger.info("update school ["+updatedSchool+"]");
		
		Response response = new Response();
		response.setUpdatedSchool(updatedSchool);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String schoolId;
		protected School school;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected School updatedSchool;
	}
}