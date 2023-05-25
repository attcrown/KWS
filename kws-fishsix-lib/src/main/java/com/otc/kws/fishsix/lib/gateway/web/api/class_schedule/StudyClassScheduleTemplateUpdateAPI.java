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
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/class-schedule/template/update")
public class StudyClassScheduleTemplateUpdateAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected StudyClassScheduleTemplateService studyClassScheduleTemplateService;
	
	
	@PostMapping
    public ResponseEntity<?> updateStudyClassScheduleTemplate(HttpServletRequest request, @RequestBody StudyClassScheduleTemplateUpdateAPI.RequestMessage requestMessage)
    {
		logger.info("### {}.updateStudyClassScheduleTemplate ###", getClass().getSimpleName());
		
		StudyClassScheduleTemplate updatedStudyClassScheduleTemplate = studyClassScheduleTemplateService.updateStudyClassScheduleTemplate(req -> {
			setupServiceRequest(request, req);
			req.setStudyClassScheduleTemplate(requestMessage.getStudyClassScheduleTemplate());
		}).getUpdatedStudyClassScheduleTemplate();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setUpdatedStudyClassScheduleTemplate(updatedStudyClassScheduleTemplate);
		
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
		protected StudyClassScheduleTemplate updatedStudyClassScheduleTemplate;
    }
}