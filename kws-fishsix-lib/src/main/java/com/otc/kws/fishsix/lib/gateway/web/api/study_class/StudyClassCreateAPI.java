package com.otc.kws.fishsix.lib.gateway.web.api.study_class;

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
import com.otc.kws.fishsix.lib.domain.entity.StudyClass;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule;
import com.otc.kws.fishsix.lib.domain.service.study_class.StudyClassCreateCommand;
import com.otc.kws.fishsix.lib.domain.service.study_class.StudyClassService;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/study-class/create")
public class StudyClassCreateAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected StudyClassService studyClassService;
	
	
	@PostMapping
	public ResponseEntity<?> createStudyClass(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### {}.createStudyClass ###", getClass().getSimpleName());
		
		StudyClassCreateCommand.Response createStudyClassResponse = studyClassService.createStudyClass(req -> {
			setupServiceRequest(request, req);
			req.setClassScheduleTemplateId(requestMessage.getClassScheduleTemplateId());
			req.setStudyClass(requestMessage.getStudyClass());
			req.setStudyClassSchedules(requestMessage.getStudyClassSchedules());
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setCreatedStudyClass(createStudyClassResponse.getCreatedStudyClass());
		bodyResponseMessage.setCreatedStudyClassSchedules(createStudyClassResponse.getCreatedStudyClassSchedules());
		bodyResponseMessage.setUpdatedStudentCourseQuotas(createStudyClassResponse.getUpdatedStudentCourseQuotas());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
    public static class RequestMessage
    {
		protected String classScheduleTemplateId;
		protected StudyClass studyClass;
		protected List<StudyClassSchedule> studyClassSchedules;
    }


    @Data
    public static class BodyResponseMessage extends ResponseMessage.BaseBody
    {
    	protected StudyClass createdStudyClass;
    	protected List<StudyClassSchedule> createdStudyClassSchedules;
		protected List<StudentCourseQuota> updatedStudentCourseQuotas;
    }
}