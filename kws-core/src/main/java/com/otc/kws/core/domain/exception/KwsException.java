package com.otc.kws.core.domain.exception;

public class KwsException extends Exception
{
	public KwsException() {
		super();
	}

	public KwsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public KwsException(String message, Throwable cause) {
		super(message, cause);
	}

	public KwsException(String message) {
		super(message);
	}

	public KwsException(Throwable cause) {
		super(cause);
	}
}