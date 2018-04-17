package gui;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import model.Employee;

public class EmployeeTableModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6489258611503475525L;
	private static String[] columnNames = {"First name", "Last Names", "Job Position", "Object"};
	
	public EmployeeTableModel(){
		super(columnNames, 10);	
		
	}
	
	public EmployeeTableModel(ArrayList<Employee> employees){
		super(columnNames, 0);	
		for(Employee employee: employees){
			addRow(employee);
		}
		
		
	}
	
	public void rebuildEmpTableModel(ArrayList<Employee> employees){
		if (getRowCount() > 0) {
		    for (int i = getRowCount() - 1; i > -1; i--) {
		        removeRow(i);
		    }
		}
		for(Employee employee: employees){
			addRow(employee);
		}
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
	public void addRow(Employee emp){
		Object[] row = new Object[4];
		row[0] = emp.getFirstName();
		row[1] = emp.getLastName();
		row[2] = emp.getTypeOfEmployee();
		row[3] = emp;
		addRow(row);
	}

}
