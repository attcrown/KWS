package com.otc.kws.fishsix.lib.domain.model;

import java.util.List;

import com.otc.kws.core.domain.entity.UserAccount;
import com.otc.kws.core.domain.entity.UserRoleMapper;
import com.otc.kws.fishsix.lib.domain.entity.Student;

import lombok.Data;

@Data
public class StudentProfile 
{
	protected Student student;
	protected UserAccount userAccount;
	protected List<UserRoleMapper> userRoleMappers;
}