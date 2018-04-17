package gui;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.table.DefaultTableModel;

import model.Shift;
import model.Workload;

@SuppressWarnings("serial")
public class WorkloadTableModel extends DefaultTableModel {

	private ArrayList<Workload> currentWeek;

	public WorkloadTableModel(ArrayList<Workload> workloads){
		super(1,0);
		setCurrentWeek(workloads);
		for(Workload workload: currentWeek){
			addWorkload(workload);
		}
	}

	public ArrayList<Workload> getCurrentWeek() {
		return currentWeek;
	}

	public void setCurrentWeek(ArrayList<Workload> currentWeek) {
		this.currentWeek = currentWeek;
	}

	public void updateCurrentWeek(){
		setColumnCount(0);
		for(Workload workload: currentWeek){
			addWorkload(workload);
		}
	}


	public String columnName(Workload workload){
		LocalDate date = workload.getDate();
		String columnName ="";
		columnName += date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
		columnName += " " + String.valueOf(date.getDayOfMonth());
		columnName += "/" + String.valueOf(date.getMonthValue());
		return columnName;
	}

	public void addWorkload(Workload workload){
		addColumn(columnName(workload));
		addShifts(workload);
	}

	public String displayShift(Shift shift){
		String displayShift = "";
		try{
			displayShift += shift.getTypeOfShift().toString().substring(0, 1);
			displayShift += shift.getNumber();
			displayShift += " " + shift.getStartTime();
			displayShift += " " + shift.getEmployee().getLastName();
		}
		catch(Exception e){
			displayShift += "--";
		}
		return displayShift;
	}

	public void addShifts(Workload workload){
		int i = 0;
		if(getRowCount() <= workload.getShifts().size()){
			//for(int j=0; j <= workload.getShifts().size() - getRowCount(); j++){
			while(getRowCount() < workload.getShifts().size()+1){
				addRow(new Object[]{});
			}
		}
		for(Shift shift: workload.getShifts()){
			setValueAt(displayShift(shift), i, findColumn(columnName(workload)));
			i++;
		}
	}

	public void removeShifts(Workload workload){
		for(int i=0; i < workload.getShifts().size(); i++){
			setValueAt(null, i, findColumn(columnName(workload)));
		}
	}

	public void updateShifts(Workload workload){
		removeShifts(workload);
		addShifts(workload);
	}

	//	public Workload getWorkload(int columnIndex){
	//		String columnName = getColumnName(columnIndex);
	//		Workload foundWorkload = null;
	//		for(Workload workload: currentWeek){
	//			if(columnName.substring(0,3).equalsIgnoreCase(workload.getDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH))){
	//				foundWorkload = workload;
	//			}
	//		}
	//
	//		return foundWorkload;
	//	}

	public Workload getWorkload(int columnIndex){
		return currentWeek.get(columnIndex);
	}



	//	public Shift getShift(int rowindex, String columnName){
	//		int columnIndex = findColumn(columnName);
	//		Workload workload = getWorkload(columnIndex);
	//		Shift foundShift = null;
	//		String shiftString = "";
	//		try{
	//			shiftString = (String) getValueAt(rowindex, columnIndex);
	//		}
	//		catch(Exception e){
	//		}
	//		ShiftType shiftType = null;
	//		if(shiftString.substring(0, 1).equalsIgnoreCase("d")){
	//			shiftType = ShiftType.DISHWASHER;
	//		}
	//		else if(shiftString.substring(0, 1).equalsIgnoreCase("w")){
	//			shiftType = ShiftType.WAITER;
	//		}
	//		int shiftNumber = Integer.valueOf(shiftString.substring(1, 2));
	//
	//		try{
	//			for(Shift shift: workload.getShifts()){
	//				if(shiftType.equals(shift.getTypeOfShift()) && shiftNumber == shift.getNumber()){
	//					foundShift = shift;
	//				}
	//			}
	//		}
	//		catch(Exception e1){
	//		}
	//		if(foundShift == null && !shiftString.isEmpty()){
	//			foundShift = workload.getShift(rowindex);
	//		}
	//
	//		return foundShift;
	//	}

	public Shift getShift(int rowIndex, int columnIndex){
		Shift foundShift = null;
		if(columnIndex != -1 || rowIndex!= -1){
			Workload workload = getWorkload(columnIndex);
			foundShift = workload.getShift(rowIndex);
		}

		return foundShift;
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}





}