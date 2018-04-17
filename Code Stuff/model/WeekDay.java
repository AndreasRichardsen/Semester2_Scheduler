package model;

public enum WeekDay implements Comparable<WeekDay> {

	MONDAY, TUESDAY, WEDNESDAY,
	THURSDAY, FRIDAY, SATURDAY, SUNDAY;

	public static WeekDay stringToEnum(String day){
		if(day.equalsIgnoreCase("Monday")){
			return WeekDay.MONDAY;
		}
		else if(day.equalsIgnoreCase("Tuesday")){
			return WeekDay.TUESDAY;
		}
		else if(day.equalsIgnoreCase("Wednesday")){
			return WeekDay.WEDNESDAY;
		}
		else if(day.equalsIgnoreCase("Thursday")){
			return WeekDay.THURSDAY;
		}
		else if(day.equalsIgnoreCase("Friday")){
			return WeekDay.FRIDAY;
		}
		else if(day.equalsIgnoreCase("Saturday")){
			return WeekDay.SATURDAY;
		}
		else if(day.equalsIgnoreCase("Sunday")){
			return WeekDay.SUNDAY;
		}
		else{
			return null;
		}
	}




}
