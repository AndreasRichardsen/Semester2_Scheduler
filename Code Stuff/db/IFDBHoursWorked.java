package db;

import java.time.LocalDate;
import java.util.ArrayList;

import model.Employee;
import model.HoursWorked;

public interface IFDBHoursWorked {

	public ArrayList<HoursWorked> getHoursWorkedForCurrentMonthById (int employeeId);
	public ArrayList<HoursWorked> getHoursWorkedForMonthById (int month, int year, int employeeId);
	public HoursWorked findHoursWorkedByDateAndEmployeeId(LocalDate date, int employeeId, boolean retrieveAssociation);
	public int insertHoursWorked(Employee employee, HoursWorked HoursWorked) throws Exception;
	public int updateHoursWorked(Employee employee, HoursWorked HoursWorked);
	public int deleteHoursWorked(Employee employee, HoursWorked HoursWorked);
	
}
