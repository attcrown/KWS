package com.otc.kws.esc.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.esc.lib.domain.entity.CourseScheduleDay;

@Repository
public interface JpaCourseScheduleDayRepository extends KwsEscJpaRepository<CourseScheduleDay, String>
{

}