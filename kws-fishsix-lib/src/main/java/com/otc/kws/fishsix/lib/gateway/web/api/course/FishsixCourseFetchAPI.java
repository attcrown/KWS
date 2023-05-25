package com.otc.kws.fishsix.lib.gateway.web.api.course;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.fishsix.lib.domain.model.CourseProfile;
import com.otc.kws.fishsix.lib.domain.service.course.CourseFetchCommand;
import com.otc.kws.fishsix.lib.domain.service.course.CourseService;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/course/fetch")
public class FishsixCourseFetchAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected CourseService courseService;
	
	
	@PostMapping
	public ResponseEntity<?> fetchCourse(HttpServletRequest request, @RequestBody(required = false) RequestMessage requestMessage)
	{
		logger.info("### {}.fetchCourse ###", getClass().getSimpleName());
		logger.info("requestMessage => {}", requestMessage);
		
		CourseFetchCommand.Response fetchCourseResponse = courseService.fetchCourse(req -> {
			setupServiceRequest(request, req);
			
			if(requestMessage != null) {
				req.setCriteria(requestMessage.getCriteria());
			}
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setCourseProfiles(fetchCourseResponse.getCourseProfiles());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected CourseFetchCommand.Request.Criteria criteria;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected List<CourseProfile> courseProfiles;
	}
}