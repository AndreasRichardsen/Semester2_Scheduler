package db;

import java.util.EnumSet;

import model.WeekDay;

public interface IFDBWeekDaysPreferredOff {
	
	public EnumSet<WeekDay> getWeekDaysPrefferedOffByEmployeeId(int employeeId, boolean retrieveAssociation);
	public int insertWeekDaysPreferredOffForEmployee(int employeeId, WeekDay weekDay) throws Exception;
	public int deleteWeekDaysPreferredOffForEmployee(int employeeId, WeekDay weekDay);
	public int deleteAllWeekDaysPreferredOffForEmployee(int employeeId) throws Exception;

}
