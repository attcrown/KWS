package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.fishsix.lib.domain.entity.TeacherHireType;

@Repository
public interface JpaTeacherHireTypeRepository extends KwsFishsixJpaRepository<TeacherHireType, String>
{
	public List<TeacherHireType> findByStatus(MasterStatusValue status);
	
	public default List<TeacherHireType> findAllActive()
	{
		return findByStatus(MasterStatusValue.Active);
	}
}