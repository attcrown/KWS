package com.otc.kws.firstjobber.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.firstjobber.lib.domain.entity.FjbInternshipRegisterTest;

@Repository
public interface JpaFjbInternshipRegisterTestRepository extends KwsFirstJobberJpaRepository<FjbInternshipRegisterTest, String>
{
	public FjbInternshipRegisterTest findTopByInternshipRegisterIdAndStatusOrderByCreatedAtDesc(String internshipRegisterId, MasterStatusValue status);
	
	public default FjbInternshipRegisterTest findLatestTest(String internshipRegisterId)
	{
		return findTopByInternshipRegisterIdAndStatusOrderByCreatedAtDesc(internshipRegisterId, MasterStatusValue.Active);
	}
}