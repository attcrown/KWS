package com.otc.kws.esc.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.esc.lib.domain.entity.CourseLocationType;

@Repository
public interface JpaCourseLocationTypeRepository extends KwsEscJpaRepository<CourseLocationType, String>
{

}