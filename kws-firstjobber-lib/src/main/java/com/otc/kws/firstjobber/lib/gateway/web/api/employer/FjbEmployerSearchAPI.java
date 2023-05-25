package com.otc.kws.firstjobber.lib.gateway.web.api.employer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.firstjobber.lib.domain.dto.FjbEmployerSearchRequestDto;
import com.otc.kws.firstjobber.lib.domain.dto.FjbEmployerSearchResponseDto;
import com.otc.kws.firstjobber.lib.domain.service.employer.FjbEmployerService;
import com.otc.kws.firstjobber.lib.gateway.web.api.BaseKwsFirstJobberAPI;

@RestController
@RequestMapping(BaseKwsFirstJobberAPI.PREFIX_PATH + "/employer/search")
public class FjbEmployerSearchAPI extends BaseKwsFirstJobberAPI
{
	@Autowired
	protected FjbEmployerService fjbEmployerService;
	
	
	@PostMapping
	public ResponseEntity<?> search(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### FjbEmployerSearchAPI.search ###");
		logger.info("requestMessage => " + requestMessage);
		
		FjbEmployerSearchResponseDto employerSearchResponseDto = fjbEmployerService.searchEmployer(req -> {
			setupServiceRequest(request, req);
			req.setEmployerSearchRequestDto(requestMessage);
		}).getEmployerSearchResponseDto();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setPageNo(employerSearchResponseDto.getPageNo());
		bodyResponseMessage.setPageSize(employerSearchResponseDto.getPageSize());
		bodyResponseMessage.setTotalPage(employerSearchResponseDto.getTotalPage());
		bodyResponseMessage.setTotalSize(employerSearchResponseDto.getTotalSize());
		bodyResponseMessage.setDatas(employerSearchResponseDto.getDatas());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	public static class RequestMessage extends FjbEmployerSearchRequestDto
	{
		
	}
	
	
	public static class BodyResponseMessage extends FjbEmployerSearchResponseDto implements ResponseMessage.Body
	{
		
	}
}