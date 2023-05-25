package com.otc.kws.esc.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.esc.lib.domain.entity.CourseType;

@Repository
public interface JpaCourseTypeRepository extends KwsEscJpaRepository<CourseType, String>
{

}