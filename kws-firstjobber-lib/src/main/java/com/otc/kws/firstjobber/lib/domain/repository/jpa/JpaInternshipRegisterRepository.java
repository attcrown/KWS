package com.otc.kws.firstjobber.lib.domain.repository.jpa;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.firstjobber.lib.domain.constant.value.InternshipRegisterStatusValue;
import com.otc.kws.firstjobber.lib.domain.entity.InternshipRegister;

@Repository
public interface JpaInternshipRegisterRepository extends KwsFirstJobberJpaRepository<InternshipRegister, String>
{
	public List<InternshipRegister> findByRegisterStatusId(InternshipRegisterStatusValue registerStatusId);
	public List<InternshipRegister> findByRegisterStatusIdIn(List<InternshipRegisterStatusValue> registerStatusIds);
	
	public default List<InternshipRegister> findByRegisterStatusIds(List<InternshipRegisterStatusValue> registerStatusIds)
	{
		return findByRegisterStatusIdIn(registerStatusIds);
	}
	
	/*
	public default List<InternshipRegister> findAllPendingRegisterStatus()
	{
		return findByRegisterStatusId(InternshipRegisterStatusValue.Pending);
	}
	*/
	
	public default List<InternshipRegister> findAllReviewingRegisterStatus()
	{
		return findByRegisterStatusId(InternshipRegisterStatusValue.Reviewing);
	}
	
	/*
	public default List<InternshipRegister> findAllPendingOrReviewingRegisterStatus()
	{
		return findByRegisterStatusIds(Arrays.asList(InternshipRegisterStatusValue.Pending, InternshipRegisterStatusValue.Reviewing));
	}
	*/
	
	public default List<InternshipRegister> findAllAcceptedRegisterStatus()
	{
		return findByRegisterStatusId(InternshipRegisterStatusValue.Accepted);
	}
	
	public default List<InternshipRegister> findAllRejectedRegisterStatus()
	{
		return findByRegisterStatusId(InternshipRegisterStatusValue.Rejected);
	}
}