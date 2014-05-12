package com.jpa.demo.model;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class PartTimeWorker extends CompanyWorker
{
	public PartTimeWorker()
	{
		
	}
	
	public PartTimeWorker(String name, Date hireDate, int vacation,
			float rate)
	{
		super(name, hireDate, vacation);
		this.rate = rate;
	}

	public float getRate()
	{
		return rate;
	}

	public void setRate(float rate)
	{
		this.rate = rate;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Float.floatToIntBits(rate);
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
		PartTimeWorker other = (PartTimeWorker) obj;
		if (Float.floatToIntBits(rate) != Float.floatToIntBits(other.rate))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "PartTimeWorker [rate=" + rate + ", getVacation()="
				+ getVacation() + ", getId()=" + getId() + ", getName()="
				+ getName() + ", getHireDate()=" + getHireDate() + "]";
	}

	private float rate;
}
