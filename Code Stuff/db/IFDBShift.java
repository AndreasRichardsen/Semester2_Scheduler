package db;

import java.time.LocalDate;
import java.util.ArrayList;

import model.Shift;	

public interface IFDBShift{
	
	public ArrayList<Shift> getAllShiftsByDate(LocalDate date, boolean retrieveAssociation);
	public int insertShift(Shift Shift, LocalDate workLoadDate) throws Exception;
	public int updateShift(Shift Shift, LocalDate workLoadDate);
	public int deleteShift(Shift Shift, LocalDate workLoadDate);

}
