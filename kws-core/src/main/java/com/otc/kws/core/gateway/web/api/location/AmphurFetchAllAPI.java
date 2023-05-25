package com.otc.kws.core.gateway.web.api.location;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.Amphur;
import com.otc.kws.core.domain.service.AmphurService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/core/location/amphur")
public class AmphurFetchAllAPI extends BaseKwsAPI
{
	@Autowired
	protected AmphurService amphurService;
	
	
	@PostMapping("/fetch-all")
	public ResponseEntity<?> fetchAll(HttpServletRequest request)
	{
		logger.info("### AmphurFetchAllAPI.fetchAll ###");
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setAmphurs(amphurService.findAll());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected List<Amphur> amphurs;
	}
}