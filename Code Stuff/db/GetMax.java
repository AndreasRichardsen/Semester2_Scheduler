

package db;
import java.sql.*;
import java.time.LocalDate;

import model.ShiftType;

/**
 * 
 */
public class GetMax {

	private static Connection con;
	
	public static int getEmployeeMaxId(){
		con = DBConnection.getInstance().getDBcon();
		int id = -1;
		ResultSet results = null;
		String baseQuery = "Select max(eId) from Employee";
		PreparedStatement preparedStmt = null;
		try{ 
			preparedStmt = con.prepareStatement(baseQuery);
			preparedStmt.setQueryTimeout(5);
			results = preparedStmt.executeQuery();
			if( results.next() ){
				id = results.getInt(1);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Query exception: Error in reading employee next id " + e);
		}
		return id;
	}
	
	public static int getNextShiftNumber(LocalDate date, ShiftType shiftType){
		con = DBConnection.getInstance().getDBcon();
		int sNumber = -1;
		ResultSet results = null;
		String baseQuery = "Select max(sNumber) from Shift where sDate = ? AND shiftType = ?";
		PreparedStatement preparedStmt = null;
		try{ 
			preparedStmt = con.prepareStatement(baseQuery);
			preparedStmt.setDate(1, java.sql.Date.valueOf(date));
			preparedStmt.setString(2, shiftType.toString());
			preparedStmt.setQueryTimeout(5);
			results = preparedStmt.executeQuery();
			if( results.next() ){
				sNumber = results.getInt(1);
			}
		}
		catch(Exception e){
			System.out.println("Query exception: Error in reading next Shift Number" + e);
		}
		
		return sNumber;
	}
	
	public static void main(String [] args){
		System.out.println(getEmployeeMaxId());
	}
}
