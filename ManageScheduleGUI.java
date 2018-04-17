import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class ManageScheduleGUI {
	public static void main(String [] args){
		ManageScheduleGUI test = new ManageScheduleGUI();
		test.buildWindow();
		
	}
	
	public void buildWindow(){
		JFrame frame = new JFrame("Fusion Reataurant");
		frame.setSize(1600, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		JPanel panel = new JPanel(new GridBagLayout());
		frame.getContentPane().add(panel, BorderLayout.WEST);
		GridBagConstraints c = new GridBagConstraints();
		c.weighty=1;
	
		c.insets= new Insets(0,20,0,0);
		JLabel title = new JLabel("Month");
		title.setFont(new Font("", Font.PLAIN, 20));
		c.gridwidth = 5;
		c.gridx=1;
		c.gridy=0;
		panel.add(title, c);
		c.gridwidth = 1;
		JLabel title2 = new JLabel("Date Information");
		title2.setFont(new Font("", Font.PLAIN, 20));
		c.gridx=7;
		c.gridy=0;
		panel.add(title2, c);
		JButton left = new JButton("<");
		c.gridwidth = 1;
		c.gridx=0;
		c.gridy=1;
		panel.add(left, c);
		
		
		
		String column_names[]= {"Mon 1/15","Tues 2/5","Wed 3/5","Thur 4/5","Fri 5/5","Sat 6/5", "Sun 7/5"};
		DefaultTableModel table_model=new DefaultTableModel(column_names,30);
		JTable table = new JTable(table_model);
		c.gridwidth = 5;
		c.gridheight=4;
		c.gridx=1;
		c.gridy=1;
		JScrollPane sp = new JScrollPane(table);
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
		JLabel date = new JLabel("Thursday, 4th May 2017");
		c.gridx=7;
		c.gridy=1;
		panel.add(date, c);
		
		String column_names2[]= {"","No. Of Guests","Est. Required Shifts"};
		DefaultTableModel table_model2=new DefaultTableModel(column_names2,10);
		JTable table2 = new JTable(table_model2);
		c.gridwidth = 2;
		c.gridheight=2;
		c.gridx=7;
		c.gridy=2;
		JScrollPane sp2 = new JScrollPane(table2);
		sp2.setPreferredSize(new Dimension(400, 200));
		panel.add(sp2, c);
		
		
		
		/*
		JTable table2 = new JTable(3,3);
		c.gridwidth = 2;
		c.gridheight = 2;
		c.gridx=5;
		c.gridy=2;
		panel.add(table2, c);*/
		
		
		
		
		JButton newShift = new JButton("NewShift");
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx=7;
		c.gridy=4;
		panel.add(newShift, c);
		JButton removeShift = new JButton("Remove Shift");
		c.gridx=8;
		c.gridy=4;
		panel.add(removeShift, c);
		JSeparator sep = new JSeparator();
		sep.setPreferredSize(new Dimension(20,30));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 20;
		c.gridx=0;
		c.gridy=5;
		panel.add(sep, c);
		c.gridwidth = 2;
		JLabel shiftInformation = new JLabel("Shift Information");
		shiftInformation.setFont(new Font("", Font.PLAIN, 20));
		c.gridx=0;
		c.gridy=6;
		panel.add(shiftInformation, c);
		c.gridwidth=1;
		
		JLabel employees = new JLabel("Employees");
		employees.setFont(new Font("", Font.PLAIN, 20));
		c.gridx=4;
		c.gridy=6;
		panel.add(employees, c);
		
		String[] choices = { "CHOICE 1","CHOICE 2"};
	    JComboBox<String> cb = new JComboBox<String>(choices);
	    c.gridx=5;
		c.gridy=6;
	    cb.setVisible(true);
	    panel.add(cb, c);

	    JLabel assignedEmployeesTitle = new JLabel("Assigned Employees:");
		c.gridx=0;
		c.gridy=7;
		panel.add(assignedEmployeesTitle, c);
		
		JLabel assignedEmployees = new JLabel("-");
		c.gridx=1;
		c.gridy=7;
		panel.add(assignedEmployees, c);
		
		JLabel startTimeTitle = new JLabel("Start Time:");
		c.gridx=0;
		c.gridy=8;
		panel.add(startTimeTitle, c);
		
		String[] choicesStartTime = { "17:00","20:00"};
	    JComboBox<String> startTime = new JComboBox<String>(choicesStartTime);
	    c.gridx=1;
		c.gridy=8;
	    startTime.setVisible(true);
	    panel.add(startTime, c);
		
	    JLabel locationTitle = new JLabel("Location:");
		c.gridx=0;
		c.gridy=9;
		panel.add(locationTitle, c);
		
		String[] choiceLocation = { "Restaurant","Banquet", "Atrium"};
	    JComboBox<String> location = new JComboBox<String>(choiceLocation);
	    c.gridx=1;
		c.gridy=9;
	    startTime.setVisible(true);
	    panel.add(location, c);
	    
	    JLabel shiftTypeTitle = new JLabel("Shift Type:");
		c.gridx=0;
		c.gridy=10;
		panel.add(shiftTypeTitle, c);
		
		String[] choiceShiftType = {"Dishwasher", "Waiter"};
	    JComboBox<String> shiftType = new JComboBox<String>(choiceShiftType);
	    c.gridx=1;
		c.gridy=10;
	    startTime.setVisible(true);
	    panel.add(shiftType, c);
	    
	    JLabel estimatedShiftLenghTitle = new JLabel("Estimated Shift Length:");
		c.gridx=0;
		c.gridy=11;
		panel.add(estimatedShiftLenghTitle, c);
		
		JLabel estimatedShiftLengt = new JLabel("your whole fucking weekend");
		c.gridx=1;
		c.gridy=11;
		panel.add(estimatedShiftLengt, c);
		
		JButton addEmployee = new JButton("< Add Employee");
		c.gridx=3;
		c.gridy=7;
		panel.add(addEmployee, c);
		
		JButton removeEmployee = new JButton("Remove Employee >");
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
		String column_names3[]= {"Name","Hours Worked","Scheduled hours est.","Hours needed est."};
		DefaultTableModel table_model3=new DefaultTableModel(column_names3,50);
		JTable employeesAvailable = new JTable(table_model3);
		c.gridwidth = 3;
		c.gridheight=7;
		c.gridx=4;
		c.gridy=7;
		JScrollPane sp3 = new JScrollPane(employeesAvailable);
		sp3.setPreferredSize(new Dimension(500, 200));
		panel.add(sp3, c);
		
		JLabel dialogBox = new JLabel("It was you not us, who fucked something up");
		c.gridx=0;
		c.gridy=12;
		panel.add(dialogBox, c);
		
		JButton save = new JButton("Save");
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
		
		
		
		
		
		frame.setVisible(true);
		
		
		
		
	}
}
