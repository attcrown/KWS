package com.otc.kws.fishsix.lib.domain.service.student_register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.StudentRegister;
import com.otc.kws.fishsix.lib.domain.entity.StudentRegister.RegisterStatus;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudentRegisterRepository;

import lombok.Data;

@Component
public class FishsixStudentRegisterRejectCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaStudentRegisterRepository studentRegisterRepository;
	
	
	public Response reject(Request request)
	{
		logger.info("### {}.reject ###", getClass().getSimpleName());
		
		StudentRegister _studentRegister = studentRegisterRepository.findById(request.getStudentRegister().getId()).orElse(null);
		
		if(_studentRegister == null) {
			throw new KwsRuntimeException("Student register not found");
		}
		
		if(_studentRegister.getRegisterStatus() != RegisterStatus.Pending) {
			throw new KwsRuntimeException("Cannot reject student register which register status is not 'Pending'");
		}
		
		request.getStudentRegister().setRegisterStatus(RegisterStatus.Rejected);
		request.getStudentRegister().setRejectedBy(request.getPerformedBy());
		request.getStudentRegister().setRejectedAt(request.getPerformedAt());
		
		StudentRegister rejectedStudentRegister = studentRegisterRepository.save(request.getStudentRegister());
		logger.info("Update studentRegister => {}", rejectedStudentRegister);
		
		Response response = new Response();
		response.setRejectedStudentRegister(rejectedStudentRegister);
		
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
		protected StudentRegister rejectedStudentRegister;
	}
}