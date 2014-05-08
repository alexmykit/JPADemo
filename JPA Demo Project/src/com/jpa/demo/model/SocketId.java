package com.jpa.demo.model;

import java.io.Serializable;

public class SocketId implements Serializable
{
	public SocketId() { }
	
	public SocketId(int port, String host)
	{
		this.port = port;
		this.host = host;
	}
	
	public int getPort()
	{
		return port;
	}
	
	public String getHost()
	{
		return host;
	}
		
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
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
		SocketId other = (SocketId) obj;
		if (host == null)
		{
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (port != other.port)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "SocketId [port=" + port + ", host=" + host + "]";
	}

	private int port;
	private String host;
	private static final long serialVersionUID = 8244708128266087780L;
}
