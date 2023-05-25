package com.otc.kws.core.gateway.web.api.education;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.dto.EducationMajorSearchRequestDto;
import com.otc.kws.core.domain.dto.EducationMajorSearchResponseDto;
import com.otc.kws.core.domain.service.education.EducationMajorService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/core/education/major")
public class EducationMajorSearchAPI extends BaseKwsAPI
{
	@Autowired
	protected EducationMajorService educationMajorService;
	
	
	@PostMapping("/search")
	public ResponseEntity search(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### EducationMajorSearchAPI.search ###");
		logger.info("requestMessage => " + requestMessage);
		
		EducationMajorSearchResponseDto educationMajorSearchResponseDto = educationMajorService.search(req -> {
			setupServiceRequest(request, req);
			req.setEducationMajorSearchRequestDto(requestMessage);
		}).getEducationMajorSearchResponseDto();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setPageNo(educationMajorSearchResponseDto.getPageNo());
		bodyResponseMessage.setPageSize(educationMajorSearchResponseDto.getPageSize());
		bodyResponseMessage.setTotalPage(educationMajorSearchResponseDto.getTotalPage());
		bodyResponseMessage.setTotalSize(educationMajorSearchResponseDto.getTotalSize());
		bodyResponseMessage.setDatas(educationMajorSearchResponseDto.getDatas());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	public static class RequestMessage extends EducationMajorSearchRequestDto
	{
		
	}
	
	
	public static class BodyResponseMessage extends EducationMajorSearchResponseDto implements ResponseMessage.Body
	{
		
	}
}