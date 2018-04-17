package model;

public enum ShiftType {
	NOT_SET,
	DISHWASHER,
	WAITER;
	
	public static ShiftType selectShiftType(String shiftType){
		if(shiftType.equalsIgnoreCase("DISHWASHER")){
			return ShiftType.DISHWASHER;
		}
		else if(shiftType.equalsIgnoreCase("WAITER")){
			return ShiftType.WAITER;
		}
		else if(shiftType.equalsIgnoreCase("NOT_SET")){
			return ShiftType.NOT_SET;
		}
		else{
			return null;
		}
	}
}

