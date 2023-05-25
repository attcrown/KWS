package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule;

@Repository
public interface JpaStudyClassScheduleRepository extends KwsFishsixJpaRepository<StudyClassSchedule, String>
{
	public List<StudyClassSchedule> findByClassId(String classId);
	
	public List<StudyClassSchedule> findByClassIdIn(Collection<String> classIds);
	
	public default List<StudyClassSchedule> findByClassIds(Collection<String> classIds)
	{
		return findByClassIdIn(classIds);
	}
	
	public List<StudyClassSchedule> findByStudentId(String studentId);
	
	public List<StudyClassSchedule> findByTeacherId(String teacherId);
	
	public List<StudyClassSchedule> findByClassIdAndStudentId(String classId, String studentId);
	
	public List<StudyClassSchedule> findByClassIdAndTeacherId(String classId, String teacherId);
	
	public List<StudyClassSchedule> findByStudentCourseQuotaId(String studentCourseQuotaId);
	
	public List<StudyClassSchedule> findByStudentCourseQuotaIdIn(Collection<String> studentCourseQuotaIds);
	
	public StudyClassSchedule findByClassIdAndStudentIdAndTeacherId(String classId, String studentId, String teacherId);
	
	public default List<StudyClassSchedule> findByStudentCourseQuotaIds(Collection<String> studentCourseQuotaIds)
	{
		return findByStudentCourseQuotaIdIn(studentCourseQuotaIds);
	}
}