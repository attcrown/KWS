package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.fishsix.lib.domain.entity.WageRateType;

@Repository
public interface JpaWageRateTypeRepository extends KwsFishsixJpaRepository<WageRateType, String>
{
	public List<WageRateType> findByStatusOrderBySeqNoAsc(MasterStatusValue status);
	
	public default List<WageRateType> findAllActive()
	{
		return findByStatusOrderBySeqNoAsc(MasterStatusValue.Active);
	}
}