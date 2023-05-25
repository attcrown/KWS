package com.otc.kws.core.domain.dto;

import java.util.List;

import lombok.Data;

@Data
public class EducationMajorSearchRequestDto 
{
	protected Criteria criteria;
	protected Paging paging;
	protected List<Sort> sortBy;
	
	
	@Data
	public static class Criteria
	{
		protected List<String> educationFacultyIds;
		protected String name;
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