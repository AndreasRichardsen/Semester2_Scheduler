package gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import controller.ScheduleController;
import model.Employee;
import model.Shift;
import model.ShiftType;
import model.Workload;


public class ManageScheduleGUI {
	public static void main(String [] args){
		ManageScheduleGUI test = new ManageScheduleGUI();
		test.buildWindow();

	}

	final private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE d MMM yyyy");
	private ScheduleController schedCtr;
	private JLabel date;
	private JLabel assignedEmployees;
	private JComboBox<String> startTime;
	private JComboBox<String> location;
	private JComboBox<String> shiftType;
	private JLabel estimatedShiftLength;
	private JTable dateInformationTable;
	private JLabel dateInformation;
	private JButton newShiftButton;
	private JButton removeShiftButton;
	private JScrollPane dateScrollPane;
	private JLabel shiftInformation;
	private JLabel employees;
	private JLabel startTimeTitle;
	private JLabel assignedEmployeesTitle;
	private JLabel locationTitle;
	private JLabel shiftTypeTitle;
	private JLabel estimatedShiftLenghTitle;
	private JButton addEmployee;
	private JButton removeEmployee;
	private WorkloadTableModel workloadTableModel;
	private JTable workloadTable;
	private workloadTableListSelectionListener wlTableSelectionlistener;
	private ComboBoxStartTimeActionListener cbStartTimeAL;
	private ComboBoxLocationActionListener cbLocationAL;
	private ComboBoxShiftTypeActionListener cbShiftTypeAL;
	private ArrayList<Workload> currentViewWorkloads;
	private JScrollPane employeesAvailableSP;
	private AvailableEmployeesTableModel availableEmpModel;
	private AvailableEmployeeTableListSelectionListener avEmpTableSelectionListener;
	private JLabel monthLabel;
	private JTable availableEmployees;
	private DateInformationTableModel dateInfoModel;
	private JLabel dialogBox;

	public ManageScheduleGUI(){
		schedCtr = ScheduleController.getInstance();
		schedCtr.getSchedule();
		currentViewWorkloads = schedCtr.getCurrentWeekWorkloads();
		cbStartTimeAL = new ComboBoxStartTimeActionListener();
		cbLocationAL = new ComboBoxLocationActionListener();
		cbShiftTypeAL = new ComboBoxShiftTypeActionListener();
		avEmpTableSelectionListener = new AvailableEmployeeTableListSelectionListener();

	}

	public void buildWindow(){
		JFrame frame = new JFrame("Fusion Reataurant");
		frame.setSize(1400, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		JPanel panel = new JPanel(new GridBagLayout());
		frame.getContentPane().add(panel, BorderLayout.WEST);
		GridBagConstraints c = new GridBagConstraints();
		c.weighty=1;

		c.insets= new Insets(0,20,0,0);
		monthLabel = new JLabel(LocalDate.now().getMonth().toString() + " " + LocalDate.now().getYear());
		monthLabel.setFont(new Font("", Font.PLAIN, 20));
		c.gridwidth = 5;
		c.gridx=1;
		c.gridy=0;
		panel.add(monthLabel, c);
		c.gridwidth = 1;
		dateInformation = new JLabel("Date Information");
		dateInformation.setFont(new Font("", Font.PLAIN, 20));
		c.gridx=7;
		c.gridy=0;
		panel.add(dateInformation, c);
		JButton left = new JButton("<");

		c.gridwidth = 1;
		c.gridx=0;
		c.gridy=1;
		panel.add(left, c);



		this.workloadTableModel = new WorkloadTableModel(currentViewWorkloads);
		workloadTable = new JTable(workloadTableModel);
		workloadTable.setCellSelectionEnabled(true);
		workloadTable.getTableHeader().setReorderingAllowed(false);
		workloadTable.getTableHeader().setResizingAllowed(false);
		workloadTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getWorkloadSelectionListener();
		c.gridwidth = 5;
		c.gridheight=4;
		c.gridx=1;
		c.gridy=1;
		JScrollPane sp = new JScrollPane(workloadTable);
		sp.setPreferredSize(new Dimension(700, 300));
		panel.add(sp, c);





		/*JTable table = new JTable(5,7);
		c.gridwidth = 3;
		c.gridheight = 4;
		c.gridx=1;
		c.gridy=1;
		panel.add(table, c);*/







		JButton right = new JButton(">");
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx=6;
		c.gridy=1;
		panel.add(right, c);
		date = new JLabel("-");
		c.gridx=7;
		c.gridy=1;
		panel.add(date, c);

		left.addActionListener(e->{
			try{
				ArrayList<Workload> previousWeekWorkloads = schedCtr.getPreviousWeekWorkloads(currentViewWorkloads);
				if(previousWeekWorkloads.size()>0){
					currentViewWorkloads = previousWeekWorkloads;
					workloadTableModel.setCurrentWeek(currentViewWorkloads);
					workloadTableModel.updateCurrentWeek();
					this.workloadTable.setColumnSelectionInterval(0,0);
					this.workloadTable.setRowSelectionInterval(0,0);
				}
			}
			catch(Exception e1){

			}
		});

		right.addActionListener(e->{

			try{
				ArrayList<Workload> nextWeekWorkloads = schedCtr.getNextWeekWorkloads(currentViewWorkloads);
				if(nextWeekWorkloads.size()>0){
					currentViewWorkloads = nextWeekWorkloads;
					workloadTableModel.setCurrentWeek(currentViewWorkloads);
					workloadTableModel.updateCurrentWeek();
					this.workloadTable.setColumnSelectionInterval(0,0);
					this.workloadTable.setRowSelectionInterval(0,0);
				}
			}
			catch(Exception e1){

			}
		});

		dateInfoModel = new DateInformationTableModel();
		dateInformationTable = new JTable(dateInfoModel);
		c.gridwidth = 2;
		c.gridheight=2;
		c.gridx=7;
		c.gridy=2;
		dateScrollPane = new JScrollPane(dateInformationTable);
		dateScrollPane.setPreferredSize(new Dimension(400, 200));
		panel.add(dateScrollPane, c);



		/*
		JTable table2 = new JTable(3,3);
		c.gridwidth = 2;
		c.gridheight = 2;
		c.gridx=5;
		c.gridy=2;
		panel.add(table2, c);*/




		newShiftButton = new JButton("NewShift");
		newShiftButton.addActionListener(e->{
			schedCtr.createNewShift();
			displayShiftInformation(schedCtr.getCurrentShift());
			enableShiftInformation(true);
			int selectedColumn = workloadTable.getSelectedColumn();
			int selectedRow = (schedCtr.getCurrentWorkload().getShifts().size()-1);
			workloadTableModel.updateShifts(schedCtr.getCurrentWorkload());
			this.workloadTable.setColumnSelectionInterval(selectedColumn, selectedColumn);
			this.workloadTable.setRowSelectionInterval(selectedRow, selectedRow);
			if(schedCtr.getCurrentShift().getTypeOfShift().equals(ShiftType.NOT_SET)){
				enableAvailableEmployeeInformation(false);
				availableEmpModel.setColumnCount(0);
			}


		});
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx=7;
		c.gridy=4;
		panel.add(newShiftButton, c);
		removeShiftButton = new JButton("Remove Shift");
		removeShiftButton.addActionListener(e->{
			Workload currentWorkload = schedCtr.getCurrentWorkload();
			Shift currentShift = schedCtr.getCurrentShift();
			currentWorkload.removeShift(currentShift);
			currentWorkload.setModified(true);
			int selectedColumn = workloadTable.getSelectedColumn();
			int selectedRow = (schedCtr.getCurrentWorkload().getShifts().size() - 1);
			if(selectedRow == -1){
				selectedRow = 0;
				workloadTable.setValueAt(null, selectedRow, selectedColumn);
			}
			workloadTableModel.updateShifts(schedCtr.getCurrentWorkload());
			this.workloadTable.setColumnSelectionInterval(selectedColumn, selectedColumn);
			this.workloadTable.setRowSelectionInterval(selectedRow, selectedRow);
			workloadTable.setValueAt(null, selectedRow+1, selectedColumn);
			try{
				if(schedCtr.getCurrentShift().getTypeOfShift().equals(ShiftType.NOT_SET) || schedCtr.getCurrentWorkload().getShifts().size() > 0){
					enableAvailableEmployeeInformation(false);
					availableEmpModel.setColumnCount(0);
				}
			}
			catch(Exception e1){
			}


		});
		c.gridx=8;
		c.gridy=4;
		panel.add(removeShiftButton, c);
		JSeparator sep = new JSeparator();
		sep.setPreferredSize(new Dimension(20,30));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 20;
		c.gridx=0;
		c.gridy=5;
		panel.add(sep, c);
		c.gridwidth = 2;
		shiftInformation = new JLabel("Shift Information");
		shiftInformation.setFont(new Font("", Font.PLAIN, 20));
		c.gridx=0;
		c.gridy=6;
		panel.add(shiftInformation, c);
		c.gridwidth=1;

		employees = new JLabel("Employees");
		employees.setFont(new Font("", Font.PLAIN, 20));
		c.gridx=4;
		c.gridy=6;
		panel.add(employees, c);



		assignedEmployeesTitle = new JLabel("Assigned Employees:");
		c.gridx=0;
		c.gridy=7;
		panel.add(assignedEmployeesTitle, c);

		assignedEmployees = new JLabel("-");
		c.gridx=1;
		c.gridy=7;
		panel.add(assignedEmployees, c);

		startTimeTitle = new JLabel("Start Time:");
		c.gridx=0;
		c.gridy=8;
		panel.add(startTimeTitle, c);

		String[] choicesStartTime = {"-","10:00", "16:00", "17:00", "18:00","20:00"};
		startTime = new JComboBox<String>(choicesStartTime);
		startTime.addActionListener(cbStartTimeAL);
		c.gridx=1;
		c.gridy=8;
		startTime.setVisible(true);
		panel.add(startTime, c);

		locationTitle = new JLabel("Location:");
		c.gridx=0;
		c.gridy=9;
		panel.add(locationTitle, c);

		String[] choiceLocation = {"-","Restaurant","Banquet"};
		location = new JComboBox<String>(choiceLocation);
		location.addActionListener(cbLocationAL);
		c.gridx=1;
		c.gridy=9;
		startTime.setVisible(true);
		panel.add(location, c);

		shiftTypeTitle = new JLabel("Shift Type:");
		c.gridx=0;
		c.gridy=10;
		panel.add(shiftTypeTitle, c);

		String[] choiceShiftType = {"NOT_SET", "DISHWASHER", "WAITER"};
		shiftType = new JComboBox<String>(choiceShiftType);
		shiftType.addActionListener(cbShiftTypeAL);
		c.gridx=1;
		c.gridy=10;
		startTime.setVisible(true);
		panel.add(shiftType, c);

		estimatedShiftLenghTitle = new JLabel("Estimated Shift Length:");
		c.gridx=0;
		c.gridy=11;
		panel.add(estimatedShiftLenghTitle, c);

		estimatedShiftLength = new JLabel("-");
		c.gridx=1;
		c.gridy=11;
		panel.add(estimatedShiftLength, c);

		addEmployee = new JButton("< Add Employee");
		addEmployee.addActionListener(e-> {
			Employee employee = schedCtr.getCurrentEmployee();
			schedCtr.addEmployeeToShift(employee);
			workloadTableModel.updateShifts(schedCtr.getCurrentWorkload());
			displayShiftInformation(schedCtr.getCurrentShift());
			removeEmployee.setEnabled(true);
			new Thread(new DisplayAvailableEmployees()).start();

		});
		c.gridx=3;
		c.gridy=7;
		panel.add(addEmployee, c);

		removeEmployee = new JButton("Remove Employee >");
		removeEmployee.addActionListener(e->{
			schedCtr.removeEmployeeFromShift(schedCtr.getCurrentShift().getEmployee());
			workloadTableModel.updateShifts(schedCtr.getCurrentWorkload());
			displayShiftInformation(schedCtr.getCurrentShift());
			removeEmployee.setEnabled(false);
			new Thread(new DisplayAvailableEmployees()).start();
		});
		c.gridx=3;
		c.gridy=8;
		panel.add(removeEmployee, c);

		/*JTable employeesAvailable = new JTable(6, 4);
		employeesAvailable.setPrefferedSize(new Dimension(1000, 500));
		c.gridwidth = 5;
		c.gridheight = 6;
		c.gridx= 4;
		c.gridy = 7;
		panel.add(employeesAvailable, c);
		 */

		availableEmpModel = new AvailableEmployeesTableModel();
		availableEmployees = new JTable(availableEmpModel);
		availableEmployees.getColumnModel().removeColumn(availableEmployees.getColumnModel().getColumn(4));
		getAvailableEmployeeSelectionListener();
		c.gridwidth = 5;
		c.gridheight=7;
		c.gridx=4;
		c.gridy=7;
		employeesAvailableSP = new JScrollPane(availableEmployees);
		employeesAvailableSP.setPreferredSize(new Dimension(500, 200));
		panel.add(employeesAvailableSP, c);

		dialogBox = new JLabel("<<System Message>>");
		c.gridx=0;
		c.gridy=12;
		panel.add(dialogBox, c);

		JButton save = new JButton("Save");
		save.addActionListener(e->{
			String errorString ="Error saving shifts:";
			try {
				schedCtr.saveWorkloadShifts();
				JOptionPane.showMessageDialog(frame, "Workloads successful saved!", "Success", JOptionPane.PLAIN_MESSAGE);
			} catch (Exception e1) {
				e1.printStackTrace();
				errorString += " \n" + e1.getMessage();
				JOptionPane.showMessageDialog(frame, errorString, "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		c.gridwidth = 1;
		c.gridx=4;
		c.gridy = 12;
		panel.add(save, c);

		JButton cancel = new JButton("Cancel");

		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				Menu test1 = new Menu();
				test1.buildWindow();

			}
		});

		c.gridwidth = 1;
		c.gridx=5;
		c.gridy = 12;
		panel.add(cancel, c);

		enableDateInformation(false);
		enableShiftInformation(false);
		enableAvailableEmployeeInformation(false);
		addEmployee.setEnabled(false);
		removeEmployee.setEnabled(false);


		frame.setVisible(true);




	}

	public void refreshWorkloadTableModel(){
		WorkloadTableModel newWorkloadTableModel = new WorkloadTableModel(currentViewWorkloads);
		this.workloadTable.setModel(newWorkloadTableModel);
		getWorkloadSelectionListener();
	}

	public void updateAndReselectCell(){
		int selectedColumn = workloadTable.getSelectedColumn();
		int selectedRow = workloadTable.getSelectedRow();
		workloadTableModel.updateShifts(schedCtr.getCurrentWorkload());
		this.workloadTable.setColumnSelectionInterval(selectedColumn, selectedColumn);
		this.workloadTable.setRowSelectionInterval(selectedRow, selectedRow);
	}


	private void displayWorkloadInformation(Workload workload){

		monthLabel.setText(workload.getDate().getMonth().toString() + " " + workload.getDate().getYear());
		date.setText(workload.getDate().format(dtf));
		DateInformationTableModel dateInfoModel = new DateInformationTableModel(workload);
		dateInformationTable.setModel(dateInfoModel);

	}

	private void clearWorkloadInformation(){

		date.setText("-");
		dateInformationTable.setModel(new DateInformationTableModel());

	}

	private void displayShiftInformation(Shift shift){
		startTime.removeActionListener(cbStartTimeAL);
		location.removeActionListener(cbLocationAL);
		shiftType.removeActionListener(cbShiftTypeAL);

		try{
			assignedEmployees.setText(shift.getEmployee().getFirstName() + " " + shift.getEmployee().getLastName());
		}
		catch(Exception e){
			assignedEmployees.setText("-");
		}
		try{
			startTime.setSelectedItem(shift.getStartTime());
		}
		catch(Exception e){
			startTime.setSelectedItem("-");
		}
		try{
			location.setSelectedItem(shift.getLocation());
		}
		catch(Exception e){
			location.setSelectedItem("-");
		}
		try{
			shiftType.setSelectedItem(shift.getTypeOfShift().toString());
		}
		catch(Exception e){
			shiftType.setSelectedItem("NOT_SET");
		}
		try{
			estimatedShiftLength.setText(String.valueOf(shift.getEstimatedShitLength()) + " hours");
		}
		catch(Exception e){
			estimatedShiftLength.setText("-");
		}

		startTime.addActionListener(cbStartTimeAL);
		location.addActionListener(cbLocationAL);
		shiftType.addActionListener(cbShiftTypeAL);
	}

	private void clearShiftInformation(){
		startTime.removeActionListener(cbStartTimeAL);
		location.removeActionListener(cbLocationAL);
		shiftType.removeActionListener(cbShiftTypeAL);

		assignedEmployees.setText("-");
		startTime.setSelectedItem("-");
		location.setSelectedItem("-");
		shiftType.setSelectedItem("NOT_SET");
		estimatedShiftLength.setText("-");

		startTime.addActionListener(cbStartTimeAL);
		location.addActionListener(cbLocationAL);
		shiftType.addActionListener(cbShiftTypeAL);
	}

	public void enableDateInformation(boolean enabled){
		dateInformation.setEnabled(enabled);
		date.setEnabled(enabled);
		dateInformationTable.setEnabled(enabled);
		dateInformationTable.getTableHeader().setEnabled(enabled);
		dateScrollPane.setEnabled(enabled);
		newShiftButton.setEnabled(enabled);
		removeShiftButton.setEnabled(enabled);
	}

	public void enableShiftInformation(boolean enabled){
		shiftInformation.setEnabled(enabled);
		assignedEmployees.setEnabled(enabled);
		assignedEmployeesTitle.setEnabled(enabled);
		startTime.setEnabled(enabled);
		startTimeTitle.setEnabled(enabled);
		location.setEnabled(enabled);
		locationTitle.setEnabled(enabled);
		shiftType.setEnabled(enabled);
		shiftTypeTitle.setEnabled(enabled);
		estimatedShiftLength.setEnabled(enabled);
		estimatedShiftLenghTitle.setEnabled(enabled);
	}

	public void enableAvailableEmployeeInformation(boolean enabled){
		employees.setEnabled(enabled);
		availableEmployees.setEnabled(enabled);
		availableEmployees.getTableHeader().setEnabled(enabled);
		employeesAvailableSP.setEnabled(enabled);
	}

	public void getWorkloadSelectionListener(){	
		try{
			this.workloadTable.getColumnModel().getSelectionModel().removeListSelectionListener(wlTableSelectionlistener);
			this.workloadTable.getSelectionModel().removeListSelectionListener(wlTableSelectionlistener);

		}
		catch(Exception e){

		}
		this.wlTableSelectionlistener = new workloadTableListSelectionListener();
		this.workloadTable.getColumnModel().getSelectionModel().addListSelectionListener(wlTableSelectionlistener);
		this.workloadTable.getSelectionModel().addListSelectionListener(wlTableSelectionlistener);

	}

	public void getAvailableEmployeeSelectionListener(){	
		try{
			availableEmployees.getColumnModel().getSelectionModel().removeListSelectionListener(avEmpTableSelectionListener);
			availableEmployees.getSelectionModel().removeListSelectionListener(avEmpTableSelectionListener);

		}
		catch(Exception e){

		}
		this.avEmpTableSelectionListener = new AvailableEmployeeTableListSelectionListener();
		availableEmployees.getColumnModel().getSelectionModel().addListSelectionListener(avEmpTableSelectionListener);
		availableEmployees.getSelectionModel().addListSelectionListener(avEmpTableSelectionListener);

	}

	private class ComboBoxStartTimeActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(schedCtr.getCurrentShift()!=null){
				String newStartTime = (String) startTime.getSelectedItem();
				schedCtr.enterShiftStartTime(newStartTime);
				updateAndReselectCell();
				displayShiftInformation(schedCtr.getCurrentShift());

			}

		}

	}

	private class ComboBoxLocationActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(schedCtr.getCurrentShift()!=null){
				String newLocation = (String) location.getSelectedItem();
				schedCtr.enterShiftLocation(newLocation);
				updateAndReselectCell();

			}

		}

	}

	private class ComboBoxShiftTypeActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(new GetAvailableEmployees()).start();
		}

	}


	private class workloadTableListSelectionListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			try{
				WorkloadTableModel tableModel = (WorkloadTableModel) workloadTable.getModel();
				Workload workload = tableModel.getWorkload(workloadTable.convertColumnIndexToModel(workloadTable.getSelectedColumn()));
				displayWorkloadInformation(workload);
				schedCtr.setCurrentWorkload(workload);
				enableDateInformation(true);
			}
			catch(Exception e1){
				clearWorkloadInformation();
				schedCtr.setCurrentWorkload(null);
				enableDateInformation(false);
			}

			try{
				WorkloadTableModel tableModel = (WorkloadTableModel) workloadTable.getModel();
				Shift shift =  tableModel.getShift(workloadTable.convertRowIndexToModel(workloadTable.getSelectedRow()),workloadTable.convertColumnIndexToModel(workloadTable.getSelectedColumn()));
				clearShiftInformation();
				displayShiftInformation(shift);
				schedCtr.setCurrentShift(shift);
				enableShiftInformation(true);
				if(shift == null){
					clearShiftInformation();
					schedCtr.setCurrentShift(null);
					enableShiftInformation(false);
					enableAvailableEmployeeInformation(false);
					availableEmpModel.setColumnCount(0);
				}

			}
			catch(Exception e2){
				clearShiftInformation();
				schedCtr.setCurrentShift(null);
				enableShiftInformation(false);
				enableAvailableEmployeeInformation(false);
				availableEmpModel.setColumnCount(0);
			}

			try{
				if(schedCtr.getCurrentShift().getTypeOfShift()!=ShiftType.NOT_SET){
					new Thread(new DisplayAvailableEmployees()).start();
				}
				else{
					enableAvailableEmployeeInformation(false);
					availableEmpModel.setColumnCount(0);
				}
			}
			catch(Exception e3){
				enableAvailableEmployeeInformation(false);
				availableEmpModel.setColumnCount(0);
				addEmployee.setEnabled(false);
				removeEmployee.setEnabled(false);
			}

			try{
				if(schedCtr.getCurrentShift().getEmployee()!=null){
					removeEmployee.setEnabled(true);
				}
			}
			catch(Exception e4){
				removeEmployee.setEnabled(false);
			}

		}
	}

	private class AvailableEmployeeTableListSelectionListener implements ListSelectionListener{
		@Override
		public void valueChanged(ListSelectionEvent e) {
			try{
				Employee employee = availableEmpModel.getEmployee(availableEmployees.convertRowIndexToModel(availableEmployees.getSelectedRow()));
				schedCtr.setCurrentEmployee(employee);
				addEmployee.setEnabled(true);
			}
			catch(Exception e1){
				e1.printStackTrace();
				schedCtr.setCurrentEmployee(null);
				addEmployee.setEnabled(false);
			}
		}

	}

	private class GetAvailableEmployees implements Runnable {
		@Override
		public void run() {
			workloadTable.setEnabled(false);
			dialogBox.setText("Getting available employees for: " + shiftType.getSelectedItem().toString());
			if(!schedCtr.getCurrentShift().getTypeOfShift().toString().equalsIgnoreCase(shiftType.getSelectedItem().toString())){
				if(!shiftType.getSelectedItem().toString().equals("NOT_SET")){
					ShiftType newShiftType = null;
					newShiftType = ShiftType.selectShiftType(shiftType.getSelectedItem().toString());
					ArrayList<Employee> availableEmp = schedCtr.enterShiftType(newShiftType);
					int nextSNumber = 0;
					for(Shift shift: schedCtr.getCurrentWorkload().getShifts()){
						if(shift.getTypeOfShift().equals(newShiftType) && !shift.getTypeOfShift().equals(ShiftType.NOT_SET)){
							nextSNumber++;
						}
					}
					schedCtr.getCurrentShift().setNumber(nextSNumber);
					availableEmpModel.rebuildAvailableEmpTableModel(availableEmp, schedCtr.getCurrentWorkload().getDate());
					enableAvailableEmployeeInformation(true);
					availableEmployees.getColumnModel().removeColumn(availableEmployees.getColumnModel().getColumn(4));
					schedCtr.getCurrentShift().setEmployee(null);
					displayShiftInformation(schedCtr.getCurrentShift());
					updateAndReselectCell();
				}
				if(shiftType.getSelectedItem().toString().equals("NOT_SET")){
					ShiftType newShiftType = null;
					newShiftType = ShiftType.selectShiftType(shiftType.getSelectedItem().toString());
					schedCtr.getCurrentShift().setTypeOfShift(newShiftType);
					schedCtr.getCurrentShift().setEmployee(null);
					displayShiftInformation(schedCtr.getCurrentShift());
					updateAndReselectCell();
					enableAvailableEmployeeInformation(false);
					availableEmpModel.setColumnCount(0);
				}

			}
			dialogBox.setText("Results found:");
			workloadTable.setEnabled(true);

		}
	};

	private class DisplayAvailableEmployees implements Runnable{

		@Override
		public void run() {
			workloadTable.setEnabled(false);
			addEmployee.setEnabled(false);
			removeEmployee.setEnabled(false);
			newShiftButton.setEnabled(false);
			enableAvailableEmployeeInformation(false);
			schedCtr.setAvailableEmployees(schedCtr.getAvailableEmployeesForCurrentWorkload());
			availableEmpModel.rebuildAvailableEmpTableModel(schedCtr.getAvailableEmployees(), schedCtr.getCurrentWorkload().getDate());
			availableEmployees.getColumnModel().removeColumn(availableEmployees.getColumnModel().getColumn(4));
			enableAvailableEmployeeInformation(true);
			newShiftButton.setEnabled(true);
			addEmployee.setEnabled(true);
			removeEmployee.setEnabled(true);
			workloadTable.setEnabled(true);
		}
	}

}


