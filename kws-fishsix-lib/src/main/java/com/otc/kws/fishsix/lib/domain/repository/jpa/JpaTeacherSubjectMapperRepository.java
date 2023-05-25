package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.fishsix.lib.domain.entity.TeacherSubjectMapper;

@Repository
public interface JpaTeacherSubjectMapperRepository extends KwsFishsixJpaRepository<TeacherSubjectMapper, String>
{
	public List<TeacherSubjectMapper> findByTeacherId(String teacherId);
	
	public List<TeacherSubjectMapper> findByTeacherIdIn(Collection<String> teacherIds);
	
	public default List<TeacherSubjectMapper> findByTeacherIds(Collection<String> teacherIds)
	{
		return findByTeacherIdIn(teacherIds);
	}
	
	public List<TeacherSubjectMapper> removeByTeacherId(String teacherId);
}