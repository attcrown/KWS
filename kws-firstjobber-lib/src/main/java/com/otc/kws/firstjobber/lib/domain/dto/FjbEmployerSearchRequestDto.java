package com.otc.kws.firstjobber.lib.domain.dto;

import java.util.List;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;

import lombok.Data;

@Data
public class FjbEmployerSearchRequestDto 
{
	protected Criteria criteria;
	protected Paging paging;
	protected List<Sort> sortBy;
	
	
	@Data
	public static class Criteria
	{
		protected List<String> employerTypeIds;
		protected String name;
		protected List<MasterStatusValue> statuses;
	}
	
	@Data
	public static class Paging
	{
		protected int pageNo = 1;
		protected int pageSize = 25;
	}
	
	@Data
	public static class Sort
	{
		protected String field;
		protected Order order;
		
		public static enum Order
		{
			asc, desc
		}
	}
}