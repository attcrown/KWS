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
public class StudentCourseQuotaUpdateCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaStudentCourseQuotaRepository studentCourseQuotaRepository;
	
	
	public Response updateStudentCourseQuota(Request request)
	{
		logger.info("### {}.updateStudentCourseQuota ###", getClass().getSimpleName());
		
		StudentCourseQuota studentCourseQuota = request.getStudentCourseQuota();
		
		if(studentCourseQuotaRepository.findById(studentCourseQuota.getId()).isEmpty()) {
			throw new KwsRuntimeException("StudentCourseQuota {id: "+studentCourseQuota.getId()+"} Not Found");
		}
		
		studentCourseQuota.setLastModifiedBy(request.getPerformedBy());
		studentCourseQuota.setLastModifiedAt(request.getPerformedAt());
		
		StudentCourseQuota updatedStudentCourseQuota = studentCourseQuotaRepository.save(studentCourseQuota);
		logger.info("Update StudentCourseQuota => {}", updatedStudentCourseQuota);
		
		Response response = new Response();
		response.setUpdatedStudentCourseQuota(updatedStudentCourseQuota);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected StudentCourseQuota studentCourseQuota;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected StudentCourseQuota updatedStudentCourseQuota;
	}
}