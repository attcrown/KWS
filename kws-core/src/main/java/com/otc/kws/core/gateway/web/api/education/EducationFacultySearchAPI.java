package com.otc.kws.core.gateway.web.api.education;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.dto.EducationFacultySearchRequestDto;
import com.otc.kws.core.domain.dto.EducationFacultySearchResponseDto;
import com.otc.kws.core.domain.service.education.EducationFacultyService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/core/education/faculty")
public class EducationFacultySearchAPI extends BaseKwsAPI
{
	@Autowired
	protected EducationFacultyService educationFacultyService;
	
	
	@PostMapping("/search")
	public ResponseEntity search(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### EducationFacultySearchAPI.search ###");
		logger.info("requestMessage => " + requestMessage);
		
		EducationFacultySearchResponseDto educationFacultySearchResponseDto = educationFacultyService.search(req -> {
			setupServiceRequest(request, req);
			req.setEducationFacultySearchRequestDto(requestMessage);
		}).getEducationFacultySearchResponseDto();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setPageNo(educationFacultySearchResponseDto.getPageNo());
		bodyResponseMessage.setPageSize(educationFacultySearchResponseDto.getPageSize());
		bodyResponseMessage.setTotalPage(educationFacultySearchResponseDto.getTotalPage());
		bodyResponseMessage.setTotalSize(educationFacultySearchResponseDto.getTotalSize());
		bodyResponseMessage.setDatas(educationFacultySearchResponseDto.getDatas());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	public static class RequestMessage extends EducationFacultySearchRequestDto
	{
		  
	}
	
	
	public static class BodyResponseMessage extends EducationFacultySearchResponseDto implements ResponseMessage.Body
	{
		
	}
}