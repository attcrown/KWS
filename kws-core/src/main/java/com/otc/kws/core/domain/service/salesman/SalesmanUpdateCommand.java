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
public class SalesmanUpdateCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaSalesmanRepository salesmanRepository;
	
	
	public Response update(Request request)
	{
		logger.info("### {}.update ###", getClass().getSimpleName());
		
		Salesman salesman = request.getSalesman();
		
		if(salesmanRepository.findById(salesman.getId()) == null) {
			throw new KwsRuntimeException("ไม่พบข้อมูลเซลล์ที่ต้องการแก้ไขในระบบ");
		}
		
		Salesman existSalesman = salesmanRepository.findByIdcardNo(salesman.getIdcardNo());
		
		if(existSalesman != null && !existSalesman.getId().equals(salesman.getId())) {
			throw new KwsRuntimeException("ชื่อบัญชีผู้ใช้ซ้ำกับข้อมูลที่มีอยู่ในระบบ, กรุณาชื่อชื่อบัญชีผู้ใช้อื่น");
		}
		
		if(salesman.getUserId() != null) {
			existSalesman = salesmanRepository.findByUserId(salesman.getUserId());
			if(existSalesman != null && !existSalesman.getId().equals(salesman.getUserId())) {
				throw new KwsRuntimeException("ชื่อบัญชีผู้ใช้ซ้ำกับข้อมูลที่มีอยู่ในระบบ, กรุณาชื่อชื่อบัญชีผู้ใช้อื่น");
			}
		}
		
		salesman.setLastModifiedBy(request.getPerformedBy());
		salesman.setLastModifiedAt(request.getPerformedAt());
		
		Salesman updatedSalesman = salesmanRepository.save(salesman);
		logger.info("Update salesman => {}", updatedSalesman);
		
		Response response = new Response();
		response.setUpdatedSalesman(updatedSalesman);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected Salesman salesman;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected Salesman updatedSalesman;
	}
}