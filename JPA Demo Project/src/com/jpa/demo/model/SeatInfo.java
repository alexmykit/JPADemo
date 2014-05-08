package com.jpa.demo.model;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class SeatInfo
{	
	public SeatInfo() { }
	
	public SeatInfo(SeatPosition position, boolean isOccupied, Date occuppiedFrom, Date occuppiedTo)
	{
		this.position = position;
		this.isOccupied = isOccupied;
		this.occuppiedFrom = occuppiedFrom;
		this.occuppiedTo = occuppiedTo;
	}
			
	public SeatPosition getPosition()
	{
		return position;
	}
	
	public void setPosition(SeatPosition position)
	{
		this.position = position;
	}
	
	public boolean isOccupied()
	{
		return isOccupied;
	}
	
	public void setOccupied(boolean isOccupied)
	{
		this.isOccupied = isOccupied;
	}
	
	public Date getOccuppiedFrom()
	{
		return occuppiedFrom;
	}
	
	public void setOccuppiedFrom(Date occuppiedFrom)
	{
		this.occuppiedFrom = occuppiedFrom;
	}
	
	public Date getOccuppiedTo()
	{
		return occuppiedTo;
	}
	
	public void setOccuppiedTo(Date occuppiedTo)
	{
		this.occuppiedTo = occuppiedTo;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (isOccupied ? 1231 : 1237);
		result = prime * result
				+ ((occuppiedFrom == null) ? 0 : occuppiedFrom.hashCode());
		result = prime * result
				+ ((occuppiedTo == null) ? 0 : occuppiedTo.hashCode());
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
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
		SeatInfo other = (SeatInfo) obj;
		if (isOccupied != other.isOccupied)
			return false;
		if (occuppiedFrom == null)
		{
			if (other.occuppiedFrom != null)
				return false;
		} else if (!occuppiedFrom.equals(other.occuppiedFrom))
			return false;
		if (occuppiedTo == null)
		{
			if (other.occuppiedTo != null)
				return false;
		} else if (!occuppiedTo.equals(other.occuppiedTo))
			return false;
		if (position == null)
		{
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "SeatInfo [position=" + position + ", isOccupied=" + isOccupied
				+ ", occuppiedFrom=" + occuppiedFrom + ", occuppiedTo="
				+ occuppiedTo + "]";
	}

	@EmbeddedId
	private SeatPosition position;
	private boolean isOccupied;
	@Temporal(TemporalType.TIMESTAMP)
	private Date occuppiedFrom;
	@Temporal(TemporalType.TIMESTAMP)
	private Date occuppiedTo;
}