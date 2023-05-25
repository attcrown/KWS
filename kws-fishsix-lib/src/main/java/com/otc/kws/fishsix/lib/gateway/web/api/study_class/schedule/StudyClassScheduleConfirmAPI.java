package com.otc.kws.fishsix.lib.gateway.web.api.study_class.schedule;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.fishsix.lib.domain.entity.StudentCourseQuota;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule;
import com.otc.kws.fishsix.lib.domain.service.study_class.StudyClassService;
import com.otc.kws.fishsix.lib.domain.service.study_class.schedule.StudyClassScheduleConfirmCommand;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/study-class/schedule/confirm")
public class StudyClassScheduleConfirmAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected StudyClassService studyClassService;
	
	
	@PostMapping
	public ResponseEntity<?> confirmStudyClassSchedule(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### {}.confirmStudyClassSchedule ###", getClass().getSimpleName());
		
		StudyClassScheduleConfirmCommand.Response confirmStudyClassScheduleResponse = studyClassService.confirmStudyClassSchedule(req -> {
			setupServiceRequest(request, req);
			req.setClassScheduleIds(requestMessage.getClassScheduleIds());
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setUpdatedStudyClassSchedules(confirmStudyClassScheduleResponse.getUpdatedStudyClassSchedules());
		bodyResponseMessage.setUpdatedStudentCourseQuotas(confirmStudyClassScheduleResponse.getUpdatedStudentCourseQuotas());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
    public static class RequestMessage
    {
		protected List<String> classScheduleIds;
    }


    @Data
    public static class BodyResponseMessage extends ResponseMessage.BaseBody
    {
    	protected List<StudyClassSchedule> updatedStudyClassSchedules;
		protected List<StudentCourseQuota> updatedStudentCourseQuotas;
    }
}