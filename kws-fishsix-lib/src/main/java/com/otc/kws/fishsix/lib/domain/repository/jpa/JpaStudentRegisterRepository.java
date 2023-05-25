package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.fishsix.lib.domain.entity.StudentRegister;

@Repository
public interface JpaStudentRegisterRepository extends KwsFishsixJpaRepository<StudentRegister, String>
{
	public List<StudentRegister> findByRegisterStatus(StudentRegister.RegisterStatus registerStatus);
}