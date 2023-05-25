package com.otc.kws.fishsix.lib.domain.service.study_class.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.StudentCourseQuota;
import com.otc.kws.fishsix.lib.domain.entity.StudyClass;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule.ClassStatus;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule.StudentQuotaStatus;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudentCourseQuotaRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassScheduleRepository;

import lombok.Data;

@Component
public class StudyClassScheduleRemoveCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaStudyClassRepository studyClassRepository;
	
	@Autowired
	protected JpaStudyClassScheduleRepository studyClassScheduleRepository;
	
	@Autowired
	protected JpaStudentCourseQuotaRepository studentCourseQuotaRepository;
	
	
	public Response removeStudyClassSchedule(Request request)
	{
		logger.info("### {}.removeStudyClassSchedule ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		StudyClassSchedule studyClassSchedule = studyClassScheduleRepository.findById(request.getClassScheduleId()).orElse(null);
		
		if(studyClassSchedule == null) {
			throw new KwsRuntimeException("StudyClassSchedule {id: "+request.getClassScheduleId()+"} Not Found");
		}
		
		StudyClass studyClass = studyClassRepository.findById(studyClassSchedule.getClassId()).orElse(null);
		
		if(studyClass == null) {
			throw new KwsRuntimeException("StudyClass {id: "+studyClassSchedule.getClassId()+"} Not Found");
		}
		
		if(studyClassSchedule.getClassStatus() == ClassStatus.Completed) {
			throw new KwsRuntimeException("Invalid class status, Cannot remove studyClassSchedule which classStatus='Completed'");
		}
		
		studyClass.setReservedStudent(studyClass.getReservedStudent() - 1);
		if(studyClass.getReservedStudent() < 0) {
			studyClass.setReservedStudent(0);
		}
		studyClass.setLastModifiedBy(request.getPerformedBy());
		studyClass.setLastModifiedAt(request.getPerformedAt());
		studyClass = studyClassRepository.save(studyClass);
		logger.info("Update studyClass => {}", studyClass);
		response.setStudyClass(studyClass);
		
		studyClassScheduleRepository.delete(studyClassSchedule);
		logger.info("Remove studyClassSchedule => {}", studyClassSchedule);
		response.setRemovedStudyClassSchedule(studyClassSchedule);
		
		if(studyClassSchedule.getStudentQuotaStatus() != StudentQuotaStatus.Returned) {
			StudentCourseQuota studentCourseQuota = studentCourseQuotaRepository.findById(studyClassSchedule.getStudentCourseQuotaId()).orElse(null);
			
			if(studentCourseQuota == null) {
				throw new KwsRuntimeException("StudentCourseQuota with id = " + studyClassSchedule.getStudentCourseQuotaId() + " Not Found");
			}
			
			if(request.getPerformedAt().getTime() < studentCourseQuota.getActivatedAt().getTime()) {
				throw new KwsRuntimeException("StudentCourseQuota with id = " + studyClassSchedule.getStudentCourseQuotaId() + " Not Activated Yet");
			}
			
			/*
			if(request.getPerformedAt().getTime() > studentCourseQuota.getExpiredAt().getTime()) {
				throw new KwsRuntimeException("StudentCourseQuota with id = " + studyClassSchedule.getStudentCourseQuotaId() + " Expired");
			}
			*/
			
			studentCourseQuota.setUsedHour(studentCourseQuota.getUsedHour() - studyClass.getClassHour());
			if(studentCourseQuota.getUsedHour() < 0) {
				studentCourseQuota.setUsedHour(0);
			}
			studentCourseQuota.setLastModifiedBy(request.getPerformedBy());
			studentCourseQuota.setLastModifiedAt(request.getPerformedAt());
			
			studentCourseQuota = studentCourseQuotaRepository.save(studentCourseQuota);
			logger.info("Update studentCourseQuota => {}", studentCourseQuota);
			response.setUpdatedStudentCourseQuota(studentCourseQuota);
		}
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String classScheduleId;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected StudyClass studyClass;
    	protected StudyClassSchedule removedStudyClassSchedule;
		protected StudentCourseQuota updatedStudentCourseQuota;
	}
}