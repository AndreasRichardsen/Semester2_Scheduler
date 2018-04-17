package gui;

import javax.swing.table.DefaultTableModel;

import controller.ScheduleController;
import model.Workload;

@SuppressWarnings("serial")
public class DateInformationTableModel extends DefaultTableModel{

	private static String[] columnNames = {" ", "No. Of Guests", "Est. Required Shifts"};
	private ScheduleController schedCtr;
	
	public DateInformationTableModel(Workload workload) {
		super(columnNames, 2);
		schedCtr = ScheduleController.getInstance();
		setValueAt("Guest Reservations", 0, 0);
		setValueAt("Event size", 1, 0);
		setValueAt(workload.getNoOfGuestsReservations(), 0, 1);
		setValueAt(workload.getNoOfGuestsEvent(), 1, 1);
		int noOfMainRoomWaiters = schedCtr.calculateNoOfMainRoomWaiterShifts(workload);
		int noOfBanquetRoomWaiters = schedCtr.calculateNoOfBanquetRoomWaiterShifts(workload);
		int noOfDishwasherShifts = schedCtr.calculateNoOfDishwasherShifts(workload);
		setValueAt(noOfMainRoomWaiters + " W " + noOfDishwasherShifts + " D", 0, 2);
		setValueAt(noOfBanquetRoomWaiters + " W", 1, 2);
		
		}
	
	public void rebuildDateInformationModel(Workload workload){
		if (getRowCount() > 0) {
			for (int i = getRowCount() - 1; i > -1; i--) {
				removeRow(i);
			}
		}
		addRow(new Object[3]);
		addRow(new Object[3]);
		setColumnIdentifiers(columnNames);
		schedCtr = ScheduleController.getInstance();
		setValueAt("Guest Reservations", 0, 0);
		setValueAt("Event size", 1, 0);
		setValueAt(workload.getNoOfGuestsReservations(), 0, 1);
		setValueAt(workload.getNoOfGuestsEvent(), 1, 1);
		int noOfMainRoomWaiters = schedCtr.calculateNoOfMainRoomWaiterShifts(workload);
		int noOfBanquetRoomWaiters = schedCtr.calculateNoOfBanquetRoomWaiterShifts(workload);
		int noOfDishwasherShifts = schedCtr.calculateNoOfDishwasherShifts(workload);
		setValueAt(noOfMainRoomWaiters + " W " + noOfDishwasherShifts + " D", 0, 2);
		setValueAt(noOfBanquetRoomWaiters + " W", 1, 2);
	}
	
	public DateInformationTableModel() {
		super();
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
	




}
