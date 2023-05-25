package com.otc.kws.fishsix.lib.domain.service.course;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.Course;
import com.otc.kws.fishsix.lib.domain.entity.CourseSubjectCollection;
import com.otc.kws.fishsix.lib.domain.entity.Subject;
import com.otc.kws.fishsix.lib.domain.model.CourseProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaCourseRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaCourseSubjectCollectionRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectRepository;

import lombok.Data;

@Component
public class CourseFetchCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaCourseRepository courseRepository;
	
	@Autowired
	protected JpaSubjectRepository subjectRepository;
	
	@Autowired
	protected JpaCourseSubjectCollectionRepository courseSubjectCollectionRepository;
	
	
	public Response fetchCourse(Request request)
	{
		logger.info("### {}.fetchCourse ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		List<Course> courses = courseRepository.findAll();
		
		if(courses != null && !courses.isEmpty() && request.getCriteria() != null) {
			List<String> courseIds = courses.stream().map(Course::getId).collect(Collectors.toList());
			List<CourseSubjectCollection> courseSubjectCollections = courseSubjectCollectionRepository.findByCourseIdIn(courseIds);
			
			List<MasterStatusValue> statuses = request.getCriteria().getStatuses();
			List<String> classChannelIds = request.getCriteria().getClassChannelIds();
			List<String> classTypeIds = request.getCriteria().getClassTypeIds();
			List<Integer> classHours = request.getCriteria().getClassHours();
			List<String> subjectIds = request.getCriteria().getSubjectIds();
			
			courses = courses.stream().
					filter(e -> {
						if(statuses != null && !statuses.isEmpty()) {
							return statuses.contains(e.getStatus());
						}
						return true;
					}).
					filter(e -> {
						if(classChannelIds != null && !classChannelIds.isEmpty()) {
							return classChannelIds.contains(e.getClassChannelId());
						}
						return true;
					}).
					filter(e -> {
						if(classTypeIds != null && !classTypeIds.isEmpty()) {
							return classTypeIds.contains(e.getClassTypeId());
						}
						return true;
					}).
					filter(e -> {
						if(classHours != null && !classHours.isEmpty()) {
							return classHours.contains(e.getHour());
						}
						return true;
					}).
					filter(e -> {
						if(subjectIds != null && !subjectIds.isEmpty() && courseSubjectCollections != null && !courseSubjectCollections.isEmpty()) {
							List<String> _subjectIds = courseSubjectCollections.stream().
									filter(e1 -> e1.getCourseId().equals(e.getId())).
									map(CourseSubjectCollection::getSubjectId).
									collect(Collectors.toList());
							
							if(_subjectIds != null && !_subjectIds.isEmpty()) {
								return _subjectIds.stream().anyMatch(s -> subjectIds.contains(s));
							}
						}
						return true;
					}).
					collect(Collectors.toList());
		}
		
		if(courses != null && !courses.isEmpty()) {
			List<CourseProfile> courseProfiles = new ArrayList<>();
			
			List<String> courseIds = courses.stream().map(Course::getId).distinct().collect(Collectors.toList());
			List<CourseSubjectCollection> courseSubjectCollections = courseSubjectCollectionRepository.findByCourseIdIn(courseIds);
			List<String> subjectIds = courseSubjectCollections.stream().map(CourseSubjectCollection::getSubjectId).collect(Collectors.toList());
			List<Subject> subjects = subjectRepository.findAllById(subjectIds);
			
			for(Course course : courses) {
				List<CourseSubjectCollection> _courseSubjectCollections = courseSubjectCollections.
						stream().
						filter(e -> e.getCourseId().equals(course.getId())).
						collect(Collectors.toList());
				
				List<String> _subjectIds = _courseSubjectCollections.
						stream().
						map(CourseSubjectCollection::getSubjectId).
						collect(Collectors.toList());
				
				List<Subject> _subjects = subjects.
						stream().
						filter(e -> _subjectIds.contains(e.getId())).
						collect(Collectors.toList());
				
				CourseProfile courseProfile = new CourseProfile();
				courseProfile.setCourse(course);
				courseProfile.setSubjects(_subjects);
				
				courseProfiles.add(courseProfile);
			}
			
			response.setCourseProfiles(courseProfiles);
		}
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected Criteria criteria;
		
		
		@Data
		public static class Criteria
		{
			protected List<MasterStatusValue> statuses;
			protected List<String> classChannelIds;
			protected List<String> classTypeIds;
			protected List<Integer> classHours;
			protected List<String> subjectIds;
		}
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected List<CourseProfile> courseProfiles;
	}
}