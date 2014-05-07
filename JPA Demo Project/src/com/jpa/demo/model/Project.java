package com.jpa.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.jpa.utilities.CollectionUtils;
import com.jpa.utilities.Projectors;

@Entity
public class Project
{
	public Project() {}
	
	public Project(String name)
	{
		this.name = name;
	}
	
	public void addProjectEmployee(Employee emp)
	{
		if (!employees.contains(emp))
		{
			employees.add(emp);
			emp.addProject(this);
		}
	}
	
	public void removeProjectEmployee(Employee emp)
	{
		if (employees.contains(emp))
		{
			employees.remove(emp);
			emp.removeProject(this);
		}
	}
	
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Project other = (Project) obj;
		
		if (id != other.id)
			return false;
		
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		
		return true;
	}

	@Override
	public String toString()
	{
		return "Project [id=" + id + ", name=" + name + ", employees="
				+ CollectionUtils.projectToList(employees, Projectors.newEmployeeToIdProjector()) + "]";
	}

	@Id
	@GeneratedValue
	private int id;
	private String name;
	
	@ManyToMany(mappedBy="projects")
	private Set<Employee> employees = new HashSet<>();
}