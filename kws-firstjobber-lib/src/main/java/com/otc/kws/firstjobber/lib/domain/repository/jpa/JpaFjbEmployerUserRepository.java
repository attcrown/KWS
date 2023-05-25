package com.otc.kws.firstjobber.lib.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.firstjobber.lib.domain.entity.FjbEmployerUser;

@Repository
public interface JpaFjbEmployerUserRepository extends KwsFirstJobberJpaRepository<FjbEmployerUser, String>
{
	public List<FjbEmployerUser> findByEmployerId(String employerId);
}