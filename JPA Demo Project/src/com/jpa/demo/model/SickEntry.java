package com.jpa.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class SickEntry
{
	public SickEntry() { }
	
	public SickEntry(Date start, int days)
	{
		this.start = start;
		this.days = days;
	}
			
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public Date getStart()
	{
		return start;
	}

	public void setStart(Date start)
	{
		this.start = start;
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
		result = prime * result + id;
		result = prime * result + ((start == null) ? 0 : start.hashCode());
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
		SickEntry other = (SickEntry) obj;
		if (days != other.days)
			return false;
		if (id != other.id)
			return false;
		if (start == null)
		{
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "SickEntry [id=" + id + ", start=" + start + ", days=" + days
				+ "]";
	}

	@Id
	@GeneratedValue
	private int id;
	@Temporal(TemporalType.DATE)
	@Column(insertable=false, updatable=false)
	private Date start;
	@Column(insertable=false, updatable=false)
	private int days;
}
