package com.otc.kws.fishsix.lib.domain.service.class_schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassScheduleTemplate;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassScheduleTemplateRepository;

import lombok.Data;

@Component
public class StudyClassScheduleTemplateRemoveCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaStudyClassScheduleTemplateRepository studyClassScheduleTemplateRepository;
	
	
	public Response removeStudyClassScheduleTemplate(Request request)
	{
		logger.info("### {}.removeStudyClassScheduleTemplate ###", getClass().getSimpleName());
		
		StudyClassScheduleTemplate studyClassScheduleTemplate = studyClassScheduleTemplateRepository.findById(request.getStudyClassScheduleTemplateId()).orElse(null);
		
		if(studyClassScheduleTemplate == null) {
			throw new KwsRuntimeException("StudyClassScheduleTemplate id = " + request.getStudyClassScheduleTemplateId() + " Not Found");
		}
		
		studyClassScheduleTemplateRepository.delete(studyClassScheduleTemplate);
		logger.info("Remove studyClassScheduleTemplate => {}", studyClassScheduleTemplate);
		
		Response response = new Response();
		response.setRemovedStudyClassScheduleTemplate(studyClassScheduleTemplate);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String studyClassScheduleTemplateId;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected StudyClassScheduleTemplate removedStudyClassScheduleTemplate;
	}
}