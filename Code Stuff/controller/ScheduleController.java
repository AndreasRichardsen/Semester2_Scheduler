package controller;

import java.time.LocalDate;
import java.util.ArrayList;

import db.ShiftDB;
import db.WorkloadDB;
import model.Employee;
import model.Shift;
import model.ShiftType;
import model.Workload;

public class ScheduleController {

	private static ScheduleController instance;
	private ArrayList<Workload>	monthWorkloads;
	private Workload currentWorkload;
	private Shift currentShift;
	private Employee currentEmployee;
	private ArrayList<Employee> availableEmployees;
	private EmployeeController empCtr;


	private ScheduleController(){
		monthWorkloads = new ArrayList<>();
		currentWorkload = null;
		currentShift = null;
		setAvailableEmployees(new ArrayList<>());
		empCtr = new EmployeeController();
	}

	public static ScheduleController getInstance(){
		if(instance == null){
			instance = new ScheduleController();
		}
		return instance;
	}

	public Workload getCurrentWorkload() {
		return currentWorkload;
	}

	public void setCurrentWorkload(Workload currentWorkload) {
		this.currentWorkload = currentWorkload;
	}

	public Shift getCurrentShift() {
		return currentShift;
	}

	public void setCurrentShift(Shift currentShift) {
		this.currentShift = currentShift;
	}

	public ArrayList<Employee> getAvailableEmployees() {
		return availableEmployees;
	}

	public void setAvailableEmployees(ArrayList<Employee> availableEmployees) {
		this.availableEmployees = availableEmployees;
	}

	public ArrayList<Workload> getSchedule(){
		WorkloadDB workloadDb = new WorkloadDB();
		monthWorkloads = workloadDb.getWorkloadsForCurrentMonth(true);
		return getCurrentWeekWorkloads();
	}

	public ArrayList<Workload> getMonthWorkloads() {
		return monthWorkloads;
	}

	public void setMonthWorkloads(ArrayList<Workload> monthWorkloads) {
		this.monthWorkloads = monthWorkloads;
	}

	public Employee getCurrentEmployee() {
		return currentEmployee;
	}

	public void setCurrentEmployee(Employee currentEmployee) {
		this.currentEmployee = currentEmployee;
	}

	/* Minimum 1 shift for a working day.
	 * More than 50 guests, need at least 2 dish washing shifts
	 * If event = true && total no of guests >= 40, +1 shift (for banquet room)
	 * More than 115 guest && no event, required 3 shifts
	 * More than 210 guests total need 4 shifts	
	 */
	public int calculateNoOfDishwasherShifts(Workload workload){
		int noOfRequiredShifts = 1;
		if(workload.getNoOfGuestsReservations() >= 50){
			noOfRequiredShifts = 2;
		}
		if(workload.getNoOfGuestsEvent() > 0 && (workload.getNoOfGuestsEvent()+workload.getNoOfGuestsReservations()) >= 40){
			noOfRequiredShifts++;
		}
		if(workload.getNoOfGuestsReservations() > 115 && workload.getNoOfGuestsEvent() == 0){
			noOfRequiredShifts = 3;
		}
		if((workload.getNoOfGuestsEvent()+workload.getNoOfGuestsReservations()) >= 210){
			noOfRequiredShifts = 4;
		}
		return noOfRequiredShifts;
	}

	/* Minimum no of waiters shifts = 1 (for < 10 guests)
	 * 80+ guests = busy 
	 * <=80 guests = not busy
	 * logic for main restaurant area
	 *  if < 10, 1 shift
	 *  if >10 <=20, 2 shifts
	 *  if >20 <=60, 3 shifts
	 *  if >60 <=80, 4 shifts
	 *  extra shifts after 4 (when busy), 1 shift per 20 additional guests
	 */
	public int calculateNoOfMainRoomWaiterShifts(Workload workload){
		int noOfRequiredShifts = 1;
		if(workload.getNoOfGuestsReservations() > 10 && workload.getNoOfGuestsReservations() <=20){
			noOfRequiredShifts = 2;
		}
		if(workload.getNoOfGuestsReservations() > 20 && workload.getNoOfGuestsReservations() <=60){
			noOfRequiredShifts = 3;
		}
		if(workload.getNoOfGuestsReservations() > 60 && workload.getNoOfGuestsReservations() <=80){
			noOfRequiredShifts = 4;
		}
		if(workload.getNoOfGuestsReservations() > 80){
			noOfRequiredShifts = 4;
			for(int i = 0; i < (workload.getNoOfGuestsReservations()-80) / 20; i++){
				noOfRequiredShifts++;
			}
		}
		return noOfRequiredShifts;
	}

	/*
	 *  If there is no event, no shifts in the banquet room required.
	 *  Minimum no of waiters shifts = 1 (for < 10 guests)
	 *  80+ guests = busy 
	 *  <=80 guests = not busy
	 *  logic for banquet room
	 *  if >0  <=10 , 1 shift
	 *  if >10 <=20, 2 shifts
	 *  if >20 <=60, 3 shifts
	 *  if >60 <=80, 4 shifts
	 *  extra shifts after 4, 1 shift per 20 additional guests
	 */
	public int calculateNoOfBanquetRoomWaiterShifts(Workload workload){
		int noOfRequiredShifts = 0;
		if(workload.getNoOfGuestsEvent() > 0 && workload.getNoOfGuestsEvent() <= 10){
			noOfRequiredShifts = 1;
		}
		if(workload.getNoOfGuestsEvent() > 10 && workload.getNoOfGuestsEvent() <= 20){
			noOfRequiredShifts = 2;
		}
		if(workload.getNoOfGuestsEvent() > 20 && workload.getNoOfGuestsEvent() <=60){
			noOfRequiredShifts = 3;
		}
		if(workload.getNoOfGuestsEvent() > 60 && workload.getNoOfGuestsEvent() <=80){
			noOfRequiredShifts = 4;
		}

		if(workload.getNoOfGuestsEvent() > 80){
			noOfRequiredShifts = 4;
			for(int i = 0; i < (workload.getNoOfGuestsEvent()-80) / 20; i++){
				noOfRequiredShifts++;
			}
		}
		return noOfRequiredShifts;
	}

	public void createNewShift(){
		Shift newShift = new Shift(0);
		currentWorkload.addShift(newShift);
		currentShift = newShift;
	}

	public void enterShiftStartTime(String startTime){
		currentShift.setStartTime(startTime);
		currentWorkload.setModified(true);
		if(!currentShift.getTypeOfShift().equals(ShiftType.NOT_SET)){
			int estimatedShiftLength = currentShift.calculateEstShiftLength(currentShift.getStartTime(), currentShift.getTypeOfShift(), currentWorkload.getNoOfGuestsEvent()+currentWorkload.getNoOfGuestsReservations());
			currentShift.setEstimatedShiftLength(estimatedShiftLength);
		}
	}

	public void enterShiftLocation(String location){
		currentShift.setLocation(location);
		currentWorkload.setModified(true);
	}

	public ArrayList<Employee> enterShiftType(ShiftType typeOfShift){
		currentShift.setTypeOfShift(typeOfShift);
		currentWorkload.setModified(true);
		if(!currentShift.getStartTime().equalsIgnoreCase("-") && !currentShift.getStartTime().isEmpty()){
			int estimatedShiftLength = currentShift.calculateEstShiftLength(currentShift.getStartTime(), typeOfShift, currentWorkload.getNoOfGuestsEvent()+currentWorkload.getNoOfGuestsReservations());
			currentShift.setEstimatedShiftLength(estimatedShiftLength);
		}
		setAvailableEmployees(empCtr.getAvailableEmployees(currentWorkload.getDate(), currentShift.getTypeOfShift()));
		return getAvailableEmployees();
	}

	public ArrayList<Employee> getAvailableEmployeesForCurrentWorkload(){
		setAvailableEmployees(empCtr.getAvailableEmployees(currentWorkload.getDate(), currentShift.getTypeOfShift()));
		return getAvailableEmployees();
	}

	public void addEmployeeToShift(Employee employee){
		currentShift.setEmployee(employee);
		currentWorkload.setModified(true);
	}

	public void removeEmployeeFromShift(Employee employee){
		currentShift.setEmployee(null);
		currentWorkload.setModified(true);
	}

	public void saveWorkloadShifts() throws Exception{
		ShiftDB shiftDb = new ShiftDB();
		for(Workload workload: monthWorkloads){
			if(workload.getModified() == true){
				shiftDb.deleteAllShiftsFromWorkload(workload);
				for(Shift shift: workload.getShifts()){
					try {
						shiftDb.insertShift(shift, workload.getDate());
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
				}
				workload.setModified(false);
			}
		}
	}

	public ArrayList<Workload> getCurrentWeekWorkloads(){
		ArrayList<Workload> currentWeekWorkloads = new ArrayList<>();
		LocalDate todaysDate = LocalDate.now();
		int todaysMonth = LocalDate.now().getMonthValue();
		int weekDayValue = todaysDate.getDayOfWeek().getValue();
		LocalDate mondayDate = todaysDate.plusDays(1 - weekDayValue);
		LocalDate tempDate = mondayDate;
		for(int i=0; i<7; i++){
			if(todaysMonth == tempDate.getMonthValue()){
				currentWeekWorkloads.add(monthWorkloads.get(tempDate.getDayOfMonth() - 1));
			}
			tempDate = tempDate.plusDays(1);
		}
		return currentWeekWorkloads;
	}

	public ArrayList<Workload> updateCurrentViewWorkload(Workload workload){
		ArrayList<Workload> currentWeekWorkloads = new ArrayList<>();
		LocalDate currentViewDate = workload.getDate();
		int Month = LocalDate.now().getMonthValue();
		int weekDayValue = currentViewDate.getDayOfWeek().getValue();
		LocalDate mondayDate = currentViewDate.plusDays(1 - weekDayValue);
		LocalDate tempDate = mondayDate;
		for(int i=0; i<7; i++){
			if(Month == tempDate.getMonthValue()){
				currentWeekWorkloads.add(monthWorkloads.get(tempDate.getDayOfMonth() - 1));
				tempDate = tempDate.plusDays(1);
			}
		}
		return currentWeekWorkloads;
	}

	public ArrayList<Workload> getPreviousWeekWorkloads(ArrayList<Workload> currentWeek){
		int todaysMonth = currentWeek.get(0).getDate().getMonthValue();
		int firstDateWeekDayValue = currentWeek.get(0).getDate().getDayOfWeek().getValue();
		LocalDate mondayDate = currentWeek.get(0).getDate().plusDays(1 - firstDateWeekDayValue);
		LocalDate previousWeekMondayDate = mondayDate.plusDays(-7);
		LocalDate tempDate = previousWeekMondayDate;
		ArrayList<Workload> newWeek = new ArrayList<>();
		for(int i=0; i<7; i++){
			if(todaysMonth == tempDate.getMonthValue()){
				newWeek.add(monthWorkloads.get(tempDate.getDayOfMonth() - 1));
			}
			tempDate = tempDate.plusDays(1);
		}
		return newWeek;
	}

	public ArrayList<Workload> getNextWeekWorkloads(ArrayList<Workload> currentWeek){
		int todaysMonth = currentWeek.get(0).getDate().getMonthValue();
		int firstDateWeekDayValue = currentWeek.get(0).getDate().getDayOfWeek().getValue();
		LocalDate mondayDate = currentWeek.get(0).getDate().plusDays(1 - firstDateWeekDayValue);
		LocalDate nextWeekMondayDate = mondayDate.plusDays(7);
		LocalDate tempDate = nextWeekMondayDate;
		ArrayList<Workload> newWeek = new ArrayList<>();
		for(int i=0; i<7; i++){
			if(todaysMonth == tempDate.getMonthValue()){
				newWeek.add(monthWorkloads.get(tempDate.getDayOfMonth() - 1));
			}
			tempDate = tempDate.plusDays(1);
		}
		return newWeek;
	}



	public static void main(String [] args){
		ScheduleController test = new ScheduleController();
		test.getSchedule();
		System.out.println(test.getCurrentWeekWorkloads());
	}


}



