package com.blackiceinc.exceptions;

public class FailedBootstrapCusomerAccessException extends RuntimeException {
	private static final long serialVersionUID = 2996101261362153939L;
	
	public FailedBootstrapCusomerAccessException() { super(); }
	public FailedBootstrapCusomerAccessException(String message) { super(message); }
	public FailedBootstrapCusomerAccessException(String message, Throwable cause) { super(message, cause); }
	public FailedBootstrapCusomerAccessException(Throwable cause) { super(cause); }
}
