package com.otc.kws.esc.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.esc.lib.domain.entity.CourseStudyClass;

@Repository
public interface JpaCourseStudyClassRepository extends KwsEscJpaRepository<CourseStudyClass, String>
{

}