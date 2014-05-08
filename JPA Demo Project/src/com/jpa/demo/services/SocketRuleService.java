package com.jpa.demo.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.jpa.demo.model.Action;
import com.jpa.demo.model.SocketId;
import com.jpa.demo.model.SocketRule;

public class SocketRuleService extends AbstractJPAService
{
	public SocketRuleService(String serviceName, EntityManager manager)
	{
		super(serviceName, manager);
	}
	
	public SocketRule createSocketRule(int port, String host, Action action, String forwardTo, boolean isActive)
	{
		try
		{
			begin();
			SocketRule socketRule = new SocketRule(port, host, action, forwardTo, isActive);
			manager.persist(socketRule);
			commit();
			
			return socketRule;
		}
		catch(PersistenceException e)
		{
			e.printStackTrace();
			rollback();
			throw e;
		}
	}
	
	public SocketRule findSocketRule(int port, String host)
	{
		SocketId socketId = new SocketId(port, host);
		
		return manager.find(SocketRule.class, socketId);
	}
}
