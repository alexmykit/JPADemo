package com.jpa.utilities;

import com.jpa.demo.model.Employee;

public class Projectors
{
	public static Projector<Employee, Integer> newEmployeeToIdProjector()
	{
		return new Projector<Employee, Integer>()
		{
			
			@Override
			public Integer project(Employee item)
			{
				return item.getId();
			}
		};
	}
}