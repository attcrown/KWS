package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.fishsix.lib.domain.entity.Subject;

@Repository
public interface JpaSubjectRepository extends KwsFishsixJpaRepository<Subject, String>
{
	public List<Subject> findByStatus(MasterStatusValue status);
	
	public default List<Subject> findAllActive()
	{
		return findByStatus(MasterStatusValue.Active);
	}
}