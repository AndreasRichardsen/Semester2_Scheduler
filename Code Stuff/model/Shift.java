package model;

import model.Employee;

public class Shift {

	private int number;
	private String startTime;
	private String location;
	private int estimatedShiftLength;
	private ShiftType typeOfShift;
	private Employee employee;

	public Shift() {
		super();
	}

	public Shift(int sNumber){
		super();
		this.number = sNumber;
		this.startTime = "";
		this.location = "";
		this.estimatedShiftLength = 0;
		this.typeOfShift = ShiftType.NOT_SET;
		this.employee = null;
		
	}

	public Shift(int number, String startTime, String location, int estimatedShiftLength, ShiftType typeOfShift, Employee employee) {

		super();
		this.number = number;
		this.startTime = startTime;
		this.location = location;
		this.estimatedShiftLength = estimatedShiftLength;
		this.typeOfShift = typeOfShift;
		this.employee = employee;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int 	number) {
		this.number = number;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getEstimatedShitLength() {
		return estimatedShiftLength;
	}

	public void setEstimatedShiftLength(int estimatedShiftLength) {
		this.estimatedShiftLength = estimatedShiftLength;
	}

	public ShiftType  getTypeOfShift() {
		return typeOfShift;
	}

	public void setTypeOfShift(ShiftType typeOfShift) {
		this.typeOfShift = typeOfShift;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public int calculateEstShiftLength(String startTime, ShiftType typeOfShift, int noOfGuests){
		int defaultShiftLength = 8;
		
		if(typeOfShift == ShiftType.DISHWASHER){
			if(startTime.equalsIgnoreCase("17:00")){
				int estimatedShiftLength = 8;
				if(noOfGuests >= 40 && noOfGuests <80){
					estimatedShiftLength += 2;
				}
				if(noOfGuests >= 80){
					estimatedShiftLength +=4;
				}
				return estimatedShiftLength;
			}
			if(startTime.equalsIgnoreCase("20:00")){
				int estimatedShiftLength = 5;
				if(noOfGuests >= 40 && noOfGuests <80){
					estimatedShiftLength += 2;
				}
				if(noOfGuests >= 80){
					estimatedShiftLength +=4;
				}
				return estimatedShiftLength;
			}	
		}
		
		if(typeOfShift == ShiftType.WAITER){
			if(startTime.equalsIgnoreCase("10:00")){
				return 8;
			}
			if(startTime.equalsIgnoreCase("16:00")){
				return 8;
			}
			if(startTime.equalsIgnoreCase("18:00")){
				return 6;
			}
			if(startTime.equalsIgnoreCase("20:00")){
				return 4;
			}
			
		}
		
		return defaultShiftLength;		
	}

	@Override
	public String toString() {
		return "Shift [number=" + number + ", startTime=" + startTime + ", location=" + location
				+ ", estimatedShiftLength=" + estimatedShiftLength + ", typeOfShift=" + typeOfShift + ", \n\t\temployee="
				+ employee + "]";
	}


	


}
