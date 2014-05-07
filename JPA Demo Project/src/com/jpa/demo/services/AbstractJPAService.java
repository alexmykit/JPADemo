package com.jpa.demo.services;

import javax.persistence.EntityManager;

import com.jpa.demo.core.Context;
import com.jpa.demo.core.Service;

public class AbstractJPAService extends Context implements Service
{
	public AbstractJPAService(String serviceName, EntityManager manager)
	{
		if (serviceName == null || serviceName.isEmpty())
			throw new IllegalArgumentException("Service name canno't be empty!");
		
		this.manager = manager;
		this.serviceName = serviceName;
	}
	
	public AbstractJPAService(String serviceName, EntityManager manager, boolean manageTransactions)
	{
		this(serviceName, manager);
		this.manageTransactions = manageTransactions;
	}
	
	public AbstractJPAService setManageTransactions(boolean manageTransactions)
	{
		this.manageTransactions = manageTransactions;
		
		return this;
	}
	
	public EntityManager getManager()
	{
		return manager;
	}

	public void setManager(EntityManager manager)
	{
		this.manager = manager;
	}
	
	protected void rollback()
	{
		if (manageTransactions) manager.getTransaction().rollback();
	}
	
	protected void commit()
	{
		if (manageTransactions) manager.getTransaction().commit();
	}
	
	protected void begin()
	{
		if (manageTransactions) manager.getTransaction().begin();
	}
	
	@Override
	public String getServiceName()
	{
		return serviceName;
	}
	
	protected EntityManager manager;
	protected String serviceName;
	private boolean manageTransactions = true;
}