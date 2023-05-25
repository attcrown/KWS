package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassType;

@Repository
public interface JpaStudyClassTypeRepository extends KwsFishsixJpaRepository<StudyClassType, String>
{
	public List<StudyClassType> findByStatus(MasterStatusValue status);
	
	public default List<StudyClassType> findAllActive()
	{
		return findByStatus(MasterStatusValue.Active);
	}
}