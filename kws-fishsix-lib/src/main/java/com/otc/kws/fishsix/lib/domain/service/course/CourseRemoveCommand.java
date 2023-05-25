package com.otc.kws.fishsix.lib.domain.service.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.Course;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaCourseRepository;

import lombok.Data;

@Component
public class CourseRemoveCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaCourseRepository courseRepository;
	
	
	public Response removeCourse(Request request)
	{
		logger.info("### {}.removeCourse ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		if(request.getCourseId() == null) {
			throw new KwsRuntimeException("Course id is null");
		}
		
		Course removedCourse = courseRepository.findById(request.getCourseId()).orElse(null);
		
		if(removedCourse != null) {
			if(request.isPhysicalRemove()) {
				courseRepository.delete(removedCourse);
				logger.info("remove course => {}", removedCourse);
			} else {
				removedCourse.setStatus(MasterStatusValue.Closed);
				removedCourse.setLastModifiedBy(request.getPerformedBy());
				removedCourse.setLastModifiedAt(request.getPerformedAt());
				
				courseRepository.save(removedCourse);
				logger.info("update course => {}", removedCourse);
			}
			
			response.setRemovedCourse(removedCourse);
		}
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String courseId;
		protected boolean physicalRemove = false;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected Course removedCourse;
	}
}