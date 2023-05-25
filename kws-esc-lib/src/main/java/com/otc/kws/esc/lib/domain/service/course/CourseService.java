package com.otc.kws.esc.lib.domain.service.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.esc.lib.domain.entity.Course;
import com.otc.kws.esc.lib.domain.repository.jpa.JpaCourseRepository;
import com.otc.kws.esc.lib.domain.service.BaseKwsEscService;

@Service
public class CourseService extends BaseKwsEscService
{
	@Autowired
	protected JpaCourseRepository courseRepository;
	
	
	public List<Course> findAll()
	{
		return courseRepository.findAll();
	}
}