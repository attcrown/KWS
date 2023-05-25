package com.otc.kws.core.domain.service.education.school;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.School;
import com.otc.kws.core.domain.exception.DuplicateEntityException;
import com.otc.kws.core.domain.repository.jpa.JpaSchoolRepository;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;

import lombok.Data;

@Component
public class SchoolCreateCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaSchoolRepository schoolRepository;
	
	
	public Response create(Request request) throws DuplicateEntityException
	{
		if(request.getSchool() == null) {
			throw new NullPointerException("School is null");
		}
		
		if(request.getSchool().getId() == null) {
			request.getSchool().setId(UUID.randomUUID().toString());
		}
		
		if(schoolRepository.findById(request.getSchool().getId()).isPresent()) {
			throw new DuplicateEntityException("Duplicate School id ["+request.getSchool().getId()+"]");
		}
		
		if(request.getSchool().getStatus() == null) {
			request.getSchool().setStatus(MasterStatusValue.Active);
		}
		request.getSchool().setCreatedBy(request.getPerformedBy());
		request.getSchool().setCreatedAt(request.getPerformedAt());
		
		School createdSchool = schoolRepository.save(request.getSchool());
		logger.info("insert school ["+createdSchool+"]");
		
		Response response = new Response();
		response.setCreatedSchool(createdSchool);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected School school;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected School createdSchool;
	}
}