package com.otc.kws.core.domain.service.salesman;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.EmployeeStatusValue;
import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.Salesman;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.repository.jpa.JpaSalesmanRepository;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;

import lombok.Data;

@Component
public class SalesmanCreateCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaSalesmanRepository salesmanRepository;
	
	
	public Response create(Request request)
	{
		logger.info("### {}.create ###", getClass().getSimpleName());
		
		Salesman salesman = request.getSalesman();
		
		if(salesman.getUserId() != null && salesmanRepository.findByUserId(salesman.getId()) != null) {
			throw new KwsRuntimeException("ชื่อบัญชีผู้ใช้ซ้ำกับข้อมูลที่มีอยู่ในระบบ, กรุณาชื่อชื่อบัญชีผู้ใช้อื่น");
		}
		
		if(salesmanRepository.findByIdcardNo(salesman.getIdcardNo()) != null) {
			throw new KwsRuntimeException("ชื่อบัญชีผู้ใช้ซ้ำกับข้อมูลที่มีอยู่ในระบบ, กรุณาชื่อชื่อบัญชีผู้ใช้อื่น");
		}
		
		if(salesman.getId() == null) {
			salesman.setId(UUID.randomUUID().toString());
		}
		if(salesman.getEmployeeStatus() == null) {
			salesman.setEmployeeStatus(EmployeeStatusValue.Actived);
		}
		if(salesman.getStatus() == null) {
			salesman.setStatus(MasterStatusValue.Active);
		}
		salesman.setCreatedBy(request.getPerformedBy());
		salesman.setCreatedAt(request.getPerformedAt());
		salesman.setLastModifiedBy(request.getPerformedBy());
		salesman.setLastModifiedAt(request.getPerformedAt());
		
		Salesman createdSalesman = salesmanRepository.save(salesman);
		logger.info("Create salesman => {}", createdSalesman);
		
		Response response = new Response();
		response.setCreatedSalesman(createdSalesman);
		
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
		protected Salesman createdSalesman;
	}
}