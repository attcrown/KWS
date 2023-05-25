package com.otc.kws.fishsix.lib.domain.service.student.course_quota;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.StudentCourseQuota;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudentCourseQuotaRepository;

import lombok.Data;

@Component
public class StudentCourseQuotaRemoveCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaStudentCourseQuotaRepository studentCourseQuotaRepository;
	
	
	public Response removeStudentCourseQuota(Request request)
	{
		logger.info("### {}.removeStudentCourseQuota ###", getClass().getSimpleName());
		
		StudentCourseQuota removedStudentCourseQuota = studentCourseQuotaRepository.findById(request.getStudentCourseQuotaId()).orElse(null);
		
		if(removedStudentCourseQuota == null) {
			throw new KwsRuntimeException("StudentCourseQuota {id: "+request.getStudentCourseQuotaId()+"} Not Found");
		}
		
		studentCourseQuotaRepository.delete(removedStudentCourseQuota);
		logger.info("Remove StudentCourseQuota => {}", removedStudentCourseQuota);
		
		Response response = new Response();
		response.setRemovedCourseQuota(removedStudentCourseQuota);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String studentCourseQuotaId;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected StudentCourseQuota removedCourseQuota;
	}
}