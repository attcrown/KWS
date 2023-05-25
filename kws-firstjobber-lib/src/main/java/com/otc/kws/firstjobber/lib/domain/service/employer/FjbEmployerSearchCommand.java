package com.otc.kws.firstjobber.lib.domain.service.employer;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.firstjobber.lib.domain.dto.FjbEmployerSearchRequestDto;
import com.otc.kws.firstjobber.lib.domain.dto.FjbEmployerSearchResponseDto;

import lombok.Data;

public abstract class FjbEmployerSearchCommand extends BaseKwsCommand
{
	@Autowired
	protected FjbEmployerService fjbEmployerService;
	
	
	public abstract Response searchEmployer(Request request);
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected FjbEmployerSearchRequestDto employerSearchRequestDto;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected FjbEmployerSearchResponseDto employerSearchResponseDto;
	}
}