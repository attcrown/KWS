package com.otc.kws.core.domain.service.education.major;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.constant.value.SchoolTypeValue;
import com.otc.kws.core.domain.entity.EducationMajor;
import com.otc.kws.core.domain.exception.DuplicateEntityException;
import com.otc.kws.core.domain.repository.jpa.JpaEducationMajorRepository;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;

import lombok.Data;

@Component
public class EducationMajorCreateCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaEducationMajorRepository educationMajorRepository;
	
	
	public Response create(Request request) throws DuplicateEntityException
	{
		if(request.getEducationMajor() == null) {
			throw new NullPointerException("EducationMajor is null");
		}
		
		if(request.getEducationMajor().getId() == null) {
			request.getEducationMajor().setId(UUID.randomUUID().toString());
		}
		
		if(educationMajorRepository.findById(request.getEducationMajor().getId()).isPresent()) {
			throw new DuplicateEntityException("Duplicate EducationMajor id ["+request.getEducationMajor().getId()+"]");
		}
		
		if(request.getEducationMajor().getSchoolTypeId() == null) {
			request.getEducationMajor().setSchoolTypeId(SchoolTypeValue.University.name());
		}
		if(request.getEducationMajor().getStatus() == null) {
			request.getEducationMajor().setStatus(MasterStatusValue.Active);
		}
		request.getEducationMajor().setCreatedBy(request.getPerformedBy());
		request.getEducationMajor().setCreatedAt(request.getPerformedAt());
		
		EducationMajor createdEducationMajor = educationMajorRepository.save(request.getEducationMajor());
		logger.info("insert educationMajor ["+createdEducationMajor+"]");
		
		Response response = new Response();
		response.setCreatedEducationMajor(createdEducationMajor);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected EducationMajor educationMajor;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected EducationMajor createdEducationMajor;
	}
}