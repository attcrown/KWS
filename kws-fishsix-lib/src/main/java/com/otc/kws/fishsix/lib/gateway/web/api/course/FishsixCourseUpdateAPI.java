package com.otc.kws.fishsix.lib.gateway.web.api.course;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.fishsix.lib.domain.entity.Course;
import com.otc.kws.fishsix.lib.domain.entity.CourseSubjectCollection;
import com.otc.kws.fishsix.lib.domain.service.course.CourseService;
import com.otc.kws.fishsix.lib.domain.service.course.CourseUpdateCommand;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/course/update")
public class FishsixCourseUpdateAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected CourseService courseService;
	
	
	@PostMapping
	public ResponseEntity<?> createCourse(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		CourseUpdateCommand.Response updateCourseResponse = courseService.updateCourse(req -> {
			setupServiceRequest(request, req);
			req.setCourse(requestMessage.getCourse());
			req.setSubjectIds(requestMessage.getSubjectIds());
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setUpdatedCourse(updateCourseResponse.getUpdatedCourse());
		bodyResponseMessage.setCreatedCourseSubjectCollections(updateCourseResponse.getCreatedCourseSubjectCollections());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected Course course;
		protected List<String> subjectIds;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected Course updatedCourse;
		protected List<CourseSubjectCollection> createdCourseSubjectCollections;
	}
}