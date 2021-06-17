package com.CommonUtils;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.MouseAdapter;
	import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
	import javax.swing.JTable;
	import javax.swing.JTextField;
	import javax.swing.table.DefaultTableModel;

	public class TestCaseSelectorPopUp {
		public static JLabel tripLabel = new JLabel();
		public static JPanel panel;
		public static JComboBox<Object> envDropDown;
		public static JComboBox<Object> browserDropDown;
		public static JComboBox<Object> apiDropDown;
		public static JTextField testCaseEditBx;
		public static DefaultComboBoxModel<Object> envLabel;
		public static DefaultComboBoxModel<Object> mailLabel;
		public static DefaultComboBoxModel<Object> browserLabel;
		public static DefaultComboBoxModel<Object> apiLabel;
		
		private static Object[] envOptions;
		private static Object[] mailSendOptions;
		private static Object[] browserOptions;
		private static Object[] apiOptions;
		
		
		
		static String[] input=new String[4];

	

	/**
	* This method helps to generate Table with buttons like Add, Update, Delete
	 * @return 
	*/
	public static String[] generateJTable() {

	// create Jpanel and JTable
	JLabel tripLabel = new JLabel();
	JPanel panel = new JPanel();
	final JTable table = new JTable();

	// create a table model and set a Column Identifiers to this model
	Object[] columns = { "Environment", "Browser", "Testcase Number","ApiRun" };
	final DefaultTableModel model = new DefaultTableModel();
	model.setColumnIdentifiers(columns);

	// set the model to the table
	table.setModel(model);

	// Change A JTable Background Color, Font Size, Font Color, Row Height
	table.setBackground(Color.white);
	table.setForeground(Color.black);
	Font font = new Font("", 1, 15);
	table.setFont(font);
	table.setRowHeight(20);
	// create JScrollPane
		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(0, 0, 880, 200);

		panel.setLayout(null);

		panel.add(pane);
		
         browserOptions = new Object[] {"Please select Browser", "Firefox","Chrome","IE", "Edge"};
		browserLabel = new DefaultComboBoxModel<Object>(browserOptions);
		browserDropDown = new JComboBox<Object>(browserLabel);
		
		envOptions = new Object[] {"No Environment Selected", "Model-A","Model-B","Model-C", "Staging"};
		envLabel = new DefaultComboBoxModel<Object>(envOptions);
		envDropDown = new JComboBox<Object>(envLabel);
		
		apiOptions = new Object[] {"Please select APIs option", "Yes","No"};
		apiLabel = new DefaultComboBoxModel<Object>(apiOptions);
		apiDropDown = new JComboBox<Object>(apiLabel);
		
		

	// create JTextFields to hold the value
	final JTextField testCaseEditBx = new JTextField("Enter test case number");
	

	// create JButtons to add the action
	JButton btnAdd = new JButton("Add");
	JButton btnDelete = new JButton("Delete");
	JButton btnUpdate = new JButton("Update");

	panel.setLayout(null);
	btnAdd.setBounds(100, 220, 100, 25);
	btnUpdate.setBounds(200, 265, 100, 25);
	btnDelete.setBounds(300, 310, 100, 25);
	
	GridBagLayout layout = new GridBagLayout();
	panel.setLayout(layout);
	
	int top=20;
	int left = 20;
	int bottom = 2;
	int right = 10;
	
	GridBagConstraints gbc=new GridBagConstraints();
	gbc.insets = new Insets(top,left,bottom,right);
	
	layout.setConstraints(panel, gbc);

	gbc.fill = GridBagConstraints.HORIZONTAL;
	gbc.gridx = 0;
	gbc.gridy = 1;
	panel.add(browserDropDown,gbc);
	
	gbc.gridx = 0;
	gbc.gridy = 2;
	panel.add(envDropDown,gbc);
	
	gbc.gridx = 0;
	gbc.gridy = 3;
	panel.add(apiDropDown,gbc);
	
	gbc.gridx = 0;
	gbc.gridy = 4;
	panel.add(testCaseEditBx,gbc);
	
	gbc.fill = GridBagConstraints.HORIZONTAL;
	gbc.gridx = 1;
	gbc.gridy = 1;
	gbc.gridwidth =1;
	gbc.gridheight =1;
	panel.add(btnAdd,gbc);
	
	gbc.gridx = 1;
	gbc.gridy = 2;
	gbc.gridwidth =1;
	gbc.gridheight =1;
	panel.add(btnUpdate,gbc);
	
	gbc.gridx = 1;
	gbc.gridy = 3;
	gbc.gridwidth =1;
	gbc.gridheight =1;
	panel.add(btnDelete,gbc);
	
	panel.add(tripLabel);
	panel.setLocation(150,150);
	panel.setVisible(true);
	
	

	

	// create an array of objects to set the row data
 final Object[] row = new Object[4];

	// button add row - Clicked on Add Button
	btnAdd.addActionListener(new ActionListener() {

	public void actionPerformed(ActionEvent e) {

	row[0] = envDropDown.getSelectedItem().toString();
	row[1] = browserDropDown.getSelectedItem().toString();
	row[2] = testCaseEditBx.getText();
	row[3] = apiDropDown.getSelectedItem().toString();

	// add row to the model
	model.addRow(row);
	}
	});

	// button remove row - Clicked on Delete Button
	btnDelete.addActionListener(new ActionListener() {

	public void actionPerformed(ActionEvent e) {

	// i = the index of the selected row
	int i = table.getSelectedRow();
	if (i >= 0) {
	// remove a row from jtable
	model.removeRow(i);
	} else {
	System.out
	.println("There were issue while Deleting the Row(s).");
	}
	}
	});

	// get selected row data From table to textfields
	table.addMouseListener(new MouseAdapter() {

	@Override
	public void mouseClicked(MouseEvent e) {

	// i = the index of the selected row
	int i = table.getSelectedRow();

	envDropDown.setSelectedItem(model.getValueAt(i, 0).toString());
	browserDropDown.setSelectedItem(model.getValueAt(i, 1).toString());
	testCaseEditBx.setText(model.getValueAt(i, 2).toString());
	apiDropDown.setSelectedItem(model.getValueAt(i, 3).toString());
	}
	});

	// button update row - Clicked on Update Button
	btnUpdate.addActionListener(new ActionListener() {

	public void actionPerformed(ActionEvent e) {

	// i = the index of the selected row
	int i = table.getSelectedRow();

	if (i >= 0) {
	model.setValueAt(envDropDown.getSelectedItem().toString(), i, 0);
	model.setValueAt(browserDropDown.getSelectedItem().toString(), i, 1);
	model.setValueAt(testCaseEditBx.getText(), i, 2);
	model.setValueAt(apiDropDown.getSelectedItem().toString(), i, 3);
	} else {
	System.out.println("Update Error");
	}
	}
	});

	
	
	int result_1 = JOptionPane.showConfirmDialog(null,panel,"fdsf",JOptionPane.OK_CANCEL_OPTION,JOptionPane.DEFAULT_OPTION);
	switch(result_1)
	{
	case JOptionPane.OK_OPTION:
		
		String environment = (String) model.getValueAt(0, 0);
		String browser = (String) model.getValueAt(0, 1);
		String testcasenos = (String) model.getValueAt(0, 2);
		String apisRun = (String) model.getValueAt(0, 3);
		
		for(int i=1;i<model.getRowCount();i++) {
			environment = environment + ":"+ model.getValueAt(i, 0);
			browser = browser + ":"+ model.getValueAt(i, 1);
			testcasenos = testcasenos + ":"+ model.getValueAt(1, 2);
			apisRun = apisRun + ":"+ model.getValueAt(i, 3);
			
		}
		input[0] = browser;
		input[1] = environment;
		input[2] = testcasenos;
		input[3] = apisRun;
		
		
		return input;
	case JOptionPane.CANCEL_OPTION:
		System.out.println("****Please click OK to execute test cases *****");
		System.exit(result_1);
		
		break;
	}
		
	
	
	return input;
	}
	
	
	
	}
	


