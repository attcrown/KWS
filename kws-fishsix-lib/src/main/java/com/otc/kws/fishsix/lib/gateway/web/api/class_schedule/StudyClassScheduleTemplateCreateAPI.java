package com.otc.kws.fishsix.lib.gateway.web.api.class_schedule;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassScheduleTemplate;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassScheduleTemplateRepository;
import com.otc.kws.fishsix.lib.domain.service.class_schedule.StudyClassScheduleTemplateService;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/class-schedule/template/create")
public class StudyClassScheduleTemplateCreateAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected JpaStudyClassScheduleTemplateRepository studyClassScheduleTemplateRepository;
	
	@Autowired
	protected StudyClassScheduleTemplateService studyClassScheduleTemplateService;
	
	
	@PostMapping
    public ResponseEntity<?> createStudyClassScheduleTemplate(HttpServletRequest request, @RequestBody StudyClassScheduleTemplateCreateAPI.RequestMessage requestMessage)
    {
		logger.info("### {}.createStudyClassScheduleTemplate ###", getClass().getSimpleName());
		
		StudyClassScheduleTemplate createdStudyClassScheduleTemplate = studyClassScheduleTemplateService.createStudyClassScheduleTemplate(req -> {
			setupServiceRequest(request, req);
			req.setStudyClassScheduleTemplate(requestMessage.getStudyClassScheduleTemplate());
		}).getCreatedStudyClassScheduleTemplate();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setCreatedStudyClassScheduleTemplate(createdStudyClassScheduleTemplate);
		
		return replySuccess(request, bodyResponseMessage);
    }
	
	
	@Data
	public static class RequestMessage
	{
		protected StudyClassScheduleTemplate studyClassScheduleTemplate;
	}
	
	
	@Data
    public static class BodyResponseMessage extends ResponseMessage.BaseBody
    {
		protected StudyClassScheduleTemplate createdStudyClassScheduleTemplate;
    }
}