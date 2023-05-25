package com.otc.kws.console.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.otc.kws.core.domain.service.ConfigService;

@Configuration
@EnableWebMvc
public class KwsConsoleWebMvcConfig implements WebMvcConfigurer
{
	@Autowired
	protected ConfigService configService;
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) 
	{
		registry.
			addResourceHandler("/assets/**").
			addResourceLocations("classpath:/static/assets/");
		
		registry.
			addResourceHandler("/" + configService.getResourceWebPrefixPath() + "/**").
			addResourceLocations(configService.getResourceFileLocation());
	}
}