package gui;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu {

	public static void main(String [] args){
		Menu test = new Menu();
		test.buildWindow();
			
	}
	
	
	
	public void buildWindow(){
		JFrame frame = new JFrame("Fusion Restaurant");
		frame.setSize(250, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		JPanel panel = new JPanel(new GridBagLayout());
		frame.getContentPane().add(panel, BorderLayout.WEST);
		GridBagConstraints c = new GridBagConstraints();
		c.weighty=1;
		
		c.insets= new Insets(0,20,0,0);
		JLabel title = new JLabel("Menu");
		title.setFont(new Font("", Font.PLAIN, 20));
		c.gridx=2;
		c.gridy=0;
		panel.add(title, c);
		
		JButton schedule = new JButton("Manage Schedule");
		schedule.setFont(new Font("", Font.PLAIN, 20));
		
		schedule.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				ManageScheduleGUI test = new ManageScheduleGUI();
				test.buildWindow();
				
				
			}
		});
		
		c.gridwidth = 3;
		c.gridx=0;
		c.gridy=1;
		panel.add(schedule, c);
		
		
		JButton employee = new JButton("Manage Employee");
		employee.setFont(new Font("", Font.PLAIN, 20));
		employee.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				ManageEmployeeGUI test = new ManageEmployeeGUI();
				test.buildMainWindow();
				
			}
		});
		c.gridwidth = 3;
		c.gridx=0;
		c.gridy=2;
		panel.add(employee, c);
		
		frame.setVisible(true);
	}
	
	
}
