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
import com.otc.kws.fishsix.lib.domain.service.class_schedule.StudyClassScheduleTemplateService;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/class-schedule/template/remove")
public class StudyClassScheduleTemplateRemoveAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected StudyClassScheduleTemplateService studyClassScheduleTemplateService;
	
	
	@PostMapping
    public ResponseEntity<?> removeStudyClassScheduleTemplate(HttpServletRequest request, @RequestBody StudyClassScheduleTemplateRemoveAPI.RequestMessage requestMessage)
    {
		logger.info("### {}.removeStudyClassScheduleTemplate ###", getClass().getSimpleName());
		
		StudyClassScheduleTemplate removedStudyClassScheduleTemplate = studyClassScheduleTemplateService.removeStudyClassScheduleTemplate(req -> {
			setupServiceRequest(request, req);
			req.setStudyClassScheduleTemplateId(requestMessage.getStudyClassScheduleTemplateId());
		}).getRemovedStudyClassScheduleTemplate();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setRemovedStudyClassScheduleTemplate(removedStudyClassScheduleTemplate);
		
		return replySuccess(request, bodyResponseMessage);
    }
	
	
	@Data
	public static class RequestMessage
	{
		protected String studyClassScheduleTemplateId;
	}
	
	
	@Data
    public static class BodyResponseMessage extends ResponseMessage.BaseBody
    {
		protected StudyClassScheduleTemplate removedStudyClassScheduleTemplate;
    }
}