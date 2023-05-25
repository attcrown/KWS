package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.fishsix.lib.domain.entity.Teacher;

@Repository
public interface JpaTeacherRepository extends KwsFishsixJpaRepository<Teacher, String>
{
	public Teacher findByIdcardNo(String idcardNo);
	
	public List<Teacher> findByStatus(MasterStatusValue status);
	
	public List<Teacher> findByStatusIn(Collection<MasterStatusValue> statuses);
}