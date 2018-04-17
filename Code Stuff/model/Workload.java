package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The workload class is designed to hold information regarding
 * a date's number of reservations and information about events
 * and then calculate an estimated number of required shifts.
 * 
 * @author Sangey Lama
 * @version 1.0 
 */
public class Workload {

	private LocalDate date;
	private int noOfGuestsReservations;
	private int noOfGuestsEvent;
	private List<Shift> shifts;
	private boolean modified;

	public Workload() {
		super();
		this.shifts = new ArrayList<>();
		this.modified = false;
	}

	public Workload(LocalDate date, int noOfGuestsReservations, int noOfGuestsEvent) {
		super();
		this.date = date;
		this.noOfGuestsReservations = noOfGuestsReservations;
		this.noOfGuestsEvent = noOfGuestsEvent;
		this.shifts = new ArrayList<>();
		this.modified = false;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getNoOfGuestsReservations() {
		return noOfGuestsReservations;
	}

	public void setNoOfGuestsReservations(int noOfGuestsReservations) {
		this.noOfGuestsReservations = noOfGuestsReservations;
	}

	public int getNoOfGuestsEvent() {
		return noOfGuestsEvent;
	}

	public void setNoOfGuestsEvent(int noOfGuestsEvent) {
		this.noOfGuestsEvent = noOfGuestsEvent;
	}



	/* More than 50 guests need at least 2 shifts
	 * If event = true && total no of guests > 40, +1 shift (for banquet room)
	 * More than 115 guest && no event, required 3 shifts
	 * More than 210 guests total need 4 shifts	
	 */
	public int calculateNoOfDishwasherShifts(){
		int noOfRequiredShifts = 1;
		if(noOfGuestsReservations >= 50){
			noOfRequiredShifts = 2;
		}
		if(noOfGuestsEvent > 0 && (noOfGuestsEvent+noOfGuestsReservations) >= 40){
			noOfRequiredShifts++;
		}
		if(noOfGuestsReservations > 115 && noOfGuestsEvent == 0){
			noOfRequiredShifts = 3;
		}
		if((noOfGuestsEvent+noOfGuestsReservations) >= 210){
			noOfRequiredShifts = 4;
		}
		return noOfRequiredShifts;
	}

	/* Minimum no of waiters shifts = 1 (for < 10 guests)
	 * 
	 * 80+ guests = busy 
	 * <80 guests = not busy
	 * 
	 * logic for main restaurant area
	 *  if < 10, 1 shift
	 *  if >10 <=20, 2 shifts
	 *  if >20 <=60, 3 shifts
	 *  if >60 <=80, 4 shifts
	 *  extra shifts after 4, 1 shift per 20 additional guests
	 */
	public int calculateNoOfMainRoomWaiterShifts(){
		int noOfRequiredShifts = 1;
		if(noOfGuestsReservations > 10 && noOfGuestsReservations <=20){
			noOfRequiredShifts = 2;
		}
		if(noOfGuestsReservations > 20 && noOfGuestsReservations <=60){
			noOfRequiredShifts = 3;
		}
		if(noOfGuestsReservations > 60 && noOfGuestsReservations <=80){
			noOfRequiredShifts = 4;
		}
		if(noOfGuestsReservations > 80){
			noOfRequiredShifts = 4;
			for(int i = 0; i < (noOfGuestsReservations-80) / 20; i++){
				noOfRequiredShifts++;
			}
		}
		return noOfRequiredShifts;
	}

	/*
	 *  Minimum no of waiters shifts = 1 (for < 10 guests)
	 *  
	 *  80+ guests = busy 
	 *  <80 guests = not busy
	 *  
	 *  logic for banquet room
	 *  if < 10, 1 shift
	 *  if >10 <=20, 2 shifts
	 *  if >20 <=60, 3 shifts
	 *  if >60 <=80, 4 shifts
	 *  extra shifts after 4, 1 shift per 20 additional guests
	 */

	public int calculateNoOfBanquetRoomWaiterShifts(){
		int noOfRequiredShifts = 1;
		if(noOfGuestsEvent > 10 && noOfGuestsEvent <= 20){
			noOfRequiredShifts = 2;
		}
		if(noOfGuestsEvent > 20 && noOfGuestsEvent <=60){
			noOfRequiredShifts = 3;
		}
		if(noOfGuestsEvent > 60 && noOfGuestsEvent <=80){
			noOfRequiredShifts = 4;
		}

		if(noOfGuestsEvent > 80){
			noOfRequiredShifts = 4;
			for(int i = 0; i < (noOfGuestsEvent-80) / 20; i++){
				noOfRequiredShifts++;
			}
		}
		return noOfRequiredShifts;
	}

	public static void main(String [] args){
		Workload test = new Workload(LocalDate.now(), 66, 35+90);
		System.out.print("Dishwasher shifts required " + test.calculateNoOfDishwasherShifts() + "\n");
		System.out.print("Waiter shifts required for Main Room " + test.calculateNoOfMainRoomWaiterShifts() + "\n");
		System.out.print("Waiter shifts required for Banquet Room " + test.calculateNoOfBanquetRoomWaiterShifts() + "\n");

	}

	public List<Shift> getShifts(){
		return shifts;
	}

	public void addShift(Shift shift){
		shifts.add(shift);
	}

	public void removeShift(Shift shift){
		shifts.remove(shift);
	}

	public Shift getShift(int index){
		if(shifts.size() > 0 && index != -1 && index < shifts.size())
			return shifts.get(index);
		else
			return null;
	}

	public boolean getModified(){
		return modified;
	}

	public void setModified(boolean isModified){
		this.modified = isModified;
	}

	@Override
	public String toString() {
		return "\nWorkload [date=" + date + ", noOfGuestsReservations=" + noOfGuestsReservations
				+ ", noOfGuestsEvent=" + noOfGuestsEvent + ", \n\tshifts=" + shifts + "]";
	}
}
