package com.otc.kws.fishsix.lib.domain.service.study_class.evaluation;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.fishsix.lib.domain.entity.StudyClass;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassEvaluation;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule;
import com.otc.kws.fishsix.lib.domain.entity.Teacher;
import com.otc.kws.fishsix.lib.domain.entity.TeacherHourAcquired;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassEvaluation.EvaluateStatus;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassEvaluation.EvaluatorType;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule.StudentCheckinStatus;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule.TeacherQuotaStatus;
import com.otc.kws.fishsix.lib.domain.entity.TeacherHourAcquired.AcquiredMethod;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassScheduleRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaTeacherHourAcquiredRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaTeacherRepository;

public abstract class BaseStudyClassEvaluationCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaStudyClassRepository studyClassRepository;
	
	@Autowired
	protected JpaStudyClassScheduleRepository studyClassScheduleRepository;
	
	@Autowired
	protected JpaTeacherHourAcquiredRepository teacherHourAcquiredRepository;
	
	@Autowired
	protected JpaTeacherRepository teacherRepository;
	
	
	protected void setTeacherHourAcquired(BaseKwsCommandRequest request, EvaluationOperation operation, StudyClassEvaluation evaluation)
	{
		logger.info("### {}.setTeacherHourAcquired ###", getClass().getSimpleName());
		
		if(evaluation.getEvaluateFromType() != EvaluatorType.Teacher || evaluation.getEvaluateToType() != EvaluatorType.Student) {
			return;
		}
		
		if((operation == EvaluationOperation.CREATE && evaluation.getEvaluateStatus() == EvaluateStatus.Approved) || operation == EvaluationOperation.APPROVE) {
			addTeacherHourAcquired(request, evaluation);
		} else if(operation == EvaluationOperation.DELETE || operation == EvaluationOperation.REJECT) {
			removeTeacherHourAcquired(request, evaluation);
		} else if(operation == EvaluationOperation.UPDATE) {
			if(evaluation.getEvaluateStatus() == EvaluateStatus.Approved) {
				addTeacherHourAcquired(request, evaluation);
			} else if(evaluation.getEvaluateStatus() == EvaluateStatus.Rejected) {
				removeTeacherHourAcquired(request, evaluation);
			}
		}
	}
	
	protected void addTeacherHourAcquired(BaseKwsCommandRequest request, StudyClassEvaluation evaluation)
	{
		logger.info("### {}.addTeacherHourAcquired ###", getClass().getSimpleName());
		
		if(teacherHourAcquiredRepository.findByTeacherIdAndClassScheduleId(evaluation.getEvaluateFromRefId(), evaluation.getClassScheduleId()) != null) {
			return;
		}
		
		StudyClassSchedule studyClassSchedule = studyClassScheduleRepository.findById(evaluation.getClassScheduleId()).orElse(null);
		
		if(studyClassSchedule == null) {
			throw new KwsRuntimeException("StudyClassSchedule {id: "+evaluation.getClassScheduleId()+"} Not Found");
		}
		
		StudyClass studyClass = studyClassRepository.findById(studyClassSchedule.getClassId()).orElse(null);
		
		if(studyClass == null) {
			throw new KwsRuntimeException("StudyClass {id: "+studyClassSchedule.getClassId()+"} Not Found");
		}
		
		Teacher teacher = teacherRepository.findById(studyClassSchedule.getTeacherId()).orElse(null);
		
		if(teacher == null) {
			throw new KwsRuntimeException("Teacher {id: "+studyClassSchedule.getTeacherId()+"} Not Found");
		}
		
		BigDecimal wageHourRage = studyClassSchedule.getTeacherWageHourRate();
		if(wageHourRage == null || wageHourRage.intValue() == 0) {
			wageHourRage = teacher.getWageHourRate();
		}
		
		TeacherHourAcquired teacherHourAcquired = new TeacherHourAcquired();
		teacherHourAcquired.setId(UUID.randomUUID().toString());
		teacherHourAcquired.setTeacherId(studyClassSchedule.getTeacherId());
		teacherHourAcquired.setClassScheduleId(studyClassSchedule.getId());
		teacherHourAcquired.setAcquiredMethod(studyClassSchedule.getStudentCheckinStatus() == StudentCheckinStatus.Present ? AcquiredMethod.Educate : AcquiredMethod.Cancel);
		teacherHourAcquired.setAcquiredFromCourseQuotaId(studyClassSchedule.getStudentCourseQuotaId());
		teacherHourAcquired.setAcquiredFromStudentId(studyClassSchedule.getStudentId());
		teacherHourAcquired.setAcquiredHour(studyClass.getClassHour());
		if(wageHourRage != null) {
			teacherHourAcquired.setAcquiredAmount(wageHourRage.multiply(new BigDecimal(studyClass.getClassHour())));
		}
		teacherHourAcquired.setStatus(MasterStatusValue.Active);
		teacherHourAcquired.setCreatedBy(request.getPerformedBy());
		teacherHourAcquired.setCreatedAt(request.getPerformedAt());
		teacherHourAcquiredRepository.save(teacherHourAcquired);
		logger.info("Create TeacherHourAcquired => {}", teacherHourAcquired);
		
		if(studyClassSchedule.getTeacherQuotaStatus() != TeacherQuotaStatus.Acquired) {
			studyClassSchedule.setTeacherQuotaStatus(TeacherQuotaStatus.Acquired);
			studyClassSchedule.setLastModifiedBy(request.getPerformedBy());
			studyClassSchedule.setLastModifiedAt(request.getPerformedAt());
			studyClassScheduleRepository.save(studyClassSchedule);
			logger.info("Update StudyClassSchedule => {}", studyClassSchedule);
		}
	}
	
	protected void removeTeacherHourAcquired(BaseKwsCommandRequest request, StudyClassEvaluation evaluation)
	{
		logger.info("### {}.removeTeacherHourAcquired ###", getClass().getSimpleName());
		
		List<TeacherHourAcquired> teacherHourAcquireds = teacherHourAcquiredRepository.removeByTeacherIdAndClassScheduleId(evaluation.getEvaluateFromRefId(), evaluation.getClassScheduleId());
		
		if(teacherHourAcquireds != null && !teacherHourAcquireds.isEmpty()) {
			logger.info("Delete teacherHourAcquired => {}", teacherHourAcquireds.get(0));
		}
		
		StudyClassSchedule studyClassSchedule = studyClassScheduleRepository.findById(evaluation.getClassScheduleId()).orElse(null);
		
		if(studyClassSchedule == null) {
			throw new KwsRuntimeException("StudyClassSchedule {id: "+evaluation.getClassScheduleId()+"} Not Found");
		}
		
		if(studyClassSchedule.getTeacherQuotaStatus() == TeacherQuotaStatus.Acquired) {
			studyClassSchedule.setTeacherQuotaStatus(TeacherQuotaStatus.Pending);
			studyClassSchedule.setLastModifiedBy(request.getPerformedBy());
			studyClassSchedule.setLastModifiedAt(request.getPerformedAt());
			studyClassScheduleRepository.save(studyClassSchedule);
			logger.info("Update StudyClassSchedule => {}", studyClassSchedule);
		}
	}
	
	
	public static enum EvaluationOperation
	{
		CREATE, UPDATE, DELETE, APPROVE, REJECT
	}
}