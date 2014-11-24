package com.blackiceinc.exceptions;

public class CustomerNotRegisteredException extends RuntimeException {
	private static final long serialVersionUID = 5793875480391571211L;
	
	public CustomerNotRegisteredException() { super(); }
	public CustomerNotRegisteredException(String message) { super(message); }
	public CustomerNotRegisteredException(String message, Throwable cause) { super(message, cause); }
	public CustomerNotRegisteredException(Throwable cause) { super(cause); }
}
