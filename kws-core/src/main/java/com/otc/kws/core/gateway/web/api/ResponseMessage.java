package com.otc.kws.core.gateway.web.api;

import java.util.function.Consumer;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage <B extends ResponseMessage.Body>
{
	protected Head head;
	protected B body;
	
	
	public void setHeadMessage(Consumer<ResponseMessage.Head> consumer)
    {
		ResponseMessage.Head head = new ResponseMessage.Head();
        consumer.accept(head);
        setHead(head);
    }
	
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Head
	{
		protected Status status;
		protected String statusCode;
		protected String statusMessage;
	}
	
	
	public static interface Body
	{
		
	}
	
	public static abstract class BaseBody implements Body
	{
		
	}
	
	
	public static enum Status
    {
    	Success, Error
    }
}