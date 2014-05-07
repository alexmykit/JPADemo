package com.jpa.demo.test.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;

import com.jpa.demo.model.Address;
import com.jpa.demo.model.Company;
import com.jpa.demo.model.Department;
import com.jpa.demo.model.Employee;
import com.jpa.demo.model.EmployeeType;
import com.jpa.demo.model.ParkingSpace;
import com.jpa.demo.model.Phone;
import com.jpa.demo.model.PhoneType;
import com.jpa.demo.model.Project;
import com.jpa.demo.model.VacationEntry;
import com.jpa.demo.services.AbstractJPAService;
import com.jpa.demo.services.CompanyService;
import com.jpa.demo.services.DepartmentService;
import com.jpa.demo.services.EmployeeService;
import com.jpa.demo.services.ParkingSpaceService;
import com.jpa.demo.services.PhoneService;
import com.jpa.demo.services.ProjectService;
import com.jpa.utilities.CollectionUtils;

public class DemoDataGenerationService extends AbstractJPAService
{
	public DemoDataGenerationService(String serviceName, EntityManager manager)
	{
		super(serviceName, manager);

		addService(new EmployeeService(EMPLOYEE_SERVICE, manager).setManageTransactions(false));
		addService(new DepartmentService(DEPARTMENT_SERVICE, manager).setManageTransactions(false));
		addService(new ParkingSpaceService(PARKING_SPACE_SERVICE, manager).setManageTransactions(false));
		addService(new ProjectService(PROJECT_SERVICE, manager).setManageTransactions(false));
		addService(new PhoneService(PHONE_SERVICE, manager).setManageTransactions(false));
		addService(new CompanyService(COMPANY_SERVICE, manager).setManageTransactions(false));
	}

	public void generateDemoData(int nCompanies, int nProjects, int nAddresses, int nDepartments, int maxProjectsPerEmployee, int maxEmployeesPerDepartment, int maxEmployeeVacations, int maxEmployeeNickNames, int maxEmployeePhones)
	{
		try
		{
			begin();
			PhoneService phoneService = lookupService(PHONE_SERVICE, PhoneService.class);
			ProjectService projectService = lookupService(PROJECT_SERVICE, ProjectService.class);
			EmployeeService empService = lookupService(EMPLOYEE_SERVICE, EmployeeService.class);

			List<Address> addresses = generateAddresses(nAddresses);
			List<Company> companies = generateCompanies(nCompanies, addresses);
			List<Department> departments = generateDepartments(nDepartments);
			List<Project> projects = generateProjects(nProjects);

			while (nDepartments-- > 0)
			{
				System.out.println("nDepartments -> " + nDepartments);
				int minEmployees = maxEmployeesPerDepartment >= 2 ? maxEmployeesPerDepartment / 2 : 1;

				int nEmployees = minEmployees + RAND.nextInt(maxEmployeesPerDepartment - minEmployees);
				List<ParkingSpace> parkingSpaces = generateParkingSpaces(nEmployees);
				int depId = departments.remove(0).getId();

				while (nEmployees-- > 0)
				{
					System.out.println("nEmployees -> " + nEmployees);
					String name = EMP_NAME_GEN.nextRandomName();
					float sallary = RAND.nextFloat() * 10000;
					Date hireDate = new GregorianCalendar(1990 + RAND.nextInt(new Date().getYear() + 1900 - 1990), RAND.nextInt(12), RAND.nextInt(28)).getTime();
					String desc = DESC_GENERATOR.nextRandomName();
					EmployeeType type = RAND.nextBoolean() ? EmployeeType.FULL_TIME : EmployeeType.PART_TIME;
					Company company = companies.get(RAND.nextInt(companies.size()));
					Address address = addresses.get(RAND.nextInt(addresses.size()));
					int parkingSpaceId = parkingSpaces.remove(0).getId();

					Employee employee = empService.createEmployee(name, sallary, hireDate, desc, type, company, address, depId, parkingSpaceId);

					int employeeProjects = RAND.nextInt(maxProjectsPerEmployee);
					List<Project> availableProjects = new ArrayList<>(projects);
					while (employeeProjects-- > 0)
					{
						int projectId = availableProjects.remove(RAND.nextInt(availableProjects.size())).getId();
						projectService.addEmployeeToProject(employee.getId(), projectId);
					}

					int empPhones = RAND.nextInt(maxEmployeePhones);
					List<Phone> phones = generatePhones(empPhones);

					for (Phone phone : phones)
					{
						phoneService.addPhoneToEmployee(employee.getId(), phone);
					}

					int employeeNickNames = RAND.nextInt(maxEmployeeNickNames);
					while (employeeNickNames-- > 0)
					{
						empService.addNickNameToEmployee(employee, NICK_NAME_GEN.nextRandomName());
					}

					int employeeVacations = RAND.nextInt(maxEmployeeVacations);
					while (employeeVacations-- > 0)
					{
						GregorianCalendar vacationStart = new GregorianCalendar();
						vacationStart.setTime(hireDate);
						vacationStart.add(GregorianCalendar.DAY_OF_YEAR, RAND.nextInt(100));

						VacationEntry entry = new VacationEntry(vacationStart.getTime(), RAND.nextInt(10) + 1);
						empService.addVacationToEmployee(employee, entry);
					}
				}		
			}
			commit();
		}
		catch(Throwable e)
		{
			e.printStackTrace();
			rollback();
			throw e;
		}
	}

	private List<ParkingSpace> generateParkingSpaces(int nParkingSpaces)
	{
		List<ParkingSpace> parkingSpaces = new ArrayList<>();
		ParkingSpaceService psService = lookupService(PARKING_SPACE_SERVICE, ParkingSpaceService.class);
		int slot = 0;

		while (nParkingSpaces-- > 0)
		{
			parkingSpaces.add(psService.createParkingSpace(slot++, "ParkingSpace#" + slot));
		}

		return parkingSpaces;		
	}

	private List<Phone> generatePhones(int nPhones)
	{
		List<Phone> phones = new ArrayList<>();
		PhoneService phoneService = lookupService(PHONE_SERVICE, PhoneService.class);

		while (nPhones-- > 0)
		{
			PhoneType phoneType = RAND.nextBoolean() ? PhoneType.HOME : PhoneType.MOBILE;

			phones.add(phoneService.createPhone(PHONES_GEN.nextRandomName(), phoneType));
		}

		return phones;
	}

	private List<Address> generateAddresses(int nAddresses)
	{
		List<Address> addresses = new ArrayList<>();


		while (nAddresses-- > 0)
		{
			addresses.add(new Address(STREET_GENERATOR.nextRandomName(), CITY_GENERATOR.nextRandomName(), STATE_GENERATOR.nextRandomName()));
		}

		return addresses;
	}

	private List<Company> generateCompanies(int nCompanies, List<Address> addresses)
	{
		List<Company> companies = new ArrayList<>();

		CompanyService compService = lookupService(COMPANY_SERVICE, CompanyService.class);

		while(nCompanies-- > 0)
		{
			Address randomAddress = addresses.get(RAND.nextInt(addresses.size()));
			companies.add(compService.createNewCompany(COMPANY_NAME_GEN.nextRandomName(), randomAddress));
		}

		return companies;
	}

	private List<Department> generateDepartments(int nDepartments)
	{
		List<Department> departments = new ArrayList<>();
		DepartmentService deptService = lookupService(DEPARTMENT_SERVICE, DepartmentService.class);

		while (nDepartments-- > 0)
		{
			departments.add(deptService.createDepartment(DEPARTMENT_NAME_GEN.nextRandomName()));
		}

		return departments;
	}

	private List<Project> generateProjects(int nProjects)
	{
		List<Project> projects = new ArrayList<>();
		ProjectService projectService = lookupService(PROJECT_SERVICE, ProjectService.class);

		while (nProjects-- > 0)
		{
			projects.add(projectService.createProject(PROJECT_NAME_GENERATOR.nextRandomName()));
		}

		return projects;
	}

	private static final String[] COMPANIES_DATA = {"SRI", "Info", "Tech", "Micro", "Nano", "Mega", "Net", "Systems", "Corp", "Flex"};
	private static final String[] DEPARTMENTS_DATA = {"J2", "NET", "Lab", "Web", "Script", "CSS"};
	private static final String[] PHONES_DATA = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
	private static final String[] NAMES_DATA = {"Sam", "Brown", "Tommy", "Tony", "Alice", "Peter", "Simon", "Jack"};
	private static final String[] NICKNAMES_DATA = {"Hacker", "Tester", "Cracker", "Biker", "Seller", "Driller", "Killer", "Filler", "Schmacker"};
	private static final String[] ADDRESS_STREET_DATA = {"State", "Street", "Elm", "Wall", "Baker", "Mart", "Piller", "Cater", "Skater", "12", "9/15", "45"};
	private static final String[] ADDRESS_CITY_DATA = {"NY", "Boston", "London", "Washington", "Quincy", "Munich", "Vegas", "Paris"};
	private static final String[] ADDRESS_STATE_DATA = {"USA", "Germany", "GB", "France", "Italy", "Spain", "Ukraine"};
	private static final String[] PROJECT_NAME_DATA = {"Book", "Face", "Note", "Keep", "Pad", "Trace", "Learn", "Code", "Academy"};
	private static final String[] DESC_DATA = CollectionUtils.range('a', 'z');

	private static final NameGenerator COMPANY_NAME_GEN = new NameGenerator(COMPANIES_DATA, " ", true, 2);
	private static final NameGenerator DEPARTMENT_NAME_GEN = new NameGenerator(DEPARTMENTS_DATA, " ", true, 2);
	private static final NameGenerator PHONES_GEN = new NameGenerator(PHONES_DATA, "", true, 10);
	private static final NameGenerator EMP_NAME_GEN = new NameGenerator(NAMES_DATA, " ", true, 2);
	private static final NameGenerator NICK_NAME_GEN = new NameGenerator(NICKNAMES_DATA, " ", true, 2);
	private static final NameGenerator STREET_GENERATOR = new NameGenerator(ADDRESS_STREET_DATA, " ", true, 2);
	private static final NameGenerator CITY_GENERATOR = new NameGenerator(ADDRESS_CITY_DATA, "", true, 1);
	private static final NameGenerator STATE_GENERATOR = new NameGenerator(ADDRESS_STATE_DATA, "", true, 1);
	private static final NameGenerator PROJECT_NAME_GENERATOR = new NameGenerator(PROJECT_NAME_DATA, " ", true, 2);
	private static final NameGenerator DESC_GENERATOR = new NameGenerator(DESC_DATA, "", true, DESC_DATA.length);

	private static final String COMPANY_SERVICE = "CompanyService";
	private static final String PHONE_SERVICE = "PhoneService";
	private static final String PROJECT_SERVICE = "ProjectService";
	private static final String PARKING_SPACE_SERVICE = "ParkingSpaceService";
	private static final String DEPARTMENT_SERVICE = "DepartmentService";
	private static final String EMPLOYEE_SERVICE = "EmployeeService";

	private static final Random RAND = new Random(System.currentTimeMillis()); 
}