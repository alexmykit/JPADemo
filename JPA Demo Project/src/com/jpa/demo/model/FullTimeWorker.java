package com.jpa.demo.model;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class FullTimeWorker extends CompanyWorker
{
	public FullTimeWorker()
	{
		
	}
	
	public FullTimeWorker(String name, Date hireDate, int vacation,
			float sallary, float pension)
	{
		super(name, hireDate, vacation);
		this.sallary = sallary;
		this.pension = pension;
	}

	public float getSallary()
	{
		return sallary;
	}

	public void setSallary(float sallary)
	{
		this.sallary = sallary;
	}

	public float getPension()
	{
		return pension;
	}

	public void setPension(float pension)
	{
		this.pension = pension;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Float.floatToIntBits(pension);
		result = prime * result + Float.floatToIntBits(sallary);
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
		FullTimeWorker other = (FullTimeWorker) obj;
		if (Float.floatToIntBits(pension) != Float
				.floatToIntBits(other.pension))
			return false;
		if (Float.floatToIntBits(sallary) != Float
				.floatToIntBits(other.sallary))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "FullTimeWorker [sallary=" + sallary + ", pension=" + pension
				+ ", getVacation()=" + getVacation() + ", getId()=" + getId()
				+ ", getName()=" + getName() + ", getHireDate()="
				+ getHireDate() + "]";
	}

	private float sallary;
	private float pension;
}
