package com.jpa.demo.model;

import java.util.Date;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class CompanyWorker extends Worker
{
	public CompanyWorker()
	{
		
	}
	
	public CompanyWorker(String name, Date hireDate, int vacation)
	{
		super(name, hireDate);
		this.vacation = vacation;
	}

	public int getVacation()
	{
		return vacation;
	}

	public void setVacation(int vacation)
	{
		this.vacation = vacation;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + vacation;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompanyWorker other = (CompanyWorker) obj;
		if (vacation != other.vacation)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "CompanyWorker [vacation=" + vacation + ", getId()=" + getId()
				+ ", getName()=" + getName() + ", getHireDate()="
				+ getHireDate() + "]";
	}

	private int vacation;
}
