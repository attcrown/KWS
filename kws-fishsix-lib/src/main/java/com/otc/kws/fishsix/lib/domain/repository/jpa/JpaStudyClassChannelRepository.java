package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassChannel;

@Repository
public interface JpaStudyClassChannelRepository extends KwsFishsixJpaRepository<StudyClassChannel, String>
{
	public List<StudyClassChannel> findByStatus(MasterStatusValue status);
	
	public default List<StudyClassChannel> findAllActive()
	{
		return findByStatus(MasterStatusValue.Active);
	}
}