package com.jpa.demo.main;

import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.jpa.demo.core.Context;
import com.jpa.demo.model.Address;
import com.jpa.demo.model.Company;
import com.jpa.demo.model.Department;
import com.jpa.demo.model.Employee;
import com.jpa.demo.model.EmployeeType;
import com.jpa.demo.model.ParkingSpace;
import com.jpa.demo.model.Phone;
import com.jpa.demo.model.PhoneType;
import com.jpa.demo.model.Project;
import com.jpa.demo.model.SickEntry;
import com.jpa.demo.model.VacationEntry;
import com.jpa.demo.services.CompanyService;
import com.jpa.demo.services.DepartmentService;
import com.jpa.demo.services.EmployeeService;
import com.jpa.demo.services.EmployeeVacationDuration;
import com.jpa.demo.services.ParkingSpaceService;
import com.jpa.demo.services.PhoneService;
import com.jpa.demo.services.ProjectService;
import com.jpa.demo.services.SickEntryService;
import com.jpa.demo.test.services.DemoDataGenerationService;

public class Run 
{
	public static void main(String[] args) 
	{
		EntityManagerFactory emf = null;
		
		try
		{
			emf = Persistence.createEntityManagerFactory(UNIT_NAME);
			EntityManager em = emf.createEntityManager();
			Context rootContext = createRootContext(em);
		
			showEmployeesWithPhoneTypes(rootContext, EnumSet.of(PhoneType.MOBILE), false);
//			showEmployeesWithPhoneTypes(rootContext, EnumSet.of(PhoneType.HOME), false);
//			showEmployeesWithPhoneTypes(rootContext, EnumSet.of(PhoneType.HOME, PhoneType.MOBILE), false);
//			showEmployeesWithPhoneTypes(rootContext, EnumSet.noneOf(PhoneType.class), false);
			System.out.println("---------------------------------------------------------------------------------");
//			showEmployeesWithPhoneTypes(rootContext, EnumSet.of(PhoneType.MOBILE), true);
//			showEmployeesWithPhoneTypes(rootContext, EnumSet.of(PhoneType.HOME), true);
//			showEmployeesWithPhoneTypes(rootContext, EnumSet.of(PhoneType.HOME, PhoneType.MOBILE), true);
//			showEmployeesWithPhoneTypes(rootContext, EnumSet.noneOf(PhoneType.class), true);
		}
		finally
		{
			if (emf != null)
			{
				emf.close();
			}
		}
	}
	
	public static void showEmployeesWithProject(Context rootContext, String projectName, boolean useCriteria)
	{
		EmployeeService empService = rootContext.lookupService("EmployeeService", EmployeeService.class);
		
		List<Employee> employees = useCriteria ? empService.getEmployeesWithProjectCriteria(projectName) : empService.getEmployeesWithProject(projectName);
		System.out.println("Number of employees with " + projectName + " project is " + employees.size());
		
		for (Employee emp : employees) 
		{
			System.out.println(emp.getName() + "\t" + emp.getProjects());
		}
	}
	
	public static void showEmployeesWithPhoneTypes(Context rootContext, EnumSet<PhoneType> phoneTypes, boolean useCriteria)
	{
		EmployeeService empService = rootContext.lookupService("EmployeeService", EmployeeService.class);
		
		List<Employee> employeesWithPhoneTypes = useCriteria ? empService.getEmployyeeWithPhoneKindsCriteria(phoneTypes) : empService.getEmployeesWithPhoneKinds(phoneTypes);
		
		System.out.println("Number of employees with phones of types " + phoneTypes + " is " + employeesWithPhoneTypes.size());
		
		for (Employee employee : employeesWithPhoneTypes)
		{
			Set<PhoneType> employeePhoneTypes = new HashSet<>(employee.getPhones().keySet());
			
			System.out.println(employee.getName() + "\t" + employeePhoneTypes );
		}
	}
	
	public static void showTotalEmployeeVacations(Context rootContext, boolean useCriteria)
	{
		EmployeeService empService = rootContext.lookupService("EmployeeService", EmployeeService.class);
		
		List<EmployeeVacationDuration> empVacations = useCriteria ? empService.getEmployeeTotalVacationDurationsCriteria() : empService.getEmployeeTotalVacationDurations();
		
		for(EmployeeVacationDuration vacationDuration : empVacations)
		{
			System.out.println(vacationDuration.getEmployee().getName() + " has " + vacationDuration.getTotalVacation() + " days of vacations");
		}
	}
	
	public static void showEmployeesWithoutVacations(Context rootContext, boolean useCriteria)
	{
		EmployeeService empService = rootContext.lookupService("EmployeeService", EmployeeService.class);
		
		List<Employee> employessWithoutVacations = useCriteria ? empService.getEmployeesWithoutVacationsCriteria() : empService.getEmployeesWithoutVacations();
		
		System.out.println("Total number of employees without vacations is " + employessWithoutVacations.size());
		for (Employee emp : employessWithoutVacations)
		{
			System.out.println(emp.getDepartment().getDepartmentName() + "\t" + emp.getName());
		}
	}
	
	public static void showEmployeesWithSallaryHgherThenAvgInEachDepartment(Context rootContext, boolean useCriteria)
	{
		EmployeeService empService = rootContext.lookupService("EmployeeService", EmployeeService.class);
		
		List<Employee> empList = useCriteria ? empService.getEmployeesWithSallaryHigherThenAvgSallaryInDepartmentCriteria() : empService.getEmployeesWithSallaryHigherThenAvgSallaryInDepartment();
		
		for (Employee emp : empList)
		{
			System.out.println(emp.getDepartment().getDepartmentName() + "\t" + emp.getName() + "\t" + emp.getSallary());
		}
	}
	
	public static void showAvgSallaryPerDepartment(Context rootContext, boolean useCriteria)
	{
		EmployeeService empService = rootContext.lookupService("EmployeeService", EmployeeService.class);
		
		Map<String, Double> avgSallary = useCriteria ? empService.getAvgSallaryInEachDepartmentCriteria() : empService.getAvgSallaryInEachDepartment();
		
		for (Map.Entry<String, Double> entry : avgSallary.entrySet())
		{
			System.out.println(entry.getKey() + " = " + entry.getValue());
		}
	}
	
	public static void listAllEmployees(Context rootContext, boolean useCriteria)
	{
		EmployeeService empService = rootContext.lookupService("EmployeeService", EmployeeService.class);
		
		List<Employee> allEmployees = useCriteria ? empService.getAllEmployeesCriteria() : empService.getAllEmployees();
		
		System.out.println("Total employees found: " + allEmployees.size());
		for (Employee e : allEmployees)
		{
			System.out.println(e);
		}
	}
	
	public static void runDemo(EntityManager em, Context rootContext)
	{
		SickEntryService sickEntryService = rootContext.lookupService("SickEntryService", SickEntryService.class);
		SickEntry sickEntry = sickEntryService.createSickEntry(new Date(), 3);
		System.out.println("Created entry is " + sickEntry);
		em.clear();
		sickEntry = sickEntryService.findSickEntry(sickEntry.getId());
		System.out.println("Sick entry after lookup in database: " + sickEntry);
		
		EmployeeService empService = rootContext.lookupService("EmployeeService", EmployeeService.class);
		DepartmentService depService = rootContext.lookupService("DepartmentService", DepartmentService.class);
		ParkingSpaceService psService = rootContext.lookupService("ParkingSpaceService", ParkingSpaceService.class);
		ProjectService projectService = rootContext.lookupService("ProjectService", ProjectService.class); 
		PhoneService phoneService = rootContext.lookupService("PhoneService", PhoneService.class);
		CompanyService companyService = rootContext.lookupService("CompanyService", CompanyService.class);
		
		Department javaDep = depService.createDepartment("J2");
		Department netDep = depService.createDepartment(".NET");
		
		ParkingSpace firstSpace = psService.createParkingSpace(1, "Space 1");
		ParkingSpace secondSpace = psService.createParkingSpace(2, "Space 2");
		ParkingSpace thirdSpace = psService.createParkingSpace(3, "Space 3");
		ParkingSpace fourthSpace = psService.createParkingSpace(3, "Space 4");
		
		Address firstAddress = new Address("Elm Street", "NY", "USA");
		Address secondAddress = new Address("State Street", "Boston", "USA");
		Address thirdAddress = new Address("Wall Street", "NY", "USA");
		Address fourthAddress = new Address("Palm Street", "Quincy", "USA");
		
		Company firstCompany = companyService.createNewCompany("SRI Infotech", secondAddress);
		Company secondCompany = companyService.createNewCompany("Valeo Inc.", fourthAddress);
		
		Employee firstEmployee = empService.createEmployee("Tommy Tester", 1500, new Date(), "Bad", EmployeeType.FULL_TIME, firstCompany, firstAddress, javaDep.getId(), firstSpace.getId());
		Employee secondEmployee = empService.createEmployee("Sam Coder", 2500, new Date(1989,10,30), "Good", EmployeeType.PART_TIME, secondCompany, secondAddress ,netDep.getId(), secondSpace.getId());
		Employee thirdEmployee = empService.createEmployee("Jimmy Hacker", 3500, new Date(), "Bad", EmployeeType.FULL_TIME, secondCompany ,thirdAddress, netDep.getId(), thirdSpace.getId());
		Employee fourthEmployee = empService.createEmployee("Sally Cleaner", 3500, new Date(), "Bad", EmployeeType.FULL_TIME, secondCompany, fourthAddress, netDep.getId(), fourthSpace.getId());
		
		empService.addNickNameToEmployee(firstEmployee, "Hitman");
		empService.addNickNameToEmployee(firstEmployee, "Batman");
		empService.addNickNameToEmployee(thirdEmployee, "Catman");
		
		empService.addVacationToEmployee(thirdEmployee, new VacationEntry(new Date(), 10));
		empService.addVacationToEmployee(thirdEmployee, new VacationEntry(new Date(System.currentTimeMillis() + 20 * 24 * 60 * 60 * 1000), 10));
		
		Project sriProject = projectService.createProject("SRI");
		Project valeoProject = projectService.createProject("Valeo");
		
		Phone firstEmpHomePhone = phoneService.createPhone("948454", PhoneType.HOME);
		Phone firstEmpMobilePhone = phoneService.createPhone("3435543534534", PhoneType.MOBILE);
		Phone secondEmpMobilePhone = phoneService.createPhone("4353452345", PhoneType.MOBILE);
		Phone thirdEmployeeMobilePhone = phoneService.createPhone("345234523456547", PhoneType.MOBILE);
		Phone fourthEmployeeMobilePhone = phoneService.createPhone("43534523546", PhoneType.MOBILE);
		
		projectService.addEmployeeToProject(firstEmployee.getId(), sriProject.getId());
		projectService.addEmployeeToProject(secondEmployee.getId(), valeoProject.getId());
		projectService.addEmployeeToProject(thirdEmployee.getId(), valeoProject.getId());
		projectService.addEmployeeToProject(fourthEmployee.getId(), valeoProject.getId());
		
		phoneService.addPhoneToEmployee(firstEmployee.getId(), firstEmpMobilePhone);
		phoneService.addPhoneToEmployee(firstEmployee.getId(), firstEmpHomePhone);
		phoneService.addPhoneToEmployee(secondEmployee.getId(), secondEmpMobilePhone);
		phoneService.addPhoneToEmployee(thirdEmployee.getId(), thirdEmployeeMobilePhone);
		phoneService.addPhoneToEmployee(fourthEmployee.getId(), fourthEmployeeMobilePhone);
		
		System.out.println("SRI project " + sriProject);
		System.out.println("Valeo project " + valeoProject);
		
		System.out.println("First employee is " + firstEmployee);
		System.out.println("Second employee is " + secondEmployee);
		System.out.println("Third employee is " + thirdEmployee);
		System.out.println("Fourth employee is " + fourthEmployee);
		
		em.refresh(sriProject);
		em.refresh(valeoProject);
		em.refresh(firstEmployee);
		em.refresh(secondEmployee);
		em.refresh(thirdEmployee);
		em.refresh(fourthEmployee);
		
		System.out.println("SRI project after refresh " + sriProject);
		System.out.println("Valeo project project after refresh " + valeoProject);
		
		System.out.println("First employee is project after refresh " + firstEmployee);
		System.out.println("Second employee is project after refresh " + secondEmployee);
		System.out.println("Third employee is project after refresh " + thirdEmployee);
		System.out.println("Fourth employee is project after refresh " + fourthEmployee);
	}
	
	public static void generateDemoData(Context rootContext)
	{
		DemoDataGenerationService service = rootContext.lookupService("DemoDataGenerationService", DemoDataGenerationService.class);
		
		System.out.println("Start generating demo data");
		long start = System.currentTimeMillis();
		service.generateDemoData(10, 100, 1000, 5, 5, 10, 30, 2, 5);
		long end = System.currentTimeMillis();
		System.out.println("Demo data generated in " + (end - start) + " ms");
	}
	
	public static Context createRootContext(EntityManager manager)
	{
		Context root = new Context();
		
		root.addService(new EmployeeService("EmployeeService", manager));
		root.addService(new DepartmentService("DepartmentService", manager));
		root.addService(new ParkingSpaceService("ParkingSpaceService", manager));
		root.addService(new ProjectService("ProjectService", manager));
		root.addService(new PhoneService("PhoneService", manager));
		root.addService(new CompanyService("CompanyService", manager));
		root.addService(new SickEntryService("SickEntryService", manager));
		root.addService(new DemoDataGenerationService("DemoDataGenerationService", manager));
		
		return root;
	}
	
	private static final String UNIT_NAME = "JPATest";
}