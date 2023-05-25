package com.otc.kws.core.gateway.web.api.location;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.Amphur;
import com.otc.kws.core.domain.service.AmphurService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/core/location/amphur")
public class AmphurFetchByProvinceAPI extends BaseKwsAPI
{
	@Autowired
	protected AmphurService amphurService;
	
	
	@PostMapping("/fetch-by-province")
	public ResponseEntity<?> fetchByProvince(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### AmphurFetchByProvinceAPI.fetchByProvince ###");
		logger.info("requestMessage => " + requestMessage);
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setAmphurs(amphurService.findByProvinceId(requestMessage.getProvinceId()));
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String provinceId;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected List<Amphur> amphurs;
	}
}