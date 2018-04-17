package db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;

import model.WeekDay;

/**
 * PROBABLY FUCKED
 * @author Sangey
 *
 */
public class WeekDaysPreferredOffDB implements IFDBWeekDaysPreferredOff {

	private Connection con;

	public WeekDaysPreferredOffDB(){

		con = DBConnection.getInstance().getDBcon();
	}

	@Override
	public EnumSet<WeekDay> getWeekDaysPrefferedOffByEmployeeId(int employeeId, boolean retrieveAssociation) {
		ResultSet results = null;
		EnumSet<WeekDay> weekDayList = EnumSet.noneOf(WeekDay.class);	
		String baseQuery =  "select DayOfTheWeek, employeeId "
				+ "FROM DaysOfWeekPreferredOff "
				+ "Where employeeId = ? ";
		PreparedStatement preparedStmt = null;
		try{ 
			preparedStmt = con.prepareStatement(baseQuery);
			preparedStmt.setInt(1, employeeId);
			preparedStmt.setQueryTimeout(5);
			results = preparedStmt.executeQuery();

			while( results.next() ){
				WeekDay weekDay;
				weekDay = WeekDay.stringToEnum(results.getString("DayOfTheWeek"));
				weekDayList.add(weekDay);
			}    

		}//finish try	
		catch(Exception e){
			System.out.println("Query exception - select: "+e);
			e.printStackTrace();
		}
		finally{
			try {
				preparedStmt.close();
				results.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return weekDayList;
	}

	@Override
	public int insertWeekDaysPreferredOffForEmployee(int employeeId, WeekDay weekDay) throws Exception {
		int rowsCounted = -1;
		String baseQuery = "INSERT INTO DaysOfWeekPreferredOff(DayOfTheWeek, employeeId) "
				+ "VALUES ( ?, ?)";
		PreparedStatement preparedStmt = null;
		try{
			preparedStmt = con.prepareStatement(baseQuery);
			preparedStmt.setString(1, weekDay.toString());
			preparedStmt.setInt(2, employeeId);
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
			catch(Exception e){
				e.printStackTrace();				
			}
		}
		return rowsCounted;
	}

	@Override
	public int deleteWeekDaysPreferredOffForEmployee(int employeeId, WeekDay weekDay) {
		int rowsCounted =-1;
		String baseQuery = "DELETE FROM DaysOfWeekPreferredOff WHERE employeeId = ? AND DayOfTheWeek = ? ";
		PreparedStatement preparedStmt = null;

		try{
			preparedStmt = con.prepareStatement(baseQuery);
			preparedStmt.setInt(1, employeeId);
			preparedStmt.setString(2, weekDay.toString());
			preparedStmt.setQueryTimeout(5);
			rowsCounted = preparedStmt.executeUpdate();
		}
		catch(Exception e){
			System.out.println("Delete exception in HoursWorked db: "+e);
			e.printStackTrace();
		}
		finally{
			try{
				preparedStmt.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return rowsCounted;
	}

	public int deleteAllWeekDaysPreferredOffForEmployee(int employeeId) throws Exception {
		int rowsCounted =-1;
		String baseQuery = "DELETE FROM DaysOfWeekPreferredOff WHERE employeeId = ?";
		PreparedStatement preparedStmt = null;

		try{
			preparedStmt = con.prepareStatement(baseQuery);
			preparedStmt.setInt(1, employeeId);
			preparedStmt.setQueryTimeout(5);
			rowsCounted = preparedStmt.executeUpdate();
		}
		catch(Exception e){
			System.out.println("Delete exception in  WeekDaysPreferredOff db: "+e);
			e.printStackTrace();
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
		return rowsCounted;
	}

	public static void main(String [] args){
		WeekDaysPreferredOffDB test = new WeekDaysPreferredOffDB();
		try {
			System.out.println(test.deleteWeekDaysPreferredOffForEmployee(1, WeekDay.TUESDAY));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
