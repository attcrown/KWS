package com.otc.kws.fishsix.lib.domain.model;

import java.util.List;

import com.otc.kws.core.domain.entity.Role;
import com.otc.kws.core.domain.entity.UserAccount;
import com.otc.kws.core.domain.entity.UserRoleMapper;
import com.otc.kws.fishsix.lib.domain.entity.Teacher;
import com.otc.kws.fishsix.lib.domain.entity.TeacherSubjectMapper;

import lombok.Data;

@Data
public class TeacherProfile 
{
	protected Teacher teacher;
	protected List<TeacherSubjectMapper> teacherSubjectMappers;
	protected UserAccount userAccount;
	protected List<UserRoleMapper> userRoleMappers;
	protected List<Role> roles;
	protected Attachment attachment;
	
	
	@Data
	public static class Attachment
	{
		protected String idCardFileURL;
	}
}