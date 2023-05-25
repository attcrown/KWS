package com.otc.kws.core.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.Salesman;

@Repository
public interface JpaSalesmanRepository extends KwsJpaRepository<Salesman, String>
{
	public Salesman findByUserId(String userId);
	
	public Salesman findByIdcardNo(String idcardNo);
	
	public List<Salesman> findByStatus(MasterStatusValue status);
	
	public default List<Salesman> findAllActive()
	{
		return findByStatus(MasterStatusValue.Active);
	}
}