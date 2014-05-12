package com.jpa.demo.services;

import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.jpa.demo.model.Address;
import com.jpa.demo.model.Company;
import com.jpa.demo.model.Department;
import com.jpa.demo.model.Employee;
import com.jpa.demo.model.EmployeeType;
import com.jpa.demo.model.ParkingSpace;
import com.jpa.demo.model.Phone;
import com.jpa.demo.model.PhoneType;
import com.jpa.demo.model.Project;
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
	
	public List<Employee> getAllEmployeesCriteria()
	{
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
		query.from(Employee.class);
		
		return manager.createQuery(query).getResultList();
	}
	
	public Map<String, Double> getAvgSallaryInEachDepartment()
	{
		Map<String, Double> avgSallaryInDepartments = new HashMap<>();
		
		Query avgSallaryPerDeptQuery = manager.createQuery("SELECT dep.departmentName, AVG(emp.sallary) FROM Department dep JOIN dep.employees emp GROUP BY dep");
		List results = avgSallaryPerDeptQuery.getResultList();
		
		for (Object result : results)
		{
			Object[] resultSet = (Object[])result;
			
			avgSallaryInDepartments.put((String)resultSet[0], (Double)resultSet[1]);
		}
		
		return avgSallaryInDepartments;
	}
	
	public Map<String, Double> getAvgSallaryInEachDepartmentCriteria()
	{
		Map<String, Double> avgSallaryInDepartments = new HashMap<>();
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		CriteriaQuery<Tuple> query = builder.createTupleQuery();
		Root<Department> depRoot = query.from(Department.class);
		Join<Department, Employee> depEmpJoinRoot = depRoot.join("employees");
		
		query.multiselect(depRoot.get("departmentName").alias("departmentName"), builder.avg(depEmpJoinRoot.<Double>get("sallary")).alias("avgSallary")).groupBy(depRoot);
		
		TypedQuery<Tuple> resQuery = manager.createQuery(query); 
		
		List<Tuple> results = resQuery.getResultList();
		
		for (Tuple row : results)
		{
			avgSallaryInDepartments.put((String)row.get("departmentName"), (Double)row.get("avgSallary"));
		}
		
		return avgSallaryInDepartments;
	}
	
	public List<Employee> getEmployeesWithSallaryHigherThenAvgSallaryInDepartment()
	{
		TypedQuery<Employee> query = manager.createQuery("SELECT e FROM Employee e WHERE e.sallary > (SELECT AVG(emps.sallary) FROM Employee emps WHERE emps.department = e.department)", Employee.class);
		
		return query.getResultList();
	}
	
	public List<Employee> getEmployeesWithSallaryHigherThenAvgSallaryInDepartmentCriteria()
	{
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		CriteriaQuery<Employee> critQuery = builder.createQuery(Employee.class);
		Root<Employee> employeeRoot = critQuery.from(Employee.class);
		Subquery<Double> sallarySubQuery = critQuery.subquery(Double.class);
		Root<Employee> subqueryRoot = sallarySubQuery.from(Employee.class);
		sallarySubQuery.select(builder.avg(subqueryRoot.<Double>get("sallary"))).where(builder.equal(subqueryRoot.get("department"), employeeRoot.get("department")));
		
		critQuery.select(employeeRoot).where(builder.gt(employeeRoot.<Double>get("sallary"), sallarySubQuery));
		
		TypedQuery<Employee> typedQuery = manager.createQuery(critQuery);
		
		return typedQuery.getResultList();
	}
	
	public List<Employee> getEmployeesWithoutVacations()
	{
		TypedQuery<Employee> query = manager.createQuery("SELECT e FROM Employee e WHERE SIZE(e.vacations) = 0", Employee.class);
		
		return query.getResultList();
	}
	
	public List<Employee> getEmployeesWithoutVacationsCriteria()
	{
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		CriteriaQuery<Employee> critQuery = builder.createQuery(Employee.class);
		Root<Employee> root = critQuery.from(Employee.class);
		
		critQuery.select(root).where(builder.equal(builder.size(root.<List<VacationEntry>>get("vacations")), 0));
		
		TypedQuery<Employee> query = manager.createQuery(critQuery);
		
		return query.getResultList();
	}
	
	public List<EmployeeVacationDuration> getEmployeeTotalVacationDurations()
	{
		TypedQuery<EmployeeVacationDuration> query = manager.createQuery("SELECT NEW com.jpa.demo.services.EmployeeVacationDuration(e, SUM(v.days)) FROM Employee e JOIN e.vacations v GROUP BY e", EmployeeVacationDuration.class);
		
		return query.getResultList();
	}
	
	public List<EmployeeVacationDuration> getEmployeeTotalVacationDurationsCriteria()
	{
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<EmployeeVacationDuration> critQuery = builder.createQuery(EmployeeVacationDuration.class);
		
		Root<Employee> empRoot = critQuery.from(Employee.class);
		Join<Employee, VacationEntry> empVacationjoin = empRoot.join("vacations");
		critQuery.groupBy(empRoot);
		
		critQuery.select(builder.construct(EmployeeVacationDuration.class, empRoot, builder.sum(empVacationjoin.<Long>get("days"))));
		
		TypedQuery<EmployeeVacationDuration> query = manager.createQuery(critQuery);
		
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
	
	public List<Employee> getEmployyeeWithPhoneKindsCriteria(EnumSet<PhoneType> phoneTypes)
	{
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Employee> critQuery = builder.createQuery(Employee.class);
		Root<Employee> root = critQuery.from(Employee.class);
		critQuery.select(root);
		Subquery<Integer> firstTypeSq = critQuery.subquery(Integer.class);		
		Root<Employee> firstSqRoot = firstTypeSq.correlate(root);
		MapJoin<Employee, PhoneType, Phone> firstSqPhoneJoin = firstSqRoot.joinMap("phones");
		firstTypeSq.select(builder.literal(1)).where(builder.equal(firstSqPhoneJoin.value().get("phoneType"), builder.parameter(PhoneType.class, "firstType")));
		Subquery<Integer> secondTypeSq = critQuery.subquery(Integer.class);
		Root<Employee> secondSqRoot = secondTypeSq.correlate(root);
		MapJoin<Employee, PhoneType, Phone> secondSqPhoneJoin = secondSqRoot.joinMap("phones");
		secondTypeSq.select(builder.literal(1)).where(builder.equal(secondSqPhoneJoin.value().get("phoneType"), builder.parameter(PhoneType.class, "secondType")));
		Predicate firstExistsPredicate = builder.exists(firstTypeSq);
		Predicate secondExistsPredicate = builder.exists(secondTypeSq);
		
		PhoneType firstParameter = null, secondParameter = null;
		if (phoneTypes.isEmpty())
		{
			firstExistsPredicate = builder.not(firstExistsPredicate);
			secondExistsPredicate = builder.not(secondExistsPredicate);
			firstParameter = PhoneType.HOME;
			secondParameter = PhoneType.MOBILE;
		}
		else
		{
				if (phoneTypes.size() == 1)
				{
					secondExistsPredicate = builder.not(secondExistsPredicate);
					firstParameter = (PhoneType)phoneTypes.toArray()[0];
					secondParameter = (PhoneType)phoneTypes.complementOf(phoneTypes).toArray()[0];
				}
				else
				{
					Object[] types = phoneTypes.toArray();
					firstParameter = (PhoneType)types[0];
					secondParameter = (PhoneType)types[1];
				}
		}
		
		Predicate finalPredicate = builder.and(firstExistsPredicate, secondExistsPredicate);
		critQuery.where(finalPredicate);
		
		TypedQuery<Employee> query = manager.createQuery(critQuery);
		query.setParameter("firstType", firstParameter);
		query.setParameter("secondType", secondParameter);
		
		return query.getResultList();
	}
	
	public List<Employee> getEmployeesWithProject(String projectName)
	{
		TypedQuery<Employee> query = manager.createQuery("SELECT emp FROM Employee emp WHERE EXISTS(SELECT 1 FROM emp.projects p WHERE p.name=:name)", Employee.class);
		query.setParameter("name", projectName);
		
		return query.getResultList();
	}
	
	public List<Employee> getEmployeesWithProjectCriteria(String projectName)
	{
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Employee> critQuery = builder.createQuery(Employee.class);
		Root<Employee> root = critQuery.from(Employee.class);
		Subquery<Integer> projSq = critQuery.subquery(Integer.class);
		Root<Employee> sqRoot = projSq.correlate(root);
		Join<Employee, Project> sqEmpJoin = sqRoot.join("projects");
		
		projSq.select(builder.literal(1)).where(builder.equal(sqEmpJoin.get("name"), builder.parameter(String.class, "name")));
		critQuery.where(builder.exists(projSq));
		
		TypedQuery<Employee> query = manager.createQuery(critQuery);
		query.setParameter("name", projectName);
		
		return query.getResultList();
	}
	
	public static final String DEPT_SRV = "DepartmentService";
	public static final String PARK_SRV = "ParkingSpaceService";
}