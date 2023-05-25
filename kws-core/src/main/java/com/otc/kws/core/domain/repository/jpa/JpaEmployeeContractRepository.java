package com.otc.kws.core.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.EmployeeContract;

@Repository
public interface JpaEmployeeContractRepository extends KwsJpaRepository<EmployeeContract, String>
{
	public List<EmployeeContract> findByEmployeeId(String employeeId);
	
	public List<EmployeeContract> findByUserId(String userId);
}