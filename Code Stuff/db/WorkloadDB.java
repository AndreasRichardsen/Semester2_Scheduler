package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import model.Workload;
import model.Shift;
/**
 * 
 * @author Sangey
 * This class is designed to handle communication between the database
 * and the java application for the class Workload. It uses DBConnection
 * to connect to the SQL server
 *
 */
public class WorkloadDB implements IFDBWorkload {

	private Connection con;

	public WorkloadDB(){
		con = DBConnection.getInstance().getDBcon();
	}

	@Override
	public ArrayList<Workload> getAllWorkloads(boolean retrieveAssociation) {
		ResultSet results = null;
		ArrayList<Workload> workloadList = new ArrayList<Workload>();	

		String query =  "SELECT [Date], NoOfGuestsReservations, NoOfGuestsEvents "
				+ "FROM [Workload]";
		PreparedStatement preparedStmt = null;

		try{ 
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setQueryTimeout(5);
			results = preparedStmt.executeQuery();

			while( results.next() ){
				Workload workloadObj = new Workload();
				workloadObj = buildWorkload(results);	
				workloadList.add(workloadObj);	
			}  
			if(retrieveAssociation)
			{ 
				ShiftDB shiftDb = new ShiftDB();
				for(Workload workloadObj : workloadList){
					ArrayList<Shift> shiftList = shiftDb.getAllShiftsByDate(workloadObj.getDate(), true);
					for(int i=0; i < shiftList.size(); i++){
						workloadObj.addShift(shiftList.get(i));
					}
				}
			}
		}
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
		return workloadList;
	}

	@Override
	public ArrayList<Workload> getWorkloadsForCurrentMonth(boolean retrieveAssociation) {
		ResultSet results = null;
		ArrayList<Workload> workloadList = new ArrayList<Workload>();	

		String query =  "select [Date], NoOfGuestsReservations, NoOfGuestsEvents "
				+ "FROM [Workload] "
				+ "Where Month(date) = Month(getDate()) AND Year(date) = Year(getDate())";
		PreparedStatement preparedStmt = null;

		try{ 
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setQueryTimeout(5);
			results = preparedStmt.executeQuery();

			while( results.next() ){
				Workload workloadObj = new Workload();
				workloadObj = buildWorkload(results);	
				workloadList.add(workloadObj);	
			}   
			if(retrieveAssociation)
			{ 
				ShiftDB shiftDb = new ShiftDB();
				for(Workload workloadObj : workloadList){
					ArrayList<Shift> shiftList = shiftDb.getAllShiftsByDate(workloadObj.getDate(), true);
					for(int i=0; i < shiftList.size(); i++){
						workloadObj.addShift(shiftList.get(i));
					}
				}
			}
		}
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
		return workloadList;
	}

	@Override
	public ArrayList<Workload> getWorkloadsForMonth(LocalDate date, Boolean retrieveAssociation) {
		ResultSet results = null;
		ArrayList<Workload> workloadList = new ArrayList<Workload>();	

		String query =  "select [Date], NoOfGuestsReservations, NoOfGuestsEvents "
				+ "FROM [Workload] "
				+ "Where Month(date) = Month(?) AND Year(date) = Year(?)";
		PreparedStatement preparedStmt = null;

		try{ 
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setDate(1, java.sql.Date.valueOf(date));
			preparedStmt.setDate(2, java.sql.Date.valueOf(date));
			preparedStmt.setQueryTimeout(5);
			results = preparedStmt.executeQuery();

			while( results.next() ){
				Workload workloadObj = new Workload();
				workloadObj = buildWorkload(results);	
				workloadList.add(workloadObj);	
			}   
			if(retrieveAssociation)
			{ 
				ShiftDB shiftDb = new ShiftDB();
				for(Workload workloadObj : workloadList){
					ArrayList<Shift> shiftList = shiftDb.getAllShiftsByDate(workloadObj.getDate(), true);
					for(int i=0; i < shiftList.size(); i++){
						workloadObj.addShift(shiftList.get(i));
					}
				}
			}
		}
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
		return workloadList;
	}

	@Override
	public Workload findWorkloadByDate(LocalDate date, boolean retrieveAssociation) {
		ResultSet workloadResult = null;
		Workload foundWorkloadObj = new Workload();		
		String baseQuery = "SELECT [Date], NoOfGuestsReservations, NoOfGuestsEvents "
				+ "FROM [Workload] "
				+ "Where date = ?";
		PreparedStatement preparedStmt = null;

		try{
			preparedStmt = con.prepareStatement(baseQuery);
			preparedStmt.setDate(1, java.sql.Date.valueOf(date));
			preparedStmt.setQueryTimeout(5);
			workloadResult = preparedStmt.executeQuery();
			if(workloadResult.next()){
				foundWorkloadObj = buildWorkload(workloadResult);
				if(retrieveAssociation)
				{ 
					ShiftDB shiftDb = new ShiftDB();
					ArrayList<Shift> shiftList = shiftDb.getAllShiftsByDate(foundWorkloadObj.getDate(), true);
					for(int i=0; i < shiftList.size(); i++){
						foundWorkloadObj.addShift(shiftList.get(i));
					}
				}
			}
		}
		catch(Exception e){
			System.out.println("Query exception: "+e);
		}
		finally{
			try {
				preparedStmt.close();
				workloadResult.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return foundWorkloadObj;
	}

	@Override
	public int insertWorkload(Workload workload) throws Exception {
		int rowsCounted = -1;
		String baseQuery = "INSERT INTO [Workload]([Date], NoOfGuestsReservations, NoOfGuestsEvents) "
				+ "VALUES ( ?, ?, ?)";
		PreparedStatement preparedStmt = null;

		try{
			// insert Workload into DB 
			DBConnection.startTransaction();
			preparedStmt = con.prepareStatement(baseQuery);
			preparedStmt.setDate(1, java.sql.Date.valueOf(workload.getDate()));
			preparedStmt.setInt(2, workload.getNoOfGuestsReservations());
			preparedStmt.setInt(3, workload.getNoOfGuestsEvent());
			preparedStmt.setQueryTimeout(5);
			rowsCounted = preparedStmt.executeUpdate();

			//insert Shifts
			ShiftDB shiftDb = new ShiftDB();
			for(Shift shift: workload.getShifts()){
				shiftDb.insertShift(shift, workload.getDate());
			}
			//commit transaction if no exceptions have occured
			DBConnection.commitTransaction();
		}
		catch(Exception e){
			DBConnection.rollbackTransaction();
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
	public int updateWorkload(Workload workload){
		int rowsCounted = -1;
		String baseQuery = "UPDATE [Workload] SET "
				+ "NoOfGuestsReservations = ?, "
				+ "NoOfGuestsEvents = ? "
				+ "WHERE [date] = ? ";
		PreparedStatement preparedStmt = null;
		try{
			preparedStmt = con.prepareStatement(baseQuery);
			preparedStmt.setInt(1, workload.getNoOfGuestsReservations());
			preparedStmt.setInt(2, workload.getNoOfGuestsEvent());
			preparedStmt.setDate(3, java.sql.Date.valueOf(workload.getDate()));
			preparedStmt.setQueryTimeout(5);
			rowsCounted = preparedStmt.executeUpdate();
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
	public int deleteWorkload(Workload workload) {
		int rowsCounted = -1;
		String baseQuery = "DELETE FROM [Workload] "
				+ "WHERE [date] = ? ";
		PreparedStatement preparedStmt = null;
		try{
			preparedStmt = con.prepareStatement(baseQuery);
			preparedStmt.setDate(1, java.sql.Date.valueOf(workload.getDate()));
			preparedStmt.setQueryTimeout(5);
			rowsCounted = preparedStmt.executeUpdate();
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

	private Workload buildWorkload(ResultSet results)
	{   Workload workloadObj = new Workload();
	try { 
		workloadObj.setDate((results).getDate("Date").toLocalDate());
		workloadObj.setNoOfGuestsReservations(results.getInt("NoOfGuestsReservations"));
		workloadObj.setNoOfGuestsEvent(results.getInt("NoOfGuestsEvents"));
	}
	catch(Exception e)
	{
		System.out.println("error in building the Workload object");
	}
	return workloadObj;
	}


	//IGNORE STUFF BELOW ITS FOR TESTING

	public int selectWeek(LocalDate date){
		double weekNo = Math.ceil(date.getDayOfMonth() / 7.0) ;
		return (int)weekNo;
	}
	
	public ArrayList<Workload> getCurrentWeekWorkloads(){
		ArrayList<Workload> currentWeekWorkloads = new ArrayList<>();
		LocalDate todaysDate = LocalDate.now();
		int weekDayValue = todaysDate.getDayOfWeek().getValue();
		LocalDate mondayDate = todaysDate.plusDays(1 - weekDayValue);
		LocalDate tempDate = mondayDate;
		for(int i=0; i<7; i++){
			currentWeekWorkloads.add(findWorkloadByDate(tempDate, true));
			tempDate = tempDate.plusDays(1);
		}
		return currentWeekWorkloads;
	}

}
