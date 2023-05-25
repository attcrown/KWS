package com.otc.kws.firstjobber.lib.domain.profile;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.otc.kws.core.domain.entity.UserAccount;
import com.otc.kws.core.domain.entity.UserPersonalInfo;
import com.otc.kws.core.domain.entity.UserRoleMapper;
import com.otc.kws.firstjobber.lib.domain.entity.FjbEmployerUser;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FjbEmployerUserProfile 
{
	protected FjbEmployerUser employerUser;
	protected UserAccount userAccount;
	protected List<UserRoleMapper> userRoleMappers;
	protected UserPersonalInfo userPersonalInfo;
}