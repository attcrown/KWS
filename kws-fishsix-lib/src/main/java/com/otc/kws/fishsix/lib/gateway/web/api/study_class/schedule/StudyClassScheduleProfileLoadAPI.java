package com.otc.kws.fishsix.lib.gateway.web.api.study_class.schedule;

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
import com.otc.kws.fishsix.lib.domain.entity.StudyClass;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassScheduleTemplate;
import com.otc.kws.fishsix.lib.domain.model.StudyClassScheduleProfile;
import com.otc.kws.fishsix.lib.domain.service.study_class.StudyClassService;
import com.otc.kws.fishsix.lib.domain.service.study_class.schedule.StudyClassScheduleProfileLoadCommand;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/study-class/schedule/profile/load")
public class StudyClassScheduleProfileLoadAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected StudyClassService studyClassService;
	
	
	@PostMapping
	public ResponseEntity<?> loadStudyClassScheduleProfile(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### {}.loadStudyClassScheduleProfile ###", getClass().getSimpleName());
		logger.info("requestMessage => {}", requestMessage);
		
		StudyClassScheduleProfileLoadCommand.Response loadProfileResponse = studyClassService.loadStudyClassScheduleProfile(req -> {
			setupServiceRequest(request, req);
			req.setDate(requestMessage.getDate());
			if(requestMessage.getForceCreateEmptyStudyClasses() != null) {
				req.setForceCreateEmptyStudyClasses(requestMessage.getForceCreateEmptyStudyClasses());
			}
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setStudyClassScheduleTemplates(loadProfileResponse.getStudyClassScheduleTemplates());
		bodyResponseMessage.setForceCreatedStudyClasses(loadProfileResponse.getForceCreatedStudyClasses());
		bodyResponseMessage.setStudyClassScheduleProfile(loadProfileResponse.getStudyClassScheduleProfile());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
    public static class RequestMessage
    {
		protected Date date;
		protected Boolean forceCreateEmptyStudyClasses;
    }


    @Data
    public static class BodyResponseMessage extends ResponseMessage.BaseBody
    {
    	protected List<StudyClassScheduleTemplate> studyClassScheduleTemplates;
		protected List<StudyClass> forceCreatedStudyClasses;
		protected StudyClassScheduleProfile studyClassScheduleProfile;
    }
}