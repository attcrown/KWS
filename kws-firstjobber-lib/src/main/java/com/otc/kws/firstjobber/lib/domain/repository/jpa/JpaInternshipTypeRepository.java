package com.otc.kws.firstjobber.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.firstjobber.lib.domain.entity.InternshipType;

@Repository
public interface JpaInternshipTypeRepository extends KwsFirstJobberJpaRepository<InternshipType, String>
{

}