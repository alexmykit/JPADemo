package com.jpa.demo.core;

public class ServiceNotFoundException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public ServiceNotFoundException() {}
	
	public ServiceNotFoundException(String msg)
	{
		super(msg);
	}
	
	public ServiceNotFoundException(Throwable cause)
	{
		super(cause);
	}
	
	public ServiceNotFoundException(String msg, Throwable cause)
	{
		super(msg);
		initCause(cause);
	}
}
