package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.fishsix.lib.domain.entity.EducationClass;

@Repository
public interface JpaEducationClassRepository extends KwsFishsixJpaRepository<EducationClass, String>
{
	public List<EducationClass> findByStatus(MasterStatusValue status);
	
	public default List<EducationClass> findAllActive()
	{
		return findByStatus(MasterStatusValue.Active);
	}
}