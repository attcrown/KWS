package com.otc.kws.core.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.EmployeeStatus;

@Repository
public interface JpaEmployeeStatusRepository extends KwsJpaRepository<EmployeeStatus, String>
{
	public List<EmployeeStatus> findByStatus(MasterStatusValue status);
	
	public default List<EmployeeStatus> findAllActive()
	{
		return findByStatus(MasterStatusValue.Active);
	}
}