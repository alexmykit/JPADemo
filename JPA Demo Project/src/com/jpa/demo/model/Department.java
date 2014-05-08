package com.jpa.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.jpa.utilities.CollectionUtils;
import com.jpa.utilities.Projectors;

@Entity
public class Department
{
	public Department() {}
	
	public Department(String departmentName)
	{
		this.departmentName = departmentName;
	}
		
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getDepartmentName()
	{
		return departmentName;
	}

	public void setDepartmentName(String departmentName)
	{
		this.departmentName = departmentName;
	}
	
	public void addEmployee(Employee emp)
	{
		employees.add(emp);
	}
	
	public Set<Employee> getEmployees()
	{
		return employees;
	}

	public void setEmployees(Set<Employee> employees)
	{
		this.employees = employees;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((departmentName == null) ? 0 : departmentName.hashCode());
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		if (departmentName == null)
		{
			if (other.departmentName != null)
				return false;
		} else if (!departmentName.equals(other.departmentName))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "Department [id=" + id + ", departmentName=" + departmentName + ", employees=" + CollectionUtils.projectToList(employees, Projectors.newEmployeeToIdProjector())
				+ "]";
	}

	@Id
	@GeneratedValue
	private int id;
	private String departmentName;
	@OneToMany(mappedBy="department")
	private Set<Employee> employees = new HashSet<>();
}
