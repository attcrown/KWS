package com.otc.kws.fishsix.lib.domain.model;

import java.util.List;

import com.otc.kws.fishsix.lib.domain.entity.Course;
import com.otc.kws.fishsix.lib.domain.entity.Subject;

import lombok.Data;

@Data
public class CourseProfile 
{
	protected Course course;
	protected List<Subject> subjects;
}