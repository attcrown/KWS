package com.otc.kws.marketpet.lib.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestCheckBase64 
{
	public static void main(String[] args) throws Exception
	{
		String text1 = "abc123";
		String text2 = "hXBrPBf+V1HNhNCqx37gPfHeq9e23D//I63+FVaoXZk=";
		
		System.out.println("text1 => " + isBase64(text1));
		System.out.println("text2 => " + isBase64(text2));
	}
	
	protected static boolean isBase64(String text) 
	{
	    String pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
	    Pattern regex = Pattern.compile(pattern);
	    Matcher matcher = regex.matcher(text);
	    if (matcher.find()) {
	        return true;
	    } else {
	        return false;
	    }
	}
}