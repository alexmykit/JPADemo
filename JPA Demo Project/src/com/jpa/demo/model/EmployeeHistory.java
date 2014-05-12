package com.jpa.demo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class EmployeeHistory implements Serializable
{
	public EmployeeHistory()
	{
		
	}
	
	public EmployeeHistory(Employee employee, Date lastUpdate)
	{
		this.employee = employee;
		this.lastUpdate = lastUpdate;
	}
	
	public Employee getEmployee()
	{
		return employee;
	}
	
	public void setEmployee(Employee employee)
	{
		this.employee = employee;
	}
	
	public Date getLastUpdate()
	{
		return lastUpdate;
	}
	
	public void setLastUpdate(Date lastUpdate)
	{
		this.lastUpdate = lastUpdate;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((employee == null) ? 0 : employee.hashCode());
		result = prime * result
				+ ((lastUpdate == null) ? 0 : lastUpdate.hashCode());
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
		EmployeeHistory other = (EmployeeHistory) obj;
		if (employee == null)
		{
			if (other.employee != null)
				return false;
		}
		else if (!employee.equals(other.employee))
			return false;
		if (lastUpdate == null)
		{
			if (other.lastUpdate != null)
				return false;
		}
		else if (!lastUpdate.equals(other.lastUpdate))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "EmployeeHistory [employee=" + employee + ", lastUpdate="
				+ lastUpdate + "]";
	}

	@Id
	@OneToOne
	@JoinColumn(name="EMP_ID")
	private Employee employee;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;
	
	private static final long serialVersionUID = 667552344961326554L;
}