package com.otc.kws.marketpet.lib.domain.service.customer;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.marketpet.lib.domain.entity.MkpCustomer;
import com.otc.kws.marketpet.lib.domain.service.MkpConfigService;

import lombok.Data;

public abstract class MkpCustomerCalculatePointCommand extends BaseKwsCommand
{
	@Autowired
	protected MkpConfigService mkpConfigService;
	
	@Autowired
	protected MkpCustomerPointAcquireConfigService mkpCustomerPointAcquireConfigService;
	
	@Autowired
	protected MkpCustomerService mkpCustomerService;
	
	
	public abstract Response calculatePoint(Request request);
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String customerId;
		protected MkpCustomer mkpCustomer;
		protected BigDecimal price;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected List<PointAcquire> pointAcquires;
		
		
		@Data
		public static class PointAcquire
		{
			protected String customerId;
			protected MkpCustomer mkpCustomer;
			protected BigDecimal point;
			protected BigDecimal remainAmount; // ยอดสะสมคงเหลือที่รอไปคำนวณในรอบ bill ถัดไป
		}
	}
}