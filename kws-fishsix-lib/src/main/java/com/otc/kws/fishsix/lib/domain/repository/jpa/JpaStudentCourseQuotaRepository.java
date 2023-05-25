package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.otc.kws.fishsix.lib.domain.entity.StudentCourseQuota;

@Repository
public interface JpaStudentCourseQuotaRepository extends KwsFishsixJpaRepository<StudentCourseQuota, String>
{
	public List<StudentCourseQuota> findByCourseOrderId(String courseOrderId);
	
	public List<StudentCourseQuota> findByCourseId(String courseId);
	
	public List<StudentCourseQuota> findByStudentId(String studentId);
	
	@Query("FROM StudentCourseQuota e WHERE e.studentId = :studentId AND :date >= e.activatedAt AND :date <= e.expiredAt")
	public List<StudentCourseQuota> findActiveQuotaByStudentId(@Param("studentId") String studentId, @Param("date") Date date);
	
	public default List<StudentCourseQuota> findActiveQuotaByStudentId(String studentId)
	{
		return findActiveQuotaByStudentId(studentId, new Date());
	}
}