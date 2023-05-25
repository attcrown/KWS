package com.otc.kws.fishsix.lib.domain.service.study_class.schedule;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.Student;
import com.otc.kws.fishsix.lib.domain.entity.StudentCourseQuota;
import com.otc.kws.fishsix.lib.domain.entity.StudyClass;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule.StudentQuotaStatus;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule.TeacherQuotaStatus;
import com.otc.kws.fishsix.lib.domain.model.StudentCourseQuotaInfo;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudentCourseQuotaRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudentRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassScheduleRepository;
import com.otc.kws.fishsix.lib.domain.service.student.FishsixStudentService;

import lombok.Data;

@Component
public class StudyClassScheduleCreateCommand extends BaseKwsCommand
{
	@Autowired
	protected FishsixStudentService studentService;
	
	@Autowired
	protected JpaStudentRepository studentRepository;
	
	@Autowired
	protected JpaStudyClassRepository studyClassRepository;
	
	@Autowired
	protected JpaStudyClassScheduleRepository studyClassScheduleRepository;
	
	@Autowired
	protected JpaStudentCourseQuotaRepository studentCourseQuotaRepository;
	
	
	public Response createStudyClassSchedule(Request request)
	{
		logger.info("### {}.createStudyClassSchedule ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		StudyClass studyClass = request.getStudyClass();
		StudyClassSchedule studyClassSchedule = request.getStudyClassSchedule();
		
		if(studyClass == null && studyClassSchedule != null && studyClassSchedule.getClassId() != null) {
			studyClass = studyClassRepository.findById(studyClassSchedule.getClassId()).orElse(null);
		}
		
		if(studyClass == null) {
			throw new KwsRuntimeException("StudyClass Not Found");
		}
		
		if(studyClass.getMaxStudent() == studyClass.getReservedStudent()) {
			throw new KwsRuntimeException("คลาสเรียนถูกจองเต็มแล้ว");
		}
		
		if(studyClassSchedule.getStudentId() == null) {
			throw new KwsRuntimeException("studentId is not defined");
		}
		
		Student student = studentRepository.findById(studyClassSchedule.getStudentId()).orElse(null);
		
		if(student == null) {
			throw new KwsRuntimeException("student {id: "+studyClassSchedule.getStudentId()+"} not found");
		}
		
		
		// ****************************** studyClass ****************************** //
		studyClass.setReservedStudent(studyClass.getReservedStudent() + 1);
		studyClass.setLastModifiedBy(request.getPerformedBy());
		studyClass.setLastModifiedAt(request.getPerformedAt());
		studyClass = studyClassRepository.save(studyClass);
		logger.info("Update studyClass => {}", studyClass);
		response.setStudyClass(studyClass);
		// ****************************** studyClass ****************************** //
		
		
		// ****************************** studyClassSchedule ****************************** //
		if(studyClassScheduleRepository.findByClassIdAndStudentIdAndTeacherId(studyClassSchedule.getClassId(), studyClassSchedule.getStudentId(), studyClassSchedule.getTeacherId()) != null) {
			throw new KwsRuntimeException("ลงตารางสอน/เรียน ซ้ำ");
		}
		
		if(studyClassSchedule.getId() == null) {
			studyClassSchedule.setId(UUID.randomUUID().toString());
		}
		
		if(studyClassSchedule.getClassId() == null) {
			studyClassSchedule.setClassId(studyClass.getId());
		}
		
		if(studyClassSchedule.getClassStatus() == null) {
			studyClassSchedule.setClassStatus(StudyClassSchedule.ClassStatus.Reserved);
		}
		
		if(studyClassSchedule.getClassStatus() == StudyClassSchedule.ClassStatus.Reserved) {
			if(studyClassSchedule.getClassReservedBy() == null) {
				studyClassSchedule.setClassReservedBy(request.getPerformedBy());
			}
			if(studyClassSchedule.getClassReservedAt() == null) {
				studyClassSchedule.setClassReservedAt(request.getPerformedAt());
			}
		}
		
		if(studyClassSchedule.getClassStatus() == StudyClassSchedule.ClassStatus.Cancelled) {
			if(studyClassSchedule.getClassCancelledBy() == null) {
				studyClassSchedule.setClassCancelledBy(request.getPerformedBy());
			}
			if(studyClassSchedule.getClassCancelledAt() == null) {
				studyClassSchedule.setClassCancelledAt(request.getPerformedAt());
			}
		}
		
		if(studyClassSchedule.getClassStatus() == StudyClassSchedule.ClassStatus.Confirmed) {
			if(studyClassSchedule.getClassConfirmedBy() == null) {
				studyClassSchedule.setClassConfirmedBy(request.getPerformedBy());
			}
			if(studyClassSchedule.getClassConfirmedAt() == null) {
				studyClassSchedule.setClassConfirmedAt(request.getPerformedAt());
			}
		}
		
		if(studyClassSchedule.getClassStatus() == StudyClassSchedule.ClassStatus.Completed) {
			if(studyClassSchedule.getClassCompletedBy() == null) {
				studyClassSchedule.setClassCompletedBy(request.getPerformedBy());
			}
			if(studyClassSchedule.getClassCompletedAt() == null) {
				studyClassSchedule.setClassCompletedAt(request.getPerformedAt());
			}
		}
		
		if(studyClassSchedule.getStudentQuotaStatus() == null) {
			studyClassSchedule.setStudentQuotaStatus(StudentQuotaStatus.Reserved);
		}
		
		if(studyClassSchedule.getTeacherQuotaStatus() == null) {
			studyClassSchedule.setTeacherQuotaStatus(TeacherQuotaStatus.Pending);
		}
		
		if(studyClassSchedule.getStudentCourseQuotaId() == null) {
			List<StudentCourseQuotaInfo> studentCourseQuotaInfos = studentService.loadStudentCourseQuotaInfo(req -> {
				req.copyFrom(request);
				req.setStudentId(student.getId());
			}).getStudentCourseQuotaInfos();
			
			StudentCourseQuota studentCourseQuota = studentCourseQuotaInfos.
					stream().
					filter(e -> e.getSubjectedIds() != null && e.getSubjectedIds().contains(studyClassSchedule.getSubjectId())).
					map(e -> e.getStudentCourseQuota()).
					findFirst().
					orElse(null);
			
			if(studentCourseQuota != null) {
				studyClassSchedule.setStudentCourseQuotaId(studentCourseQuota.getId());
			} else {
				throw new KwsRuntimeException("StudentCourseQuota Not Found");
			}
		}
		
		studyClassSchedule.setCreatedBy(request.getPerformedBy());
		studyClassSchedule.setCreatedAt(request.getPerformedAt());
		
		StudyClassSchedule createdStudyClassSchedule = studyClassScheduleRepository.save(studyClassSchedule);
		logger.info("Create studyClassSchedule => {}", createdStudyClassSchedule);
		response.setCreatedStudyClassSchedule(createdStudyClassSchedule);
		// ****************************** studyClassSchedule ****************************** //
		
		
		// ****************************** studentCourseQuota ****************************** //
		if(studyClassSchedule.getStudentQuotaStatus() == StudentQuotaStatus.Reserved || studyClassSchedule.getStudentQuotaStatus() == StudentQuotaStatus.Used) {
			StudentCourseQuota studentCourseQuota = studentCourseQuotaRepository.findById(studyClassSchedule.getStudentCourseQuotaId()).orElse(null);
			
			if(studentCourseQuota == null) {
				throw new KwsRuntimeException("StudentCourseQuota with id = " + studyClassSchedule.getStudentCourseQuotaId() + " Not Found");
			}
			
			if(! studentCourseQuota.getStudentId().equals(student.getId())) {
				throw new KwsRuntimeException("Invalid Student Quota For Student {id: "+student.getId()+"}");
			}
			
			if(request.getPerformedAt().getTime() < studentCourseQuota.getActivatedAt().getTime()) {
				throw new KwsRuntimeException("StudentCourseQuota with id = " + studyClassSchedule.getStudentCourseQuotaId() + " Not Activated Yet");
			}
			
			if(request.getPerformedAt().getTime() > studentCourseQuota.getExpiredAt().getTime()) {
				throw new KwsRuntimeException("StudentCourseQuota with id = " + studyClassSchedule.getStudentCourseQuotaId() + " Expired");
			}
			
			int remainHour = studentCourseQuota.getInitialHour() - studentCourseQuota.getUsedHour();
			
			if(remainHour - studyClass.getClassHour() <= 0) {
				throw new KwsRuntimeException("StudentCourseQuota with id = " + studyClassSchedule.getStudentCourseQuotaId() + " Exceed Limit Quota");
			}
			
			studentCourseQuota.setUsedHour(studentCourseQuota.getUsedHour() + studyClass.getClassHour());
			studentCourseQuota.setLastModifiedBy(request.getPerformedBy());
			studentCourseQuota.setLastModifiedAt(request.getPerformedAt());
			
			StudentCourseQuota updatedStudentCourseQuota = studentCourseQuotaRepository.save(studentCourseQuota);
			logger.info("Update studentCourseQuota => {}", updatedStudentCourseQuota);
			response.setUpdatedStudentCourseQuota(updatedStudentCourseQuota);
		}
		// ****************************** studentCourseQuota ****************************** //
		
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected StudyClass studyClass;
		protected StudyClassSchedule studyClassSchedule;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected StudyClass studyClass;
    	protected StudyClassSchedule createdStudyClassSchedule;
		protected StudentCourseQuota updatedStudentCourseQuota;
	}
}