package com.otc.kws.firstjobber.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.firstjobber.lib.domain.entity.JobCategory;

@Repository
public interface JpaJobCategoryRepository extends KwsFirstJobberJpaRepository<JobCategory, String>
{

}