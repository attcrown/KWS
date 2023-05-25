package com.otc.kws.core.domain.util;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DayOfWeekUtil 
{
	public static DayOfWeek getDayOfWeek(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendarDayToDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
	}
	
	public static int dayOfWeekToCalendarDay(DayOfWeek day)
	{
	    return (day.getValue() % 7) + 1;
	}

	public static DayOfWeek calendarDayToDayOfWeek(int calendarDay)
	{
	    int temp = (calendarDay + 6) % 7;
	    return (temp == 0) ? DayOfWeek.SUNDAY : DayOfWeek.of(temp);
	}
	
	
	/*
	public static void main(String[] args) throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		
		Date date1 = sdf.parse("2022-04-24");
		Date date2 = sdf.parse("2022-04-25");
		Date date3 = sdf.parse("2022-04-26");
		Date date4 = sdf.parse("2022-04-27");
		Date date5 = sdf.parse("2022-04-28");
		Date date6 = sdf.parse("2022-04-29");
		Date date7 = sdf.parse("2022-04-30");
		Date date8 = sdf.parse("2022-05-01");
		Date date9 = sdf.parse("2022-05-02");
		
		System.out.println("2022-04-24 => " + DayOfWeekUtil.getDayOfWeek(date1));
		System.out.println("2022-04-25 => " + DayOfWeekUtil.getDayOfWeek(date2));
		System.out.println("2022-04-26 => " + DayOfWeekUtil.getDayOfWeek(date3));
		System.out.println("2022-04-27 => " + DayOfWeekUtil.getDayOfWeek(date4));
		System.out.println("2022-04-28 => " + DayOfWeekUtil.getDayOfWeek(date5));
		System.out.println("2022-04-29 => " + DayOfWeekUtil.getDayOfWeek(date6));
		System.out.println("2022-04-30 => " + DayOfWeekUtil.getDayOfWeek(date7));
		System.out.println("2022-05-01 => " + DayOfWeekUtil.getDayOfWeek(date8));
		System.out.println("2022-05-02 => " + DayOfWeekUtil.getDayOfWeek(date9));
	}
	*/
}