package com.jpa.demo.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.jpa.demo.model.Employee;
import com.jpa.demo.model.Phone;
import com.jpa.demo.model.PhoneType;

public class PhoneService extends AbstractJPAService
{
	public PhoneService(String serviceName, EntityManager manager)
	{
		super(serviceName, manager);
		addService(new EmployeeService(EMP_SRV, manager));
	}
	
	public Phone createPhone(String phone, PhoneType phoneType)
	{
		try
		{
			Phone newPhone = new Phone(phone, phoneType);
			begin();
			manager.persist(newPhone);
			commit();
			
			return newPhone;
		}
		catch(PersistenceException e)
		{
			e.printStackTrace();
			rollback();
			throw e;
		}
	}
	
	public Phone findPhone(int id)
	{
		return manager.find(Phone.class, id);
	}
	
	public void addPhoneToEmployee(int employeeId, Phone phone)
	{
		Employee employee = lookupService(EMP_SRV, EmployeeService.class).findEmployeeById(employeeId);
		
		if (employee == null)
			throw new ServiceException("Employee with id " + employeeId + " not found");
		
		try
		{
			begin();
			employee.addPhone(phone);
			commit();
		}
		catch(PersistenceException e)
		{
			e.printStackTrace();
			manager.getTransaction().rollback();
			throw e;
		}
	}
	
	public static final String EMP_SRV = "EmployeeService";
}