package com.otc.kws.firstjobber.lib.domain.service.internship;

import java.text.SimpleDateFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.kws.core.domain.ServerURLProvider;
import com.otc.kws.core.domain.constant.KwsConst;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.service.education.EducationFacultyService;
import com.otc.kws.core.domain.service.education.EducationLevelService;
import com.otc.kws.core.domain.service.education.EducationMajorService;
import com.otc.kws.core.domain.service.education.SchoolService;
import com.otc.kws.core.domain.service.notification.NotificationSendNotifyCommand;
import com.otc.kws.core.domain.service.notification.NotificationService;
import com.otc.kws.firstjobber.lib.domain.entity.InternshipRegister;
import com.otc.kws.firstjobber.lib.event.internship.registration.InternshipRegisterEvent;

import lombok.Data;

public abstract class InternshipRegisterNotifyCommand extends BaseKwsCommand
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
	protected NotificationService notificationService;
	
	
	public abstract Response notifyInternshipRegister(Request request);
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String event = InternshipRegisterEvent.class.getName();
		protected InternshipRegister internshipRegister;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected NotificationSendNotifyCommand.Response sendNotifyResponse;
	}
}