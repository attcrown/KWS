package com.otc.kws.fishsix.lib.domain.service.teacher_register;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.TeacherRegister;
import com.otc.kws.fishsix.lib.domain.entity.TeacherRegister.RegisterStatus;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaTeacherRegisterRepository;

import lombok.Data;

@Component
public class FishsixTeacherRegisterCreateCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaTeacherRegisterRepository teacherRegisterRepository;
	
	
	public Response create(Request request)
	{
		logger.info("### {}.create ###", getClass().getSimpleName());
		
		request.getTeacherRegister().setId(UUID.randomUUID().toString());
		if(request.getTeacherRegister().getRegisterStatus() == null) {
			request.getTeacherRegister().setRegisterStatus(RegisterStatus.Pending);
		}
		request.getTeacherRegister().setRegisteredAt(request.getPerformedAt());
		
		TeacherRegister createdTeacherRegister = teacherRegisterRepository.save(request.getTeacherRegister());
		logger.info("Create teacherRegister => {}", createdTeacherRegister);
		
		Response response = new Response();
		response.setCreatedTeacherRegister(createdTeacherRegister);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected TeacherRegister teacherRegister;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected TeacherRegister createdTeacherRegister;
	}
}