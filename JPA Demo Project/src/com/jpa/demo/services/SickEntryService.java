package com.jpa.demo.services;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.jpa.demo.model.SickEntry;

public class SickEntryService extends AbstractJPAService
{
	public SickEntryService(String serviceName, EntityManager manager)
	{
		super(serviceName, manager);
	}
	
	public SickEntry createSickEntry(Date from, int days)
	{
		try
		{
			manager.getTransaction().begin();
			SickEntry entry = new SickEntry(from, days);
			manager.persist(entry);
			manager.getTransaction().commit();
			
			return entry;
		}
		catch(PersistenceException e)
		{
			e.printStackTrace();
			manager.getTransaction().rollback();
			throw e;
		}
	}
	
	public SickEntry findSickEntry(int entryId)
	{
		return manager.find(SickEntry.class, entryId);
	}
}
