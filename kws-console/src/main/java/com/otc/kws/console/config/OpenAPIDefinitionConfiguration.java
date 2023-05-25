package com.otc.kws.console.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition
(
	info = @Info
	(
		title = "KWS API", 
		version = "1.0.0", 
		description = "KWS Rest API Endpoint Definition"
	)
)
public class OpenAPIDefinitionConfiguration 
{

}