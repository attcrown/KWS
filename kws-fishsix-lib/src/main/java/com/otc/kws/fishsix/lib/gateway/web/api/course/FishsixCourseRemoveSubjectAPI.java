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
import com.otc.kws.fishsix.lib.domain.service.course.CourseRemoveSubjectCommand;
import com.otc.kws.fishsix.lib.domain.service.course.CourseService;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/course/remove-subject")
public class FishsixCourseRemoveSubjectAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected CourseService courseService;
	
	
	@PostMapping
	public ResponseEntity<?> removeSubject(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### {}.removeSubject ###", getClass().getSimpleName());
		logger.info("requestMessage => {}", requestMessage);
		
		CourseRemoveSubjectCommand.Response removeSubjectResponse = courseService.removeSubject(req -> {
			setupServiceRequest(request, req);
			req.setCourseSubjectCollectionId(requestMessage.getCourseSubjectCollectionId());
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setRemovedCourseSubjectCollection(removeSubjectResponse.getRemovedCourseSubjectCollection());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String courseSubjectCollectionId;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected CourseSubjectCollection removedCourseSubjectCollection;
	}
}