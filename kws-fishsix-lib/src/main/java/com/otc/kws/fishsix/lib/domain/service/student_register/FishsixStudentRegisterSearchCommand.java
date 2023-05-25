package com.otc.kws.fishsix.lib.domain.service.student_register;

import java.util.List;

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
public class FishsixStudentRegisterSearchCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaStudentRegisterRepository studentRegisterRepository;
	
	
	public Response search(Request request)
	{
		logger.info("### {}.search ###", getClass().getSimpleName());
		
		Response response = new Response();
		response.setStudentRegisters(studentRegisterRepository.findByRegisterStatus(RegisterStatus.Pending));
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected List<StudentRegister> studentRegisters;
	}
}