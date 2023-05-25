package com.otc.kws.fishsix.lib.domain.service.teacher_register;

import java.util.List;

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
public class FishsixTeacherRegisterSearchCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaTeacherRegisterRepository teacherRegisterRepository;
	
	
	public Response search(Request request)
	{
		logger.info("### {}.search ###", getClass().getSimpleName());
		
		Response response = new Response();
		response.setTeacherRegisters(teacherRegisterRepository.findByRegisterStatus(RegisterStatus.Pending));
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected List<TeacherRegister> teacherRegisters;
	}
}