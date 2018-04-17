package model;

public enum EmployeeType {
	DISHWASHER,
	WAITER;
	
	public static EmployeeType selectEmployeeType(String employeeType){
		if(employeeType.equalsIgnoreCase("DISHWASHER")){
			return EmployeeType.DISHWASHER;
		}
		else if(employeeType.equalsIgnoreCase("WAITER")){
			return EmployeeType.WAITER;
		}
		else{
			return null;
		}
	}
}

