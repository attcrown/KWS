package com.otc.kws.fishsix.lib.domain.service.class_schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class StudyClassScheduleTemplateUpdateCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaStudyClassScheduleTemplateRepository studyClassScheduleTemplateRepository;
	
	
	public Response updateStudyClassScheduleTemplate(Request request)
	{
		logger.info("### {}.updateStudyClassScheduleTemplate ###", getClass().getSimpleName());
		
		StudyClassScheduleTemplate studyClassScheduleTemplate = request.getStudyClassScheduleTemplate();
		
		if(studyClassScheduleTemplateRepository.findById(studyClassScheduleTemplate.getId()).isEmpty()) {
			throw new KwsRuntimeException("StudyClassScheduleTemplate id = " + studyClassScheduleTemplate.getId() + " Not Found");
		}
		
		if(studyClassScheduleTemplate.getClassChannelId().equals(CourseChannelValue.OnLine.name())) {
			studyClassScheduleTemplate.setClassLocationId(null);
		}
		
		studyClassScheduleTemplate.setDay(DayOfWeekUtil.getDayOfWeek(studyClassScheduleTemplate.getStartDate()));
		studyClassScheduleTemplate.setLastModifiedBy(request.getPerformedBy());
		studyClassScheduleTemplate.setLastModifiedAt(request.getPerformedAt());
		
		StudyClassScheduleTemplate updatedStudyClassScheduleTemplate = studyClassScheduleTemplateRepository.save(studyClassScheduleTemplate);
		logger.info("Update studyClassScheduleTemplate => {}", updatedStudyClassScheduleTemplate);
		
		Response response = new Response();
		response.setUpdatedStudyClassScheduleTemplate(updatedStudyClassScheduleTemplate);
		
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
		protected StudyClassScheduleTemplate updatedStudyClassScheduleTemplate;
	}
}