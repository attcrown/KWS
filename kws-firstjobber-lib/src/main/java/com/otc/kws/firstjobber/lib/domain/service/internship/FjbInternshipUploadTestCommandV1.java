package com.otc.kws.firstjobber.lib.domain.service.internship;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.EmailTemplate;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.firstjobber.lib.domain.constant.value.InternshipRegisterStatusValue;
import com.otc.kws.firstjobber.lib.domain.entity.FjbInternshipRegisterTest;
import com.otc.kws.firstjobber.lib.domain.entity.InternshipRegister;

@Component
public class FjbInternshipUploadTestCommandV1 extends FjbInternshipUploadTestCommand
{
	@Override
	public Response uploadTest(Request request) 
	{
		Response response = new Response();
		
		String internshipRegisterId = request.getInternshipRegisterTest().getInternshipRegisterId();
		
		InternshipRegister internshipRegister = internshipRegisterService.findById(internshipRegisterId);;
		
		if(internshipRegister == null) {
			throw new KwsRuntimeException("InternshipRegister id ["+request.getInternshipRegisterTest().getInternshipRegisterId()+"] Not Found");
		}
		
		FjbInternshipRegisterTest internshipRegisterTest = null;
		
		if(request.getInternshipRegisterTest().getId() != null) {
			internshipRegisterTest = fjbInternshipRegisterTestService.findById(request.getInternshipRegisterTest().getId());
		}
		
		if(internshipRegisterTest == null) {
			internshipRegisterTest = request.getInternshipRegisterTest();
		} else {
			String sixteenPersonalityImageURI = internshipRegisterTest.getSixteenPersonalityImageURI();
			String high5ImageURI = internshipRegisterTest.getHigh5ImageURI();
			String iqImageURI = internshipRegisterTest.getIqImageURI();
			
			if(sixteenPersonalityImageURI != null) {
				fileStoreService.removeFile(req -> {
					req.copyFrom(request);
					req.setFileURI(sixteenPersonalityImageURI);
				});
			}
			
			if(internshipRegisterTest.getHigh5ImageURI() != null) {
				fileStoreService.removeFile(req -> {
					req.copyFrom(request);
					req.setFileURI(high5ImageURI);
				});
			}
			
			if(internshipRegisterTest.getIqImageURI() != null) {
				fileStoreService.removeFile(req -> {
					req.copyFrom(request);
					req.setFileURI(iqImageURI);
				});
			}
		}
		
		if(request.getSixteenPersonalityImageFile() != null) {
			FjbInternshipUpload16PersonalityTestImageCommand.Response upload16PersonalityResponse = internshipRegisterService.upload16PersonalityTestImage(req -> {
				req.copyFrom(request);
				req.setInternshipId(internshipRegisterId);
				req.setTestImageFile(request.getSixteenPersonalityImageFile());
			});
			
			logger.info("sixteenPersonalityImageURL => {}", upload16PersonalityResponse.getUrl());
			response.setSixteenPersonalityImageURL(upload16PersonalityResponse.getUrl());
			internshipRegisterTest.setSixteenPersonalityImageURI(upload16PersonalityResponse.getUri());
		}
		
		if(request.getHigh5ImageFile() != null) {
			FjbInternshipUploadHigh5TestImageCommand.Response uploadHigh5TestResponse = internshipRegisterService.uploadHigh5TestImage(req -> {
				req.copyFrom(request);
				req.setInternshipId(internshipRegisterId);
				req.setTestImageFile(request.getHigh5ImageFile());
			});
			
			logger.info("high5ImageURL => {}", uploadHigh5TestResponse.getUrl());
			response.setHigh5ImageURL(uploadHigh5TestResponse.getUrl());
			internshipRegisterTest.setHigh5ImageURI(uploadHigh5TestResponse.getUri());
		}
		
		if(request.getIqImageFile() != null) {
			FjbInternshipUploadIQTestImageCommand.Response uploadIQTestResponse = internshipRegisterService.uploadIQTestImage(req -> {
				req.copyFrom(request);
				req.setInternshipId(internshipRegisterId);
				req.setTestImageFile(request.getIqImageFile());
			});
			
			logger.info("iqImageURL => {}", uploadIQTestResponse.getUrl());
			response.setIqImageURL(uploadIQTestResponse.getUrl());
			internshipRegisterTest.setIqImageURI(uploadIQTestResponse.getUri());
		}
		
		if(internshipRegisterTest.getId() == null) {
			internshipRegisterTest.setId(UUID.randomUUID().toString());
			internshipRegisterTest.setStatus(MasterStatusValue.Active);
			internshipRegisterTest.setCreatedBy(request.getPerformedBy());
			internshipRegisterTest.setCreatedAt(request.getPerformedAt());
			
			try {
				internshipRegisterTest = fjbInternshipRegisterTestService.create(internshipRegisterTest);
				logger.info("create internshipRegisterTest [{}]", internshipRegisterTest);
			} catch(Exception ex) {
				throw new KwsRuntimeException(ex);
			}
		} else {
			internshipRegisterTest.setLastModifiedBy(request.getPerformedBy());
			internshipRegisterTest.setLastModifiedAt(request.getPerformedAt());
			try {
				internshipRegisterTest = fjbInternshipRegisterTestService.update(internshipRegisterTest);
				logger.info("update internshipRegisterTest [{}]", internshipRegisterTest);
			} catch(Exception ex) {
				throw new KwsRuntimeException(ex);
			}
		}
		
		internshipRegister.setRegisterStatusId(InternshipRegisterStatusValue.TestUploaded);
		internshipRegister.setUploadTestBy(request.getPerformedBy());
		internshipRegister.setUploadTestAt(request.getPerformedAt());
		try {
			InternshipRegister updatedInternshipRegister = internshipRegisterService.update(internshipRegister);
			logger.info("update internshipRegister [{}]", updatedInternshipRegister);
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		
		internshipUploadTestEventPublisher.publishInternshipUploadTestEvent(internshipRegister, internshipRegisterTest);
		
		CompletableFuture.runAsync(() -> {
			sendEmail(internshipRegister);
		});
		
		response.setUploadedInternshipRegisterTest(internshipRegisterTest);
		
		return response;
	}
	
	
	protected void sendEmail(InternshipRegister internshipRegister)
	{
		logger.info("### " + getClass().getSimpleName() + ".sendEmail ###");
		
		EmailTemplate emailTemplate = emailTemplateService.findById("email.fjb.registration.internship.upload_test");
		
		if(emailTemplate != null) {
			
			String userFullName = internshipRegister.getFirstName() + " " + internshipRegister.getLastName();
			
			String fromEmail = emailTemplate.getFromEmail();
			String fromName = emailTemplate.getFromName();
			List<String> mailTos = Arrays.asList(internshipRegister.getEmail());
			String subject = emailTemplate.getSubjectTemplate();
			String content = emailTemplate.getContentTemplate().replace("${user_full_name}", userFullName);
			
			logger.info("fromEmail => " + fromEmail);
			logger.info("fromName => " + fromName);
			logger.info("mailTos => " + mailTos);
			logger.info("subject => " + subject);
			logger.info("content => " + content);
			
			if(fromEmail != null && fromName != null) {
				emailService.sendMail(fromEmail, fromName, mailTos, subject, content);
			} else {
				emailService.sendMail(mailTos, subject, content);
			}
		}
	}
}