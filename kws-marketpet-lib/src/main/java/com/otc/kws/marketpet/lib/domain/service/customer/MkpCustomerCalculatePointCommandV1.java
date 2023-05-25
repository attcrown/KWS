package com.otc.kws.marketpet.lib.domain.service.customer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.marketpet.lib.domain.entity.MkpCustomer;
import com.otc.kws.marketpet.lib.domain.entity.MkpCustomerPointAcquireConfig;

@Component
public class MkpCustomerCalculatePointCommandV1 extends MkpCustomerCalculatePointCommand
{
	@Override
	public Response calculatePoint(Request request) 
	{
		MkpCustomer mkpCustomer = request.getMkpCustomer();
		
		if(mkpCustomer == null) {
			mkpCustomer = mkpCustomerService.findById(request.getCustomerId());
			
			if(mkpCustomer == null) {
				throw new KwsRuntimeException("Customer id ["+request.getCustomerId()+"] Not Found");
			}
		}
		
		int maxHierachyAcquire = mkpConfigService.getPointMaxHierachyAcquire();
		BigDecimal pricePerPoint = mkpConfigService.getPointPricePerPoint();
		List<MkpCustomerPointAcquireConfig> mkpCustomerPointAcquireConfigs = mkpCustomerPointAcquireConfigService.getAllActive();
		
		BigDecimal calculatedPrice = request.getPrice().add(mkpCustomer.getRemainAmount());
		
		Response.PointAcquire pointAcquire = new Response.PointAcquire();
		pointAcquire.setCustomerId(mkpCustomer.getId());
		pointAcquire.setMkpCustomer(mkpCustomer);
		pointAcquire.setPoint(new BigDecimal(calculatedPrice.divide(pricePerPoint).intValue()));
		pointAcquire.setRemainAmount(calculatedPrice.remainder(pricePerPoint).setScale(2, RoundingMode.DOWN));
		
		List<Response.PointAcquire> pointAcquires = new ArrayList<>();
		pointAcquires.add(pointAcquire);
		
		if(maxHierachyAcquire > 0 && mkpCustomer.getParentLineId() != null) {
			MkpCustomer currentCustomer = mkpCustomerService.findById(mkpCustomer.getParentLineId());
			
			for(int i=1; i<=maxHierachyAcquire; i++) {
				int index = i;
				
				if(currentCustomer == null) {
					break;
				}
				
				MkpCustomerPointAcquireConfig pointAcquireConfig = mkpCustomerPointAcquireConfigs.
						stream().
						filter(e -> e.getHierachyLevel() == index).
						findFirst().
						orElse(null);
				
				Response.PointAcquire _pointAcquire = new Response.PointAcquire();
				_pointAcquire.setCustomerId(currentCustomer.getId());
				_pointAcquire.setMkpCustomer(currentCustomer);
				_pointAcquire.setPoint(pointAcquire.getPoint().multiply(pointAcquireConfig.getAcquiredPercentage()).setScale(2, RoundingMode.HALF_UP));
				_pointAcquire.setRemainAmount(new BigDecimal(0));
				
				pointAcquires.add(_pointAcquire);
				
				if(currentCustomer.getParentLineId() == null) {
					break;
				}
				
				currentCustomer = mkpCustomerService.findById(currentCustomer.getParentLineId());
			}
		}
		
		Response response = new Response();
		response.setPointAcquires(pointAcquires);
		
		return response;
	}
}