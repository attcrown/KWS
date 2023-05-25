package com.otc.kws.esc.lib.domain.model;

import java.sql.Time;

import com.otc.kws.esc.lib.domain.constant.value.DayOfWeekValue;

import lombok.Data;

@Data
public class RequestCourseScheduleModel 
{
	protected DayOfWeekValue day;
	protected ScheduleTime time;
	protected String locationId;
	
	
	@Data
	public static class ScheduleTime
	{
		protected Time start;
		protected Time end;
	}
}