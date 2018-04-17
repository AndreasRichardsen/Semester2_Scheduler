package db;

import java.util.ArrayList;

import model.Employee;
import model.EmployeeType;

public interface IFDBEmployee {
	
	public ArrayList<Employee> getAllEmployees(boolean retrieveAssociation);
	public ArrayList<Employee> getAllEmployeesByEmployeeType(EmployeeType empType, boolean retrieveAssociation);
	public Employee getEmployeeById(int id, boolean retrieveAssociation);
	public ArrayList<Employee> getEmployeesByLastName(String name, boolean retrieveAssociation);
	public int insertEmployee(Employee employee) throws Exception;
	public int updateEmployee(Employee employee) throws Exception;
	public int deleteEmployee(Employee employee) throws Exception;
}
