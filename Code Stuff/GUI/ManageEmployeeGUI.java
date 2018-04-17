package gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import controller.EmployeeController;
import model.Employee;
import model.EmployeeType;
import model.WeekDay;

public class ManageEmployeeGUI {

	private EmployeeController empCtr = new EmployeeController();
	JTable table;
	EmployeeTableModel empTableModel;
	JLabel idValue;
	JLabel firstNameValue;
	JLabel lastNameValue;	
	JLabel emailValue;
	JLabel phoneNoValue;
	JLabel jobPosValue;
	JLabel daysUnavailableValue;
	JLabel daysPreferredOffValue;
	JLabel minWorkingHoursValue;
	JLabel maxWorkingHoursValue;
	JLabel hoursWorkedValue;
	JButton updateEmployee;
	JButton removeEmployee;
	JButton manageDatesOff;

	public static void main(String [] args){

		ManageEmployeeGUI test = new ManageEmployeeGUI();
		test.buildMainWindow();

	}

	public void buildMainWindow(){
		JFrame frame = new JFrame("Manage Employee");
		frame.setSize(1000, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		JPanel panel = new JPanel(new GridBagLayout());
		frame.getContentPane().add(panel, BorderLayout.WEST);
		GridBagConstraints c = new GridBagConstraints();
		c.weighty=1;

		c.insets= new Insets(0,20,0,0);
		JTextField search = new JTextField("Search");
		empTableModel = new EmployeeTableModel(empCtr.getAllEmployees());
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(empTableModel);
		genericFocusListener(search);
		search.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				String text = search.getText();
				if (text.length() == 0) {
					sorter.setRowFilter(null);
				} else {
					try{
						sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0,1,2));
					}
					catch(Exception e){
					}
				}
			}
		});
		search.setPreferredSize(new Dimension(250, 20));
		c.gridx=0;
		c.gridy=0;
		panel.add(search, c);



		table = new JTable(empTableModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSorter(sorter);
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(3));
		table.getSelectionModel().addListSelectionListener(e->{
			try{
				Employee employee = (Employee)table.getModel().getValueAt(table.convertRowIndexToModel(table.getSelectedRow()), 3);
				empCtr.setCurrentEmployee(employee);
				EmployeeInformation(employee);
				updateEmployee.setEnabled(true);
				removeEmployee.setEnabled(true);
				manageDatesOff.setEnabled(true);
			}
			catch(Exception ex){
			}
		});
		c.gridwidth = 2;
		c.gridx=0;
		c.gridy=1;
		JScrollPane sp = new JScrollPane(table);
		sp.setPreferredSize(new Dimension(500, 320));
		panel.add(sp, c);

		JPanel panel2 = new JPanel(new GridBagLayout());
		panel2.setBorder(
				BorderFactory.createTitledBorder("Employee Infromation"));
		panel2.setPreferredSize(new Dimension(400, 300));
		c.gridheight = 2;
		c.gridx=2;
		c.gridy=0;
		panel.add(panel2, c);


		c.gridwidth=1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		//c.insets= new Insets(0,100,0,0);


		JLabel id = new JLabel("ID:");
		c.gridx=0;
		c.gridy=0;
		panel2.add(id, c);

		JLabel firstName = new JLabel("First Name:");
		c.gridx=0;
		c.gridy=1;
		panel2.add(firstName, c);

		JLabel lastName = new JLabel("Last Name:");
		c.gridx=0;
		c.gridy=2;
		panel2.add(lastName, c);

		JLabel email = new JLabel("Email: ");
		c.gridx=0;
		c.gridy=3;
		panel2.add(email, c);

		JLabel phoneNo = new JLabel("Phone Number");
		c.gridx=0;
		c.gridy=4;
		panel2.add(phoneNo, c);

		JLabel jobPos = new JLabel("Job Position");
		c.gridx=0;
		c.gridy=5;
		panel2.add(jobPos, c);

		JLabel daysUnavailable = new JLabel("Days Unavailable:");
		c.gridx=0;
		c.gridy=6;
		panel2.add(daysUnavailable, c);

		JLabel daysPreferredOff = new JLabel("Days Preferred Off:");
		c.gridx=0;
		c.gridy=7;
		panel2.add(daysPreferredOff, c);

		JLabel minWorkingHours = new JLabel("Min. Working Hours:");
		c.gridx=0;
		c.gridy=8;
		panel2.add(minWorkingHours, c);

		JLabel maxWorkingHours = new JLabel("Max Working Hours");
		c.gridx=0;
		c.gridy=9;
		panel2.add(maxWorkingHours, c);

		JLabel hoursWorked = new JLabel("Hours Worked:");
		c.gridx=0;
		c.gridy=10;
		panel2.add(hoursWorked, c);


		idValue = new JLabel("0");
		c.gridx=1;
		c.gridy=0;
		panel2.add(idValue, c);

		firstNameValue = new JLabel("1");
		c.gridx=1;
		c.gridy=1;
		panel2.add(firstNameValue, c);

		lastNameValue = new JLabel("2");
		c.gridx=1;
		c.gridy=2;
		panel2.add(lastNameValue, c);

		emailValue = new JLabel("3");
		c.gridx=1;
		c.gridy=3;
		panel2.add(emailValue, c);

		phoneNoValue = new JLabel("4");
		c.gridx=1;
		c.gridy=4;
		panel2.add(phoneNoValue, c);

		jobPosValue = new JLabel("5");
		c.gridx=1;
		c.gridy=5;
		panel2.add(jobPosValue, c);

		daysUnavailableValue = new JLabel("6");
		c.gridx=1;
		c.gridy=6;
		panel2.add(daysUnavailableValue, c);

		daysPreferredOffValue = new JLabel("7");
		c.gridx=1;
		c.gridy=7;
		panel2.add(daysPreferredOffValue, c);

		minWorkingHoursValue = new JLabel("8");
		c.gridx=1;
		c.gridy=8;
		panel2.add(minWorkingHoursValue, c);

		maxWorkingHoursValue = new JLabel("9");
		c.gridx=1;
		c.gridy=9;
		panel2.add(maxWorkingHoursValue, c);

		hoursWorkedValue = new JLabel("10");
		c.gridx=1;
		c.gridy=10;
		panel2.add(hoursWorkedValue, c);

		c.gridwidth = 1;

		JButton addEmployee = new JButton("Add Employee");
		addEmployee.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				buildAddEmployeeWindow();

			}
		});
		c.gridx=0;
		c.gridy=3;
		panel.add(addEmployee, c);

		updateEmployee = new JButton("Update Employee");
		updateEmployee.setEnabled(false);
		updateEmployee.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				buildUpdateEmployeeWindow();

			}
		});
		c.gridx=1;
		c.gridy=3;
		panel.add(updateEmployee, c);

		removeEmployee = new JButton("Remove Employee");
		removeEmployee.setEnabled(false);
		removeEmployee.addActionListener(e->{
			int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to delete: " + empCtr.getCurrentEmployee().getFirstName() + " " +  empCtr.getCurrentEmployee().getLastName() + "\nfrom the database?","Warning", JOptionPane.WARNING_MESSAGE);
			if(dialogResult == JOptionPane.YES_OPTION){
				try {
					empCtr.deleteEmployee(empCtr.getCurrentEmployee());
					empTableModel.removeRow(table.convertRowIndexToModel(table.getSelectedRow()));
					if(table.getSelectedRow() == -1){
						updateEmployee.setEnabled(false);
						removeEmployee.setEnabled(false);
						manageDatesOff.setEnabled(false);
						empCtr.setCurrentEmployee(null);
						ClearEmployeeInformation();
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frame, "Error deleting employee - try again later", "Remove Employee Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		c.gridx=2;
		c.gridy=3;
		panel.add(removeEmployee, c);

		manageDatesOff = new JButton("Manage Dates Off");
		manageDatesOff.setEnabled(false);
		manageDatesOff.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				buildManageDaysOffWindow();

			}
		});
		c.gridx=3;
		c.gridy=3;
		panel.add(manageDatesOff, c);

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Menu test = new Menu();
				frame.setVisible(false);
				test.buildWindow();

			}
		});
		c.gridx=3;
		c.gridy = 4;
		panel.add(cancel, c);

		frame.setVisible(true);
	}

	private void EmployeeInformation(Employee employee){
		idValue.setText(String.valueOf(employee.getId()));
		firstNameValue.setText(employee.getFirstName());
		lastNameValue.setText(employee.getLastName());
		emailValue.setText(employee.getEmail());
		phoneNoValue.setText(employee.getPhoneNumber());
		jobPosValue.setText(employee.getTypeOfEmployee().toString());
		daysUnavailableValue.setText(Employee.listToString(employee.getWeekDaysUnavailable()));
		daysPreferredOffValue.setText(Employee.listToString(employee.getWeekDaysPreferredOff()));
		minWorkingHoursValue.setText(String.valueOf(employee.getMinWorkingHours()));
		maxWorkingHoursValue.setText(String.valueOf(employee.getMaxWorkingHours()));
		hoursWorkedValue.setText(String.valueOf(employee.getHoursWorkedThisMonth()));

	}

	private void ClearEmployeeInformation(){
		idValue.setText("");
		firstNameValue.setText("");
		lastNameValue.setText("");
		emailValue.setText("");
		phoneNoValue.setText("");
		jobPosValue.setText("");
		daysUnavailableValue.setText("");
		daysPreferredOffValue.setText("");
		minWorkingHoursValue.setText("");
		maxWorkingHoursValue.setText("");
		hoursWorkedValue.setText("");

	}

	private void genericFocusListener(JTextField textfield){
		String defaultText = textfield.getText();
		textfield.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e){
				if(textfield.getText().equalsIgnoreCase(defaultText)){
					textfield.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e){
				if(textfield.getText().length() <= 0){
					textfield.setText(defaultText);
				}
			}

		});
	}

	JTextField fromInput;
	JTextField toInput;
	JButton remove;

	public void buildManageDaysOffWindow(){
		JFrame frame = new JFrame("Fusion Reataurant");
		frame.setSize(300, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel(new GridBagLayout());
		frame.getContentPane().add(panel, BorderLayout.WEST);
		GridBagConstraints c = new GridBagConstraints();
		c.weighty=1;

		c.insets = new Insets(0, 20, 0, 20);

		JLabel datesOff = new JLabel("Dates Off");
		c.gridx=0;
		c.gridy=0;
		panel.add(datesOff, c);

		c.gridwidth = 2;

		DefaultTableModel table_datesOff  = new DatesOffTableModel(empCtr.getCurrentEmployee());
		JTable datesTable = new JTable(table_datesOff);
		datesTable.getSelectionModel().addListSelectionListener(e->{
			remove.setEnabled(true);
		});
		c.gridx=0;
		c.gridy=1;
		JScrollPane sp = new JScrollPane(datesTable);
		sp.setPreferredSize(new Dimension(260, 200));
		panel.add(sp, c);

		remove = new JButton("Remove");
		remove.setEnabled(false);
		remove.addActionListener(e->{
			String dateString = datesTable.getValueAt(datesTable.getSelectedRow(), datesTable.getSelectedColumn()).toString();
			int year = Integer.valueOf(dateString.substring(0, 4));
			int month = Integer.valueOf(dateString.substring(5, 7));
			int dayOfMonth = Integer.valueOf(dateString.substring(8, 10));
			LocalDate date = LocalDate.of(year, month, dayOfMonth);
			empCtr.removeDateUnavailable(empCtr.getCurrentEmployee(), date);
			table_datesOff.removeRow(datesTable.convertRowIndexToModel(datesTable.getSelectedRow()));
			empCtr.getCurrentEmployee().getDatesUnavailable().remove(date);
			if(datesTable.getSelectedRow() == -1){
				remove.setEnabled(false);
			}
		});
		c.gridx=0;
		c.gridy=2;
		panel.add(remove, c);

		c.insets = new Insets(0, 0, 0, 0);

		JSeparator sep = new JSeparator();
		sep.setPreferredSize(new Dimension(20,30));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=5;
		panel.add(sep, c);

		c.insets = new Insets(0, 20, 0, 20);

		JLabel date = new JLabel("Add dates unavailable to work");
		c.gridx=0;
		c.gridy=6;
		panel.add(date, c);

		JLabel from = new JLabel("From:");
		c.gridx=0;
		c.gridy=7;
		panel.add(from, c);

		fromInput = new JTextField(); 
		fromInput.setToolTipText("Please enter date (YYYY-MM-DD) e.g. 2017-02-09");
		fromInput.addActionListener(e->{
			DateValidator dateValidator = new DateValidator();
			String fromDate = fromInput.getText().trim();
			String toDate = toInput.getText().trim();
			if(toDate.length() > 0 && dateValidator.validate(toDate) && fromDate.length() > 0 && dateValidator.validate(fromDate)){
				try{
					LocalDate fromDateLD = LocalDate.parse(fromDate);
					LocalDate toDateLD = LocalDate.parse(toDate);
					if(fromDateLD.isBefore(toDateLD)){
						LocalDate insertDate = fromDateLD;
						LocalDate previousInsertedDate = fromDateLD;
						for(int i=0; i<30 && previousInsertedDate.isBefore(toDateLD); i++){
							empCtr.insertDateUnavailable(empCtr.getCurrentEmployee(), insertDate);
							table_datesOff.addRow(new Object[]{insertDate});
							empCtr.getCurrentEmployee().getDatesUnavailable().add(insertDate);
							previousInsertedDate = insertDate;
							insertDate = insertDate.plusDays(1);
						}
						fromInput.setText("");
						toInput.setText("");
					}
					else{
						JOptionPane.showMessageDialog(frame, "Make sure the to-date is after the from-date", "Dates Range error", JOptionPane.ERROR_MESSAGE);
					}

				}
				catch(Exception ex){
					ex.printStackTrace();
					JOptionPane.showMessageDialog(frame, "Error inserting the dates unavailable\n Please check the dates are correct and try again", "Dates Unavailable insertion error", JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(fromDate.length() > 0 && dateValidator.validate(fromDate)){
				try {
					LocalDate insertDate = LocalDate.parse(fromDate);
					empCtr.insertDateUnavailable(empCtr.getCurrentEmployee(), insertDate);
					table_datesOff.addRow(new Object[]{insertDate});
					empCtr.getCurrentEmployee().getDatesUnavailable().add(insertDate);
					fromInput.setText("");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frame, "Error inserting the date unavailable\n Please check the date is correct and try again", "Date Unavailable insertion error", JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(fromDate.length() == 0){
				JOptionPane.showMessageDialog(frame, "Please enter a date", "Empty input", JOptionPane.ERROR_MESSAGE);
			}
			else{
				JOptionPane.showMessageDialog(frame, "Unknown error with date insertion\nWhat on earth did you do?", "Unknown date insertion error", JOptionPane.ERROR_MESSAGE);
			}
		});
		c.gridx=0;
		c.gridy=8;
		panel.add(fromInput, c);

		JLabel to = new JLabel("To:*");
		c.gridx=0;
		c.gridy=9;
		panel.add(to, c);

		toInput = new JTextField();
		toInput.setToolTipText("Please enter date (YYYY-MM-DD) e.g. 2017-02-09");
		toInput.addActionListener(e->{
			DateValidator dateValidator = new DateValidator();
			String fromDate = fromInput.getText().trim();
			String toDate = toInput.getText().trim();
			if(toDate.length() > 0 && dateValidator.validate(toDate) && fromDate.length() > 0 && dateValidator.validate(fromDate)){
				try{
					LocalDate fromDateLD = LocalDate.parse(fromDate);
					LocalDate toDateLD = LocalDate.parse(toDate);
					if(fromDateLD.isBefore(toDateLD)){
						LocalDate insertDate = fromDateLD;
						LocalDate previousInsertedDate = fromDateLD;
						for(int i=0; i<30 && previousInsertedDate.isBefore(toDateLD); i++){
							empCtr.insertDateUnavailable(empCtr.getCurrentEmployee(), insertDate);
							table_datesOff.addRow(new Object[]{insertDate});
							empCtr.getCurrentEmployee().getDatesUnavailable().add(insertDate);
							previousInsertedDate = insertDate;
							insertDate = insertDate.plusDays(1);
						}
						fromInput.setText("");
						toInput.setText("");
					}
					else{
						JOptionPane.showMessageDialog(frame, "Make sure the to-date is after the from-date", "Dates Range error", JOptionPane.ERROR_MESSAGE);
					}

				}
				catch(Exception ex){
					ex.printStackTrace();
					JOptionPane.showMessageDialog(frame, "Error inserting the dates unavailable\n Please check the dates are correct and try again", "Dates Unavailable insertion error", JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(fromDate.length() > 0 && dateValidator.validate(fromDate)){
				try {
					LocalDate insertDate = LocalDate.parse(fromDate);
					empCtr.insertDateUnavailable(empCtr.getCurrentEmployee(), insertDate);
					table_datesOff.addRow(new Object[]{insertDate});
					empCtr.getCurrentEmployee().getDatesUnavailable().add(insertDate);
					fromInput.setText("");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frame, "Error inserting the date unavailable\n Please check the date is correct and try again", "Date Unavailable insertion error", JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(fromDate.length() == 0){
				JOptionPane.showMessageDialog(frame, "Please enter a date", "Empty input", JOptionPane.ERROR_MESSAGE);
			}
			else{
				JOptionPane.showMessageDialog(frame, "Unknown error with date insertion\nWhat on earth did you do?", "Unknown date insertion error", JOptionPane.ERROR_MESSAGE);
			}
		});
		c.gridx=0;
		c.gridy=10;
		panel.add(toInput, c);

		JLabel footNote = new JLabel("* If this field is empty it will be one date only!");
		footNote.setFont(new Font("", Font.PLAIN, 10));
		c.gridx=0;
		c.gridy=11;
		panel.add(footNote, c);

		c.gridwidth = 1;

		JButton add = new JButton("Add");
		add.addActionListener(e->{
			DateValidator dateValidator = new DateValidator();
			String fromDate = fromInput.getText().trim();
			String toDate = toInput.getText().trim();
			if(toDate.length() > 0 && dateValidator.validate(toDate) && fromDate.length() > 0 && dateValidator.validate(fromDate)){
				try{
					LocalDate fromDateLD = LocalDate.parse(fromDate);
					LocalDate toDateLD = LocalDate.parse(toDate);
					if(fromDateLD.isBefore(toDateLD)){
						LocalDate insertDate = fromDateLD;
						LocalDate previousInsertedDate = fromDateLD;
						for(int i=0; i<30 && previousInsertedDate.isBefore(toDateLD); i++){
							if(i ==29){
								JOptionPane.showMessageDialog(frame, "Date range was too large:\nOnly first 30 dates added", "Excessive date range error", JOptionPane.ERROR_MESSAGE);
							}
							empCtr.insertDateUnavailable(empCtr.getCurrentEmployee(), insertDate);
							table_datesOff.addRow(new Object[]{insertDate});
							empCtr.getCurrentEmployee().getDatesUnavailable().add(insertDate);
							previousInsertedDate = insertDate;
							insertDate = insertDate.plusDays(1);
						}
						fromInput.setText("");
						toInput.setText("");
					}
					else{
						JOptionPane.showMessageDialog(frame, "Make sure the to-date is after the from-date", "Dates Range error", JOptionPane.ERROR_MESSAGE);
					}

				}
				catch(Exception ex){
					ex.printStackTrace();
					JOptionPane.showMessageDialog(frame, "Error inserting the dates unavailable\n Please check the dates are correct and try again", "Dates Unavailable insertion error", JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(!dateValidator.validate(toDate) && toDate.length() > 0){
				JOptionPane.showMessageDialog(frame, "Please check the dates you have entered are valid", "Invalid date insertion error", JOptionPane.ERROR_MESSAGE);
			}
			else if(!dateValidator.validate(fromDate) && fromDate.length() > 0){
				JOptionPane.showMessageDialog(frame, "Please check the dates you have entered are valid", "Invalid date insertion error", JOptionPane.ERROR_MESSAGE);
			}
			else if (fromDate.isEmpty() && toDate.length() > 0){
				JOptionPane.showMessageDialog(frame, "Please enter a from date", "Date insertion error", JOptionPane.ERROR_MESSAGE);
			}
			else if(fromDate.length() > 0 && dateValidator.validate(fromDate)){
				try {
					LocalDate insertDate = LocalDate.parse(fromDate);
					empCtr.insertDateUnavailable(empCtr.getCurrentEmployee(), insertDate);
					table_datesOff.addRow(new Object[]{insertDate});
					empCtr.getCurrentEmployee().getDatesUnavailable().add(insertDate);
					fromInput.setText("");
					toInput.setText("");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frame, "Error inserting the date unavailable\n Please check the date is correct and try again", "Date Unavailable insertion error", JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(fromDate.length() == 0){
				JOptionPane.showMessageDialog(frame, "Please enter a date", "Empty input", JOptionPane.ERROR_MESSAGE);
			}
			else{
				JOptionPane.showMessageDialog(frame, "Something went wrong, please try again", "Unknown date insertion error", JOptionPane.ERROR_MESSAGE);
			}
		});
		c.gridx=0;
		c.gridy=12;
		panel.add(add, c);

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);

			}
		});
		c.gridx=1;
		c.gridy=12;
		panel.add(cancel, c);





		frame.setVisible(true);


	}

	public void buildAddEmployeeWindow(){
		JFrame frame = new JFrame("Fusion Reataurant");
		frame.setSize(400, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel(new GridBagLayout());
		frame.getContentPane().add(panel, BorderLayout.WEST);
		GridBagConstraints c = new GridBagConstraints();
		c.weighty=1;

		c.gridwidth = 2;
		//c.insets = new Insets(0, 20, 0, 0);

		JTextField firstName = new JTextField("First Name");
		firstName.setToolTipText("New Employee's first name");
		genericFocusListener(firstName);
		c.gridx = 0;
		c.gridy = 0;
		firstName.setPreferredSize(new Dimension(150, 20));
		panel.add(firstName, c);

		c.gridwidth = 2;

		JTextField lastName = new JTextField("Last Name");
		lastName.setToolTipText("New Employee's last name");
		genericFocusListener(lastName);
		c.gridx = 2;
		c.gridy = 0;
		lastName.setPreferredSize(new Dimension(150, 20));
		panel.add(lastName, c);

		JTextField email = new JTextField("Email");
		email.setToolTipText("New Employee's email e.g. employee@email.com");
		genericFocusListener(email);
		c.gridx = 0;
		c.gridy = 1;
		email.setPreferredSize(new Dimension(150, 20));
		panel.add(email, c);

		JTextField phoneNo = new JTextField("PhoneNo");
		phoneNo.setToolTipText("New Employee's phone number: (danish numbers only) e.g. +45 1234 5678");
		genericFocusListener(phoneNo);
		c.gridx = 2;
		c.gridy = 1;
		phoneNo.setPreferredSize(new Dimension(150, 20));
		panel.add(phoneNo, c);

		JTextField minWorkHours = new JTextField("Min Hours");
		minWorkHours.setToolTipText("Minimum monthly working hours 0-100");
		genericFocusListener(minWorkHours);
		c.gridx = 0;
		c.gridy = 2;
		minWorkHours.setPreferredSize(new Dimension(150, 20));
		panel.add(minWorkHours, c);

		JTextField maxWorkHours = new JTextField("Max Hours");
		maxWorkHours.setToolTipText("Maximum monthly working hours 0-100");
		genericFocusListener(maxWorkHours);
		c.gridx = 2;
		c.gridy = 2;
		maxWorkHours.setPreferredSize(new Dimension(150, 20));
		panel.add(maxWorkHours, c);

		JComboBox<EmployeeType> workPos = new JComboBox<>(EmployeeType.values());
		c.gridx = 0;
		c.gridy = 3;
		workPos.setPreferredSize(new Dimension(150, 20));
		panel.add(workPos, c);

		c.gridwidth =1;

		JLabel unavailabelDays = new JLabel("Unavailable Days");
		unavailabelDays.setToolTipText("Check days employee is unavailable for work");
		c.gridx=0;
		c.gridy=4;
		panel.add(unavailabelDays, c);

		JCheckBox mon = new JCheckBox("Monday");
		c.gridx = 0;
		c.gridy = 5;
		panel.add(mon, c);

		JCheckBox tues = new JCheckBox("Tuesday");
		c.gridx = 1;
		c.gridy = 5;
		panel.add(tues, c);

		JCheckBox wed = new JCheckBox("Wednesday");
		c.gridx = 2;
		c.gridy = 5;
		panel.add(wed, c);

		JCheckBox thur = new JCheckBox("Thursday");
		c.gridx = 3;
		c.gridy = 5;
		panel.add(thur, c);

		JCheckBox fri = new JCheckBox("Friday");
		c.gridx = 0;
		c.gridy = 6;
		panel.add(fri, c);

		JCheckBox sat = new JCheckBox("Saturday");
		c.gridx = 1;
		c.gridy = 6;
		panel.add(sat, c);

		JCheckBox sun = new JCheckBox("Sunday");
		c.gridx = 2;
		c.gridy = 6;
		panel.add(sun, c);

		JLabel preferredDaysOff = new JLabel("Preferred Days Off");
		preferredDaysOff.setToolTipText("Check days employee prefers not to work");
		c.gridx=0;
		c.gridy=7;
		panel.add(preferredDaysOff, c);

		JCheckBox mon1 = new JCheckBox("Monday");
		c.gridx = 0;
		c.gridy = 8;
		panel.add(mon1, c);

		JCheckBox tues1 = new JCheckBox("Tuesday");
		c.gridx = 1;
		c.gridy = 8;
		panel.add(tues1, c);

		JCheckBox wed1 = new JCheckBox("Wednesday");
		c.gridx = 2;
		c.gridy = 8;
		panel.add(wed1, c);

		JCheckBox thur1 = new JCheckBox("Thursday");
		c.gridx = 3;
		c.gridy = 8;
		panel.add(thur1, c);

		JCheckBox fri1 = new JCheckBox("Friday");
		c.gridx = 0;
		c.gridy = 9;
		panel.add(fri1, c);

		JCheckBox sat1 = new JCheckBox("Saturday");
		c.gridx = 1;
		c.gridy = 9;
		panel.add(sat1, c);

		JCheckBox sun1 = new JCheckBox("Sunday");
		c.gridx = 2;
		c.gridy = 9;
		panel.add(sun1, c);

		JButton save = new JButton("Save");
		save.addActionListener(e->{
			Employee newEmployee = new Employee();
			String errorString = "Problem with inputs: ";
			boolean inputCheck = true;
			NameValidator nv = new NameValidator();
			String eFirstName = firstName.getText().trim();
			if(nv.validate(eFirstName)){
				newEmployee.setFirstName(eFirstName);
			}
			else{
				errorString += "\nFirst Name ";
				inputCheck = false;
			}
			String eLastName = lastName.getText().trim();
			if(nv.validate(eLastName)){
				newEmployee.setLastName(eLastName);
			}
			else{
				errorString += "\nLast Name ";
				inputCheck = false;
			}
			EmailValidator ev = new EmailValidator();
			String eEmail = email.getText().trim();
			if(ev.validate(eEmail)){
				newEmployee.setEmail(eEmail);
			}
			else{
				errorString += "\nEmail ";
				inputCheck = false;
			}
			PhoneNoValidator pnv = new PhoneNoValidator();
			String ePhoneNumber = phoneNo.getText().trim();
			if(pnv.validate(ePhoneNumber)){
				newEmployee.setPhoneNumber(ePhoneNumber);
			}
			else{
				errorString += "\nPhone No. ";
				inputCheck = false;
			}
			WorkingHoursValidator whv = new WorkingHoursValidator();
			String eMinWorkingHours = minWorkHours.getText().trim();
			if(whv.validate(eMinWorkingHours)){
				newEmployee.setMinWorkingHours(Integer.valueOf(eMinWorkingHours));
			}
			else{
				errorString += "\nMin Working Hours ";
				inputCheck = false;
			}
			String eMaxWorkingHours = maxWorkHours.getText().trim();
			if(whv.validate(eMaxWorkingHours)){
				newEmployee.setMaxWorkingHours(Integer.valueOf(eMaxWorkingHours));
			}
			else{
				errorString += "\nMax Working Hours ";
				inputCheck = false;
			}
			EmployeeType employeeType = (EmployeeType)workPos.getSelectedItem();
			newEmployee.setTypeOfEmployee(employeeType);
			if(mon.isSelected())
				newEmployee.getWeekDaysUnavailable().add(WeekDay.MONDAY);
			if(tues.isSelected())
				newEmployee.getWeekDaysUnavailable().add(WeekDay.TUESDAY);
			if(wed.isSelected())
				newEmployee.getWeekDaysUnavailable().add(WeekDay.WEDNESDAY);
			if(thur.isSelected())
				newEmployee.getWeekDaysUnavailable().add(WeekDay.THURSDAY);
			if(fri.isSelected())
				newEmployee.getWeekDaysUnavailable().add(WeekDay.FRIDAY);
			if(sat.isSelected())
				newEmployee.getWeekDaysUnavailable().add(WeekDay.SATURDAY);
			if(sun.isSelected())
				newEmployee.getWeekDaysUnavailable().add(WeekDay.SUNDAY);
			if(mon1.isSelected())
				newEmployee.getWeekDaysPreferredOff().add(WeekDay.MONDAY);
			if(tues1.isSelected())
				newEmployee.getWeekDaysPreferredOff().add(WeekDay.TUESDAY);
			if(wed1.isSelected())
				newEmployee.getWeekDaysPreferredOff().add(WeekDay.WEDNESDAY);
			if(thur1.isSelected())
				newEmployee.getWeekDaysPreferredOff().add(WeekDay.THURSDAY);
			if(fri1.isSelected())
				newEmployee.getWeekDaysPreferredOff().add(WeekDay.FRIDAY);
			if(sat1.isSelected())
				newEmployee.getWeekDaysPreferredOff().add(WeekDay.SATURDAY);
			if(sun1.isSelected())
				newEmployee.getWeekDaysPreferredOff().add(WeekDay.SUNDAY);
			if(inputCheck){
				try {
					int nextId = empCtr.insertEmployee(newEmployee);
					newEmployee.setId(nextId);
					JOptionPane.showMessageDialog(frame, "Employee successfully Saved", "Success", JOptionPane.PLAIN_MESSAGE);
					frame.setVisible(false);
					empTableModel.addRow(newEmployee);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frame, "Employee Insertion Error - try again", "Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
			else{
				JOptionPane.showMessageDialog(frame, errorString, "Invalid input Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		c.gridx=0;
		c.gridy= 10;
		panel.add(save, c);

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);

			}
		});
		c.gridx=3;
		c.gridy= 10;
		panel.add(cancel, c);



		frame.setVisible(true);
	}

	public void buildUpdateEmployeeWindow(){
		JFrame frame = new JFrame("Fusion Reataurant");
		frame.setSize(400, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel(new GridBagLayout());
		frame.getContentPane().add(panel, BorderLayout.WEST);
		GridBagConstraints c = new GridBagConstraints();
		c.weighty=1;

		c.gridwidth = 2;
		//c.insets = new Insets(0, 20, 0, 0);

		Employee employee = empCtr.getCurrentEmployee();
		JTextField firstName = new JTextField(employee.getFirstName());
		firstName.setToolTipText("Update employee's first name");
		c.gridx = 0;
		c.gridy = 0;
		firstName.setPreferredSize(new Dimension(150, 20));
		panel.add(firstName, c);

		c.gridwidth = 2;

		JTextField lastName = new JTextField(employee.getLastName());
		lastName.setToolTipText("Update employee's last name");
		c.gridx = 2;
		c.gridy = 0;
		lastName.setPreferredSize(new Dimension(150, 20));
		panel.add(lastName, c);

		JTextField email = new JTextField(employee.getEmail());
		email.setToolTipText("Update employee's email e.g. employee@email.com");
		c.gridx = 0;
		c.gridy = 1;
		email.setPreferredSize(new Dimension(150, 20));
		panel.add(email, c);

		JTextField phoneNo = new JTextField(employee.getPhoneNumber());
		phoneNo.setToolTipText("Update employee's phone number: (danish numbers only) e.g. +45 1234 5678");
		c.gridx = 2;
		c.gridy = 1;
		phoneNo.setPreferredSize(new Dimension(150, 20));
		panel.add(phoneNo, c);

		JTextField minWorkHours = new JTextField(String.valueOf(employee.getMinWorkingHours()));
		minWorkHours.setToolTipText("Update employee's minimum monthly working hours 0-100");
		c.gridx = 0;
		c.gridy = 2;
		minWorkHours.setPreferredSize(new Dimension(150, 20));
		panel.add(minWorkHours, c);

		JTextField maxWorkHours = new JTextField(String.valueOf(employee.getMaxWorkingHours()));
		maxWorkHours.setToolTipText("Update employee's maximum monthly working hours 0-100");
		c.gridx = 2;
		c.gridy = 2;
		maxWorkHours.setPreferredSize(new Dimension(150, 20));
		panel.add(maxWorkHours, c);

		JComboBox<EmployeeType> workPos = new JComboBox<>(EmployeeType.values());
		workPos.setSelectedItem(employee.getTypeOfEmployee());
		c.gridx = 0;
		c.gridy = 3;
		workPos.setPreferredSize(new Dimension(150, 20));
		panel.add(workPos, c);

		c.gridwidth =1;

		JLabel unavailabelDays = new JLabel("Unavailable Days");
		c.gridx=0;
		c.gridy=4;
		panel.add(unavailabelDays, c);

		JCheckBox mon = new JCheckBox("Monday");
		c.gridx = 0;
		c.gridy = 5;
		mon.setSelected(false);
		panel.add(mon, c);

		JCheckBox tues = new JCheckBox("Tuesday");
		c.gridx = 1;
		c.gridy = 5;
		tues.setSelected(false);
		panel.add(tues, c);

		JCheckBox wed = new JCheckBox("Wednesday");
		c.gridx = 2;
		c.gridy = 5;
		wed.setSelected(false);
		panel.add(wed, c);

		JCheckBox thur = new JCheckBox("Thursday");
		c.gridx = 3;
		c.gridy = 5;
		thur.setSelected(false);
		panel.add(thur, c);

		JCheckBox fri = new JCheckBox("Friday");
		c.gridx = 0;
		c.gridy = 6;
		fri.setSelected(false);
		panel.add(fri, c);

		JCheckBox sat = new JCheckBox("Saturday");
		c.gridx = 1;
		c.gridy = 6;
		sat.setSelected(false);
		panel.add(sat, c);

		JCheckBox sun = new JCheckBox("Sunday");
		c.gridx = 2;
		c.gridy = 6;
		sun.setSelected(false);
		panel.add(sun, c);

		if(employee.getWeekDaysUnavailable().contains(WeekDay.MONDAY)){
			mon.setSelected(true);
		}
		if(employee.getWeekDaysUnavailable().contains(WeekDay.TUESDAY)){
			tues.setSelected(true);
		}
		if(employee.getWeekDaysUnavailable().contains(WeekDay.WEDNESDAY)){
			wed.setSelected(true);
		}
		if(employee.getWeekDaysUnavailable().contains(WeekDay.THURSDAY)){
			thur.setSelected(true);
		}
		if(employee.getWeekDaysUnavailable().contains(WeekDay.FRIDAY)){
			fri.setSelected(true);
		}
		if(employee.getWeekDaysUnavailable().contains(WeekDay.SATURDAY)){
			sat.setSelected(true);
		}
		if(employee.getWeekDaysUnavailable().contains(WeekDay.SUNDAY)){
			sun.setSelected(true);
		}

		JLabel prefferedDaysOff = new JLabel("Preffered Days Off");
		c.gridx=0;
		c.gridy=7;
		panel.add(prefferedDaysOff, c);

		JCheckBox mon1 = new JCheckBox("Monday");
		c.gridx = 0;
		c.gridy = 8;
		mon1.setSelected(false);
		panel.add(mon1, c);

		JCheckBox tues1 = new JCheckBox("Tuesday");
		c.gridx = 1;
		c.gridy = 8;
		tues1.setSelected(false);
		panel.add(tues1, c);

		JCheckBox wed1 = new JCheckBox("Wednesday");
		c.gridx = 2;
		c.gridy = 8;
		wed1.setSelected(false);
		panel.add(wed1, c);

		JCheckBox thur1 = new JCheckBox("Thursday");
		c.gridx = 3;
		c.gridy = 8;
		thur1.setSelected(false);
		panel.add(thur1, c);

		JCheckBox fri1 = new JCheckBox("Friday");
		c.gridx = 0;
		c.gridy = 9;
		fri1.setSelected(false);
		panel.add(fri1, c);

		JCheckBox sat1 = new JCheckBox("Saturday");
		c.gridx = 1;
		c.gridy = 9;
		sat1.setSelected(false);
		panel.add(sat1, c);

		JCheckBox sun1 = new JCheckBox("Sunday");
		c.gridx = 2;
		c.gridy = 9;
		sun1.setSelected(false);
		panel.add(sun1, c);

		if(employee.getWeekDaysPreferredOff().contains(WeekDay.MONDAY)){
			mon1.setSelected(true);
		}
		if(employee.getWeekDaysPreferredOff().contains(WeekDay.TUESDAY)){
			tues1.setSelected(true);
		}
		if(employee.getWeekDaysPreferredOff().contains(WeekDay.WEDNESDAY)){
			wed1.setSelected(true);
		}
		if(employee.getWeekDaysPreferredOff().contains(WeekDay.THURSDAY)){
			thur1.setSelected(true);
		}
		if(employee.getWeekDaysPreferredOff().contains(WeekDay.FRIDAY)){
			fri1.setSelected(true);
		}
		if(employee.getWeekDaysPreferredOff().contains(WeekDay.SATURDAY)){
			sat1.setSelected(true);
		}
		if(employee.getWeekDaysPreferredOff().contains(WeekDay.SUNDAY)){
			sun1.setSelected(true);
		}

		JButton save = new JButton("Save");
		save.addActionListener(e->{
			Employee updateEmployee = empCtr.getCurrentEmployee();
			String errorString = "Problem with inputs: ";
			boolean inputCheck = true;
			NameValidator nv = new NameValidator();
			String eFirstName = firstName.getText().trim();
			if(nv.validate(eFirstName)){
				updateEmployee.setFirstName(eFirstName);
			}
			else{
				errorString += "\nFirst Name ";
				inputCheck = false;
			}
			String eLastName = lastName.getText().trim();
			if(nv.validate(eLastName)){
				updateEmployee.setLastName(eLastName);
			}
			else{
				errorString += "\nLast Name ";
				inputCheck = false;
			}
			EmailValidator ev = new EmailValidator();
			String eEmail = email.getText().trim();
			if(ev.validate(eEmail)){
				updateEmployee.setEmail(eEmail);
			}
			else{
				errorString += "\nEmail ";
				inputCheck = false;
			}
			PhoneNoValidator pnv = new PhoneNoValidator();
			String ePhoneNumber = phoneNo.getText().trim();
			if(pnv.validate(ePhoneNumber)){
				updateEmployee.setPhoneNumber(ePhoneNumber);
			}
			else{
				errorString += "\nPhone No. ";
				inputCheck = false;
			}
			WorkingHoursValidator whv = new WorkingHoursValidator();
			String eMinWorkingHours = minWorkHours.getText().trim();
			if(whv.validate(eMinWorkingHours)){
				updateEmployee.setMinWorkingHours(Integer.valueOf(eMinWorkingHours));
			}
			else{
				errorString += "\nMin Working Hours ";
				inputCheck = false;
			}
			String eMaxWorkingHours = maxWorkHours.getText().trim();
			if(whv.validate(eMaxWorkingHours)){
				updateEmployee.setMaxWorkingHours(Integer.valueOf(eMaxWorkingHours));
			}
			else{
				errorString += "\nMax Working Hours ";
				inputCheck = false;
			}
			EmployeeType employeeType = (EmployeeType)workPos.getSelectedItem();
			updateEmployee.setTypeOfEmployee(employeeType);

			updateEmployee.getWeekDaysUnavailable().clear();
			if(mon.isSelected())
				updateEmployee.getWeekDaysUnavailable().add(WeekDay.MONDAY);
			if(tues.isSelected())
				updateEmployee.getWeekDaysUnavailable().add(WeekDay.TUESDAY);
			if(wed.isSelected())
				updateEmployee.getWeekDaysUnavailable().add(WeekDay.WEDNESDAY);
			if(thur.isSelected())
				updateEmployee.getWeekDaysUnavailable().add(WeekDay.THURSDAY);
			if(fri.isSelected())
				updateEmployee.getWeekDaysUnavailable().add(WeekDay.FRIDAY);
			if(sat.isSelected())
				updateEmployee.getWeekDaysUnavailable().add(WeekDay.SATURDAY);
			if(sun.isSelected())
				updateEmployee.getWeekDaysUnavailable().add(WeekDay.SUNDAY);
			updateEmployee.getWeekDaysPreferredOff().clear();
			if(mon1.isSelected())
				updateEmployee.getWeekDaysPreferredOff().add(WeekDay.MONDAY);
			if(tues1.isSelected())
				updateEmployee.getWeekDaysPreferredOff().add(WeekDay.TUESDAY);
			if(wed1.isSelected())
				updateEmployee.getWeekDaysPreferredOff().add(WeekDay.WEDNESDAY);
			if(thur1.isSelected())
				updateEmployee.getWeekDaysPreferredOff().add(WeekDay.THURSDAY);
			if(fri1.isSelected())
				updateEmployee.getWeekDaysPreferredOff().add(WeekDay.FRIDAY);
			if(sat1.isSelected())
				updateEmployee.getWeekDaysPreferredOff().add(WeekDay.SATURDAY);
			if(sun1.isSelected())
				updateEmployee.getWeekDaysPreferredOff().add(WeekDay.SUNDAY);
			if(inputCheck){
				try {
					empCtr.updateEmployee(updateEmployee);
					JOptionPane.showMessageDialog(frame, "Employee successfully updated", "Success", JOptionPane.PLAIN_MESSAGE);
					frame.setVisible(false);
					empTableModel.rebuildEmpTableModel(empCtr.getAllEmployees());
					EmployeeInformation(updateEmployee);
					
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frame, "Employee update Error - try again", "Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
			else{
				JOptionPane.showMessageDialog(frame, errorString, "Invalid input Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		c.gridx=0;
		c.gridy= 10;
		panel.add(save, c);

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);

			}
		});
		c.gridx=3;
		c.gridy= 10;
		panel.add(cancel, c);



		frame.setVisible(true);
	}


}
