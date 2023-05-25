package com.otc.kws.fishsix.lib.domain.service.student_register;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.StudentRegister;
import com.otc.kws.fishsix.lib.domain.entity.StudentRegister.RegisterStatus;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudentRegisterRepository;

import lombok.Data;

@Component
public class FishsixStudentRegisterCreateCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaStudentRegisterRepository studentRegisterRepository;
	
	
	public Response create(Request request)
	{
		logger.info("### {}.create ###", getClass().getSimpleName());
		
		request.getStudentRegister().setId(UUID.randomUUID().toString());
		if(request.getStudentRegister().getRegisterStatus() == null) {
			request.getStudentRegister().setRegisterStatus(RegisterStatus.Pending);
		}
		request.getStudentRegister().setRegisteredAt(request.getPerformedAt());
		
		StudentRegister createdStudentRegister = studentRegisterRepository.save(request.getStudentRegister());
		logger.info("Create studentRegister => {}", createdStudentRegister);
		
		Response response = new Response();
		response.setCreatedStudentRegister(createdStudentRegister);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected StudentRegister studentRegister;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected StudentRegister createdStudentRegister;
	}
}