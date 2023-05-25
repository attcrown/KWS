package com.otc.kws.fishsix.lib.gateway.web.api.study_class.schedule;

import java.util.Date;

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
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/study-class/schedule/student-reserve")
public class StudyClassScheduleStudentReserveAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected StudyClassService studyClassService;
	
	
	@PostMapping
	public ResponseEntity<?> studentReserve(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### {}.studentReserve ###", getClass().getSimpleName());
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
    public static class RequestMessage
    {
		protected String classScheduleTemplateId;
		protected Date reservedDate;
		protected String studentId;
		protected String subjectId;
		protected String subjectChapterId;
    }


    @Data
    public static class BodyResponseMessage extends ResponseMessage.BaseBody
    {
    	protected StudyClass studyClass;
    	protected StudyClassSchedule reservedStudyClassSchedule;
		protected StudentCourseQuota updatedStudentCourseQuota;
    }
}