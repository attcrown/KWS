package com.otc.kws.core.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.NameTitle;

@Repository
public interface JpaNameTitleRepository extends KwsJpaRepository<NameTitle, String>
{
	public List<NameTitle> findByStatusOrderBySeqNoAsc(MasterStatusValue status);
	
	public default List<NameTitle> findAllActive()
	{
		return findByStatusOrderBySeqNoAsc(MasterStatusValue.Active);
	}
}