package com.otc.kws.firstjobber.lib.domain.service.internship;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.kws.core.domain.ServerURLProvider;
import com.otc.kws.core.domain.model.FileData;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.service.EmailService;
import com.otc.kws.core.domain.service.filestore.FileStoreService;
import com.otc.kws.core.domain.service.notification.EmailTemplateService;
import com.otc.kws.firstjobber.lib.domain.entity.FjbInternshipRegisterExperience;
import com.otc.kws.firstjobber.lib.domain.entity.InternshipRegister;
import com.otc.kws.firstjobber.lib.domain.service.FjbConfigService;
import com.otc.kws.firstjobber.lib.domain.service.InternshipRegisterService;
import com.otc.kws.firstjobber.lib.event.internship.registration.InternshipRegisterEventPublisher;

import lombok.Data;

public abstract class InternshipRegisterCommand extends BaseKwsCommand
{
	@Autowired
	protected ServerURLProvider serverURLProvider;
	
	@Autowired
	protected FjbConfigService fjbConfigService;
	
	@Autowired
	protected InternshipRegisterService internshipRegisterService;
	
	@Autowired
	protected FjbInternshipRegisterExperienceService internshipRegisterExperienceService;
	
	@Autowired
	protected FileStoreService fileStoreService;
	
	@Autowired
	protected InternshipRegisterEventPublisher internshipRegisterEventPublisher;
	
	@Autowired
	protected EmailService emailService;
	
	@Autowired
	protected EmailTemplateService emailTemplateService;
	
	
	public abstract Response register(Request request);
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected InternshipRegister internshipRegister;
		protected List<FjbInternshipRegisterExperience> internshipRegisterExperiences;
		protected FileData formalPhotoFile;
		protected FileData informalPhotoFile;
		protected FileData resumeFile;
		protected FileData internLetterFile;
		protected FileData internCertFile;
		protected List<FileData> portfolioFiles;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected InternshipRegister createdInternshipRegister;
		protected List<FjbInternshipRegisterExperience> createdInternshipRegisterExperiences;
	}
}