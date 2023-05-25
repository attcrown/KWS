package com.otc.kws.fishsix.lib.domain.service.teacher_register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.TeacherRegister;
import com.otc.kws.fishsix.lib.domain.entity.TeacherRegister.RegisterStatus;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaTeacherRegisterRepository;

import lombok.Data;

@Component
public class FishsixTeacherRegisterRejectCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaTeacherRegisterRepository teacherRegisterRepository;
	
	
	public Response reject(Request request)
	{
		logger.info("### {}.reject ###", getClass().getSimpleName());
		
		TeacherRegister _teacherRegister = teacherRegisterRepository.findById(request.getTeacherRegister().getId()).orElse(null);
		
		if(_teacherRegister == null) {
			throw new KwsRuntimeException("Teacher register not found");
		}
		
		if(_teacherRegister.getRegisterStatus() != RegisterStatus.Pending) {
			throw new KwsRuntimeException("Cannot reject teacher register which register status is not 'Pending'");
		}
		
		request.getTeacherRegister().setRegisterStatus(RegisterStatus.Rejected);
		request.getTeacherRegister().setRejectedBy(request.getPerformedBy());
		request.getTeacherRegister().setRejectedAt(request.getPerformedAt());
		
		TeacherRegister rejectedTeacherRegister = teacherRegisterRepository.save(request.getTeacherRegister());
		logger.info("Reject teacherRegister => {}", rejectedTeacherRegister);
		
		Response response = new Response();
		response.setRejectedTeacherRegister(rejectedTeacherRegister);
		
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
		protected TeacherRegister rejectedTeacherRegister;
	}
}