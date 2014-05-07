package com.jpa.demo.core;

import java.util.HashMap;
import java.util.Map;

public class Context
{
	public Context()
	{
		
	}
	
	public void addService(Service service)
	{	
		if (contextServices.containsKey(service.getServiceName()))
			throw new ServiceAlreadyExistsException("Service with name \"" + service.getServiceName() + "\" already exists");
		
		contextServices.put(service.getServiceName(), service);
	}
	
	public <T extends Service> T lookupService(String serviceName, Class<T> serviceClass) throws ServiceNotFoundException
	{
		Service service = contextServices.get(serviceName);
		
		if (service == null)
			throw new ServiceNotFoundException("Service \"" + serviceName + "\" not found");
		
		if (service.getClass() != serviceClass)
			throw new ServiceNotFoundException("Service of requested type " + serviceClass + " was not found");
		
		return (T)service;
	}
	
	private Map<String, Service> contextServices = new HashMap<>();
}