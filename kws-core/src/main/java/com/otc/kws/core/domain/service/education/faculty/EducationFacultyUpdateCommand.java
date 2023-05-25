package com.otc.kws.core.domain.service.education.faculty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.entity.EducationFaculty;
import com.otc.kws.core.domain.exception.EntityNotFoundException;
import com.otc.kws.core.domain.repository.jpa.JpaEducationFacultyRepository;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;

import lombok.Data;

@Component
public class EducationFacultyUpdateCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaEducationFacultyRepository educationFacultyRepository;
	
	
	public Response update(Request request) throws EntityNotFoundException
	{
		String educationFacultyId = request.getEducationFacultyId();
		EducationFaculty educationFaculty = request.getEducationFaculty();
		
		if(educationFaculty.getId() == null) {
			educationFaculty.setId(educationFacultyId);
		}
		
		if(educationFacultyId == null || educationFacultyId.trim().isEmpty()) {
			throw new EntityNotFoundException("EducationFaculty id ["+educationFacultyId+"] Not Found");
		}
		
		if(educationFacultyRepository.findById(educationFacultyId).isEmpty()) {
			throw new EntityNotFoundException("EducationFaculty id ["+educationFacultyId+"] Not Found");
		}
		
		educationFaculty.setLastModifiedBy(request.getPerformedBy());
		educationFaculty.setLastModifiedAt(request.getPerformedAt());
		
		EducationFaculty updatedEducationFaculty = educationFacultyRepository.save(educationFaculty);
		logger.info("update educationFaculty ["+updatedEducationFaculty+"]");
		
		Response response = new Response();
		response.setUpdatedEducationFaculty(updatedEducationFaculty);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String educationFacultyId;
		protected EducationFaculty educationFaculty;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected EducationFaculty updatedEducationFaculty;
	}
}