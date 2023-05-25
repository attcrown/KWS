package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.fishsix.lib.domain.entity.TeacherWorktimeType;

@Repository
public interface JpaTeacherWorktimeTypeRepository extends KwsFishsixJpaRepository<TeacherWorktimeType, String>
{
	public List<TeacherWorktimeType> findByStatus(MasterStatusValue status);
	
	public default List<TeacherWorktimeType> findAllActive()
	{
		return findByStatus(MasterStatusValue.Active);
	}
}