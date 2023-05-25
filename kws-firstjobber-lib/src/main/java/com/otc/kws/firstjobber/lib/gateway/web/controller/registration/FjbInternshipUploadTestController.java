package com.otc.kws.firstjobber.lib.gateway.web.controller.registration;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.otc.kws.core.domain.ServerURLProvider;
import com.otc.kws.core.domain.constant.KwsConst;
import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.ResourceAccessControl;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.High5TestCategoryService;
import com.otc.kws.core.domain.service.ResourceAccessControlService;
import com.otc.kws.core.domain.service.SixteenPersonalityCategoryService;
import com.otc.kws.core.domain.service.SixteenPersonalityTypeService;
import com.otc.kws.core.domain.service.filestore.FileStoreService;
import com.otc.kws.firstjobber.lib.domain.entity.InternshipRegister;
import com.otc.kws.firstjobber.lib.domain.service.FjbConfigService;
import com.otc.kws.firstjobber.lib.domain.service.InternshipRegisterService;
import com.otc.kws.firstjobber.lib.gateway.web.controller.KwsFirstJobberBaseController;

@Controller
@RequestMapping(KwsFirstJobberBaseController.PREFIX_PATH + "/registration/internship")
public class FjbInternshipUploadTestController extends KwsFirstJobberBaseController
{
	protected static final int RESOURCE_EXPIRED_MINUTE = 30;
	
	
	@Autowired
	protected InternshipRegisterService internshipRegisterService;
	
	@Autowired
	protected FjbConfigService fjbConfigService;
	
	@Autowired
	protected SixteenPersonalityTypeService sixteenPersonalityTypeService;
	
	@Autowired
	protected SixteenPersonalityCategoryService sixteenPersonalityCategoryService;
	
	@Autowired
	protected High5TestCategoryService high5TestCategoryService;
	
	@Autowired
	protected FileStoreService fileStoreService;
	
	@Autowired
	protected ResourceAccessControlService resourceAccessControlService;
	
	@Autowired
	protected ServerURLProvider serverURLProvider;
	
	
	@GetMapping("/upload-test")
	public String handle(HttpServletRequest request, @RequestParam String internshipRegisterId, Model model) throws Exception
	{
		logger.info("### KwsFirstJobberInternshipRegistrationController.handle ###");
		logger.info("internshipRegisterId => {}", internshipRegisterId);
		
		InternshipRegister internshipRegister = internshipRegisterService.findById(internshipRegisterId);
		
		if(internshipRegister == null) {
			throw new KwsRuntimeException("InternshipRegister id ["+internshipRegister+"] Not Found");
		}
		
		String formalImageURL = null;
		String informalImageURL = null;
		
		if(internshipRegister.getFormalPhotoURI() != null) {
			formalImageURL = fileStoreService.getURL(req -> {
				setupServiceRequest(request, req);
				req.setFileURI(internshipRegister.getFormalPhotoURI());
			}).getUrl();
		}
		
		if(internshipRegister.getInformalPhotoURI() != null) {
			informalImageURL = fileStoreService.getURL(req -> {
				setupServiceRequest(request, req);
				req.setFileURI(internshipRegister.getInformalPhotoURI());
			}).getUrl();
		}
		
		if(formalImageURL != null) {
			Date now = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			calendar.add(Calendar.MINUTE, RESOURCE_EXPIRED_MINUTE);
			Date expiredAt = calendar.getTime();
			
			ResourceAccessControl resourceAccessControl = new ResourceAccessControl();
			resourceAccessControl.setId(UUID.randomUUID().toString());
			resourceAccessControl.setAccessToken(UUID.randomUUID().toString() + ":" + UUID.randomUUID().toString());
			resourceAccessControl.setServletPath(formalImageURL.replace(serverURLProvider.getServerURL(), ""));
			resourceAccessControl.setStatus(MasterStatusValue.Active);
			resourceAccessControl.setCreatedAt(now);
			resourceAccessControl.setExpiredAt(expiredAt);
			
			try {
				resourceAccessControl = resourceAccessControlService.create(resourceAccessControl);
				logger.info("create resourceAccessControl => [{}]", resourceAccessControl);
				
				formalImageURL += "?" + KwsConst.HTTP_PARAM_RESOURCE_ACCESS_TOKEN + "=" + resourceAccessControl.getAccessToken();
				logger.info("formalImageURL => {}", formalImageURL);
			} catch(Exception ex) {
				throw new KwsRuntimeException(ex);
			}
		}
		
		if(informalImageURL != null) {
			Date now = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			calendar.add(Calendar.MINUTE, RESOURCE_EXPIRED_MINUTE);
			Date expiredAt = calendar.getTime();
			
			ResourceAccessControl resourceAccessControl = new ResourceAccessControl();
			resourceAccessControl.setId(UUID.randomUUID().toString());
			resourceAccessControl.setAccessToken(UUID.randomUUID().toString() + ":" + UUID.randomUUID().toString());
			resourceAccessControl.setServletPath(informalImageURL.replace(serverURLProvider.getServerURL(), ""));
			resourceAccessControl.setStatus(MasterStatusValue.Active);
			resourceAccessControl.setCreatedAt(now);
			resourceAccessControl.setExpiredAt(expiredAt);
			
			try {
				resourceAccessControl = resourceAccessControlService.create(resourceAccessControl);
				logger.info("create resourceAccessControl => [{}]", resourceAccessControl);
				
				informalImageURL += "?" + KwsConst.HTTP_PARAM_RESOURCE_ACCESS_TOKEN + "=" + resourceAccessControl.getAccessToken();
				logger.info("informalImageURL => {}", informalImageURL);
			} catch(Exception ex) {
				throw new KwsRuntimeException(ex);
			}
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("internshipRegister", internshipRegister);
		data.put("high5TestCategories", high5TestCategoryService.getAll());
		data.put("sixteenPersonalityTypes", sixteenPersonalityTypeService.getAll());
		data.put("sixteenPersonalityCategories", sixteenPersonalityCategoryService.getAll());
		data.put("testHigh5Name", fjbConfigService.getTestHigh5Name());
		data.put("testHigh5Link", fjbConfigService.getTestHigh5Link());
		data.put("test16PersonlityName", fjbConfigService.getTestSixteenPersonalityName());
		data.put("test16PersonlityLink", fjbConfigService.getTestSixteenPersonalityLink());
		data.put("testIQName", fjbConfigService.getTestIQName());
		data.put("testIQLink", fjbConfigService.getTestIQLink());
		
		if(formalImageURL != null) {
			data.put("formalImageURL", formalImageURL);
		}
		
		if(informalImageURL != null) {
			data.put("informalImageURL", informalImageURL);
		}
		
		model.addAttribute("kws_webEnv", getWebEnv(request));
	    model.addAttribute("kws_serverURL", getServerURL(request));
	    model.addAttribute("kws_data", data);
		
		return getCurrentWeb(request).getViewFile().replace("${web_template}", getCurrentWebTemplate(request));
	}
}