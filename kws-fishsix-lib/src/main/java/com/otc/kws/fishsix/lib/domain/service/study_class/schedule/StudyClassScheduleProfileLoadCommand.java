package com.otc.kws.fishsix.lib.domain.service.study_class.schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.StudyClass;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassScheduleTemplate;
import com.otc.kws.fishsix.lib.domain.model.StudyClassScheduleProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassScheduleRepository;
import com.otc.kws.fishsix.lib.domain.service.class_schedule.StudyClassScheduleTemplateService;
import com.otc.kws.fishsix.lib.domain.service.study_class.StudyClassService;

import lombok.Data;

@Component
public class StudyClassScheduleProfileLoadCommand extends BaseKwsCommand
{
	@Autowired
	protected StudyClassScheduleTemplateService studyClassScheduleTemplateService;
	
	@Autowired
	protected StudyClassService studyClassService;
	
	@Autowired
	protected JpaStudyClassRepository studyClassRepository;
	
	@Autowired
	protected JpaStudyClassScheduleRepository studyClassScheduleRepository;
	
	
	public Response loadStudyClassScheduleProfile(Request request)
	{
		logger.info("### {}.loadStudyClassScheduleProfile ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		SimpleDateFormat dateTimeFMT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		SimpleDateFormat timeFMT = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
		
		StudyClassScheduleProfile classScheduleProfile = new StudyClassScheduleProfile();
		classScheduleProfile.setDate(dateTimeFMT.format(request.getDate()));
		classScheduleProfile.setPeriods(new ArrayList<>());
		response.setStudyClassScheduleProfile(classScheduleProfile);
		
		List<StudyClassScheduleTemplate> studyClassScheduleTemplates = studyClassScheduleTemplateService.loadStudyClassScheduleTemplate(req -> {
			req.copyFrom(request);
			req.setDate(request.getDate());
		}).getStudyClassScheduleTemplates();
		
		if(studyClassScheduleTemplates == null || studyClassScheduleTemplates.isEmpty()) {
			return response;
		}
		
		response.setStudyClassScheduleTemplates(studyClassScheduleTemplates);
		
		List<StudyClass> studyClasses = studyClassRepository.findByStudyDate(request.getDate());
		
		if(studyClasses == null || studyClasses.isEmpty() && !request.isForceCreateEmptyStudyClasses()) {
			return response;
		}
		
		if(request.isForceCreateEmptyStudyClasses()) {
			List<StudyClass> forceCreatedStudyClasses = new ArrayList<>();
			for(StudyClassScheduleTemplate studyClassScheduleTemplate : studyClassScheduleTemplates) {
				StudyClass studyClass = findMatchedStudyClass(studyClasses, studyClassScheduleTemplate);
				
				if(studyClass == null) {
					studyClass = new StudyClass();
					studyClass.setStudyDate(request.getDate());
					
					StudyClass _studyClass = studyClass;
					
					studyClass = studyClassService.createStudyClass(req -> {
						req.copyFrom(request);
						req.setClassScheduleTemplateId(studyClassScheduleTemplate.getId());
						req.setStudyClass(_studyClass);
					}).getCreatedStudyClass();
					
					forceCreatedStudyClasses.add(studyClass);
				}
			}
			response.setForceCreatedStudyClasses(forceCreatedStudyClasses);
			
			studyClasses = studyClassRepository.findByStudyDate(request.getDate());
		}
		
		studyClasses.sort((o1, o2) -> o1.getStudyStartTime().compareTo(o2.getStudyStartTime()));
		
		List<String> classIds = studyClasses.
				stream().
				map(StudyClass::getId).
				distinct().
				collect(Collectors.toList());
		
		List<StudyClassSchedule> classSchedules = studyClassScheduleRepository.findByClassIds(classIds);
		
		if(studyClasses != null && !studyClasses.isEmpty()) {
			for(StudyClass studyClass : studyClasses) {
				String startTime = timeFMT.format(studyClass.getStudyStartTime());
				String endTime = timeFMT.format(studyClass.getStudyEndTime());
				
				StudyClassScheduleProfile.Period period = classScheduleProfile.getPeriods().
						stream().
						filter(e -> e.getStartTime().equals(startTime) && e.getEndTime().equals(endTime)).
						findFirst().
						orElse(null);
				
				if(period == null) {
					period = StudyClassScheduleProfile.Period.
							builder().
							startTime(startTime).
							endTime(endTime).
							classes(new ArrayList<>()).
							build();
					
					classScheduleProfile.getPeriods().add(period);
				}
				
				StudyClassScheduleProfile.Class clazz = new StudyClassScheduleProfile.Class();
				clazz.setId(studyClass.getId());
				clazz.setClassName(studyClass.getClassName());
				clazz.setClassChannelId(studyClass.getClassChannelId());
				clazz.setClassTypeId(studyClass.getClassTypeId());
				clazz.setClassLocationId(studyClass.getClassLocationId());
				clazz.setStudyDay(studyClass.getStudyDay());
				clazz.setStudyDate(studyClass.getStudyDate());
				clazz.setStudyStartTime(studyClass.getStudyStartTime());
				clazz.setStudyEndTime(studyClass.getStudyEndTime());
				clazz.setClassHour(studyClass.getClassHour());
				clazz.setMaxStudent(studyClass.getMaxStudent());
				clazz.setReservedStudent(studyClass.getReservedStudent());
				clazz.setDescription(studyClass.getDescription());
				clazz.setRemark(studyClass.getRemark());
				clazz.setCreatedBy(studyClass.getCreatedBy());
				clazz.setCreatedAt(studyClass.getCreatedAt());
				clazz.setLastModifiedBy(studyClass.getLastModifiedBy());
				clazz.setLastModifiedAt(studyClass.getLastModifiedAt());
				
				period.getClasses().add(clazz);
				
				if(classSchedules != null && !classSchedules.isEmpty()) {
					List<StudyClassSchedule> schedules = classSchedules.
							stream().
							filter(e -> e.getClassId().equals(studyClass.getId())).
							collect(Collectors.toList());
					
					clazz.setSchedules(schedules);
				}
			}
		}
		
		return response;
	}
	
	protected StudyClass findMatchedStudyClass(List<StudyClass> studyClasses, StudyClassScheduleTemplate studyClassScheduleTemplate)
	{
		if(studyClasses == null || studyClasses.isEmpty()) {
			return null;
		}
		
		return studyClasses.
				stream().
				filter(studyClass -> {
					if(! studyClass.getClassChannelId().equals(studyClassScheduleTemplate.getClassChannelId())) {
						return false;
					}
					if(! studyClass.getClassTypeId().equals(studyClassScheduleTemplate.getClassTypeId())) {
						return false;
					}
					if(studyClass.getClassLocationId() != null && studyClassScheduleTemplate.getClassLocationId() != null) {
						if(! studyClass.getClassLocationId().equals(studyClassScheduleTemplate.getClassLocationId())) {
							return false;
						}
					}
					if(studyClass.getStudyDay() != studyClassScheduleTemplate.getDay()) {
						return false;
					}
					if(! studyClass.getStudyStartTime().equals(studyClassScheduleTemplate.getStartTime())) {
						return false;
					}
					if(! studyClass.getStudyEndTime().equals(studyClassScheduleTemplate.getEndTime())) {
						return false;
					}
					return true;
				}).
				findFirst().
				orElse(null);
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected Date date;
		protected boolean forceCreateEmptyStudyClasses; // กรณียังไม่มี study_class ในวันที่ระบุ ให้สร้างเลยหรือไม่
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected List<StudyClassScheduleTemplate> studyClassScheduleTemplates;
		protected List<StudyClass> forceCreatedStudyClasses;
		protected StudyClassScheduleProfile studyClassScheduleProfile;
	}
}