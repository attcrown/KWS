package com.otc.kws.firstjobber.lib.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.firstjobber.lib.domain.entity.Job;

@Repository
public interface JpaJobRepository extends KwsFirstJobberJpaRepository<Job, String>
{
	public List<Job> findByStatus(MasterStatusValue status);
	
	public default List<Job> findAllActive()
	{
		return findByStatus(MasterStatusValue.Active);
	}
}