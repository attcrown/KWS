package com.otc.kws.core.domain.util;

import java.io.IOException;
import java.io.Writer;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil 
{
	public static <T> T parse(String jsonString, Class<T> valueType) throws JsonMappingException, JsonProcessingException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(jsonString, valueType);
	}
	
	public static String stringify(Object value) throws JsonProcessingException 
	{
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(value);
	}
	
	public static void writeValue(Writer writer, Object value) throws JsonGenerationException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, value);
	}
}