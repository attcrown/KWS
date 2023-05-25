package com.otc.kws.fishsix.lib.domain.service.course;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.Course;
import com.otc.kws.fishsix.lib.domain.entity.CourseSubjectCollection;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaCourseRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaCourseSubjectCollectionRepository;

import lombok.Data;

@Component
public class CourseCreateCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaCourseRepository courseRepository;
	
	@Autowired
	protected JpaCourseSubjectCollectionRepository courseSubjectCollectionRepository;
	
	
	public Response createCourse(Request request)
	{
		logger.info("### {}.createCourse ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		if(request.getCourse() == null) {
			return response;
		}
		
		if(request.getCourse().getId() == null) {
			request.getCourse().setId(UUID.randomUUID().toString());
		}
		if(request.getCourse().getStatus() == null) {
			request.getCourse().setStatus(MasterStatusValue.Active);
		}
		request.getCourse().setCreatedBy(request.getPerformedBy());
		request.getCourse().setCreatedAt(request.getPerformedAt());
		request.getCourse().setLastModifiedBy(request.getPerformedBy());
		request.getCourse().setLastModifiedAt(request.getPerformedAt());
		
		Course createdCourse = courseRepository.save(request.getCourse());		
		logger.info("create course => {}", createdCourse);
		
		response.setCreatedCourse(createdCourse);
		
		if(request.getSubjectIds() != null && !request.getSubjectIds().isEmpty()) {
			List<CourseSubjectCollection> courseSubjectCollections = new ArrayList<>();
			
			for(String subjectId : request.getSubjectIds()) {
				CourseSubjectCollection courseSubjectCollection = new CourseSubjectCollection();
				courseSubjectCollection.setId(UUID.randomUUID().toString());
				courseSubjectCollection.setCourseId(createdCourse.getId());
				courseSubjectCollection.setSubjectId(subjectId);
				courseSubjectCollection.setStatus(MasterStatusValue.Active);
				courseSubjectCollection.setCreatedBy(request.getPerformedBy());
				courseSubjectCollection.setCreatedAt(request.getPerformedAt());
				courseSubjectCollection.setLastModifiedBy(request.getPerformedBy());
				courseSubjectCollection.setLastModifiedAt(request.getPerformedAt());
				
				courseSubjectCollectionRepository.save(courseSubjectCollection);
				logger.info("create courseSubjectCollection => {}", courseSubjectCollection);
				
				courseSubjectCollections.add(courseSubjectCollection);
			}
			
			response.setCreatedCourseSubjectCollections(courseSubjectCollections);
		}
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected Course course;
		protected List<String> subjectIds;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected Course createdCourse;
		protected List<CourseSubjectCollection> createdCourseSubjectCollections;
	}
}