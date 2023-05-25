package com.otc.kws.fishsix.lib.domain.service.class_schedule;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.util.DayOfWeekUtil;
import com.otc.kws.fishsix.lib.domain.constant.value.CourseChannelValue;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassScheduleTemplate;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassScheduleTemplateRepository;

import lombok.Data;

@Component
public class StudyClassScheduleTemplateCreateCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaStudyClassScheduleTemplateRepository studyClassScheduleTemplateRepository;
	
	
	public Response createStudyClassScheduleTemplate(Request request)
	{
		logger.info("### {}.createStudyClassScheduleTemplate ###", getClass().getSimpleName());
		
		StudyClassScheduleTemplate studyClassScheduleTemplate = request.getStudyClassScheduleTemplate();
		
		if(studyClassScheduleTemplate.getId() == null) {
			studyClassScheduleTemplate.setId(UUID.randomUUID().toString());
		} else {
			if(studyClassScheduleTemplateRepository.findById(studyClassScheduleTemplate.getId()).isPresent()) {
				throw new KwsRuntimeException("Duplicate StudyClassScheduleTemplate id = " + studyClassScheduleTemplate.getId());
			}
		}
		
		studyClassScheduleTemplate.setDay(DayOfWeekUtil.getDayOfWeek(studyClassScheduleTemplate.getStartDate()));
		
		if(studyClassScheduleTemplate.getClassChannelId().equals(CourseChannelValue.OnLine.name())) {
			studyClassScheduleTemplate.setClassLocationId(null);
		}
		
		if(studyClassScheduleTemplate.getStatus() == null) {
			studyClassScheduleTemplate.setStatus(MasterStatusValue.Active);
		}
		
		studyClassScheduleTemplate.setCreatedBy(request.getPerformedBy());
		studyClassScheduleTemplate.setCreatedAt(request.getPerformedAt());
		
		StudyClassScheduleTemplate createdStudyClassScheduleTemplate = studyClassScheduleTemplateRepository.save(studyClassScheduleTemplate);
		logger.info("Create studyClassScheduleTemplate => {}", createdStudyClassScheduleTemplate);
		
		Response response = new Response();
		response.setCreatedStudyClassScheduleTemplate(createdStudyClassScheduleTemplate);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected StudyClassScheduleTemplate studyClassScheduleTemplate;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected StudyClassScheduleTemplate createdStudyClassScheduleTemplate;
	}
}