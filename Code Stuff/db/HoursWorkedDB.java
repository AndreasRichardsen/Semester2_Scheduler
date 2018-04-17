package db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import model.Employee;
import model.HoursWorked;

public class HoursWorkedDB implements IFDBHoursWorked {

	private Connection con;
	
	public HoursWorkedDB()
	{
		con = DBConnection.getInstance().getDBcon();
	}
	

	@Override
	public HoursWorked findHoursWorkedByDateAndEmployeeId(LocalDate date, int employeeId, boolean retrieveAssociation) {
		ResultSet results;
		HoursWorked foundHoursWorked = null;	
		try{ 
			con = DBConnection.getInstance().getDBcon();
			//DBConnection.startTransaction();
			PreparedStatement preparedstat = null;
			String baseQuery = "SELECT Date, NoOfHours, EmployeeId "  
					+ "FROM HoursWorked "
					+ "WHERE date = ?  AND employeeId = ?" ;
			
			preparedstat = con.prepareStatement(baseQuery);
			preparedstat.setDate(1, Date.valueOf(date));
			preparedstat.setInt(2, employeeId);
			results = preparedstat.executeQuery();
			if(results.next()){
				foundHoursWorked = buildHoursWorked(results);
			}
			
			   
			if(retrieveAssociation)
			{   
				//no assosciations to be retrieved
				
			}//end if   

		}//finish try	
		catch(Exception e){
			System.out.println("Query exception - select: "+e);
			e.printStackTrace();
		}
		return foundHoursWorked;
	}

	@Override
	public int insertHoursWorked(Employee employee, HoursWorked HoursWorked) throws Exception {
		int rc = -1;
		
		try{
			con = DBConnection.getInstance().getDBcon();
			
			PreparedStatement preparedstat = null;
			String baseQuery = "INSERT INTO HoursWorked(date, noOfHours, employeeId)" + "VALUES( ?, ?, ?)";
			preparedstat = con.prepareStatement(baseQuery);
			preparedstat.setDate(1, Date.valueOf(HoursWorked.getDate()));
			preparedstat.setDouble(2,  HoursWorked.getNoOfHours());
			preparedstat.setInt(3, employee.getId());
			rc = preparedstat.executeUpdate();
			 
			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw e;
			
		}
		return rc;
	}

	@Override
	public int updateHoursWorked(Employee employee, HoursWorked HoursWorked) {
		int rc=-1;
		
		try{
			con = DBConnection.getInstance().getDBcon();
		
			PreparedStatement preparedstat = null;
			String baseQuery = "UPDATE HoursWorked " + 
				"SET noOfHours = ? " +
				"WHERE employeeId = ? AND date = ?";
		
			preparedstat = con.prepareStatement(baseQuery);
			preparedstat.setDouble(1,  HoursWorked.getNoOfHours());
			preparedstat.setInt(2, employee.getId());
			preparedstat.setDate(3, java.sql.Date.valueOf(HoursWorked.getDate()));
			rc = preparedstat.executeUpdate();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		}
		return rc;
	}

	@Override
	public int deleteHoursWorked(Employee employee, HoursWorked HoursWorked) {
		int rc=-1;
		
		try{
			con = DBConnection.getInstance().getDBcon();
		
			PreparedStatement preparedstat = null;
			String baseQuery = "DELETE FROM HoursWorked WHERE date = ? AND employeeId = ?";
		
			preparedstat = con.prepareStatement(baseQuery);
			preparedstat.setDate(1, java.sql.Date.valueOf(HoursWorked.getDate()));
			preparedstat.setInt(2, employee.getId());
			rc = preparedstat.executeUpdate();
		}
		catch(Exception ex){
			System.out.println("Delete exception in HoursWorked db: "+ex);
			ex.printStackTrace();
		}
		return rc;
	}

	private HoursWorked buildHoursWorked(ResultSet results)
	{   HoursWorked hoursWorkedObj = new HoursWorked();
		try { 
			hoursWorkedObj.setDate(results.getDate("date").toLocalDate());
			hoursWorkedObj.setNoOfHours(results.getInt("NoOfHours")); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("error in building the HoursWorked object");
		}
		return hoursWorkedObj;
	}
	

	@Override
	public ArrayList<HoursWorked> getHoursWorkedForCurrentMonthById(int employeeId) {
		ResultSet results;
		HoursWorked foundHoursWorked = new HoursWorked();
		ArrayList<HoursWorked> list = new ArrayList<HoursWorked>();
		try {
			con = DBConnection.getInstance().getDBcon();
			
			PreparedStatement preparedstat = null;
			String baseQuery = "SELECT date, employeeId, NoOfHours FROM HoursWorked WHERE EmployeeId = ? AND Date >= ? AND Date <= ? ";
			preparedstat = con.prepareStatement(baseQuery);
			preparedstat.setInt(1, employeeId);
			preparedstat.setDate(2, java.sql.Date.valueOf(LocalDate.now().withDayOfMonth(1)));
			preparedstat.setDate(3, java.sql.Date.valueOf(LocalDate.now().plusMonths(1).withDayOfMonth(1).minusDays(1)));
			results = preparedstat.executeQuery();
			while(results.next()){
				foundHoursWorked = buildHoursWorked(results);
				list.add(foundHoursWorked);
			}
			
		}
		catch(Exception e){
			System.out.println("Query exception - select: "+e);
			e.printStackTrace();
		}
		return list;
		
	}


	@Override
	public ArrayList<HoursWorked> getHoursWorkedForMonthById(int month, int year, int employeeId) {
		ResultSet results;
		HoursWorked foundHoursWorked = new HoursWorked();
		ArrayList<HoursWorked> list = new ArrayList<HoursWorked>();
		try {
			con = DBConnection.getInstance().getDBcon();
			
			PreparedStatement preparedstat = null;
			String baseQuery = "SELECT date, employeeId, NoOfHours FROM HoursWorked WHERE EmployeeId = ? AND MONTH(Date) = ? AND YEAR(Date) = ?";
			preparedstat = con.prepareStatement(baseQuery);
			preparedstat.setInt(1, employeeId);
			preparedstat.setInt(2, month);
			preparedstat.setInt(3, year);
			results = preparedstat.executeQuery();
			while(results.next()){
				foundHoursWorked = buildHoursWorked(results);
				list.add(foundHoursWorked);
			}
			
		}
		catch(Exception e){
			System.out.println("Query exception - select: "+e);
			e.printStackTrace();
		}
		return list;
	}
}
