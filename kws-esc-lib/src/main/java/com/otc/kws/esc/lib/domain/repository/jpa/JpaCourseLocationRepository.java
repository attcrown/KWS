package com.otc.kws.esc.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.esc.lib.domain.entity.CourseLocation;

@Repository
public interface JpaCourseLocationRepository extends KwsEscJpaRepository<CourseLocation, String>
{

}