package com.otc.kws.core.domain.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.EducationClassLevel;

@Repository
public interface JpaEducationClassLevelRepository extends KwsJpaRepository<EducationClassLevel, String>
{
	public List<EducationClassLevel> findByStatus(MasterStatusValue status);
	
	public List<EducationClassLevel> findByStatus(MasterStatusValue status, Sort sort);
	
	public default List<EducationClassLevel> findAllActive()
	{
		return findByStatus(MasterStatusValue.Active);
	}
	
	public default List<EducationClassLevel> findAllActiveOrderBySeqNo()
	{
		return findByStatus(MasterStatusValue.Active, Sort.by(Direction.ASC, "seqNo"));
	}
}