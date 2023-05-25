package com.otc.kws.esc.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.esc.lib.domain.entity.CourseSchedule;

@Repository
public interface JpaCourseScheduleRepository extends KwsEscJpaRepository<CourseSchedule, String>
{

}