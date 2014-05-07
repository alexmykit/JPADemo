package com.jpa.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ParkingSpace
{
	public ParkingSpace() { }
	
	public ParkingSpace(int slot, String label)
	{
		this.slot = slot;
		this.label = label;
	}
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getSlot()
	{
		return slot;
	}
	public void setSlot(int slot)
	{
		this.slot = slot;
	}
	public String getLabel()
	{
		return label;
	}
	public void setLabel(String label)
	{
		this.label = label;
	}

	public Employee getEmployee()
	{
		return employee;
	}

	public void setEmployee(Employee employee)
	{
		this.employee = employee;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + slot;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		System.out.println("ParkingSpace.equals() called");
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParkingSpace other = (ParkingSpace) obj;
		if (id != other.id)
			return false;
		if (label == null)
		{
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (slot != other.slot)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "ParkingSpace [id=" + id + ", slot=" + slot + ", label=" + label + ", employee=" + (employee == null ? null : employee.getId())
				+ "]";
	}

	@Id
	@GeneratedValue
	private int id;
	private int slot;
	private String label;
	@OneToOne(mappedBy="parkingSpace")
	private Employee employee;
}