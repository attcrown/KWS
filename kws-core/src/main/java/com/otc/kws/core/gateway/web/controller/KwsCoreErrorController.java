package com.otc.kws.core.gateway.web.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(KwsCoreBaseController.PREFIX_PATH)
public class KwsCoreErrorController extends KwsCoreBaseController implements ErrorController
{
	@GetMapping("/error")
    public String handleError(HttpServletRequest request) 
	{
    	logger.debug("### KwsCoreErrorController.handleError ###");
    	
    	Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            logger.debug("statusCode => " + statusCode);
        
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return getPrefixViewFilePath(request) + "/error/404";
            } else if(statusCode == HttpStatus.UNAUTHORIZED.value()) {
            	return getPrefixViewFilePath(request) + "/error/401";
            } else if(statusCode == HttpStatus.FORBIDDEN.value()) {
            	return getPrefixViewFilePath(request) + "/error/401";
            } else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            	return getPrefixViewFilePath(request) + "/error/500";
            }
        }
        
        return getPrefixViewFilePath(request) + "/error/500";
    }
}