package com.otc.kws.fishsix.lib.gateway.web.api.student;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.fishsix.lib.domain.model.StudentCourseQuotaInfo;
import com.otc.kws.fishsix.lib.domain.service.student.FishsixStudentService;
import com.otc.kws.fishsix.lib.domain.service.student.course_quota.StudentCourseQuotaInfoLoadCommand;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/student/course-quota/info/load")
public class FishsixStudentCourseQuotaInfoLoadAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected FishsixStudentService studentService;
	
	
	@PostMapping
	public ResponseEntity<?> loadStudentCourseQuotaInfo(HttpServletRequest request, @RequestBody(required = false) RequestMessage requestMessage)
	{
		logger.info("### {}.loadStudentCourseQuotaInfo ###", getClass().getSimpleName());
		logger.info("requestMessage => {}", requestMessage);
		
		StudentCourseQuotaInfoLoadCommand.Response loadQuotaInfoResponse = studentService.loadStudentCourseQuotaInfo(req -> {
			setupServiceRequest(request, req);
			req.setStudentId(requestMessage.getStudentId());
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setStudentCourseQuotaInfos(loadQuotaInfoResponse.getStudentCourseQuotaInfos());
		bodyResponseMessage.setSummaryStudentCourseQuotaInfo(loadQuotaInfoResponse.getSummaryStudentCourseQuotaInfo());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage 
	{
		protected String studentId;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected List<StudentCourseQuotaInfo> studentCourseQuotaInfos;
		protected StudentCourseQuotaInfo summaryStudentCourseQuotaInfo;
	}
}