package com.jpa.demo.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.jpa.demo.model.Employee;
import com.jpa.demo.model.Project;

public class ProjectService extends AbstractJPAService
{
	public ProjectService(String serviceName, EntityManager manager)
	{
		super(serviceName, manager);
		addService(new EmployeeService(EMP_SRV, manager));
	}
	
	public Project createProject(String name)
	{
		try
		{
			Project project = new Project(name);
			
			begin();
			manager.persist(project);
			commit();
			
			return project;
		}
		catch(PersistenceException e)
		{
			e.printStackTrace();
			rollback();
			throw e;
		}
	}
	
	public Project findProject(int projectId)
	{
		return manager.find(Project.class, projectId);
	}
	
	public void addEmployeeToProject(int employeeId, int projectId)
	{
		Project project = findProject(projectId);
		if (project == null)
			throw new ServiceException("Project with id " + projectId + " not found");
		
		Employee employee = lookupService(EMP_SRV, EmployeeService.class).findEmployeeById(employeeId);
		if (employee == null)
			throw new ServiceException("Employee with id " + employeeId + " not found");
		
		try
		{
			begin();
			project.addProjectEmployee(employee);
			commit();
		}
		catch(PersistenceException e)
		{
			e.printStackTrace();
			rollback();
			throw e;
		}
	}
	
	public void removeEmployeeFromProject(int employeeId, int projectId)
	{
		Project project = findProject(projectId);
		if (project == null)
			throw new ServiceException("Project with id " + projectId + " not found");
		
		Employee employee = lookupService(EMP_SRV, EmployeeService.class).findEmployeeById(employeeId);
		if (employee == null)
			throw new ServiceException("Employee with id " + employeeId + " not found");
		
		try
		{
			begin();
			project.removeProjectEmployee(employee);
			commit();
		}
		catch(PersistenceException e)
		{
			e.printStackTrace();
			rollback();
			throw e;
		}
	}
	
	private static final String EMP_SRV = "EmployeeService";
}