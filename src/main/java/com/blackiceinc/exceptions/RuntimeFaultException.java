package com.blackiceinc.exceptions;

public class RuntimeFaultException extends RuntimeException {
	private static final long serialVersionUID = 7401594060516714127L;
	
	public RuntimeFaultException() { super(); }
	public RuntimeFaultException(String message) { super(message); }
	public RuntimeFaultException(String message, Throwable cause) { super(message, cause); }
	public RuntimeFaultException(Throwable cause) { super(cause); }
}
