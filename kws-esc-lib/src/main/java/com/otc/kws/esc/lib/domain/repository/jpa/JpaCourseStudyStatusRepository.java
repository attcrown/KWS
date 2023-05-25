package com.otc.kws.esc.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.esc.lib.domain.entity.CourseStudyStatus;

@Repository
public interface JpaCourseStudyStatusRepository extends KwsEscJpaRepository<CourseStudyStatus, String>
{

}