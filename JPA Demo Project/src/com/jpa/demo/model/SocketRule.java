package com.jpa.demo.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(SocketId.class)
public class SocketRule
{		
	public SocketRule() { }
	
	public SocketRule(int port, String host, Action action, String forwardTo,
			boolean isActive)
	{
		this.port = port;
		this.host = host;
		this.action = action;
		this.forwardTo = forwardTo;
		this.isActive = isActive;
	}
			
	public int getPort()
	{
		return port;
	}
	
	public void setPort(int port)
	{
		this.port = port;
	}
	
	public String getHost()
	{
		return host;
	}
	
	public void setHost(String host)
	{
		this.host = host;
	}
	
	public Action getAction()
	{
		return action;
	}
	
	public void setAction(Action action)
	{
		this.action = action;
	}
	
	public String getForwardTo()
	{
		return forwardTo;
	}
	
	public void setForwardTo(String forwardTo)
	{
		this.forwardTo = forwardTo;
	}
	
	public boolean isActive()
	{
		return isActive;
	}
	
	public void setActive(boolean isActive)
	{
		this.isActive = isActive;
	}
			
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result
				+ ((forwardTo == null) ? 0 : forwardTo.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + (isActive ? 1231 : 1237);
		result = prime * result + port;
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
		SocketRule other = (SocketRule) obj;
		if (action != other.action)
			return false;
		if (forwardTo == null)
		{
			if (other.forwardTo != null)
				return false;
		} else if (!forwardTo.equals(other.forwardTo))
			return false;
		if (host == null)
		{
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (isActive != other.isActive)
			return false;
		if (port != other.port)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "SocketRule [port=" + port + ", host=" + host + ", action="
				+ action + ", forwardTo=" + forwardTo + ", isActive="
				+ isActive + "]";
	}

	@Id
	private int port;
	@Id
	private String host;
	@Enumerated(EnumType.STRING)
	private Action action;
	private String forwardTo;
	private boolean isActive;
}
