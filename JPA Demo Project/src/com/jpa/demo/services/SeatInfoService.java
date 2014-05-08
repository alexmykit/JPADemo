package com.jpa.demo.services;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.jpa.demo.model.SeatInfo;
import com.jpa.demo.model.SeatPosition;

public class SeatInfoService extends AbstractJPAService
{
	public SeatInfoService(String serviceName, EntityManager manager)
	{
		super(serviceName, manager);
	}
	
	public SeatInfo createSeatInfo(SeatPosition position, boolean isOccupied, Date occuppiedFrom, Date occuppiedTo)
	{
		try
		{
			begin();
			SeatInfo seatInfo = new SeatInfo(position, isOccupied, occuppiedFrom, occuppiedTo);
			manager.persist(seatInfo);
			commit();
			
			return seatInfo;
		}
		catch(PersistenceException e)
		{
			e.printStackTrace();
			rollback();
			throw e;
		}
	}
	
	public SeatInfo findSeatInfo(int row, int column)
	{
		SeatPosition position = new SeatPosition(row, column);
		
		return manager.find(SeatInfo.class, position);
	}
}