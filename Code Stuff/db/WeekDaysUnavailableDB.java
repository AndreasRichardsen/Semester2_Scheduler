package db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;

import model.WeekDay;

/**
 * ITS FINE I CHECKED ALL GOOD FUCK THESE GUYS
 * @author Sangey
 *
 */
public class WeekDaysUnavailableDB implements IFDBWeekDaysUnavailable{

	private Connection con;
	
	public WeekDaysUnavailableDB(){
		
		con = DBConnection.getInstance().getDBcon();
	}

	@Override
	public EnumSet<WeekDay> getWeekDaysUnavailableByEmployeeId(int employeeId, boolean retrieveAssociation) {
		ResultSet results = null;
		EnumSet<WeekDay> weekDayList = EnumSet.noneOf(WeekDay.class);

		String query =  "select DayOfTheWeek, employeeId "
				+ "FROM DaysOfWeekUnavailable "
				+ "Where employeeId = ? ";
		PreparedStatement preparedStmt = null;

		try{ 
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, employeeId);
			results = preparedStmt.executeQuery();
			preparedStmt.setQueryTimeout(5);

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
	public int insertWeekDaysUnavailableForEmployee(int employeeId, WeekDay weekDay) throws Exception {
		int rowsCounted = -1;
		String baseQuery = "INSERT INTO DaysOfWeekUnavailable(DayOfTheWeek, employeeId) "
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
	public int deleteWeekDaysUnavailableForEmployee(int employeeId, WeekDay weekDay) {
		int rowsCounted =-1;

		try{
			PreparedStatement preparedStmt = null;
			String baseQuery = "DELETE FROM DaysOfWeekUnavailable WHERE DayOfTheWeek = ? AND employeeId = ?";

			DBConnection.getInstance().getDBcon();
			preparedStmt = con.prepareStatement(baseQuery);
			preparedStmt.setString(1, weekDay.toString());
			preparedStmt.setInt(2, employeeId);
			rowsCounted = preparedStmt.executeUpdate();
		}
		catch(Exception ex){
			System.out.println("Delete exception in WeekDaysUnavailable db: "+ex);
			ex.printStackTrace();
		}
		return rowsCounted;
	}

	@Override
	public int deleteAllWeekDaysUnavailableForEmployee(int employeeId) throws Exception {
		int rowsCounted =-1;
		String baseQuery = "DELETE FROM DaysOfWeekUnavailable WHERE employeeId = ?";
		PreparedStatement preparedStmt = null;

		try{
			preparedStmt = con.prepareStatement(baseQuery);
			preparedStmt.setInt(1, employeeId);
			preparedStmt.setQueryTimeout(5);
			rowsCounted = preparedStmt.executeUpdate();
		}
		catch(Exception e){
			System.out.println("Delete exception in WeekDaysUnavailable db: "+e);
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

