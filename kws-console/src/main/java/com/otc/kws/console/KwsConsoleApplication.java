package com.otc.kws.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.otc.kws.core.domain.constant.KwsConst;

@SpringBootApplication(scanBasePackages = KwsConst.BASE_PACKAGE_NAME)
public class KwsConsoleApplication 
{
	public static void main(String[] args) 
	{
		SpringApplication.run(KwsConsoleApplication.class, args);
	}
}