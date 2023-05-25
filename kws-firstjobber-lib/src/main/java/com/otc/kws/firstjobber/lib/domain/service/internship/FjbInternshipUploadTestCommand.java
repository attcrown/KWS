package com.otc.kws.firstjobber.lib.domain.service.internship;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.kws.core.domain.model.FileData;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.service.EmailService;
import com.otc.kws.core.domain.service.filestore.FileStoreService;
import com.otc.kws.core.domain.service.notification.EmailTemplateService;
import com.otc.kws.firstjobber.lib.domain.entity.FjbInternshipRegisterTest;
import com.otc.kws.firstjobber.lib.domain.service.InternshipRegisterService;
import com.otc.kws.firstjobber.lib.event.internship.registration.InternshipUploadTestEventPublisher;

import lombok.Data;

public abstract class FjbInternshipUploadTestCommand extends BaseKwsCommand
{
	@Autowired
	protected InternshipRegisterService internshipRegisterService;
	
	@Autowired
	protected FjbInternshipRegisterTestService fjbInternshipRegisterTestService;
	
	@Autowired
	protected FileStoreService fileStoreService;
	
	@Autowired
	protected EmailService emailService;
	
	@Autowired
	protected EmailTemplateService emailTemplateService;
	
	@Autowired
	protected InternshipUploadTestEventPublisher internshipUploadTestEventPublisher;
	
	
	public abstract Response uploadTest(Request request);
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected FjbInternshipRegisterTest internshipRegisterTest;
		protected FileData sixteenPersonalityImageFile;
		protected FileData high5ImageFile;
		protected FileData iqImageFile;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected FjbInternshipRegisterTest uploadedInternshipRegisterTest;
		protected String sixteenPersonalityImageURL;
		protected String high5ImageURL;
		protected String iqImageURL;
	}
}