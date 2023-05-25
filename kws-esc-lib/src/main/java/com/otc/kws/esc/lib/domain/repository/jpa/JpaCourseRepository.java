package com.otc.kws.esc.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.esc.lib.domain.entity.Course;

@Repository
public interface JpaCourseRepository extends KwsEscJpaRepository<Course, String>
{

}