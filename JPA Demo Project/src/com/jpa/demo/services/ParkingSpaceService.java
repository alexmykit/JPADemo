package com.jpa.demo.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.jpa.demo.model.ParkingSpace;

public class ParkingSpaceService extends AbstractJPAService
{
	public ParkingSpaceService(String serviceName, EntityManager manager)
	{
		super(serviceName, manager);
	}
	
	public ParkingSpace createParkingSpace(int slot, String label)
	{
		try
		{
			begin();
			ParkingSpace parkingSpace = new ParkingSpace(slot, label);
			manager.persist(parkingSpace);
			commit();
			return parkingSpace;
		}
		catch(PersistenceException e)
		{
			e.printStackTrace();
			rollback();
			throw e;
		}
	}
	
	public ParkingSpace findParkingSpace(int id)
	{
		return manager.find(ParkingSpace.class, id);
	}
}
