package com.otc.kws.core.domain.exception;

public class DuplicateEntityException extends KwsException
{
	public DuplicateEntityException() {
		super();
	}

	public DuplicateEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DuplicateEntityException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateEntityException(String message) {
		super(message);
	}

	public DuplicateEntityException(Throwable cause) {
		super(cause);
	}
}