package com.otc.kws.firstjobber.lib.domain.service.internship;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.exception.EntityNotFoundException;
import com.otc.kws.firstjobber.lib.domain.entity.InternshipRegister;

@Component
public class InternshipUpdateRegisterStatusCommandV1 extends InternshipUpdateRegisterStatusCommand
{
	@Override
	public Response updateRegisterStatus(Request request) throws EntityNotFoundException
	{
		InternshipRegister internshipRegister = internshipRegisterService.findById(request.getInternshipRegisterId());
		
		if(internshipRegister == null) {
			throw new EntityNotFoundException("InternshipRegister with id [" + request.getInternshipRegisterId() + "] Not Found");
		}
		
		internshipRegister.setRegisterStatusId(request.getInternshipRegisterStatusId());
		
		switch (request.getInternshipRegisterStatusId()) {
			/*
			case Pending:
				break;
			*/

			case Reviewing:
				internshipRegister.setReviewedRemark(request.getRemark());
				internshipRegister.setReviewedBy(request.getPerformedBy());
				internshipRegister.setReviewedAt(request.getPerformedAt());
				doReview(request, internshipRegister);
				break;
				
			case Rejected:
				internshipRegister.setRejectedRemark(request.getRemark());
				internshipRegister.setRejectedBy(request.getPerformedBy());
				internshipRegister.setRejectedAt(request.getPerformedAt());
				doReject(request, internshipRegister);
				break;
				
			case Accepted:
				internshipRegister.setAcceptedRemark(request.getRemark());
				internshipRegister.setAcceptedBy(request.getPerformedBy());
				internshipRegister.setAcceptedAt(request.getPerformedAt());
				doAccept(request, internshipRegister);
				break;
		}
		
		InternshipRegister updatedInternshipRegister = internshipRegisterService.update(internshipRegister);
		logger.info("update internshipRegister => " + updatedInternshipRegister);
		
		Response response = new Response();
		response.setUpdatedInternshipRegister(updatedInternshipRegister);
		
		return response;
	}
	
	
	protected void doReview(Request request, InternshipRegister updatedInternshipRegister)
	{
		logger.info("### " + getClass().getSimpleName() + ".doReview ###");
	}
	
	
	protected void doAccept(Request request, InternshipRegister updatedInternshipRegister)
	{
		logger.info("### " + getClass().getSimpleName() + ".doAccept ###");
	}
	
	
	protected void doReject(Request request, InternshipRegister updatedInternshipRegister)
	{
		logger.info("### " + getClass().getSimpleName() + ".doReject ###");
	}
}