package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.fishsix.lib.domain.entity.EvaluationFactor;

@Repository
public interface JpaEvaluationFactorRepository extends KwsFishsixJpaRepository<EvaluationFactor, String>
{
	public List<EvaluationFactor> findByStatus(MasterStatusValue status, Sort sort);
	
	public default List<EvaluationFactor> findAllActive()
	{
		return findByStatus(MasterStatusValue.Active, Sort.by(Order.asc("seqNo")));
	}
}