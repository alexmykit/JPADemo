package com.jpa.demo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Access(AccessType.FIELD)
public class Employee implements Serializable
{
	public Employee() {}
	
	public Employee(String name, float sallary, Date hireDate, String description, EmployeeType type, Department department, ParkingSpace parkingSpace)
	{
		setName(name);
		this.sallary = sallary;
		this.hireDate = hireDate;
		this.description = description;
		this.type = type;
		this.department = department;
		this.parkingSpace = parkingSpace;
		parkingSpace.setEmployee(this);
		department.addEmployee(this);
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	@Access(AccessType.PROPERTY)
	public String getName()
	{
//		System.out.println("Employee.getName() called");
		return firstName + " " + lastName;
	}
	
	public void setName(String name)
	{
		String[] names = name.split("\\s+");
		
		if (names.length != 2)
			throw new IllegalArgumentException("Invalid name passed to setName(): " + name);
		
		firstName = names[0];
		lastName = names[1];
	}
	public float getSallary()
	{
		return sallary;
	}
	public void setSallary(float sallary)
	{
		this.sallary = sallary;
	}
	public Date getHireDate()
	{
		return hireDate;
	}
	public void setHireDate(Date hireDate)
	{
		this.hireDate = hireDate;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public EmployeeType getType()
	{
		return type;
	}

	public void setType(EmployeeType type)
	{
		this.type = type;
	}
	
	public Address getAddress()
	{
		return address;
	}

	public void setAddress(Address address)
	{
		this.address = address;
	}

	public Company getCompany()
	{
		return company;
	}

	public Department getDepartment()
	{
		return department;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}

	public ParkingSpace getParkingSpace()
	{
		return parkingSpace;
	}

	public void setParkingSpace(ParkingSpace parkingSpace)
	{
		this.parkingSpace = parkingSpace;
	}

	public Set<Project> getProjects()
	{
		return projects;
	}

	public void setProjects(Set<Project> projects)
	{
		this.projects = projects;
	}

	public Map<PhoneType, Phone> getPhones()
	{
		return phones;
	}

	public void setPhones(Map<PhoneType, Phone> phones)
	{
		this.phones = phones;
	}

	public List<VacationEntry> getVacations()
	{
		return vacations;
	}

	public void setVacations(List<VacationEntry> vacations)
	{
		this.vacations = vacations;
	}

	public void setCompany(Company company)
	{
		this.company = company;
	}

	public void addProject(Project project)
	{
		if (!projects.contains(project))
		{
			projects.add(project);
			project.addProjectEmployee(this);
		}
	}
	
	public void removeProject(Project project)
	{
		if (projects.contains(project))
		{
			projects.remove(project);
			project.removeProjectEmployee(this);
		}
	}
	
	public void addPhone(Phone phone)
	{
		phones.put(phone.getPhoneType(), phone);
	}
	
	public void addVacationEntry(VacationEntry entry)
	{
		vacations.add(entry);
	}
	
	public void addNickName(String nickName)
	{
		nickNames.add(nickName);
	}
	
	public List<VacationEntry> getVacationEntries()
	{
		return new ArrayList<>(vacations);
	}
	
	public Set<String> getNickNames()
	{
		return new HashSet<>(nickNames);
	}
	
	@Override
	public int hashCode()
	{
//		System.out.println("Employee.hashCode() called");
		
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((hireDate == null) ? 0 : hireDate.hashCode());
		result = prime * result + id;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + Float.floatToIntBits(sallary);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
//		System.out.println("Employee.equals() called");
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (hireDate == null)
		{
			if (other.hireDate != null)
				return false;
		} else if (!hireDate.equals(other.hireDate))
			return false;
		if (id != other.id)
			return false;
		if (firstName == null)
		{
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null)
		{
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (Float.floatToIntBits(sallary) != Float
				.floatToIntBits(other.sallary))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "Employee [id=" + id + ", name=" + getName() + ", sallary=" + sallary
				+ ", hireDate=" + hireDate + ", description=" + description + " , type=" + type + " , department=" +
				department + " , parkingSpace=" + parkingSpace + " , projects=" + projects + " , phones=" + phones +
				" , address=" + address + " , company=" + company + 
				" , vacations=" + vacations + ", niockNames=" + nickNames +"]";
	}

	@Id
	@GeneratedValue
	private int id;
	@Transient
	private String firstName;
	@Transient
	private String lastName;
	private float sallary;
	@Temporal(TemporalType.DATE)
	private Date hireDate;
	@Lob
	private String description;
	@Enumerated(EnumType.STRING)
	private EmployeeType type;
	
	@ManyToOne
	@JoinColumn(name="DEPT_ID")
	private Department department;
	
	@OneToOne
	private ParkingSpace parkingSpace;
	@ManyToMany
	private Set<Project> projects = new HashSet<>();
	
	@OneToMany
	@MapKeyEnumerated(EnumType.STRING)
	@MapKey(name="phoneType")
	private Map<PhoneType, Phone> phones = new HashMap<>();
	
	@Embedded
	Address address;
	
	@ManyToOne
	private Company company;
	
	@ElementCollection
	private List<VacationEntry> vacations = new ArrayList<>();
	
	@ElementCollection
	private Set<String> nickNames = new HashSet<>();
	
	private static final long serialVersionUID = -3570219469645496222L;
}