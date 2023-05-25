package com.otc.kws.fishsix.lib.domain.service.student.course_quota;

import java.util.UUID;

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
public class StudentCourseQuotaCreateCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaStudentCourseQuotaRepository studentCourseQuotaRepository;
	
	
	public Response createStudentCourseQuota(Request request)
	{
		logger.info("### {}.createStudentCourseQuota ###", getClass().getSimpleName());
		
		StudentCourseQuota studentCourseQuota = request.getStudentCourseQuota();
		
		if(studentCourseQuota.getId() != null && studentCourseQuotaRepository.findById(studentCourseQuota.getId()).isPresent()) {
			throw new KwsRuntimeException("Duplicate student course quota {id: "+studentCourseQuota.getId()+"}");
		}
		
		if(studentCourseQuota.getId() == null) {
			studentCourseQuota.setId(UUID.randomUUID().toString());
		}
		
		studentCourseQuota.setCreatedBy(request.getPerformedBy());
		studentCourseQuota.setCreatedAt(request.getPerformedAt());
		
		StudentCourseQuota createdStudentCourseQuota = studentCourseQuotaRepository.save(studentCourseQuota);
		logger.info("Create StudentCourseQuota => {}", createdStudentCourseQuota);
		
		Response response = new Response();
		response.setCreatedStudentCourseQuota(createdStudentCourseQuota);
		
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
		protected StudentCourseQuota createdStudentCourseQuota;
	}
}