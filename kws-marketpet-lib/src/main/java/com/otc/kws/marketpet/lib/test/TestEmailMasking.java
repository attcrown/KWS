package com.otc.kws.marketpet.lib.test;

public class TestEmailMasking 
{
	public static void main(String[] args) 
	{
		String text = "otc.anonymous.02@gmail.com";
		String maskedText = text.replaceAll("(^[^@]{3}|(?!^)\\G)[^@]", "$1*");
		System.out.println("text => " + text);
		System.out.println("maskedText => " + maskedText);
	}
}