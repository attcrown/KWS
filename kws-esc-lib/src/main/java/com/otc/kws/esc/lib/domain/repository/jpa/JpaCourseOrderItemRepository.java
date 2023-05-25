package com.otc.kws.esc.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.esc.lib.domain.entity.CourseOrderItem;

@Repository
public interface JpaCourseOrderItemRepository extends KwsEscJpaRepository<CourseOrderItem, String>
{

}