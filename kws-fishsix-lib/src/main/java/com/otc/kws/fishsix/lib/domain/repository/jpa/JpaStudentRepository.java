package com.otc.kws.fishsix.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.fishsix.lib.domain.entity.Student;

@Repository
public interface JpaStudentRepository extends KwsFishsixJpaRepository<Student, String>
{

}