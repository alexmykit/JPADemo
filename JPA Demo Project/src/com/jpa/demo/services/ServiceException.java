package com.jpa.demo.services;

public class ServiceException extends RuntimeException
{
	private static final long serialVersionUID = 2969494496364548996L;

	public ServiceException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ServiceException(String message)
	{
		super(message);
	}

	public ServiceException(Throwable cause)
	{
		super(cause);
	}
}
