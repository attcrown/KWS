package com.otc.kws.firstjobber.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.firstjobber.lib.domain.entity.InternshipRegisterStatus;

@Repository
public interface JpaInternshipRegisterStatusRepository extends KwsFirstJobberJpaRepository<InternshipRegisterStatus, String>
{

}