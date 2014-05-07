package com.jpa.demo.services;

import com.jpa.demo.model.Employee;

public class EmployeeVacationDuration
{
	public EmployeeVacationDuration(Employee employee, long totalVacation)
	{
		this.employee = employee;
		this.totalVacation = totalVacation;
	}
			
	public Employee getEmployee()
	{
		return employee;
	}
	public long getTotalVacation()
	{
		return totalVacation;
	}
			
	@Override
	public String toString()
	{
		return "EmployeeVacationDuration [employee=" + employee
				+ ", totalVacation=" + totalVacation + "]";
	}

	private Employee employee;
	private long totalVacation;
}
