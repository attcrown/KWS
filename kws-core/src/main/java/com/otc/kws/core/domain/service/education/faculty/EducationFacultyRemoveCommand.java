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
public class EducationFacultyRemoveCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaEducationFacultyRepository educationFacultyRepository;
	
	
	public Response remove(Request request) throws EntityNotFoundException
	{
		EducationFaculty removedEducationFaculty = educationFacultyRepository.findById(request.getEducationFacultyId()).orElse(null);
		
		if(removedEducationFaculty == null) {
			throw new EntityNotFoundException("EducationFaculty id ["+request.getEducationFacultyId()+"] Not Found");
		}
		
		educationFacultyRepository.deleteById(request.getEducationFacultyId());
		logger.info("remove EducationFaculty ["+removedEducationFaculty+"]");
		
		Response response = new Response();
		response.setRemovedEducationFaculty(removedEducationFaculty);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String educationFacultyId;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected EducationFaculty removedEducationFaculty;
	}
}