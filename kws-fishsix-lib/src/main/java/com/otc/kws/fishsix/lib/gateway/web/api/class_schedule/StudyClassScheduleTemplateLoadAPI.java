package com.otc.kws.fishsix.lib.gateway.web.api.class_schedule;

import java.util.Date;
import java.util.List;

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
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/class-schedule/template/load")
public class StudyClassScheduleTemplateLoadAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected StudyClassScheduleTemplateService studyClassScheduleTemplateService;
	
	
	@PostMapping
    public ResponseEntity<?> loadStudyClassScheduleTemplate(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
    {
		logger.info("### {}.loadStudyClassScheduleTemplate ###", getClass().getSimpleName());
		logger.info("requestMessage => {}", requestMessage);
		
		List<StudyClassScheduleTemplate> studyClassScheduleTemplates = studyClassScheduleTemplateService.loadStudyClassScheduleTemplate(req -> {
			setupServiceRequest(request, req);
			req.setDate(requestMessage.getDate());
		}).getStudyClassScheduleTemplates();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setStudyClassScheduleTemplates(studyClassScheduleTemplates);
		
		return replySuccess(request, bodyResponseMessage);
    }
	
	
	@Data
	public static class RequestMessage
	{
		protected Date date;
	}
	
	
	@Data
    public static class BodyResponseMessage extends ResponseMessage.BaseBody
    {
		protected List<StudyClassScheduleTemplate> studyClassScheduleTemplates;
    }
}