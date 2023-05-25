package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.fishsix.lib.domain.entity.TeacherHourAcquired;

@Repository
public interface JpaTeacherHourAcquiredRepository extends KwsFishsixJpaRepository<TeacherHourAcquired, String>
{
	public TeacherHourAcquired findByTeacherIdAndClassScheduleId(String teacherId, String classScheduleId);
	
	public List<TeacherHourAcquired> findByTeacherId(String teacherId);
	
	public List<TeacherHourAcquired> findByClassScheduleId(String classScheduleId);
	
	public List<TeacherHourAcquired> findByAcquiredFromCourseQuotaId(String acquiredByCourseQuotaId);
	
	public List<TeacherHourAcquired> findByAcquiredFromStudentId(String acquiredByStudentId);
	
	public List<TeacherHourAcquired> removeByTeacherIdAndClassScheduleId(String teacherId, String classScheduleId);
}