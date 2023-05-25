package com.otc.kws.fishsix.lib.gateway.web.api.course;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.fishsix.lib.domain.entity.CourseSubjectCollection;
import com.otc.kws.fishsix.lib.domain.service.course.CourseAddSubjectCommand;
import com.otc.kws.fishsix.lib.domain.service.course.CourseService;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/course/add-subject")
public class FishsixCourseAddSubjectAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected CourseService courseService;
	
	
	@PostMapping
	public ResponseEntity<?> addSubject(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### {}.addSubject ###", getClass().getSimpleName());
		logger.info("requestMessage => {}", requestMessage);
		
		CourseAddSubjectCommand.Response addSubjectResponse = courseService.addSubject(req -> {
			setupServiceRequest(request, req);
			req.setCourseSubjectCollection(requestMessage.getCourseSubjectCollection());
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setAddedCourseSubjectCollection(addSubjectResponse.getAddedCourseSubjectCollection());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected CourseSubjectCollection courseSubjectCollection;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected CourseSubjectCollection addedCourseSubjectCollection;
	}
}