package com.otc.kws.firstjobber.lib.domain.profile;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.otc.kws.firstjobber.lib.domain.entity.FjbAddress;
import com.otc.kws.firstjobber.lib.domain.entity.FjbEmployer;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FjbEmployerProfile 
{
	protected FjbEmployer employer;
	protected FjbAddress contactAddress;
	protected List<FjbEmployerUserProfile> employerUserProfiles;
}