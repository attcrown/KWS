package com.otc.kws.core.gateway.web.api.location;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.Province;
import com.otc.kws.core.domain.service.ProvinceService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/core/location/province")
public class ProvinceFetchAllAPI extends BaseKwsAPI
{
	@Autowired
	protected ProvinceService provinceService;
	
	
	@PostMapping("/fetch-all")
	public ResponseEntity<?> fetchAll(HttpServletRequest request)
	{
		logger.info("### ProvinceFetchAllAPI.fetchAll ###");
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setProvinces(provinceService.findAll());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected List<Province> provinces;
	}
}