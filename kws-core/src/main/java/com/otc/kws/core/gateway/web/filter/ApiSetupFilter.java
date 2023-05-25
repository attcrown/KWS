package com.otc.kws.core.gateway.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.otc.kws.core.domain.constant.KwsConst;
import com.otc.kws.core.domain.entity.Api;
import com.otc.kws.core.domain.service.ApiService;
import com.otc.kws.core.domain.util.JsonUtil;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

public class ApiSetupFilter extends BaseFilter
{
	@Autowired
	protected ApiService apiService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException 
	{
		logger.debug("### ApiSetupFilter.doFilterInternal ###");
		
		Api currentAPI = apiService.getByMethodAndPath(request.getMethod().toUpperCase(), request.getServletPath());
		logger.debug("currentAPI => " + currentAPI);
		
		if(currentAPI == null) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
	        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	        
	        ResponseMessage.Head head = new ResponseMessage.Head();
	        head.setStatus(ResponseMessage.Status.Error);
	        head.setStatusCode("500001");
	        head.setStatusMessage("API Not Found");
	        
	        ResponseMessage<?> responseMessage = new ResponseMessage<>();
	        responseMessage.setHead(head);
	        
	        JsonUtil.writeValue(response.getWriter(), responseMessage);
	        
	        return;
		}
		
		request.setAttribute(KwsConst.HTTP_REQ_ATTR_CURRENT_API, currentAPI);
		
		filterChain.doFilter(request, response);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException 
	{
		logger.debug("### ApiSetupFilter.shouldNotFilter ###");
	    return !isAPI(request);
	}
}