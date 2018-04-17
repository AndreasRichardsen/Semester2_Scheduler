package db;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;

import db.GetMax;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Employee;
import model.EmployeeType;
import model.HoursWorked;
import model.WeekDay;

public class EmployeeDB implements IFDBEmployee {

	private Connection con;

	public EmployeeDB() {
		con = DBConnection.getInstance().getDBcon();
	}

	@Override
	public ArrayList<Employee> getAllEmployees(boolean retrieveAssociation) {
		ResultSet results = null;
		ArrayList<Employee> emplist = new ArrayList<Employee>();

		String query = "SELECT eId, eFirstName, eLastName, ePhoneNumber, eEmail, eMinWorkHours, eMaxWorkHours, eTypeOfEmployee " 
				+ "FROM Employee";

		PreparedStatement preparedStmt = null;

		try {
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setQueryTimeout(5);
			results = preparedStmt.executeQuery();

			while(results.next()) {
				Employee empObj = new Employee();
				empObj = buildEmployee(results);
				if(empObj.getId() != 0)
					emplist.add(empObj);
				if(retrieveAssociation) {
					//retrieve Week Days Unavailable
					WeekDaysUnavailableDB wdUnavailable = new WeekDaysUnavailableDB();
					EnumSet<WeekDay> weekDaysUnavailable = wdUnavailable.getWeekDaysUnavailableByEmployeeId(empObj.getId(), false);
					empObj.setWeekDaysUnavailable(weekDaysUnavailable);

					//retrieve Week Days Preferred Off
					WeekDaysPreferredOffDB wdPreferredOff = new WeekDaysPreferredOffDB();
					EnumSet<WeekDay> weekDayPreferredOff = wdPreferredOff.getWeekDaysPrefferedOffByEmployeeId(empObj.getId(), false);
					empObj.setWeekDaysPreferredOff(weekDayPreferredOff);

					//retrieve Dates Unavailable
					DatesUnavailableDB dUnavailable = new DatesUnavailableDB();
					ArrayList<LocalDate> datesUnavailable = dUnavailable.getFutureDatesUnavailableByEmployeeId(empObj.getId());
					empObj.setDatesUnavailable(datesUnavailable);

					// retrieve Hours Worked
					HoursWorkedDB hoursWorkedDB = new HoursWorkedDB();
					ArrayList<HoursWorked> hoursWorked = hoursWorkedDB.getHoursWorkedForCurrentMonthById(empObj.getId());
					empObj.setHoursWorked(hoursWorked);
				}
			}
		}
		catch(Exception e) {
			System.out.println("Query exception - select: "+e);
			e.printStackTrace();
		}
		finally {
			try {
				preparedStmt.close();
				results.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return emplist;
	}

	@Override
	public ArrayList<Employee> getAllEmployeesByEmployeeType(EmployeeType empType, boolean retrieveAssociation) {
		ResultSet results = null;
		ArrayList<Employee> emplist = new ArrayList<Employee>();

		String baseQuery = "SELECT eId, eFirstName, eLastName, ePhoneNumber, eEmail, eMinWorkHours, eMaxWorkHours, eTypeOfEmployee " 
				+ "FROM Employee "
				+ "WHERE eTypeOfEmployee = ? ";

		PreparedStatement preparedStmt = null;

		try {
			preparedStmt = con.prepareStatement(baseQuery);
			preparedStmt.setString(1, empType.toString());
			preparedStmt.setQueryTimeout(5);
			results = preparedStmt.executeQuery();

			while(results.next()) {
				Employee empObj = new Employee();
				empObj = buildEmployee(results);
				emplist.add(empObj);
				if(retrieveAssociation) {
					//retrieve Week Days Unavailable
					WeekDaysUnavailableDB wdUnavailable = new WeekDaysUnavailableDB();
					EnumSet<WeekDay> weekDaysUnavailable = wdUnavailable.getWeekDaysUnavailableByEmployeeId(empObj.getId(), false);
					empObj.setWeekDaysUnavailable(weekDaysUnavailable);

					//retrieve Week Days Preferred Off
					WeekDaysPreferredOffDB wdPreferredOff = new WeekDaysPreferredOffDB();
					EnumSet<WeekDay> weekDayPreferredOff = wdPreferredOff.getWeekDaysPrefferedOffByEmployeeId(empObj.getId(), false);
					empObj.setWeekDaysPreferredOff(weekDayPreferredOff);

					//retrieve Dates Unavailable
					DatesUnavailableDB dUnavailable = new DatesUnavailableDB();
					ArrayList<LocalDate> datesUnavailable = dUnavailable.getFutureDatesUnavailableByEmployeeId(empObj.getId());
					empObj.setDatesUnavailable(datesUnavailable);

					// retrieve Hours Worked
					HoursWorkedDB hoursWorkedDB = new HoursWorkedDB();
					ArrayList<HoursWorked> hoursWorked = hoursWorkedDB.getHoursWorkedForCurrentMonthById(empObj.getId());
					empObj.setHoursWorked(hoursWorked);
				}
			}

		}
		catch(Exception e) {
			System.out.println("Query exception - select: "+e);
			e.printStackTrace();
		}
		finally {
			try {
				preparedStmt.close();
				results.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return emplist;
	}

	@Override
	public Employee getEmployeeById(int id, boolean retrieveAssociation) {
		ResultSet results = null;
		Employee foundEmpObj = new Employee();

		String baseQuery = "SELECT eId, eFirstName, eLastName, ePhoneNumber, eEmail, eMinWorkHours, eMaxWorkHours, eTypeOfEmployee " 
				+ "FROM Employee "
				+ "WHERE eId = ? ";

		PreparedStatement preparedStmt = null;

		try {
			preparedStmt = con.prepareStatement(baseQuery);
			preparedStmt.setInt(1, id);
			preparedStmt.setQueryTimeout(5);
			results = preparedStmt.executeQuery();

			if(results.next()) {
				foundEmpObj = buildEmployee(results);	
				if(retrieveAssociation) {
					//retrieve Week Days Unavailable
					WeekDaysUnavailableDB wdUnavailable = new WeekDaysUnavailableDB();
					EnumSet<WeekDay> weekDaysUnavailable = wdUnavailable.getWeekDaysUnavailableByEmployeeId(foundEmpObj.getId(), false);
					foundEmpObj.setWeekDaysUnavailable(weekDaysUnavailable);

					//retrieve Week Days Preferred Off
					WeekDaysPreferredOffDB wdPreferredOff = new WeekDaysPreferredOffDB();
					EnumSet<WeekDay> weekDayPreferredOff = wdPreferredOff.getWeekDaysPrefferedOffByEmployeeId(foundEmpObj.getId(), false);
					foundEmpObj.setWeekDaysPreferredOff(weekDayPreferredOff);

					//retrieve Dates Unavailable
					DatesUnavailableDB dUnavailable = new DatesUnavailableDB();
					ArrayList<LocalDate> datesUnavailable = dUnavailable.getFutureDatesUnavailableByEmployeeId(foundEmpObj.getId());
					foundEmpObj.setDatesUnavailable(datesUnavailable);

					// retrieve Hours Worked
					HoursWorkedDB hoursWorkedDB = new HoursWorkedDB();
					ArrayList<HoursWorked> hoursWorked = hoursWorkedDB.getHoursWorkedForCurrentMonthById(foundEmpObj.getId());
					foundEmpObj.setHoursWorked(hoursWorked);
				}
			}

		}
		catch(Exception e) {
			System.out.println("Query exception: "+e);
			e.printStackTrace();
		}
		finally {
			try {
				preparedStmt.close();
				results.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return foundEmpObj;
	}

	@Override
	public ArrayList<Employee> getEmployeesByLastName(String name, boolean retrieveAssociation) {
		ResultSet results = null;
		ArrayList<Employee> emplist = new ArrayList<Employee>();

		String baseQuery = "SELECT eId, eFirstName, eLastName, ePhoneNumber, eEmail, eMinWorkHours, eMaxWorkHours, eTypeOfEmployee " 
				+ "FROM Employee "
				+ "WHERE eLastName = ? ";

		PreparedStatement preparedStmt = null;

		try {
			preparedStmt = con.prepareStatement(baseQuery);
			preparedStmt.setString(1, name);
			preparedStmt.setQueryTimeout(5);
			results = preparedStmt.executeQuery();

			while(results.next()) {
				Employee empObj = new Employee();
				empObj = buildEmployee(results);
				emplist.add(empObj);
				if(retrieveAssociation) {
					//retrieve Week Days Unavailable
					WeekDaysUnavailableDB wdUnavailable = new WeekDaysUnavailableDB();
					EnumSet<WeekDay> weekDaysUnavailable = wdUnavailable.getWeekDaysUnavailableByEmployeeId(empObj.getId(), false);
					empObj.setWeekDaysUnavailable(weekDaysUnavailable);

					//retrieve Week Days Preferred Off
					WeekDaysPreferredOffDB wdPreferredOff = new WeekDaysPreferredOffDB();
					EnumSet<WeekDay> weekDayPreferredOff = wdPreferredOff.getWeekDaysPrefferedOffByEmployeeId(empObj.getId(), false);
					empObj.setWeekDaysPreferredOff(weekDayPreferredOff);

					//retrieve Dates Unavailable
					DatesUnavailableDB dUnavailable = new DatesUnavailableDB();
					ArrayList<LocalDate> datesUnavailable = dUnavailable.getFutureDatesUnavailableByEmployeeId(empObj.getId());
					empObj.setDatesUnavailable(datesUnavailable);

					// retrieve Hours Worked
					HoursWorkedDB hoursWorkedDB = new HoursWorkedDB();
					ArrayList<HoursWorked> hoursWorked = hoursWorkedDB.getHoursWorkedForCurrentMonthById(empObj.getId());
					empObj.setHoursWorked(hoursWorked);
				}
			}

		}
		catch(Exception e) {
			System.out.println("Query exception - select: "+e);
			e.printStackTrace();
		}
		finally {
			try {
				preparedStmt.close();
				results.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return emplist;
	}

	@Override
	public int insertEmployee(Employee employee) throws Exception {

		int nextId = GetMax.getEmployeeMaxId();
		nextId = nextId + 1;
		int rc = -1;
		String baseQuery="INSERT INTO employee(eId, eFirstName, eLastName, ePhoneNumber, eEmail, eMinWorkHours, eMaxWorkHours, eTypeOfEmployee) "
				+ "VALUES(?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStmt = null;
		try{ 
			DBConnection.startTransaction();
			preparedStmt = con.prepareStatement(baseQuery);
			preparedStmt.setInt(1, nextId);
			preparedStmt.setString(2, employee.getFirstName());
			preparedStmt.setString(3, employee.getLastName());
			preparedStmt.setString(4, employee.getPhoneNumber());
			preparedStmt.setString(5, employee.getEmail());
			preparedStmt.setInt(6, employee.getMinWorkingHours());
			preparedStmt.setInt(7, employee.getMaxWorkingHours());
			preparedStmt.setString(8, employee.getTypeOfEmployee().toString());
			preparedStmt.setQueryTimeout(5);
			rc = preparedStmt.executeUpdate();

			WeekDaysPreferredOffDB wdpoDB = new WeekDaysPreferredOffDB();
			for(WeekDay weekDayPO: employee.getWeekDaysPreferredOff()){
				wdpoDB.insertWeekDaysPreferredOffForEmployee(nextId, weekDayPO);
			}
			WeekDaysUnavailableDB wduDB = new WeekDaysUnavailableDB();
			for(WeekDay weekDayU: employee.getWeekDaysUnavailable()){
				wduDB.insertWeekDaysUnavailableForEmployee(nextId, weekDayU);
			}
			if(rc == -1){
				Exception e = new Exception();
				throw e;
			}
			DBConnection.commitTransaction();
		}
		catch(SQLException e){
			DBConnection.rollbackTransaction();
			System.out.println("Employee insertion error.");
			throw e;
		}
		finally {
			try {
				preparedStmt.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return nextId;
	}

	@Override
	public int updateEmployee(Employee employee) throws Exception {
		Employee empObj  = employee;
		int rc=-1;
		PreparedStatement preparedStmt = null;

		String baseQuery="UPDATE Employee SET "+
				"eFirstName ='" + empObj.getFirstName() + "',"+
				"eLastName ='"+ empObj.getLastName() + "', "+
				"ePhoneNumber ='"+ empObj.getPhoneNumber() + "', " +
				"eEmail ='"+ empObj.getEmail() + "', " +
				"eMinWorkHours ="+ empObj.getMinWorkingHours() + ", " +
				"eMaxWorkHours ="+ empObj.getMaxWorkingHours() + ", " +
				"eTypeOfEmployee ='"+ empObj.getTypeOfEmployee().toString()+"'"+
				" WHERE eId = "+ empObj.getId();

		try{ 
			DBConnection.startTransaction();
			preparedStmt = con.prepareStatement(baseQuery);
			preparedStmt.setQueryTimeout(5);
			rc = preparedStmt.executeUpdate();

			WeekDaysUnavailableDB wduDB = new WeekDaysUnavailableDB();
			wduDB.deleteAllWeekDaysUnavailableForEmployee(employee.getId());
			for(WeekDay weekday: employee.getWeekDaysUnavailable()){
				wduDB.insertWeekDaysUnavailableForEmployee(employee.getId(), weekday);
			}

			WeekDaysPreferredOffDB wdpoDB = new WeekDaysPreferredOffDB();
			wdpoDB.deleteAllWeekDaysPreferredOffForEmployee(employee.getId());
			for(WeekDay weekday: employee.getWeekDaysPreferredOff()){
				wdpoDB.insertWeekDaysPreferredOffForEmployee(employee.getId(), weekday);
			}
			DBConnection.commitTransaction();
		}
		catch(Exception e){
			DBConnection.rollbackTransaction();
			System.out.println("Update exception in Employee db: "+e);
			throw e;
		}
		finally{
			try{
				preparedStmt.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return(rc);
	}

	@Override
	public int deleteEmployee(Employee employee) throws Exception {
		int rc=-1;
		String query="DELETE FROM employee WHERE eId = ?";
		PreparedStatement preparedStmt = null;
		try{
			DBConnection.startTransaction();
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, employee.getId());
			preparedStmt.setQueryTimeout(5);
			rc = preparedStmt.executeUpdate();

			WeekDaysPreferredOffDB wdpoDB = new WeekDaysPreferredOffDB();
			wdpoDB.deleteAllWeekDaysPreferredOffForEmployee(employee.getId());

			WeekDaysUnavailableDB wduDB = new WeekDaysUnavailableDB();
			wduDB.deleteAllWeekDaysUnavailableForEmployee(employee.getId());
			DBConnection.commitTransaction();
		}	
		catch(Exception e){
			DBConnection.rollbackTransaction();
			System.out.println("Delete exception in employee db: "+e);
			throw e;
		}
		finally{
			try{
				preparedStmt.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return(rc);
	}

	private Employee buildEmployee(ResultSet results)
	{   Employee empObj = new Employee();
	try{
		empObj.setId(results.getInt("eId"));
		empObj.setFirstName(results.getString("eFirstName"));
		empObj.setLastName(results.getString("eLastName"));
		empObj.setPhoneNumber(results.getString("ePhoneNumber"));
		empObj.setEmail(results.getString("eEmail"));
		empObj.setMinWorkingHours(results.getInt("eMinWorkHours"));
		empObj.setMaxWorkingHours(results.getInt("eMaxWorkHours"));
		empObj.setTypeOfEmployee(EmployeeType.selectEmployeeType(results.getString("eTypeOfEmployee")));
	}
	catch(Exception e)
	{
		System.out.println("Error in building the Employee object.");
		e.printStackTrace();
	}
	return empObj;
	}



	public static void main(String [] args){
		EmployeeDB test = new EmployeeDB();
		System.out.println(test.getAllEmployees(true));

	}
}
