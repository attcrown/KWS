package com.otc.kws.esc.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.esc.lib.domain.entity.CoursePackage;

@Repository
public interface JpaCoursePackageRepository extends KwsEscJpaRepository<CoursePackage, String>
{

}