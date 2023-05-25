package com.otc.kws.fishsix.lib.domain.service.study_class.schedule;

import java.math.BigDecimal;

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
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule.StudentCheckinStatus;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule.StudentQuotaStatus;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule.TeacherQuotaStatus;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudentCourseQuotaRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassScheduleRepository;

import lombok.Data;

@Component
public class StudyClassScheduleCheckinCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaStudyClassRepository studyClassRepository;
	
	@Autowired
	protected JpaStudyClassScheduleRepository studyClassScheduleRepository;
	
	@Autowired
	protected JpaStudentCourseQuotaRepository studentCourseQuotaRepository;
	
	
	public Response checkinStudyClassSchedule(Request request)
	{
		logger.info("### {}.checkinStudyClassSchedule ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		StudyClassSchedule studyClassSchedule = studyClassScheduleRepository.findById(request.getClassScheduleId()).orElse(null);
		
		if(studyClassSchedule == null) {
			throw new KwsRuntimeException("StudyClassSchedule {id: "+request.getClassScheduleId()+"} Not Found");
		}
		
		StudyClass studyClass = studyClassRepository.findById(studyClassSchedule.getClassId()).orElse(null);
		
		if(studyClass == null) {
			throw new KwsRuntimeException("StudyClass {id: "+studyClassSchedule.getClassId()+"} Not Found");
		}
		
		StudentCourseQuota studentCourseQuota = studentCourseQuotaRepository.findById(studyClassSchedule.getStudentCourseQuotaId()).orElse(null);
		
		if(studentCourseQuota == null) {
			throw new KwsRuntimeException("StudentCourseQuota with id = " + studyClassSchedule.getStudentCourseQuotaId() + " Not Found");
		}
		
		if(request.getPerformedAt().getTime() < studentCourseQuota.getActivatedAt().getTime()) {
			throw new KwsRuntimeException("StudentCourseQuota with id = " + studyClassSchedule.getStudentCourseQuotaId() + " Not Activated Yet");
		}
		
		if(studyClassSchedule.getStudentQuotaStatus() != StudentQuotaStatus.Returned && request.getStudentQuotaStatus() == StudentQuotaStatus.Returned) {
			studentCourseQuota.setUsedHour(studentCourseQuota.getUsedHour() - studyClass.getClassHour());
			if(studentCourseQuota.getUsedHour() < 0) {
				studentCourseQuota.setUsedHour(0);
			}
			studentCourseQuota.setLastModifiedBy(request.getPerformedBy());
			studentCourseQuota.setLastModifiedAt(request.getPerformedAt());
			
			studentCourseQuota = studentCourseQuotaRepository.save(studentCourseQuota);
			logger.info("Update studentCourseQuota => {}", studentCourseQuota);
		} else if(studyClassSchedule.getStudentQuotaStatus() == StudentQuotaStatus.Returned && request.getStudentQuotaStatus() != StudentQuotaStatus.Returned) {
			int remainHour = studentCourseQuota.getInitialHour() - studentCourseQuota.getUsedHour();
			if(remainHour - studyClass.getClassHour() <= 0) {
				throw new KwsRuntimeException("StudentCourseQuota with id = " + studyClassSchedule.getStudentCourseQuotaId() + " Exceed Limit Quota");
			}
			
			studentCourseQuota.setUsedHour(studentCourseQuota.getUsedHour() + studyClass.getClassHour());
			studentCourseQuota.setLastModifiedBy(request.getPerformedBy());
			studentCourseQuota.setLastModifiedAt(request.getPerformedAt());
			
			studentCourseQuota = studentCourseQuotaRepository.save(studentCourseQuota);
			logger.info("Update studentCourseQuota => {}", studentCourseQuota);
		}

		if(request.getChangedTeacherId() != null) {
			studyClassSchedule.setTeacherId(request.getChangedTeacherId());
		}
		studyClassSchedule.setClassStatus(ClassStatus.Completed);
		studyClassSchedule.setClassCompletedBy(request.getPerformedBy());
		studyClassSchedule.setClassCompletedAt(request.getPerformedAt());
		studyClassSchedule.setStudentCheckinStatus(request.getCheckinStatus());
		studyClassSchedule.setStudentCheckinBy(request.getPerformedBy());
		studyClassSchedule.setStudentCheckinAt(request.getPerformedAt());
		studyClassSchedule.setStudentCheckinRemark(request.getCheckinRemark());
		studyClassSchedule.setStudentQuotaStatus(request.getStudentQuotaStatus());
		studyClassSchedule.setTeacherQuotaStatus(request.getTeacherQuotaStatus());
		if(request.getTeacherWageHourRate() != null && request.getTeacherWageHourRate().intValue() != 0) {
			studyClassSchedule.setTeacherWageHourRate(request.getTeacherWageHourRate());
		}
		studyClassSchedule.setLastModifiedBy(request.getPerformedBy());
		studyClassSchedule.setLastModifiedAt(request.getPerformedAt());
		studyClassSchedule = studyClassScheduleRepository.save(studyClassSchedule);
		logger.info("Update studyClassSchedule => {}", studyClassSchedule);
		
		response.setStudyClass(studyClass);
		response.setUpdatedStudyClassSchedule(studyClassSchedule);
		response.setUpdatedStudentCourseQuota(studentCourseQuota);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String classScheduleId;
		protected String changedTeacherId; // กรณีเปลี่ยนครูผู้สอน
		protected StudentCheckinStatus checkinStatus;
		protected String checkinRemark;
		protected StudentQuotaStatus studentQuotaStatus = StudentQuotaStatus.Used;
		protected TeacherQuotaStatus teacherQuotaStatus = TeacherQuotaStatus.Pending;
		protected BigDecimal teacherWageHourRate;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected StudyClass studyClass;
		protected StudyClassSchedule updatedStudyClassSchedule;
		protected StudentCourseQuota updatedStudentCourseQuota;
	}
}