package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassLocation;

@Repository
public interface JpaStudyClassLocationRepository extends KwsFishsixJpaRepository<StudyClassLocation, String>
{
	public List<StudyClassLocation> findByStatus(MasterStatusValue status);
	
	public default List<StudyClassLocation> findAllActive()
	{
		return findByStatus(MasterStatusValue.Active);
	}
}