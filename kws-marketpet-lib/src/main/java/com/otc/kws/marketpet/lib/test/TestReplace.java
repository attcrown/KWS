package com.otc.kws.marketpet.lib.test;

public class TestReplace 
{
	public static void main(String[] args) 
	{
		String serverURL = "http://localhost:8080/ezwow-fwg";
		String content = "abc ${server_url} def ${server_url}...123";
		System.out.println(content);
		System.out.println(content.replace("${server_url}", serverURL));
		System.out.println(content.replaceAll("${server_url}", serverURL));
	}
}