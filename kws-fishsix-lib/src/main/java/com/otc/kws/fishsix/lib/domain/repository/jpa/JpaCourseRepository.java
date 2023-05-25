package com.otc.kws.fishsix.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.fishsix.lib.domain.entity.Course;

@Repository
public interface JpaCourseRepository extends KwsFishsixJpaRepository<Course, String>
{

}