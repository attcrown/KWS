package com.otc.kws.esc.lib.gateway.web.api.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.esc.lib.domain.service.course.CourseService;
import com.otc.kws.esc.lib.gateway.web.api.BaseKwsEscAPI;

@RestController
@RequestMapping("/api/course/v1")
public class FetchAllCourseAPI extends BaseKwsEscAPI
{
	@Autowired
	protected CourseService courseService;
	
	
	@GetMapping
	public Object fetchAllCourse()
	{
		return courseService.findAll();
	}
}