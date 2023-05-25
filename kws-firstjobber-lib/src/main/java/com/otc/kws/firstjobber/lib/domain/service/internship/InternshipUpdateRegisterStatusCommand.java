package com.otc.kws.firstjobber.lib.domain.service.internship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.otc.kws.core.domain.exception.EntityNotFoundException;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.firstjobber.lib.domain.constant.value.InternshipRegisterStatusValue;
import com.otc.kws.firstjobber.lib.domain.entity.InternshipRegister;
import com.otc.kws.firstjobber.lib.domain.service.InternshipRegisterService;

import lombok.Data;

public abstract class InternshipUpdateRegisterStatusCommand extends BaseKwsCommand
{
	@Autowired
	@Lazy
	protected InternshipRegisterService internshipRegisterService;
	
	
	public abstract Response updateRegisterStatus(Request request) throws EntityNotFoundException;
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String internshipRegisterId;
		protected InternshipRegisterStatusValue internshipRegisterStatusId;
		protected String remark;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected InternshipRegister updatedInternshipRegister;
	}
}