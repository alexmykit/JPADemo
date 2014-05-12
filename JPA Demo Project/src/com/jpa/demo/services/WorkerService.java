package com.jpa.demo.services;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.jpa.demo.model.CompanyWorker;
import com.jpa.demo.model.ContractorWorker;
import com.jpa.demo.model.FullTimeWorker;
import com.jpa.demo.model.PartTimeWorker;
import com.jpa.demo.model.Worker;

public class WorkerService extends AbstractJPAService
{
	public WorkerService(String serviceName, EntityManager manager)
	{
		super(serviceName, manager);
	}
	
	public FullTimeWorker createFullTimeWorker(String name, Date hireDate, int vacation,
			float sallary, float pension)
	{
		try
		{
			begin();
			FullTimeWorker worker = new FullTimeWorker(name, hireDate, vacation, sallary, pension);
			manager.persist(worker);
			commit();
			
			return worker;
		}
		catch(PersistenceException e)
		{
			e.printStackTrace();
			rollback();
			throw e;
		}
	}
	
	public PartTimeWorker createPartTimeWorker(String name, Date hireDate, int vacation,
			float rate)
	{
		try
		{
			begin();
			PartTimeWorker worker = new PartTimeWorker(name, hireDate, vacation, rate);
			manager.persist(worker);
			commit();
			return worker;
		}
		catch(PersistenceException e)
		{
			e.printStackTrace();
			rollback();
			throw e;
		}
	}
	
	public ContractorWorker createContractorWorker(String name, Date hireDate, float rate,
			int term)
	{
		try
		{
			begin();
			ContractorWorker worker = new ContractorWorker(name, hireDate, rate, term);
			manager.persist(worker);
			commit();
			
			return worker;
		}
		catch(PersistenceException e)
		{
			e.printStackTrace();
			rollback();
			throw e;
		}
	}
	
	public Worker findWorker(int workerId)
	{
		return manager.find(Worker.class, workerId);
	}
}