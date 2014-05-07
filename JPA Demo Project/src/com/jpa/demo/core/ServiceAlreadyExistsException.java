package com.jpa.demo.core;

public class ServiceAlreadyExistsException extends RuntimeException
{
	private static final long serialVersionUID = -8189521656190964797L;

	public ServiceAlreadyExistsException() { }

	public ServiceAlreadyExistsException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ServiceAlreadyExistsException(String message)
	{
		super(message);
	}

	public ServiceAlreadyExistsException(Throwable cause)
	{
		super(cause);
	}
}
