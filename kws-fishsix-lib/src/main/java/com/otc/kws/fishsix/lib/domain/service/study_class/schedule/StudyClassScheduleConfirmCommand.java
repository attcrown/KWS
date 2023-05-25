package com.otc.kws.fishsix.lib.domain.service.study_class.schedule;

import java.util.ArrayList;
import java.util.List;

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
public class StudyClassScheduleConfirmCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaStudyClassRepository studyClassRepository;
	
	@Autowired
	protected JpaStudyClassScheduleRepository studyClassScheduleRepository;
	
	@Autowired
	protected JpaStudentCourseQuotaRepository studentCourseQuotaRepository;
	
	
	public Response confirmStudyClassSchedule(Request request)
	{
		logger.info("### {}.confirmStudyClassSchedule ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		List<StudyClassSchedule> studyClassSchedules = studyClassScheduleRepository.findAllById(request.getClassScheduleIds());
		
		if(studyClassSchedules != null && !studyClassSchedules.isEmpty()) {
			StudyClass studyClass = studyClassRepository.findById(studyClassSchedules.get(0).getClassId()).orElse(null);
			
			if(studyClass == null) {
				throw new KwsRuntimeException("StudyClass Not Found");
			}
			
			List<StudyClassSchedule> updatedStudyClassSchedules = new ArrayList<>();
			List<StudentCourseQuota> updatedStudentCourseQuotas = new ArrayList<>();
			
			for(StudyClassSchedule studyClassSchedule : studyClassSchedules) {
				if(studyClassSchedule.getStudentQuotaStatus() == StudentQuotaStatus.Returned) {
					StudentCourseQuota studentCourseQuota = studentCourseQuotaRepository.findById(studyClassSchedule.getStudentCourseQuotaId()).orElse(null);
					
					if(studentCourseQuota == null) {
						throw new KwsRuntimeException("StudentCourseQuota with id = " + studyClassSchedule.getStudentCourseQuotaId() + " Not Found");
					}
					
					if(request.getPerformedAt().getTime() < studentCourseQuota.getActivatedAt().getTime()) {
						throw new KwsRuntimeException("StudentCourseQuota with id = " + studyClassSchedule.getStudentCourseQuotaId() + " Not Activated Yet");
					}
					
					int remainHour = studentCourseQuota.getInitialHour() - studentCourseQuota.getUsedHour();
					if(remainHour - studyClass.getClassHour() <= 0) {
						throw new KwsRuntimeException("StudentCourseQuota with id = " + studyClassSchedule.getStudentCourseQuotaId() + " Exceed Limit Quota");
					}
					
					studentCourseQuota.setUsedHour(studentCourseQuota.getUsedHour() + studyClass.getClassHour());
					studentCourseQuota.setLastModifiedBy(request.getPerformedBy());
					studentCourseQuota.setLastModifiedAt(request.getPerformedAt());
					updatedStudentCourseQuotas.add(studentCourseQuota);
				} 
				
				studyClassSchedule.setClassStatus(ClassStatus.Confirmed);
				studyClassSchedule.setClassConfirmedBy(request.getPerformedBy());
				studyClassSchedule.setClassConfirmedAt(request.getPerformedAt());
				studyClassSchedule.setStudentQuotaStatus(StudentQuotaStatus.Reserved);
				studyClassSchedule.setLastModifiedBy(request.getPerformedBy());
				studyClassSchedule.setLastModifiedAt(request.getPerformedAt());
				updatedStudyClassSchedules.add(studyClassSchedule);
			}
			
			studyClassScheduleRepository.saveAll(updatedStudyClassSchedules);
			logger.info("Update studyClassSchedules => {}", updatedStudyClassSchedules);
			response.setUpdatedStudyClassSchedules(updatedStudyClassSchedules);
			
			if(! updatedStudentCourseQuotas.isEmpty()) {
				studentCourseQuotaRepository.saveAll(updatedStudentCourseQuotas);
				logger.info("Update studentCourseQuotas => {}", updatedStudentCourseQuotas);
			}
			response.setUpdatedStudentCourseQuotas(updatedStudentCourseQuotas);
		}
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected List<String> classScheduleIds;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
    	protected List<StudyClassSchedule> updatedStudyClassSchedules;
		protected List<StudentCourseQuota> updatedStudentCourseQuotas;
	}
}