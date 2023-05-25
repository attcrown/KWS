package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.fishsix.lib.domain.entity.TeacherRegister;

@Repository
public interface JpaTeacherRegisterRepository extends KwsFishsixJpaRepository<TeacherRegister, String>
{
	public List<TeacherRegister> findByRegisterStatus(TeacherRegister.RegisterStatus registerStatus);
}