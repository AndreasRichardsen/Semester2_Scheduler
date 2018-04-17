package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;


public class Employee {
	
	private int id;
	private String firstName, lastName, phoneNumber, email;
	private int minWorkingHours;
	private int maxWorkingHours;
	private EmployeeType typeOfEmployee;
	private EnumSet<WeekDay> weekDaysUnavailable;
	private EnumSet<WeekDay> weekDaysPreferredOff;
	private ArrayList<LocalDate> datesUnavailable;
	private ArrayList<HoursWorked> hoursWorked;
	
	public Employee()
	{
		super();
		weekDaysUnavailable = EnumSet.noneOf(WeekDay.class);
		weekDaysPreferredOff = EnumSet.noneOf(WeekDay.class);
		datesUnavailable = new ArrayList<LocalDate>();
		hoursWorked = new ArrayList<HoursWorked>();
	}
	
	

	public Employee(String firstName, String lastName, String phoneNumber, String email, int minWorkingHours,
			int maxWorkingHours, EmployeeType typeOfEmployee) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.minWorkingHours = minWorkingHours;
		this.maxWorkingHours = maxWorkingHours;
		this.typeOfEmployee = typeOfEmployee;
		weekDaysUnavailable = EnumSet.noneOf(WeekDay.class);
		weekDaysPreferredOff = EnumSet.noneOf(WeekDay.class);
		datesUnavailable = new ArrayList<LocalDate>();
		hoursWorked = new ArrayList<HoursWorked>();
	}
	
	public Employee(int id, String firstName, String lastName, String phoneNumber, String email, int minWorkingHours,
			int maxWorkingHours, EmployeeType typeOfEmployee) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.minWorkingHours = minWorkingHours;
		this.maxWorkingHours = maxWorkingHours;
		this.typeOfEmployee = typeOfEmployee;
		weekDaysUnavailable = EnumSet.noneOf(WeekDay.class);
		weekDaysPreferredOff = EnumSet.noneOf(WeekDay.class);
		datesUnavailable = new ArrayList<LocalDate>();
		hoursWorked = new ArrayList<HoursWorked>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getMinWorkingHours() {
		return minWorkingHours;
	}

	public void setMinWorkingHours(int minWorkingHours) {
		this.minWorkingHours = minWorkingHours;
	}

	public int getMaxWorkingHours() {
		return maxWorkingHours;
	}

	public void setMaxWorkingHours(int maxWorkingHours) {
		this.maxWorkingHours = maxWorkingHours;
	}

	public EmployeeType getTypeOfEmployee() {
		return typeOfEmployee;
	}

	public void setTypeOfEmployee(EmployeeType typeOfEmployee) {
		this.typeOfEmployee = typeOfEmployee;
	}

	public EnumSet<WeekDay> getWeekDaysUnavailable() {
		return weekDaysUnavailable;
	}

	public void setWeekDaysUnavailable(EnumSet<WeekDay> weekDayUnavailable) {
		this.weekDaysUnavailable = weekDayUnavailable;
	}

	public EnumSet<WeekDay> getWeekDaysPreferredOff() {
		return weekDaysPreferredOff;
	}

	public void setWeekDaysPreferredOff(EnumSet<WeekDay> weekDayPreferredOff) {
		this.weekDaysPreferredOff = weekDayPreferredOff;
	}

	public ArrayList<LocalDate> getDatesUnavailable() {
		return datesUnavailable;
	}

	public void setDatesUnavailable(ArrayList<LocalDate> datesUnavailable) {
		this.datesUnavailable = datesUnavailable;
	}
	
	public ArrayList<HoursWorked> getHoursWorked() {
		return hoursWorked;
	}
	
	public void setHoursWorked(ArrayList<HoursWorked> hoursWorked) {
		this.hoursWorked = hoursWorked;
	}
	
	public int getHoursWorkedThisMonth(){
		int hoursWorkedThisMonth = 0;
		for(HoursWorked hw: hoursWorked){
			hoursWorkedThisMonth += hw.getNoOfHours();
		}
		return hoursWorkedThisMonth;
	}
	
	public static String listToString(EnumSet<WeekDay> list) {
	    String result = "";
	    for(WeekDay weekday: list){
	    	result += weekday.name().substring(0,3) + " ";
	    }
	    return result;
	}



	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber="
				+ phoneNumber + ", email=" + email + ", minWorkingHours=" + minWorkingHours + ", maxWorkingHours="
				+ maxWorkingHours + ", typeOfEmployee=" + typeOfEmployee + ", weekDaysUnavailable="
				+ weekDaysUnavailable + ", weekDaysPreferredOff=" + weekDaysPreferredOff + ", datesUnavailable="
				+ datesUnavailable + ", hoursWorked=" + hoursWorked + "]";
	}



	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Employee) {
			Employee e = (Employee) obj;
			return e.getId() == this.getId();
		}
		else{
			return false;
		}
	}
	
	

	
}