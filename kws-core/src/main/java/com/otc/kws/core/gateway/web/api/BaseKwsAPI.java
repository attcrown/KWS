package com.otc.kws.core.gateway.web.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;

import com.otc.kws.core.domain.ModuleProvider;
import com.otc.kws.core.domain.ServerURLProvider;
import com.otc.kws.core.domain.constant.KwsConst;
import com.otc.kws.core.domain.entity.AuthSession;
import com.otc.kws.core.domain.entity.Api;
import com.otc.kws.core.domain.model.FileData;
import com.otc.kws.core.domain.model.GrantedAuthority;
import com.otc.kws.core.domain.model.UserProfile;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.ConfigService;

public abstract class BaseKwsAPI 
{
	public static final String PREFIX_PATH = "/api";
	public static final String DEFAULT_SUCCESS_STATUS_CODE = "200";
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected ConfigService configService;
	
	@Autowired
	protected ModuleProvider moduleProvider;
	
	@Autowired
	protected ServerURLProvider serverURLProvider;
	
	
	protected String getServerURL(HttpServletRequest request)
    {
        return serverURLProvider.getServerURL();
    }
	
	protected String getModuleName(HttpServletRequest request)
	{
		return KwsConst.MODULE_NAME;
	}
	
	protected String getRuntimeModuleName(HttpServletRequest request)
	{
		return moduleProvider.getRuntimeModuleName();
	}
	
	protected void setupServiceRequest(HttpServletRequest request, BaseKwsCommandRequest commandRequest)
    {
        AuthSession authSession = getAuthSession(request);
        UserProfile userProfile = getUserProfile(request);
        
        commandRequest.setPerformedAPI(request.getRequestURI());
        if(authSession != null) {
        	commandRequest.setPerformedPlatform(authSession.getPlatform());
        	commandRequest.setPerformedAuthSession(authSession);
        }
        if(userProfile != null) {
        	commandRequest.setPerformedBy(userProfile.getUser().getId());
        	commandRequest.setPerformedUserProfile(userProfile);
        }
        commandRequest.setPerformedAt(new Date());
    }
    
    protected UserProfile getUserProfile(HttpServletRequest request)
    {
        UserProfile userProfile = null;
        if(request.getAttribute(KwsConst.HTTP_REQ_ATTR_USER_PROFILE) != null) {
            userProfile = (UserProfile) request.getAttribute(KwsConst.HTTP_REQ_ATTR_USER_PROFILE);
        }
        return userProfile;
    }
    
    protected AuthSession getAuthSession(HttpServletRequest request)
    {
        AuthSession authSession = null;
        if(request.getAttribute(KwsConst.HTTP_REQ_ATTR_AUTH_SESSION) != null) {
        	authSession = (AuthSession) request.getAttribute(KwsConst.HTTP_REQ_ATTR_AUTH_SESSION);
        }
        return authSession;
    }
    
    protected String getSessionId(HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        return session != null ? session.getId() : null;
    }
    
    protected Api getCurrentAPI(HttpServletRequest request)
    {
    	Api api = null;
        if(request.getAttribute(KwsConst.HTTP_REQ_ATTR_CURRENT_API) != null) {
            api = (Api) request.getAttribute(KwsConst.HTTP_REQ_ATTR_CURRENT_API);
        }
        return api;
    }
    
    protected GrantedAuthority getCurrentGrantedAuthority(HttpServletRequest request)
    {
    	GrantedAuthority grantedAuthority = null;
    	if(request.getAttribute(KwsConst.HTTP_REQ_ATTR_CURRENT_GRANTED_AUTHORITY) != null) {
    		grantedAuthority = (GrantedAuthority) request.getAttribute(KwsConst.HTTP_REQ_ATTR_CURRENT_GRANTED_AUTHORITY);
    	}
    	return grantedAuthority;
    }
    
    protected GrantedAuthority getAllGrantedAuthority(HttpServletRequest request)
    {
    	GrantedAuthority grantedAuthority = null;
    	if(request.getAttribute(KwsConst.HTTP_REQ_ATTR_ALL_GRANTED_AUTHORITY) != null) {
    		grantedAuthority = (GrantedAuthority) request.getAttribute(KwsConst.HTTP_REQ_ATTR_ALL_GRANTED_AUTHORITY);
    	}
    	return grantedAuthority;
    }
    
    protected String getCurrentLabelLanguageId(HttpServletRequest request)
    {
    	AuthSession authSession = getAuthSession(request);
    	if(authSession != null) {
    		return authSession.getLabelLanguageId();
    	} else {
    		return configService.getDefaultLabelLanguageId();
    	}
    }
    
    protected FileData buildFileData(MultipartFile multipartFile) throws Exception
    {
    	if(multipartFile == null) {
    		return null;
    	}
    	
    	FileData fileData = new FileData();
    	fileData.setBytes(multipartFile.getBytes());
    	fileData.setSize(multipartFile.getSize());
    	fileData.setContentType(multipartFile.getContentType());
    	fileData.setOriginalFileName(multipartFile.getOriginalFilename());
    	fileData.setFileExtension(FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
    	fileData.setInputStream(multipartFile.getInputStream());
    	return fileData;
    }
    
    protected List<FileData> buildFileDatas(MultipartFile multipartFiles[]) throws Exception
    {
    	List<FileData> fileDatas = null;
    	
    	if(multipartFiles != null && multipartFiles.length > 0) {
    		fileDatas = new ArrayList<>();
    		for(int i=0; i<multipartFiles.length; i++) {
    	    	fileDatas.add(buildFileData(multipartFiles[i]));
    		}
    	}
    	
    	return fileDatas;
    }
    
    
    // ****************************** replySuccess ****************************** //
    protected <B extends ResponseMessage.Body> ResponseEntity<ResponseMessage<B>> replySuccess(HttpServletRequest request)
    {
    	return replySuccess(request, DEFAULT_SUCCESS_STATUS_CODE, null);
    }
    
    protected <B extends ResponseMessage.Body> ResponseEntity<ResponseMessage<B>> replySuccess(HttpServletRequest request, B body)
    {
    	return replySuccess(request, DEFAULT_SUCCESS_STATUS_CODE, body);
    }
    
    protected <B extends ResponseMessage.Body> ResponseEntity<ResponseMessage<B>> replySuccess(HttpServletRequest request, String statusCode)
    {
    	return replySuccess(request, statusCode, null);
    }
    
    protected <B extends ResponseMessage.Body> ResponseEntity<ResponseMessage<B>> replySuccess(HttpServletRequest request, String statusCode, B body)
    {
    	ResponseMessage.Head head = new ResponseMessage.Head();
    	head.setStatus(ResponseMessage.Status.Success);
    	head.setStatusCode(statusCode);
    	head.setStatusMessage(ResponseMessage.Status.Success.name());
    	
    	ResponseMessage<B> responseMessage = new ResponseMessage<B>();
    	responseMessage.setHead(head);
        responseMessage.setBody(body);
        
        return ResponseEntity.ok(responseMessage);
    }
    // ****************************** replySuccess ****************************** //
    
    
    // ****************************** exception ****************************** //
    @ExceptionHandler(Throwable.class)
    public <B extends ResponseMessage.Body> ResponseEntity<ResponseMessage<B>> handleDefaultException(Throwable ex, HttpServletRequest request) 
    {
    	logger.info("### BaseAPI.handleDefaultException ###");
        logger.error("Error at ["+request.getMethod() + " : " + request.getRequestURL()+"] API.");
        logger.error(ex.getMessage(), ex);
        
        ResponseMessage.Head head = new ResponseMessage.Head();
        head.setStatus(ResponseMessage.Status.Error);
        head.setStatusCode(ex.getClass().getSimpleName());
        head.setStatusMessage(ex.getMessage());
        
        ResponseMessage<B> responseMessage = new ResponseMessage<B>();
        responseMessage.setHead(head);
        
        return ResponseEntity.ok(responseMessage);
    }
    // ****************************** exception ****************************** //
}