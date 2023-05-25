package com.otc.kws.core.domain.exception;

public class KwsRuntimeException extends RuntimeException
{
	public KwsRuntimeException() {
		super();
	}

	public KwsRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public KwsRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public KwsRuntimeException(String message) {
		super(message);
	}

	public KwsRuntimeException(Throwable cause) {
		super(cause);
	}
}