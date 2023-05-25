package com.otc.kws.firstjobber.lib.gateway.web.api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.firstjobber.lib.domain.entity.Applicant;
import com.otc.kws.firstjobber.lib.domain.entity.ApplicantAttribute;
import com.otc.kws.firstjobber.lib.domain.service.applicant.FjbApplicantAttributeService;
import com.otc.kws.firstjobber.lib.domain.service.applicant.FjbApplicantService;

@RestController
@RequestMapping("/api/firstjobber/test/txn")
public class FjbTestTxnAPI 
{
	@Autowired
	protected FjbApplicantService fjbApplicantService;
	
	@Autowired
	protected FjbApplicantAttributeService fjbApplicantAttributeService;
	
	
	@GetMapping
	@Transactional
	public Object testTxn(@RequestParam(required = false, defaultValue = "false") boolean error)
	{
		System.out.println("### FjbTestTxnAPI.testTxn ###");
		System.out.println("error => " + error);
		
		Applicant applicant = new Applicant();
		applicant.setId(UUID.randomUUID().toString());
		try {
			applicant = fjbApplicantService.create(applicant);
			System.out.println("create applicant => " + applicant);
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		
		if(error) {
			throw new KwsRuntimeException("Force ERROR for test");
		}
		
		ApplicantAttribute applicantAttribute = new ApplicantAttribute();
		applicantAttribute.setId(UUID.randomUUID().toString());
		try {
			applicantAttribute = fjbApplicantAttributeService.create(applicantAttribute);
			System.out.println("create applicantAttribute => " + applicantAttribute);
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		
		return "finish";
	}
}