package com.otc.kws.fishsix.lib.gateway.web.api.study_class.schedule;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.fishsix.lib.domain.entity.StudentCourseQuota;
import com.otc.kws.fishsix.lib.domain.entity.StudyClass;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule;
import com.otc.kws.fishsix.lib.domain.service.study_class.StudyClassService;
import com.otc.kws.fishsix.lib.domain.service.study_class.schedule.StudyClassScheduleCreateCommand;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/study-class/schedule/create")
public class StudyClassScheduleCreateAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected StudyClassService studyClassService;
	
	
	@PostMapping
	public ResponseEntity<?> createStudyClassSchedule(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### {}.createStudyClassSchedule ###", getClass().getSimpleName());
		
		StudyClassScheduleCreateCommand.Response createStudyClassScheduleResponse = studyClassService.createStudyClassSchedule(req -> {
			setupServiceRequest(request, req);
			req.setStudyClassSchedule(requestMessage.getStudyClassSchedule());
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setStudyClass(createStudyClassScheduleResponse.getStudyClass());
		bodyResponseMessage.setCreatedStudyClassSchedule(createStudyClassScheduleResponse.getCreatedStudyClassSchedule());
		bodyResponseMessage.setUpdatedStudentCourseQuota(createStudyClassScheduleResponse.getUpdatedStudentCourseQuota());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
    public static class RequestMessage
    {
		protected StudyClassSchedule studyClassSchedule;
    }


    @Data
    public static class BodyResponseMessage extends ResponseMessage.BaseBody
    {
    	protected StudyClass studyClass;
    	protected StudyClassSchedule createdStudyClassSchedule;
		protected StudentCourseQuota updatedStudentCourseQuota;
    }
}