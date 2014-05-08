package com.jpa.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SeatPosition implements Serializable
{	
	public SeatPosition() { }
	
	public SeatPosition(int row, int column)
	{
		this.row = row;
		this.column = column;
	}
		
	public int getRow()
	{
		return row;
	}
	
	public int getColumn()
	{
		return column;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
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
		SeatPosition other = (SeatPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "SeatPosition [row=" + row + ", column=" + column + "]";
	}

	private int row;
	@Column(name="col")
	private int column;
	private static final long serialVersionUID = 4957625618442974253L;
}
