package model;


import java.time.LocalDate;

public class HoursWorked {

	private LocalDate date;
	private double noOfHours;
	
	public HoursWorked() {
		super();
	}

	public HoursWorked(LocalDate date, double noOfHours) {
		super();
		this.date = date;
		this.noOfHours = noOfHours;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getNoOfHours() {
		return noOfHours;
	}

	public void setNoOfHours(double noOfHours) {
		this.noOfHours = noOfHours;
	}

	@Override
	public String toString() {
		return "HoursWorked [date=" + date + ", noOfHours=" + noOfHours + "]";
	}
	
}
