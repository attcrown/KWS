package com.otc.kws.esc.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.esc.lib.domain.entity.CourseScheduleTime;

@Repository
public interface JpaCourseScheduleTimeRepository extends KwsEscJpaRepository<CourseScheduleTime, String>
{

}