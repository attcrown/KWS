package com.otc.kws.esc.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.esc.lib.domain.entity.CourseOrderStatus;

@Repository
public interface JpaCourseOrderStatusRepository extends KwsEscJpaRepository<CourseOrderStatus, String>
{

}