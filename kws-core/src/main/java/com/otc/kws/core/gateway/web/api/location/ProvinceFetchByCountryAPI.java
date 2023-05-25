package com.otc.kws.core.gateway.web.api.location;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.Province;
import com.otc.kws.core.domain.service.ProvinceService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/core/location/province")
public class ProvinceFetchByCountryAPI extends BaseKwsAPI
{
	@Autowired
	protected ProvinceService provinceService;
	
	
	@PostMapping("/fetch-by-country")
	public ResponseEntity<?> fetchByCountry(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### ProvinceFetchByCountryAPI.fetchByCountry ###");
		logger.info("requestMessage => " + requestMessage);
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setProvinces(provinceService.findByCountryId(requestMessage.getCountryId()));
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String countryId;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected List<Province> provinces;
	}
}