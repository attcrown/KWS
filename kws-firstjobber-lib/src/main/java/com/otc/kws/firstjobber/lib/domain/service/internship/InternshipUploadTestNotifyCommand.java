package com.otc.kws.firstjobber.lib.domain.service.internship;

import java.text.SimpleDateFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.kws.core.domain.ServerURLProvider;
import com.otc.kws.core.domain.constant.KwsConst;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.service.High5TestCategoryService;
import com.otc.kws.core.domain.service.SixteenPersonalityCategoryService;
import com.otc.kws.core.domain.service.education.EducationFacultyService;
import com.otc.kws.core.domain.service.education.EducationLevelService;
import com.otc.kws.core.domain.service.education.EducationMajorService;
import com.otc.kws.core.domain.service.education.SchoolService;
import com.otc.kws.core.domain.service.notification.NotificationSendNotifyCommand;
import com.otc.kws.core.domain.service.notification.NotificationService;
import com.otc.kws.firstjobber.lib.domain.entity.FjbInternshipRegisterTest;
import com.otc.kws.firstjobber.lib.domain.entity.InternshipRegister;
import com.otc.kws.firstjobber.lib.event.internship.registration.InternshipUploadTestEvent;

import lombok.Data;

public abstract class InternshipUploadTestNotifyCommand extends BaseKwsCommand
{
	protected final SimpleDateFormat sdf = new SimpleDateFormat(KwsConst.DEFAULT_DATETIME_FORMAT, Locale.ENGLISH);
	
	
	@Autowired
	protected ServerURLProvider serverURLProvider;
	
	@Autowired
	protected EducationLevelService educationLevelService;
	
	@Autowired
	protected SchoolService schoolService;
	
	@Autowired
	protected EducationFacultyService educationFacultyService;
	
	@Autowired
	protected EducationMajorService educationMajorService;
	
	@Autowired
	protected SixteenPersonalityCategoryService sixteenPersonalityCategoryService;
	
	@Autowired
	protected High5TestCategoryService high5TestCategoryService;
	
	@Autowired
	protected NotificationService notificationService;
	
	
	public abstract Response notifyInternshipUploadTest(Request request);
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String event = InternshipUploadTestEvent.class.getName();
		protected InternshipRegister internshipRegister;
		protected FjbInternshipRegisterTest internshipRegisterTest;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected NotificationSendNotifyCommand.Response sendNotifyResponse;
	}
}