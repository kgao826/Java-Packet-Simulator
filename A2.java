import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.*; 
import java.io.File;
import java.util.*;
import javax.swing.table.DefaultTableModel;

/**
 * <p> This class creates a program in a JFrame window on the running operating system.
 * The program allows the user to import a file of Packets preprovided in a specific 
 * format and analylize that data to show a table of data which the user can select 
 * options to alter the displayed data. </p>
 * 
 * <p> This class requires the following classes to run: Host, Packet, Simulator, 
 * PacketTableModel classes. </p>
 * 
 * @author Kevin A Gao
 */

public class A2 extends JFrame{

	private Object[][] tableData;
	
	/**
	 * <p>This constructor creates the UI window and program on the operating system.</p>
	 * <p>The program will contains the following user interactable items:
	 * <b>JMenu</b>: A drop down menu where you can select a file for import and a quit button.
	 * <b>JRadioButton</b>: Two radio buttons to choose between <i>Source Hosts</i> and <i>Destination Hosts</i>
	 * <b>JComboBox</b>: A combobox hidden at first, the shown to allow users to select <i>Host Ip Addresses</i>
	 * <b>JTable</b>: A table that shows all the data based on the selection of radio button and combolist components.
	 * </p>
	 */
	public A2() {
		Color lightGrey = new Color(204, 204, 204);

		//Radiobuttons
		JRadioButton sourceHostsRadioButton = new JRadioButton("Source hosts", true);
		JRadioButton destHostsRadioButton = new JRadioButton("Destination hosts");
		sourceHostsRadioButton.setBackground(lightGrey);
		destHostsRadioButton.setBackground(lightGrey);
		JPanel radioButtonPanel = new JPanel();
		radioButtonPanel.add(sourceHostsRadioButton);
		radioButtonPanel.add(destHostsRadioButton);
		ButtonGroup radioButtons = new ButtonGroup();
		radioButtons.add(sourceHostsRadioButton);
		radioButtons.add(destHostsRadioButton);
		
		//ComboBox
		JComboBox addressesComboBox = new JComboBox();
		radioButtonPanel.add(addressesComboBox);
		addressesComboBox.setVisible(false);
		
		//Table
		JTable table = new JTable();
		DefaultTableModel tableModel = new DefaultTableModel(){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVisible(false);
		String[] columnHeaderSource = {"Time Stamp", "Destination IP Address", "Packet Size"};
		String[] columnHeaderDest = {"Time Stamp", "Source IP Address", "Packet Size"};

		//Menu
		JMenu menu = new JMenu("File");
		JMenuBar menuBar = new JMenuBar();
		JMenuItem open, quit;
		open = new JMenuItem("Open trace file");
		quit = new JMenuItem("Quit");
		menu.add(open);
		menu.add(quit);
		menuBar.add(menu);
		setJMenuBar(menuBar);
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				dispose();
			}
		});
		
		//frame format	
		setLayout(new BorderLayout());
		setName("A2");
		radioButtonPanel.setBackground(lightGrey);
		getContentPane().add(radioButtonPanel, BorderLayout.PAGE_START);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 500);
		setVisible(true);

		//Opens the file
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				File file;
				JFileChooser chooser = new JFileChooser();
				int value = chooser.showOpenDialog(A2.this);
				if (value == JFileChooser.APPROVE_OPTION) {
					file = chooser.getSelectedFile();
					
					//update upon initial selection
					tableModel.setRowCount(0);
					tableModel.setColumnIdentifiers(columnHeaderSource);
					sourceHostsRadioButton.setSelected(true);
					String[] comboBoxList = getComboBoxlist(file, true);
					addressesComboBox.removeAllItems();
					for(String item : comboBoxList){
						addressesComboBox.addItem(item);
					}
					String host = String.valueOf(addressesComboBox.getSelectedItem());
					tableData = getTableDataArray(file, host, true);
					for (int i = 0; i < tableData.length; i ++){
						tableModel.addRow(tableData[i]);
					}
					addressesComboBox.setVisible(true);
					scrollPane.setVisible(true);
					
					//radioButton listeners for further selections
					sourceHostsRadioButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							String[] comboBoxList = getComboBoxlist(file, true);
							addressesComboBox.removeAllItems();
							for(String item : comboBoxList){
								addressesComboBox.addItem(item);
							}
							tableModel.setColumnIdentifiers(columnHeaderSource);
						}
					});
					destHostsRadioButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							String[] comboBoxList = getComboBoxlist(file, false);
							addressesComboBox.removeAllItems();
							for(String item : comboBoxList){
								addressesComboBox.addItem(item);
							}
							tableModel.setColumnIdentifiers(columnHeaderDest);
						}
					});
					
					//ComboBox Listener
					addressesComboBox.addActionListener(new ActionListener () {
						public void actionPerformed(ActionEvent event) {
							tableModel.setRowCount(0);
							String host = String.valueOf(addressesComboBox.getSelectedItem());
							if(host != "null"){
								if(destHostsRadioButton.isSelected()){
									tableData = getTableDataArray(file, host, false);
								}
								else {
									tableData = getTableDataArray(file, host, true);
								}
								tableModel.setColumnIdentifiers(columnHeaderSource);
								for (int i = 0; i < tableData.length; i ++){
									tableModel.addRow(tableData[i]);
								}
							}
						}
					});
				}
			}
		});
	}

	/**
	 * <p>Returns a String array that will be used to populate a JComboBox.
	 * The file must be the file that is selected for import.
	 * Depending on the type of data required, the sourceData Boolean will determine
	 * whether the data required is Source (True) or Destination (False)</p>
	 * 
	 * @param file			The filepath of the chosen file
	 * @param sourceData	Whether the data is Source (True) or Destination (False)
	 * @return 				A String array of the items required in the combobox list
	 */
	public String[] getComboBoxlist(File file, Boolean sourceData){
		if(sourceData){
			Simulator packets = new Simulator(file);
			Host[] hostsInput = packets.getUniqueSortedSourceHosts();
			String[] comboBoxList = new String[hostsInput.length];
			for(int i = 0; i < hostsInput.length; i++){
				comboBoxList[i] = hostsInput[i].toString();
			}
			return comboBoxList;
		}
		else {
			Simulator packets = new Simulator(file);
			Host[] hostsInput = packets.getUniqueSortedDestHosts();
			String[] comboBoxList = new String[hostsInput.length];
			for(int i = 0; i < hostsInput.length; i++){
				comboBoxList[i] = hostsInput[i].toString();
			}
			return comboBoxList;
		}
	}
	
	/**
	 * <p>Returns a 2D array Object for use to create a tableModel. It uses the Simulator,
	 * Packet and PacketTableModel classes.
	 * The method will turn the file input into a list of Packets 
	 * it will then filter the ip addresses to only that is chosen by the user
	 * it will create a PacketTableModel out of those packets
	 * and iterate through the PacketTableModel to create the 2D array for a JTable.</p>
	 * 
	 * @param file			The filepath of the chosen file
	 * @param ip			The ip number that is to be searched
	 * @param sourceData	Whether the data is Source (True) or Destination (False)
	 * @return				A 2D array with the required table data
	 */
	public Object[][] getTableDataArray(File file, String ip, Boolean sourceData){
		Simulator packets = new Simulator(file);
		Packet[] packetsInput = packets.getTableData(ip, sourceData);
		PacketTableModel tableModel = new PacketTableModel(packetsInput, sourceData);
		Object[][] tableData = new Object[packetsInput.length + 2][3];
		for(int i = 0; i < packetsInput.length; i++){
			tableData[i][0] = tableModel.getValueAt(i, 0);
			tableData[i][1] = tableModel.getValueAt(i, 1);
			tableData[i][2] = tableModel.getValueAt(i, 2);
		}
		tableData[packetsInput.length][2] = tableModel.getValueAt(packetsInput.length, 1);
		tableData[packetsInput.length + 1][2] = tableModel.getValueAt(packetsInput.length + 1, 1);
		return tableData;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new A2();
			}
		});
	}
}