package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;

import db.DatesUnavailableDB;
import db.EmployeeDB;
import db.HoursWorkedDB;
import model.Employee;
import model.EmployeeType;
import model.HoursWorked;
import model.ShiftType;
import model.WeekDay;

public class EmployeeController {

	private EmployeeDB employeeDb;
	private DatesUnavailableDB datesUnavailableDb;
	private HoursWorkedDB hoursWorkedDb;
	private Employee currentEmployee;

	public EmployeeController(){
		employeeDb = new EmployeeDB();
		datesUnavailableDb = new DatesUnavailableDB();
		hoursWorkedDb = new HoursWorkedDB();
	}

	public Employee getCurrentEmployee() {
		return currentEmployee;
	}

	public void setCurrentEmployee(Employee currentEmployee) {
		this.currentEmployee = currentEmployee;
	}

	public ArrayList<Employee> getAllEmployees(){
		return employeeDb.getAllEmployees(true);
	}

	public ArrayList<Employee> getAvailableEmployees(LocalDate date, ShiftType typeOfShift){
		ArrayList<Employee> availableEmployees = new ArrayList<>();
		if(!typeOfShift.equals("NOT_SET")){
			EmployeeType empType = getEmployeeTypeFromShiftType(typeOfShift);
			availableEmployees = employeeDb.getAllEmployeesByEmployeeType(empType, true);
			availableEmployees = filterAvailableEmployees(availableEmployees, date);
		}
		return availableEmployees;
	}

	public ArrayList<Employee> filterAvailableEmployees(ArrayList<Employee> employeeList, LocalDate date){
		ArrayList<Employee> availableEmployees = new ArrayList<>();
		for(Employee employee: employeeList){
			EnumSet<WeekDay> weekDaysUnavailable = employee.getWeekDaysUnavailable();
			ArrayList<LocalDate> datesUnavailable = employee.getDatesUnavailable();
			if(!weekDaysUnavailable.contains(WeekDay.stringToEnum(date.getDayOfWeek().toString())) && !datesUnavailable.contains(date)){
				availableEmployees.add(employee);
			}
		}
		return availableEmployees;
	}

	public int insertEmployee(Employee employee) throws Exception{
		int nextId = employeeDb.insertEmployee(employee);
		return nextId;
	}

	public void updateEmployee(Employee employee) throws Exception{
		employeeDb.updateEmployee(employee);
	}

	public void deleteEmployee(Employee employee) throws Exception{
		employeeDb.deleteEmployee(employee);
	}

	public void insertDateUnavailable(Employee employee, LocalDate date) throws Exception{
		datesUnavailableDb.insertDateUnavailableForEmployee(employee, date);
	}

	public void removeDateUnavailable(Employee employee, LocalDate date){
		datesUnavailableDb.deleteDateUnavailableForEmployee(employee, date);
	}

	public int getScheduledHoursForMonth(LocalDate date, Employee employee){
		int scheduledHours = 0;
		ArrayList<HoursWorked> hoursWorkedForMonth = hoursWorkedDb.getHoursWorkedForMonthById(date.getMonthValue(), date.getYear(), employee.getId());
		for(HoursWorked hw: hoursWorkedForMonth){
			scheduledHours += hw.getNoOfHours();
		}
		return scheduledHours;
	}

	private EmployeeType getEmployeeTypeFromShiftType(Enum<ShiftType> typeOfShift){
		if(typeOfShift == ShiftType.DISHWASHER)
			return EmployeeType.DISHWASHER;
		else if(typeOfShift == ShiftType.WAITER)
			return EmployeeType.WAITER;
		else
			return null;
	}

	//	private static boolean checkWeekDay(WeekDay weekDay) {
	//		   try{
	//		      return  WeekDay.valueOf(weekDay.name()) != null;
	//		   } catch (java.lang.IllegalArgumentException e) {
	//		      return false;
	//		   }
	//		}

	public static void main(String [] args){
		EmployeeController test = new EmployeeController();
		System.out.println(test.getAvailableEmployees(LocalDate.now(), ShiftType.DISHWASHER));
	}
}
