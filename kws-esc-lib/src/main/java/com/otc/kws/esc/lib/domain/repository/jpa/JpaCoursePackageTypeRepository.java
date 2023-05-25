package com.otc.kws.esc.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.esc.lib.domain.entity.CoursePackageType;

@Repository
public interface JpaCoursePackageTypeRepository extends KwsEscJpaRepository<CoursePackageType, String>
{

}