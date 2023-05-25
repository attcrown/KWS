package com.otc.kws.firstjobber.lib.domain.service.internship;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.EmailTemplate;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.firstjobber.lib.domain.constant.value.InternshipRegisterStatusValue;
import com.otc.kws.firstjobber.lib.domain.entity.FjbInternshipRegisterExperience;
import com.otc.kws.firstjobber.lib.domain.entity.InternshipRegister;

@Component
public class InternshipRegisterCommandV1 extends InternshipRegisterCommand
{
	@Override
	public Response register(Request request) 
	{
		Response response = new Response();
		
		try {
			if(request.getInternshipRegister().getId() == null) {
				request.getInternshipRegister().setId(UUID.randomUUID().toString());
			}
			
			// ****************************** upload formal_photo ****************************** //
			if(request.getFormalPhotoFile() != null) {
				String formalPhotoURI = internshipRegisterService.uploadFormalPhoto(req -> {
					req.copyFrom(request);
					req.setInternshipId(request.getInternshipRegister().getId());
					req.setFormalPhotoFile(request.getFormalPhotoFile());
				}).getUri();
				
				request.getInternshipRegister().setFormalPhotoURI(formalPhotoURI);
			}
			// ****************************** upload formal_photo ****************************** //
			
			// ****************************** upload informal_photo ****************************** //
			if(request.getInformalPhotoFile() != null) {
				String informalPhotoURI = internshipRegisterService.uploadInformalPhoto(req -> {
					req.copyFrom(request);
					req.setInternshipId(request.getInternshipRegister().getId());
					req.setInformalPhotoFile(request.getInformalPhotoFile());
				}).getUri();
				
				request.getInternshipRegister().setInformalPhotoURI(informalPhotoURI);
			}
			// ****************************** upload informal_photo ****************************** //
			
			// ****************************** upload resume_file ****************************** //
			if(request.getResumeFile() != null) {
				String resumeFileURI = internshipRegisterService.uploadResumeFile(req -> {
					req.copyFrom(request);
					req.setInternshipId(request.getInternshipRegister().getId());
					req.setResumeFile(request.getResumeFile());
				}).getUri();
				
				request.getInternshipRegister().setResumeFileURI(resumeFileURI);
			}
			// ****************************** upload resume_file ****************************** //
			
			// ****************************** upload portfolio_files ****************************** //
			if(request.getPortfolioFiles() != null && !request.getPortfolioFiles().isEmpty()) {
				InternshipUploadPortfolioFileCommand.Response uploadPortfolioFileResponse = internshipRegisterService.uploadPortfolioFile(req -> {
					req.copyFrom(request);
					req.setInternshipId(request.getInternshipRegister().getId());
					req.setPortfolioFiles(request.getPortfolioFiles());
				});
				
				if(uploadPortfolioFileResponse.getReplyItems() != null && !uploadPortfolioFileResponse.getReplyItems().isEmpty()) {
					int index = 1;
					for(InternshipUploadPortfolioFileCommand.Response.ReplyItem replyItem : uploadPortfolioFileResponse.getReplyItems()) {
						if(index > 5) {
							break;
						}
						
						if(index == 1) {
							request.getInternshipRegister().setPortfolio1FileURI(replyItem.getUri());
						} else if(index == 2) {
							request.getInternshipRegister().setPortfolio2FileURI(replyItem.getUri());
						} else if(index == 3) {
							request.getInternshipRegister().setPortfolio3FileURI(replyItem.getUri());
						} else if(index == 4) {
							request.getInternshipRegister().setPortfolio4FileURI(replyItem.getUri());
						} else if(index == 5) {
							request.getInternshipRegister().setPortfolio5FileURI(replyItem.getUri());
						} 
						
						index++;
					}
				}
			}
			// ****************************** upload portfolio_files ****************************** //
			
			// ****************************** upload intern_letter_file ****************************** //
			if(request.getInternLetterFile() != null) {
				String internLetterFileURI = internshipRegisterService.uploadInternLetterFile(req -> {
					req.copyFrom(request);
					req.setInternshipId(request.getInternshipRegister().getId());
					req.setInternLetterFile(request.getInternLetterFile());
				}).getUri();
				
				request.getInternshipRegister().setInternLetterFileURI(internLetterFileURI);
			}
			// ****************************** upload intern_letter_file ****************************** //
			
			request.getInternshipRegister().setRegisterStatusId(InternshipRegisterStatusValue.Registered);
			request.getInternshipRegister().setRegisteredBy(request.getPerformedBy());
			request.getInternshipRegister().setRegisteredAt(request.getPerformedAt());
			
			InternshipRegister createdInternshipRegister = internshipRegisterService.create(request.getInternshipRegister());
			logger.info("create internshipRegister => " + createdInternshipRegister);
			
			if(request.getInternshipRegisterExperiences() != null && !request.getInternshipRegisterExperiences().isEmpty()) {
				for(FjbInternshipRegisterExperience experience : request.getInternshipRegisterExperiences()) {
					if(experience.getId() == null) {
						experience.setId(UUID.randomUUID().toString());
					}
					
					if(experience.getInternshipRegisterId() == null) {
						experience.setInternshipRegisterId(createdInternshipRegister.getId());
					}
					
					if(experience.getStatus() == null) {
						experience.setStatus(MasterStatusValue.Active);
					}
					
					experience = internshipRegisterExperienceService.create(experience);
					logger.info("create internshipRegisterExperience => [{}]", experience);
				}
			}
			
			internshipRegisterEventPublisher.publishInternshipRegisterEvent(createdInternshipRegister);
			
			CompletableFuture.runAsync(() -> {
				sendEmail(createdInternshipRegister);
			});
			
			response.setCreatedInternshipRegister(createdInternshipRegister);
			response.setCreatedInternshipRegisterExperiences(request.getInternshipRegisterExperiences());
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		
		return response;
	}
	
	
	protected void sendEmail(InternshipRegister internshipRegister)
	{
		logger.info("### " + getClass().getSimpleName() + ".sendEmail ###");
		
		EmailTemplate emailTemplate = emailTemplateService.findById("email.fjb.registration.internship.register");
		
		if(emailTemplate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("th", "TH"));
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, 3);
			
			String userFullName = internshipRegister.getFirstName() + " " + internshipRegister.getLastName();
			String uploadTestLink = fjbConfigService.getInternshipUploadTestLink().
					replace("${server_url}", serverURLProvider.getServerURL()).
					replace("${internship_register_id}", internshipRegister.getId());
			String uploadTestDate = sdf.format(calendar.getTime());
			
			String fromEmail = emailTemplate.getFromEmail();
			String fromName = emailTemplate.getFromName();
			List<String> mailTos = Arrays.asList(internshipRegister.getEmail());
			String subject = emailTemplate.getSubjectTemplate();
			String content = emailTemplate.getContentTemplate().
					replace("${user_full_name}", userFullName).
					replace("${upload_test_link}", uploadTestLink).
					replace("${upload_test_date}", uploadTestDate);
			
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