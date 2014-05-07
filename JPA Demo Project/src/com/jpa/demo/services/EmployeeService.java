package com.jpa.demo.services;

import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.jpa.demo.model.Address;
import com.jpa.demo.model.Company;
import com.jpa.demo.model.Department;
import com.jpa.demo.model.Employee;
import com.jpa.demo.model.EmployeeType;
import com.jpa.demo.model.ParkingSpace;
import com.jpa.demo.model.PhoneType;
import com.jpa.demo.model.VacationEntry;

public class EmployeeService extends AbstractJPAService
{
	public EmployeeService(String serviceName, EntityManager manager)
	{
		super(serviceName, manager);
		addService(new DepartmentService(DEPT_SRV, manager));
		addService(new ParkingSpaceService(PARK_SRV, manager));
	}
	
	public Employee createEmployee(String name, float sallary, Date hireDate, String desc, EmployeeType type, Company company ,Address address, int depId, int parkingSpaceId)
	{
		try
		{
			DepartmentService deptService = lookupService(DEPT_SRV, DepartmentService.class);
			Department employeeDepartment = deptService.findDepartment(depId);
			
			if (employeeDepartment == null)
			{
				throw new ServiceException("Department with id " + depId +  " was not found");
			}
			
			ParkingSpaceService parkService = lookupService(PARK_SRV, ParkingSpaceService.class);
			ParkingSpace parkingSpace = parkService.findParkingSpace(parkingSpaceId);
			
			if (parkingSpace == null)
			{
				throw new ServiceException("Parking space with id " + parkingSpaceId + " was not found");
			}
			
			begin();
			
			Employee employee = new Employee(name, sallary, hireDate, desc, type, employeeDepartment, parkingSpace);
			employee.setAddress(address);
			employee.setCompany(company);
			manager.persist(employee);
			
			commit();
			
			return employee;
		}
		catch(PersistenceException e)
		{
			e.printStackTrace();
			rollback();
			throw e;
		}
	}
	
	public void addVacationToEmployee(Employee employee, VacationEntry vacationEntry)
	{
		try
		{
			begin();
			employee.addVacationEntry(vacationEntry);
			manager.persist(employee);
			commit();
		}
		catch(PersistenceException e)
		{
			e.printStackTrace();
			rollback();
			throw e;
		}
	}
	
	public void addNickNameToEmployee(Employee employee, String nickName)
	{
		try
		{
			begin();
			employee.addNickName(nickName);
			manager.persist(employee);
			commit();
		}
		catch(PersistenceException e)
		{
			e.printStackTrace();
			rollback();
			throw e;
		}
	}
	
	public Employee findEmployeeById(int id)
	{
		return manager.find(Employee.class, id);
	}
	
	public List<Employee> getAllEmployees()
	{
		TypedQuery<Employee> findAllQuery = manager.createQuery("SELECT e FROM Employee e", Employee.class);
		
		return findAllQuery.getResultList();
	}
	
	public Map<String, Double> getAvgSallaryInEachDepartment()
	{
		Map<String, Double> avgSallaryInDepartments = new HashMap<String, Double>();
		
		Query avgSallaryPerDeptQuery = manager.createQuery("SELECT dep.departmentName, AVG(emp.sallary) FROM Department dep JOIN dep.employees emp GROUP BY dep");
		List results = avgSallaryPerDeptQuery.getResultList();
		
		for (Object result : results)
		{
			Object[] resultSet = (Object[])result;
			
			avgSallaryInDepartments.put((String)resultSet[0], (Double)resultSet[1]);
		}
		
		return avgSallaryInDepartments;
	}
	
	public List<Employee> getEmployeesWithSallaryHigherThenAvgSallaryInDepartment()
	{
		TypedQuery<Employee> query = manager.createQuery("SELECT e FROM Employee e WHERE e.sallary > (SELECT AVG(emps.sallary) FROM Employee emps WHERE emps.department = e.department)", Employee.class);
		
		return query.getResultList();
	}
	
	public List<Employee> getEmployeesWithoutVacations()
	{
		TypedQuery<Employee> query = manager.createQuery("SELECT e FROM Employee e WHERE SIZE(e.vacations) = 0", Employee.class);
		
		return query.getResultList();
	}
	
	public List<EmployeeVacationDuration> getEmployeeTotalVacationDurations()
	{
		TypedQuery<EmployeeVacationDuration> query = manager.createQuery("SELECT NEW com.jpa.demo.services.EmployeeVacationDuration(e, SUM(v.days)) FROM Employee e JOIN e.vacations v GROUP BY e", EmployeeVacationDuration.class);
		
		return query.getResultList();
	}
	
	public List<Employee> getEmployeesWithPhoneKinds(EnumSet<PhoneType> phoneTypes)
	{
		String queryPattern = "SELECT e FROM Employee e WHERE %s EXISTS(SELECT 1 FROM e.phones p WHERE KEY(p)=:firstType) AND %s EXISTS(SELECT 1 FROM e.phones p WHERE KEY(p)=:secondType)";
		TypedQuery<Employee> query = null; 
		
		if (phoneTypes.isEmpty())
		{
			queryPattern = String.format(queryPattern, "NOT", "NOT");
			query = manager.createQuery(queryPattern, Employee.class).setParameter("firstType", PhoneType.HOME).setParameter("secondType", PhoneType.MOBILE);
		}
		else
		{
			if (phoneTypes.size() == 1)
			{
				queryPattern = String.format(queryPattern, "", "NOT");
				query = manager.createQuery(queryPattern, Employee.class).setParameter("firstType", phoneTypes.toArray()[0]).setParameter("secondType", phoneTypes.complementOf(phoneTypes).toArray()[0]);
			}
			else
			{
				queryPattern = String.format(queryPattern, "", "");
				Object[] types = phoneTypes.toArray();
				query = manager.createQuery(queryPattern, Employee.class).setParameter("firstType", types[0]).setParameter("secondType", types[1]);
			}
		}
		
		return query.getResultList();
	}
	
	public static final String DEPT_SRV = "DepartmentService";
	public static final String PARK_SRV = "ParkingSpaceService";
}