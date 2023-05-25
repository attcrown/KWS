package com.otc.kws.core.gateway.web.api.school;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.dto.SchoolSearchRequestDto;
import com.otc.kws.core.domain.dto.SchoolSearchResponseDto;
import com.otc.kws.core.domain.service.education.SchoolService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/core/education/school")
public class SchoolSearchAPI extends BaseKwsAPI
{
	@Autowired
	protected SchoolService schoolService;
	
	
	@PostMapping("/search")
	public ResponseEntity<?> search(HttpServletRequest request, @RequestBody(required = false) RequestMessage requestMessage)
	{
		logger.info("### SchoolSearchAPI.search ####");
		logger.info("requestMessage => " + requestMessage);
		
		SchoolSearchResponseDto schoolSearchResponseDto = schoolService.search(req -> {
			setupServiceRequest(request, req);
			req.setSchoolSearchRequestDto(requestMessage);
		}).getSchoolSearchResponseDto();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setPageNo(schoolSearchResponseDto.getPageNo());
		bodyResponseMessage.setPageSize(schoolSearchResponseDto.getPageSize());
		bodyResponseMessage.setTotalPage(schoolSearchResponseDto.getTotalPage());
		bodyResponseMessage.setTotalSize(schoolSearchResponseDto.getTotalSize());
		bodyResponseMessage.setDatas(schoolSearchResponseDto.getDatas());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	protected static class RequestMessage extends SchoolSearchRequestDto
	{

	}
	
	
	protected static class BodyResponseMessage extends SchoolSearchResponseDto implements ResponseMessage.Body
	{

	}
}