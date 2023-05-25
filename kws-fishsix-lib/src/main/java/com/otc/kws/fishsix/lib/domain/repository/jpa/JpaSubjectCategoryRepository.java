package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.fishsix.lib.domain.entity.SubjectCategory;

@Repository
public interface JpaSubjectCategoryRepository extends KwsFishsixJpaRepository<SubjectCategory, String>
{
	public List<SubjectCategory> findByStatus(MasterStatusValue status);
	
	public default List<SubjectCategory> findAllActive()
	{
		return findByStatus(MasterStatusValue.Active);
	}
}