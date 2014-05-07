package com.jpa.demo.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.jpa.demo.model.Department;

public class DepartmentService extends AbstractJPAService
{
	public DepartmentService(String serviceName, EntityManager manager)
	{
		super(serviceName, manager);
	}

	public Department createDepartment(String departmentName)
	{
		try
		{
			begin();
			Department dep = new Department(departmentName);
			manager.persist(dep);
			commit();
			return dep;
		}
		catch(PersistenceException e)
		{
			e.printStackTrace();
			rollback();
			throw e;
		}
	}
	
	public Department findDepartment(int id)
	{
		return manager.find(Department.class, id);
	}
}
