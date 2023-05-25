package com.otc.kws.esc.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.esc.lib.domain.entity.CourseStudentQuota;

@Repository
public interface JpaCourseStudentQuotaRepository extends KwsEscJpaRepository<CourseStudentQuota, String>
{

}