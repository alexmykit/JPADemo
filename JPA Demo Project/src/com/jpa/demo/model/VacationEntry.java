package com.jpa.demo.model;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class VacationEntry
{
	public VacationEntry() { }
			
	public VacationEntry(Date startDate, int days)
	{
		this.startDate = startDate;
		this.days = days;
	}
	
	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public int getDays()
	{
		return days;
	}

	public void setDays(int days)
	{
		this.days = days;
	}


	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + days;
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
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
		VacationEntry other = (VacationEntry) obj;
		if (days != other.days)
			return false;
		if (startDate == null)
		{
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}
		
	@Override
	public String toString()
	{
		return "VacationEntry [startDate=" + startDate + ", days=" + days + "]";
	}

	@Temporal(TemporalType.DATE)
	private Date startDate;
	private int days;
}