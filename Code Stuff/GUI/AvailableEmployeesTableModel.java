package gui;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import controller.ScheduleController;
import model.Employee;
import model.Shift;
import model.Workload;

@SuppressWarnings("serial")
public class AvailableEmployeesTableModel extends DefaultTableModel {

	private static String[] columnNames = {"Name", "Hours Worked", "Est. Scheduled Hours", "Est. Hours Needed", "Object placeholder"};
	private ScheduleController schedCtr = ScheduleController.getInstance();
	
	public AvailableEmployeesTableModel(){
		super(columnNames, 10);	

	}

	public AvailableEmployeesTableModel(ArrayList<Employee> employees, LocalDate date){
		super(columnNames, 0);	
		for(Employee employee: employees){
			addRow(employee, date);
		}

	}

	public void rebuildAvailableEmpTableModel(ArrayList<Employee> employees, LocalDate date){
		if (getRowCount() > 0) {
			for (int i = getRowCount() - 1; i > -1; i--) {
				removeRow(i);
			}
		}
		setColumnIdentifiers(columnNames);
		for(Employee employee: employees){
			addRow(employee, date);
		}
	}


	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public Employee getEmployee(int rowIndex){
		Employee emp = null;
		if(rowIndex != -1){
			emp = (Employee) getValueAt(rowIndex, 4);
		}
		return emp;
	}

	public void addRow(Employee emp, LocalDate date){
		Object[] row = new Object[5];
		row[0] = emp.getFirstName() + " " + emp.getLastName();
		row[1] = emp.getHoursWorkedThisMonth();
		row[2] = getMonthsFutureShiftsForEmployee(emp);
		row[3] = emp.getMinWorkingHours() - (int)row[1] - (int)row[2] + "         (Min: " + emp.getMinWorkingHours() + ")";
		row[4] = emp;
		addRow(row);
	}
	
	public int getMonthsFutureShiftsForEmployee(Employee emp){
		int scheduledHours = 0;
		for(Workload workload: schedCtr.getMonthWorkloads()){
			if(workload.getDate().getDayOfMonth() >= LocalDate.now().getDayOfMonth()){
				for(Shift shift: workload.getShifts()){
					if(emp.equals(shift.getEmployee())){
						scheduledHours += shift.getEstimatedShitLength();
					}
				}
			}
		}
		return scheduledHours;
	}
	
	
}
