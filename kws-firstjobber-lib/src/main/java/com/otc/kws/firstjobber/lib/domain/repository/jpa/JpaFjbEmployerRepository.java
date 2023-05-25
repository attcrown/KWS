package com.otc.kws.firstjobber.lib.domain.repository.jpa;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.firstjobber.lib.domain.entity.FjbEmployer;

@Repository
public interface JpaFjbEmployerRepository extends KwsFirstJobberJpaRepository<FjbEmployer, String>
{
	public Page<FjbEmployer> findByEmployerTypeIdIn(Collection<String> employerTypeIds, Pageable pageable);
	public Page<FjbEmployer> findByNameContains(String name, Pageable pageable);
	public Page<FjbEmployer> findByStatusIn(Collection<MasterStatusValue> statuses, Pageable pageable);
	
	public Page<FjbEmployer> findByEmployerTypeIdInAndNameContains(Collection<String> employerTypeIds, String name, Pageable pageable);
	public Page<FjbEmployer> findByEmployerTypeIdInAndStatusIn(Collection<String> employerTypeIds, Collection<MasterStatusValue> statuses, Pageable pageable);
	public Page<FjbEmployer> findByNameContainsAndStatusIn(String name, Collection<MasterStatusValue> statuses, Pageable pageable);
	
	public Page<FjbEmployer> findByEmployerTypeIdInAndNameContainsAndStatusIn(Collection<String> employerTypeIds, String name, Collection<MasterStatusValue> statuses, Pageable pageable);
}