package com.jpa.demo.services;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.jpa.demo.model.Employee;
import com.jpa.demo.model.EmployeeHistory;

public class EmployeeHistoryService extends AbstractJPAService
{
	public EmployeeHistoryService(String serviceName, EntityManager manager)
	{
		super(serviceName, manager);
	}
	
	public EmployeeHistory createEmployeeHistory(Employee employee, Date lastModified)
	{
		try
		{
			begin();
			EmployeeHistory history = new EmployeeHistory(employee, lastModified);
			manager.persist(history);
			commit();
			
			return history;
		}
		catch(PersistenceException e)
		{
			e.printStackTrace();
			rollback();
			throw e;
		}
	}
	
	public EmployeeHistory findEmployeeHistory(Employee employee)
	{
		return manager.find(EmployeeHistory.class, employee.getId());
	}
}
