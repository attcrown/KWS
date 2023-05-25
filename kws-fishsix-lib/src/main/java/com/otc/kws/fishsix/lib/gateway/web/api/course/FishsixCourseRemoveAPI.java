package com.otc.kws.fishsix.lib.gateway.web.api.course;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.fishsix.lib.domain.entity.Course;
import com.otc.kws.fishsix.lib.domain.service.course.CourseRemoveCommand;
import com.otc.kws.fishsix.lib.domain.service.course.CourseService;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/course/remove")
public class FishsixCourseRemoveAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected CourseService courseService;
	
	
	@PostMapping
	public ResponseEntity<?> createCourse(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		CourseRemoveCommand.Response removeCourseResponse = courseService.removeCourse(req -> {
			setupServiceRequest(request, req);
			req.setCourseId(requestMessage.getCourseId());
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setRemovedCourse(removeCourseResponse.getRemovedCourse());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String courseId;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected Course removedCourse;
	}
}