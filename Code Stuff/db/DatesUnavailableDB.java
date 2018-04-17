package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import model.Employee;

public class DatesUnavailableDB implements IFDBDatesUnavailable{

	private Connection con;

	public DatesUnavailableDB() {
		con = DBConnection.getInstance().getDBcon();
	}

	@Override
	public ArrayList<LocalDate> getAllDatesUnavailableByEmployeeID(int id) {
		ResultSet results = null;
		ArrayList<LocalDate> datelist = new ArrayList<LocalDate>();
		String baseQuery = "SELECT Date, employeeId "
				+ "FROM DatesUnavailable " 
				+ "WHERE employeeId = ? ";
		PreparedStatement preparedStmt = null;

		try {
			preparedStmt = con.prepareStatement(baseQuery);
			preparedStmt.setInt(1, id);
			preparedStmt.setQueryTimeout(5);
			results = preparedStmt.executeQuery();

			while(results.next()) {
				LocalDate dateObj = null;
				dateObj = results.getDate("Date").toLocalDate();
				datelist.add(dateObj);
			}
		}
		catch(Exception e) {
			System.out.println("Query exception - select: "+e);
			e.printStackTrace();
		}
		finally{
			try{
				preparedStmt.close();
				results.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}

		return datelist;
	}

	@Override
	public ArrayList<LocalDate> getFutureDatesUnavailableByEmployeeId(int id) {
		ResultSet results = null;
		ArrayList<LocalDate> datelist = new ArrayList<LocalDate>();
		String query = "SELECT Date, employeeId "
				+ "FROM DatesUnavailable "
				+ "WHERE Date >= ? AND employeeId = ?";
		PreparedStatement preparedStmt = null;

		try{
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
			preparedStmt.setInt(2, id);
			preparedStmt.setQueryTimeout(5);
			results = preparedStmt.executeQuery();

			while(results.next()) {
				LocalDate dateObj = null;
				dateObj = results.getDate("Date").toLocalDate();
				datelist.add(dateObj);
			}	
		}
		catch(Exception e){
			e.printStackTrace();	
		}
		finally{
			try{
				preparedStmt.close();
				results.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}

		return datelist;
	}

	@Override
	public ArrayList<LocalDate> getAllDatesUnavailableForCurrentMonth() {
		ResultSet results = null;
		ArrayList<LocalDate> datelist = new ArrayList<LocalDate>();	

		String query =  "select [Date], employeeId "
				+ "FROM datesunavailable "
				+ "Where Month(date) = Month(getDate()) AND Year(date) = Year(getDate())";
		PreparedStatement preparedStmt = null;

		try{ 
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setQueryTimeout(5);
			results = preparedStmt.executeQuery();

			while( results.next() ){
				LocalDate dateObj = null;
				dateObj = results.getDate("Date").toLocalDate();
				datelist.add(dateObj);	
			}    	
		}	
		catch(Exception e){
			System.out.println("Query exception - select: "+e);
			e.printStackTrace();
		}
		finally{
			try{
				preparedStmt.close();
				results.close();
			} catch (SQLException e){
				e.printStackTrace();
			}

		}
		return datelist;
	}

	@Override
	public int insertDateUnavailableForEmployee(Employee employee, LocalDate date) throws SQLException {
		int rowsCounted = -1;
		String query = "INSERT INTO DatesUnavailable(Date, employeeId) "
				+ "VALUES( ?,?)";

		PreparedStatement preparedStmt = null;

		try{
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setDate(1, java.sql.Date.valueOf(date.toString()));
			preparedStmt.setInt(2, employee.getId());
			preparedStmt.setQueryTimeout(5);
			rowsCounted = preparedStmt.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		finally{
			try{
				preparedStmt.close();
			}
			catch(Exception e) {
				e.printStackTrace();				
			}
		}
		return rowsCounted;
	}

	@Override
	public int deleteDateUnavailableForEmployee(Employee employee, LocalDate date) {
		int rowsCounted = -1;
		String query = "DELETE FROM DatesUnavailable WHERE employeeId = ? AND Date = ? ";

		PreparedStatement preparedStmt = null; 

		try {
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, employee.getId());
			preparedStmt.setDate(2, java.sql.Date.valueOf(date.toString()));
			preparedStmt.setQueryTimeout(5);
			rowsCounted = preparedStmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				preparedStmt.close();
			}
			catch(Exception e) {
				e.printStackTrace();				
			}
		}
		return rowsCounted;
	}

	public static void main(String [] args) {
		DatesUnavailableDB test = new DatesUnavailableDB();
		System.out.println(test.getAllDatesUnavailableForCurrentMonth());
	}
}
