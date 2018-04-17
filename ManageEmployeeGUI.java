
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ManageEmployeeGUI {
	 
	public static void main(String [] args){
		ManageEmployeeGUI test = new ManageEmployeeGUI();
		test.buildUpdateEmployeeWindow();
		
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
		search.setPreferredSize(new Dimension(250, 20));
		c.gridx=0;
		c.gridy=0;
		panel.add(search, c);
		
		String column_names[]= {"First name", "Last Names", "Job Position"};
		DefaultTableModel table_model=new DefaultTableModel(column_names,30);
		JTable table = new JTable(table_model);
		c.gridwidth = 2;
		//c.gridheight = 12;
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
		
		JLabel daysPrefferdOf = new JLabel("Days Prefferd Off");
		c.gridx=0;
		c.gridy=7;
		panel2.add(daysPrefferdOf, c);
		
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
		
		
		JLabel idValue = new JLabel("0");
		c.gridx=1;
		c.gridy=0;
		panel2.add(idValue, c);
		
		JLabel firstNameValue = new JLabel("1");
		c.gridx=1;
		c.gridy=1;
		panel2.add(firstNameValue, c);
		
		JLabel lastNameValue = new JLabel("2");
		c.gridx=1;
		c.gridy=2;
		panel2.add(lastNameValue, c);
		
		JLabel emailValue = new JLabel("3");
		c.gridx=1;
		c.gridy=3;
		panel2.add(emailValue, c);
		
		JLabel phoneNoValue = new JLabel("4");
		c.gridx=1;
		c.gridy=4;
		panel2.add(phoneNoValue, c);
		
		JLabel jobPosValue = new JLabel("5");
		c.gridx=1;
		c.gridy=5;
		panel2.add(jobPosValue, c);
		
		JLabel daysUnavailableValue = new JLabel("6");
		c.gridx=1;
		c.gridy=6;
		panel2.add(daysUnavailableValue, c);
		
		JLabel daysPrefferdOfValue = new JLabel("7");
		c.gridx=1;
		c.gridy=7;
		panel2.add(daysPrefferdOfValue, c);
		
		JLabel minWorkingHoursValue = new JLabel("8");
		c.gridx=1;
		c.gridy=8;
		panel2.add(minWorkingHoursValue, c);
		
		JLabel maxWorkingHoursValue = new JLabel("9");
		c.gridx=1;
		c.gridy=9;
		panel2.add(maxWorkingHoursValue, c);
		
		JLabel hoursWorkedValue = new JLabel("10");
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
		
		JButton updateEmployee = new JButton("Update Employee");
		updateEmployee.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buildUpdateEmployeeWindow();
				
			}
		});
		c.gridx=1;
		c.gridy=3;
		panel.add(updateEmployee, c);
		
		JButton removeEmployee = new JButton("Remove Employee");
		c.gridx=2;
		c.gridy=3;
		panel.add(removeEmployee, c);
		
		JButton manageDatesOff = new JButton("Manage Dates Off");
		manageDatesOff.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buildManageDaysOffWindow();
				
			}
		});
		c.gridx=3;
		c.gridy=3;
		panel.add(manageDatesOff, c);
		
		JButton cancel = new JButton("cancel");
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
		
		String column_names[]= {""};
		DefaultTableModel table_model=new DefaultTableModel(column_names,50);
		JTable employeesAvailable = new JTable(table_model);
		c.gridx=0;
		c.gridy=1;
		JScrollPane sp = new JScrollPane(employeesAvailable);
		sp.setPreferredSize(new Dimension(260, 200));
		panel.add(sp, c);
		
		JButton remove = new JButton("Remove");
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
		
		JLabel date = new JLabel("Date");
		c.gridx=0;
		c.gridy=6;
		panel.add(date, c);
		
		JLabel from = new JLabel("From");
		c.gridx=0;
		c.gridy=7;
		panel.add(from, c);
		
		JTextField fromInput = new JTextField(); 
		c.gridx=0;
		c.gridy=8;
		panel.add(fromInput, c);
		
		JLabel to = new JLabel("To*");
		c.gridx=0;
		c.gridy=9;
		panel.add(to, c);
		
		JTextField toInput = new JTextField(); 
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
		
		JTextField firstNames = new JTextField("First Names");
		c.gridx = 0;
		c.gridy = 0;
		firstNames.setPreferredSize(new Dimension(150, 20));
		panel.add(firstNames, c);
		
		c.gridwidth = 2;
		
		JTextField lastName = new JTextField("Last Name");
		c.gridx = 2;
		c.gridy = 0;
		lastName.setPreferredSize(new Dimension(150, 20));
		panel.add(lastName, c);
		
		JTextField email = new JTextField("Email");
		c.gridx = 0;
		c.gridy = 1;
		email.setPreferredSize(new Dimension(150, 20));
		panel.add(email, c);
		
		JTextField phoneNo = new JTextField("PhoneNo");
		c.gridx = 2;
		c.gridy = 1;
		phoneNo.setPreferredSize(new Dimension(150, 20));
		panel.add(phoneNo, c);
		
		JTextField minWorkHours = new JTextField("Min Hours");
		c.gridx = 0;
		c.gridy = 2;
		minWorkHours.setPreferredSize(new Dimension(150, 20));
		panel.add(minWorkHours, c);
		
		JTextField maxWorkHours = new JTextField("Max Hours");
		c.gridx = 2;
		c.gridy = 2;
		maxWorkHours.setPreferredSize(new Dimension(150, 20));
		panel.add(maxWorkHours, c);
		
		JTextField workPos = new JTextField("Work Position");
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
		
		JLabel prefferedDaysOff = new JLabel("Preffered Days Off");
		c.gridx=0;
		c.gridy=7;
		panel.add(prefferedDaysOff, c);
		
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
		
		JTextField firstNames = new JTextField("Bjorn");
		c.gridx = 0;
		c.gridy = 0;
		firstNames.setPreferredSize(new Dimension(150, 20));
		panel.add(firstNames, c);
		
		c.gridwidth = 2;
		
		JTextField lastName = new JTextField("Smith");
		c.gridx = 2;
		c.gridy = 0;
		lastName.setPreferredSize(new Dimension(150, 20));
		panel.add(lastName, c);
		
		JTextField email = new JTextField("bs@gmail.com");
		c.gridx = 0;
		c.gridy = 1;
		email.setPreferredSize(new Dimension(150, 20));
		panel.add(email, c);
		
		JTextField phoneNo = new JTextField("1234567");
		c.gridx = 2;
		c.gridy = 1;
		phoneNo.setPreferredSize(new Dimension(150, 20));
		panel.add(phoneNo, c);
		
		JTextField minWorkHours = new JTextField("43");
		c.gridx = 0;
		c.gridy = 2;
		minWorkHours.setPreferredSize(new Dimension(150, 20));
		panel.add(minWorkHours, c);
		
		JTextField maxWorkHours = new JTextField("109");
		c.gridx = 2;
		c.gridy = 2;
		maxWorkHours.setPreferredSize(new Dimension(150, 20));
		panel.add(maxWorkHours, c);
		
		JTextField workPos = new JTextField("DisWasher");
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
		mon.setSelected(true);
		panel.add(mon, c);
		
		JCheckBox tues = new JCheckBox("Tuesday");
		c.gridx = 1;
		c.gridy = 5;
		tues.setSelected(false);
		panel.add(tues, c);
		
		JCheckBox wed = new JCheckBox("Wednesday");
		c.gridx = 2;
		c.gridy = 5;
		wed.setSelected(true);
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
		sat1.setSelected(true);
		panel.add(sat1, c);
		
		JCheckBox sun1 = new JCheckBox("Sunday");
		c.gridx = 2;
		c.gridy = 9;
		sun1.setSelected(true);
		panel.add(sun1, c);
		
		JButton save = new JButton("Save");
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
