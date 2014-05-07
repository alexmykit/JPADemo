package com.jpa.demo.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.jpa.demo.model.Address;
import com.jpa.demo.model.Company;

public class CompanyService extends AbstractJPAService
{

	public CompanyService(String serviceName, EntityManager manager)
	{
		super(serviceName, manager);
	}
	
	public Company createNewCompany(String name, Address address)
	{
		try
		{
			Company company = new Company(name, address);
			begin();
			manager.persist(company);
			commit();
			return company;
		}
		catch(PersistenceException e)
		{	
			e.printStackTrace();
			manager.getTransaction().rollback();
			throw e;
		}
	}
	
	public Company findCompany(int id)
	{
		return manager.find(Company.class, id);
	}
}
