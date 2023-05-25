package com.otc.kws.core.domain.service.education.faculty;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.constant.value.SchoolTypeValue;
import com.otc.kws.core.domain.entity.EducationFaculty;
import com.otc.kws.core.domain.exception.DuplicateEntityException;
import com.otc.kws.core.domain.repository.jpa.JpaEducationFacultyRepository;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;

import lombok.Data;

@Component
public class EducationFacultyCreateCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaEducationFacultyRepository educationFacultyRepository;
	
	
	public Response create(Request request) throws DuplicateEntityException
	{
		if(request.getEducationFaculty() == null) {
			throw new NullPointerException("EducationFaculty is null");
		}
		
		if(request.getEducationFaculty().getId() == null) {
			request.getEducationFaculty().setId(UUID.randomUUID().toString());
		}
		
		if(educationFacultyRepository.findById(request.getEducationFaculty().getId()).isPresent()) {
			throw new DuplicateEntityException("Duplicate EducationFaculty id ["+request.getEducationFaculty().getId()+"]");
		}
		
		if(request.getEducationFaculty().getSchoolTypeId() == null) {
			request.getEducationFaculty().setSchoolTypeId(SchoolTypeValue.University.name());
		}
		if(request.getEducationFaculty().getStatus() == null) {
			request.getEducationFaculty().setStatus(MasterStatusValue.Active);
		}
		request.getEducationFaculty().setCreatedBy(request.getPerformedBy());
		request.getEducationFaculty().setCreatedAt(request.getPerformedAt());
		
		EducationFaculty createdEducationFaculty = educationFacultyRepository.save(request.getEducationFaculty());
		logger.info("insert educationFaculty ["+createdEducationFaculty+"]");
		
		Response response = new Response();
		response.setCreatedEducationFaculty(createdEducationFaculty);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected EducationFaculty educationFaculty;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected EducationFaculty createdEducationFaculty;
	}
}