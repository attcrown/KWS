package com.otc.kws.core.domain.repository.jpa;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.Gender;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface JpaGenderRepository extends KwsJpaRepository<Gender, String>
{
	public List<Gender> findByStatusOrderBySeqNoAsc(MasterStatusValue status);
	
	public default List<Gender> findAllActive()
	{
		return findByStatusOrderBySeqNoAsc(MasterStatusValue.Active);
	}
}