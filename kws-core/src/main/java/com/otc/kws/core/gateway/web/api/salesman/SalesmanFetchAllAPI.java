package com.otc.kws.core.gateway.web.api.salesman;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.Salesman;
import com.otc.kws.core.domain.repository.jpa.JpaSalesmanRepository;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/core/salesman")
public class SalesmanFetchAllAPI extends BaseKwsAPI
{
	@Autowired
	protected JpaSalesmanRepository salesmanRepository;
	
	
	@PostMapping("/fetch-all")
	public ResponseEntity<?> fetchAll(HttpServletRequest request)
	{
		logger.info("### {}.fetchAll ###", getClass().getSimpleName());
		
		List<Salesman> salesmans = salesmanRepository.findAll();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setSalesmans(salesmans);
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected List<Salesman> salesmans;
	}
}