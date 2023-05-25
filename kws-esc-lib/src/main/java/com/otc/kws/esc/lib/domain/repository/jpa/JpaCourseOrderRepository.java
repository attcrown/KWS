package com.otc.kws.esc.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.esc.lib.domain.entity.CourseOrder;

@Repository
public interface JpaCourseOrderRepository extends KwsEscJpaRepository<CourseOrder, String>
{

}