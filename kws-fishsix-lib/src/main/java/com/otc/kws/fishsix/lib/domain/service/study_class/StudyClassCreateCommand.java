package com.otc.kws.fishsix.lib.domain.service.study_class;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.StudentCourseQuota;
import com.otc.kws.fishsix.lib.domain.entity.StudyClass;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassScheduleTemplate;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudentCourseQuotaRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassScheduleRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassScheduleTemplateRepository;
import com.otc.kws.fishsix.lib.domain.service.study_class.schedule.StudyClassScheduleCreateCommand;

import lombok.Data;

@Component
public class StudyClassCreateCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaStudyClassScheduleTemplateRepository studyClassScheduleTemplateRepository;
	
	@Autowired
	protected JpaStudyClassRepository studyClassRepository;
	
	@Autowired
	protected JpaStudyClassScheduleRepository studyClassScheduleRepository;
	
	@Autowired
	protected JpaStudentCourseQuotaRepository studentCourseQuotaRepository;
	
	@Autowired
	protected StudyClassService studyClassService;
	
	
	public Response createStudyClass(Request request)
	{
		logger.info("### {}.createStudyClass ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		StudyClassScheduleTemplate studyClassScheduleTemplate = null;
		
		if(request.getClassScheduleTemplateId() != null) {
			studyClassScheduleTemplate = studyClassScheduleTemplateRepository.findById(request.getClassScheduleTemplateId()).orElse(null);
		}
		
		if(studyClassScheduleTemplate == null) {
			throw new KwsRuntimeException("StudyClassScheduleTemplate id: "+request.getClassScheduleTemplateId()+" Not Found");
		}
		
		// ****************************** studyClass ****************************** //
		StudyClass studyClass = request.getStudyClass();
		
		if(studyClass.getId() == null) {
			studyClass.setId(UUID.randomUUID().toString());
		}
		
		if(studyClass.getClassName() == null && studyClassScheduleTemplate != null) {
			studyClass.setClassName(studyClassScheduleTemplate.getClassName());
		}
		
		if(studyClass.getClassChannelId() == null && studyClassScheduleTemplate != null) {
			studyClass.setClassChannelId(studyClassScheduleTemplate.getClassChannelId());
		}
		
		if(studyClass.getClassTypeId() == null && studyClassScheduleTemplate != null) {
			studyClass.setClassTypeId(studyClassScheduleTemplate.getClassTypeId());
		}
		
		if(studyClass.getClassLocationId() == null && studyClassScheduleTemplate != null) {
			studyClass.setClassLocationId(studyClassScheduleTemplate.getClassLocationId());
		}
		
		if(studyClass.getStudyDay() == null && studyClassScheduleTemplate != null) {
			studyClass.setStudyDay(studyClassScheduleTemplate.getDay());
		}
		
		if(studyClass.getStudyStartTime() == null && studyClassScheduleTemplate != null) {
			studyClass.setStudyStartTime(studyClassScheduleTemplate.getStartTime());
		}
		
		if(studyClass.getStudyEndTime() == null && studyClassScheduleTemplate != null) {
			studyClass.setStudyEndTime(studyClassScheduleTemplate.getEndTime());
		}
		
		if(studyClass.getClassHour() == null && studyClassScheduleTemplate != null) {
			studyClass.setClassHour(studyClassScheduleTemplate.getHour());
		}
		
		if(studyClass.getMaxStudent() == null && studyClassScheduleTemplate != null) {
			studyClass.setMaxStudent(studyClassScheduleTemplate.getMaxStudent());
		}
		
		studyClass.setReservedStudent(request.getStudyClassSchedules() != null ? request.getStudyClassSchedules().size() : 0);
		
		if(studyClass.getDescription() == null && studyClassScheduleTemplate != null) {
			studyClass.setDescription(studyClassScheduleTemplate.getDescription());
		}
		
		if(studyClass.getRemark() == null && studyClassScheduleTemplate != null) {
			studyClass.setRemark(studyClassScheduleTemplate.getRemark());
		}
		
		studyClass.setCreatedBy(request.getPerformedBy());
		studyClass.setCreatedAt(request.getPerformedAt());
		
		StudyClass createdStudyClass = studyClassRepository.save(studyClass);
		logger.info("Create studyClass => {}", createdStudyClass);
		
		response.setCreatedStudyClass(createdStudyClass);
		// ****************************** studyClass ****************************** //
		
		
		// ****************************** studyClassSchedule ****************************** //
		List<StudyClassSchedule> studyClassSchedules = request.getStudyClassSchedules();
		
		if(studyClassSchedules != null && !studyClassSchedules.isEmpty()) {
			List<StudyClassSchedule> createdStudyClassSchedules = new ArrayList<>();
			List<StudentCourseQuota> updatedStudentCourseQuotas = new ArrayList<>();
			
			for(StudyClassSchedule studyClassSchedule : studyClassSchedules) {
				StudyClassScheduleCreateCommand.Response createStudyClassScheduleResponse = studyClassService.createStudyClassSchedule(req -> {
					req.copyFrom(request);
					req.setStudyClass(createdStudyClass);
					req.setStudyClassSchedule(studyClassSchedule);
				});
				
				createdStudyClassSchedules.add(createStudyClassScheduleResponse.getCreatedStudyClassSchedule());
				updatedStudentCourseQuotas.add(createStudyClassScheduleResponse.getUpdatedStudentCourseQuota());
			}
			
			response.setCreatedStudyClassSchedules(createdStudyClassSchedules);
			response.setUpdatedStudentCourseQuotas(updatedStudentCourseQuotas);
		}
		// ****************************** studyClassSchedule ****************************** //
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String classScheduleTemplateId;
		protected StudyClass studyClass;
		protected List<StudyClassSchedule> studyClassSchedules;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected StudyClass createdStudyClass;
		protected List<StudyClassSchedule> createdStudyClassSchedules;
		protected List<StudentCourseQuota> updatedStudentCourseQuotas;
	}
}