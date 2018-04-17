package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import model.Employee;
import model.Shift;
import model.ShiftType;
import model.Workload;

public class ShiftDB implements IFDBShift{

	private Connection con;

	public ShiftDB(){
		con = DBConnection.getInstance().getDBcon();
	}

	@Override
	public ArrayList<Shift> getAllShiftsByDate(LocalDate date, boolean retrieveAssociation) {
		ArrayList<Shift> shiftList = new ArrayList<>();
		ResultSet results = null;
		String baseQuery = "SELECT sNumber, sDate, sStartTime, sLocation, shiftType, estimatedShiftLength, employeeId "
				+ "FROM Shift "
				+ "WHERE sDate = ?";
		PreparedStatement preparedStmt = null;

		try{
			preparedStmt = con.prepareStatement(baseQuery);
			preparedStmt.setDate(1, java.sql.Date.valueOf(date));
			preparedStmt.setQueryTimeout(5);
			results = preparedStmt.executeQuery();

			while(results.next()){
				Shift shiftObj = new Shift();
				shiftObj = buildShift(results);
				shiftList.add(shiftObj);
				if(retrieveAssociation){
					EmployeeDB employeeDb = new EmployeeDB();
					Employee employee = employeeDb.getEmployeeById(results.getInt("employeeId"), false);
					shiftObj.setEmployee(employee);
				}
			}

		}
		catch(Exception e){
			e.printStackTrace();
			return shiftList;
		}
		finally{
			try{
				preparedStmt.close();
				results.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return shiftList;
	}

	@Override
	public int insertShift(Shift shift, LocalDate workloadDate) throws Exception {
		int rowsCounted = -1;
		String baseQuey = "INSERT INTO Shift(sNumber, sDate, sStartTime, sLocation, shiftType, estimatedShiftLength, employeeId) "
				+ "VALUES ( ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStmt = null;

		try{
			// insert Shift into DB
			preparedStmt = con.prepareStatement(baseQuey);
			preparedStmt.setInt(1, shift.getNumber());
			preparedStmt.setDate(2,java.sql.Date.valueOf(workloadDate) );
			preparedStmt.setString(3, shift.getStartTime());
			preparedStmt.setString(4, shift.getLocation());
			preparedStmt.setString(5, shift.getTypeOfShift().toString());
			preparedStmt.setInt(6, shift.getEstimatedShitLength());
			if(shift.getEmployee()!= null){
				preparedStmt.setInt(7, shift.getEmployee().getId());
			}
			else{
				preparedStmt.setInt(7, 0);
			}
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
	public int updateShift(Shift shift, LocalDate workloadDate) {

		int rowsCounted = -1;
		String baseQuery = "UPDATE Shift SET "
				+ "sStartTime = ?, "
				+ "sLocation = ?, "
				+ "shiftType = ?, "
				+ "estimatedShiftLength = ?, "
				+ "employeeId = ? "
				+ "WHERE sNumber = ? AND sDate = ? ";
		PreparedStatement preparedStmt = null;

		try{
			DBConnection.getInstance().getDBcon();
			preparedStmt = con.prepareStatement(baseQuery);
			preparedStmt.setString(1, shift.getStartTime());
			preparedStmt.setString(2, shift.getLocation());
			preparedStmt.setString(3, shift.getTypeOfShift().toString());
			preparedStmt.setInt(4, shift.getEstimatedShitLength());
			if(shift.getEmployee()!= null){
				preparedStmt.setInt(5, shift.getEmployee().getId());
			}
			else{
				preparedStmt.setNull(5, java.sql.Types.NULL);
			}
			preparedStmt.setInt(6, shift.getNumber());
			preparedStmt.setDate(7, java.sql.Date.valueOf(workloadDate));
			preparedStmt.setQueryTimeout(5);
			rowsCounted = preparedStmt.executeUpdate();
			DBConnection.commitTransaction();
		}

		catch(Exception e){
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

	@Override
	public int deleteShift(Shift shift, LocalDate workloadDate) {
		int rowsCounted = -1;
		String query = "DELETE FROM Shift Where sNumber = ? AND sDate = ? ";
		PreparedStatement preparedStmt = null;

		try{
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, shift.getNumber());
			preparedStmt.setDate(2, java.sql.Date.valueOf(workloadDate));
			preparedStmt.setQueryTimeout(5);
			rowsCounted = preparedStmt.executeUpdate();
		}
		catch(Exception e){
			System.out.println("Delete exception in Shift DB " + e);
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

	public int deleteAllShiftsFromWorkload(Workload workload){
		int rowsCounted = -1;
		String query = "DELETE FROM Shift Where sDate = ? ";
		PreparedStatement preparedStmt = null;

		try{
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setDate(1, java.sql.Date.valueOf(workload.getDate()));
			preparedStmt.setQueryTimeout(5);
			rowsCounted = preparedStmt.executeUpdate();
		}
		catch(Exception e){
			System.out.println("Delete exception in Shift DB " + e);
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

	private Shift buildShift(ResultSet results){
		Shift shiftObj = new Shift();
		try { 
			shiftObj.setNumber(results.getInt("sNumber"));
			shiftObj.setStartTime(results.getString("sStartTime"));
			shiftObj.setLocation(results.getString("sLocation"));
			shiftObj.setTypeOfShift(ShiftType.selectShiftType(results.getString("shiftType")));
			shiftObj.setEstimatedShiftLength(results.getInt("estimatedShiftLength"));
			shiftObj.setEmployee(new Employee());
		}
		catch(Exception e)
		{
			System.out.println("error in building the Shift object");
		}
		return shiftObj;
	}
	
}
