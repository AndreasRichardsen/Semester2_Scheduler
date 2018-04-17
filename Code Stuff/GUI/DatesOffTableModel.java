package gui;

import java.time.LocalDate;
import javax.swing.table.DefaultTableModel;

import model.Employee;

@SuppressWarnings("serial")
public class DatesOffTableModel extends DefaultTableModel{

	private static String[] columnNames = {"Dates"};
	
	public DatesOffTableModel(Employee employee) {
		super(columnNames,0);
		for(LocalDate date: employee.getDatesUnavailable()){
			addRow(new Object[]{date});
		}
	}	

	

	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
	
}
