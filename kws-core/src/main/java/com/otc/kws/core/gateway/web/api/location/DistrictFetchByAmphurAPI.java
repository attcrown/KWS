package com.otc.kws.core.gateway.web.api.location;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.District;
import com.otc.kws.core.domain.service.DistrictService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/core/location/district")
public class DistrictFetchByAmphurAPI extends BaseKwsAPI
{
	@Autowired
	protected DistrictService districtService;
	
	
	@PostMapping("/fetch-by-amphur")
	public ResponseEntity<?> fetchByAmphur(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### DistrictFetchByAmphurAPI.fetchByAmphur ###");
		logger.info("requestMessage => " + requestMessage);
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setDistricts(districtService.findByAmphurId(requestMessage.getAmphurId()));
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String amphurId;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected List<District> districts;
	}
}