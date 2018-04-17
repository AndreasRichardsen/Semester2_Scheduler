package db;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import model.Employee;

public interface IFDBDatesUnavailable {
	
	public ArrayList<LocalDate> getAllDatesUnavailableByEmployeeID(int id);
	public ArrayList<LocalDate> getFutureDatesUnavailableByEmployeeId(int id);
	public ArrayList<LocalDate> getAllDatesUnavailableForCurrentMonth();
	public int insertDateUnavailableForEmployee(Employee employee, LocalDate date) throws SQLException;
	public int deleteDateUnavailableForEmployee(Employee employee,LocalDate date);
	
}
