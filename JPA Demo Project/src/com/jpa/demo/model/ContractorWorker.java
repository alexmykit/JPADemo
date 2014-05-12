package com.jpa.demo.model;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class ContractorWorker extends Worker
{
	public ContractorWorker()
	{
		
	}
	
	public ContractorWorker(String name, Date hireDate, float rate,
			int term)
	{
		super(name, hireDate);
		this.rate = rate;
		this.term = term;
	}

	public float getRate()
	{
		return rate;
	}

	public void setRate(float rate)
	{
		this.rate = rate;
	}

	public int getTerm()
	{
		return term;
	}

	public void setTerm(int term)
	{
		this.term = term;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Float.floatToIntBits(rate);
		result = prime * result + term;
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
		ContractorWorker other = (ContractorWorker) obj;
		if (Float.floatToIntBits(rate) != Float.floatToIntBits(other.rate))
			return false;
		if (term != other.term)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "ContractorWorker [rate=" + rate + ", term=" + term
				+ ", getId()=" + getId() + ", getName()=" + getName()
				+ ", getHireDate()=" + getHireDate() + "]";
	}

	private float rate;
	private int term;
}
