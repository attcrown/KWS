package com.otc.kws.core.domain.service.salesman;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.entity.Salesman;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.repository.jpa.JpaSalesmanRepository;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;

import lombok.Data;

@Component
public class SalesmanRemoveCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaSalesmanRepository salesmanRepository;
	
	
	public Response remove(Request request)
	{
		logger.info("### {}.remove ###", getClass().getSimpleName());
		
		Salesman removedSalesman = salesmanRepository.findById(request.getSalesmanId()).orElse(null);
		
		if(removedSalesman == null) {
			throw new KwsRuntimeException("ไม่พบข้อมูลเซลล์ที่ต้องการลบในระบบ");
		}
		
		salesmanRepository.delete(removedSalesman);
		logger.info("Remove salesman => {}", removedSalesman);
		
		Response response = new Response();
		response.setRemovedSalesman(removedSalesman);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String salesmanId;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected Salesman removedSalesman;
	}
}