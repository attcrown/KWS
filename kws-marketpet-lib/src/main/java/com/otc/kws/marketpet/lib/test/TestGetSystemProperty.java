package com.otc.kws.marketpet.lib.test;

public class TestGetSystemProperty 
{
	public static void main(String[] args) 
	{
		System.out.println("JAVA_HOME => " + System.getProperty("JAVA_HOME"));
		System.out.println("JAVA_HOME => " + System.getenv("JAVA_HOME"));
	}
}