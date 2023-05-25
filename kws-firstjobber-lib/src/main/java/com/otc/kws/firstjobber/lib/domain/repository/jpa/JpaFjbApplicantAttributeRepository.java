package com.otc.kws.firstjobber.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.firstjobber.lib.domain.entity.ApplicantAttribute;

@Repository
public interface JpaFjbApplicantAttributeRepository extends KwsFirstJobberJpaRepository<ApplicantAttribute, String>
{

}