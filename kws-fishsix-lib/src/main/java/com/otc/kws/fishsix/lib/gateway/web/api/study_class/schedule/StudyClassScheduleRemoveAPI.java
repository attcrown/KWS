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
import com.otc.kws.fishsix.lib.domain.service.study_class.schedule.StudyClassScheduleRemoveCommand;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/study-class/schedule/remove")
public class StudyClassScheduleRemoveAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected StudyClassService studyClassService;
	
	
	@PostMapping
	public ResponseEntity<?> createStudyClassSchedule(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### {}.removeStudyClassSchedule ###", getClass().getSimpleName());
		
		StudyClassScheduleRemoveCommand.Response removeStudyClassScheduleResponse = studyClassService.removeStudyClassSchedule(req -> {
			setupServiceRequest(request, req);
			req.setClassScheduleId(requestMessage.getClassScheduleId());
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setStudyClass(removeStudyClassScheduleResponse.getStudyClass());
		bodyResponseMessage.setRemovedStudyClassSchedule(removeStudyClassScheduleResponse.getRemovedStudyClassSchedule());
		bodyResponseMessage.setUpdatedStudentCourseQuota(removeStudyClassScheduleResponse.getUpdatedStudentCourseQuota());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
    public static class RequestMessage
    {
		protected String classScheduleId;
    }


    @Data
    public static class BodyResponseMessage extends ResponseMessage.BaseBody
    {
    	protected StudyClass studyClass;
    	protected StudyClassSchedule removedStudyClassSchedule;
		protected StudentCourseQuota updatedStudentCourseQuota;
    }
}