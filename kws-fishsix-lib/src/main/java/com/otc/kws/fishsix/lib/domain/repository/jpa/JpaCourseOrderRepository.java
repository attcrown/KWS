package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.fishsix.lib.domain.entity.CourseOrder;

@Repository
public interface JpaCourseOrderRepository extends KwsFishsixJpaRepository<CourseOrder, String>
{
	public List<CourseOrder> findByCourseId(String courseId);
	
	public List<CourseOrder> findByStudentId(String studentId);
	
	public List<CourseOrder> findByCourseIdAndStudentId(String courseId, String studentId);
}