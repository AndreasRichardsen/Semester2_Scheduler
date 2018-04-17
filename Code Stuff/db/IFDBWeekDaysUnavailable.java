package db;
import java.util.EnumSet;

import model.WeekDay;

public interface IFDBWeekDaysUnavailable {
		
		public EnumSet<WeekDay> getWeekDaysUnavailableByEmployeeId(int employeeId, boolean retrieveAssociation);
		public int insertWeekDaysUnavailableForEmployee(int employeeId, WeekDay weekDay) throws Exception;
		public int deleteWeekDaysUnavailableForEmployee(int employeeId, WeekDay WeekDay);
		public int deleteAllWeekDaysUnavailableForEmployee(int employeeId) throws Exception;
	}

