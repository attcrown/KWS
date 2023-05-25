package com.otc.kws.firstjobber.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.firstjobber.lib.domain.entity.Applicant;

@Repository
public interface JpaFjbApplicantRepository extends KwsFirstJobberJpaRepository<Applicant, String>
{

}