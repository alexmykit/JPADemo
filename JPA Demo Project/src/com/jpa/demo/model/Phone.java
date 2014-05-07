package com.jpa.demo.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Phone
{	
	public Phone() { }
	
	public Phone(String phoneNumber, PhoneType phoneType)
	{
		this.phoneNumber = phoneNumber;
		this.phoneType = phoneType;
	}
			
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	public PhoneType getPhoneType()
	{
		return phoneType;
	}
	public void setPhoneType(PhoneType phoneType)
	{
		this.phoneType = phoneType;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result
				+ ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result
				+ ((phoneType == null) ? 0 : phoneType.hashCode());
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
		Phone other = (Phone) obj;
		if (id != other.id)
			return false;
		if (phoneNumber == null)
		{
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (phoneType != other.phoneType)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "Phone [id=" + id + ", phoneNumber=" + phoneNumber
				+ ", phoneType=" + phoneType + "]";
	}

	@Id
	@GeneratedValue
	private int id;
	private String phoneNumber;
	@Enumerated(EnumType.STRING)
	private PhoneType phoneType;
}
