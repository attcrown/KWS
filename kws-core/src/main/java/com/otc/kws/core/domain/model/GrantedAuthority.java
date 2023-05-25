package com.otc.kws.core.domain.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.otc.kws.core.domain.constant.value.ResourceTypeValue;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GrantedAuthority 
{
	protected List<Privilege> privileges;
	
	
	@Data
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class Privilege
	{
		protected String module;
		protected ResourceTypeValue resourceType;
		protected String resourceRefId;
		protected List<String> resourceActions;
	}
}