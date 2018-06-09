import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JTextField;

public class ClientData extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private Integer firstresultninedigit;
	private Integer ninedig;

	Connection connection = null;
	private JTextField editClientField;
	private JTextField textField_1;
	
	public ClientData(String fn, String ln) throws SQLException {
		connection = SQliteConnection.dbConnector();
		initialize(fn, ln);
	}
	
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
	private void initialize(String fn, String ln) {
		frame = new JFrame();
		frame.setBounds(100, 100, 551, 328);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		firstresultninedigit = 0;
		String clients = "";
		try {
			String clientquery = "SELECT * FROM Clients WHERE FirstName=? AND LastName=?";
			PreparedStatement pst=connection.prepareStatement(clientquery);
			pst.setString(1, fn);
			pst.setString(2, ln);
			ResultSet rs = pst.executeQuery();

			int count = 0;
			while (rs.next()) {
				count+=1;
				String personchild = "";
				Integer ischild = rs.getObject(13) != null ? rs.getInt(13) : null;
				String firstlast = rs.getString(1)+" "+rs.getString(2);
				String yesno = "";
				String countfirstlast = Integer.toString(count) + "." + "\n" + "Name: " + firstlast;
				String ninedigit = "\n"+"Health Card Number: "+rs.getInt(3);
				ninedig = rs.getInt(3);
				if (count == 1) {
					firstresultninedigit = ninedig;
				}
				String phonenumber = "\n"+"Telephone Number: "+rs.getString(4);
				String streetaddress = "\n"+"Street Address: "+rs.getString(5);
				String city = "\n"+"City: "+rs.getString(6);
				String postalcode = "\n"+"Postal Code: "+rs.getString(7);
				String country = "\n"+"Country of Origin: "+rs.getString(8);
				String dateofbirth = "\n"+"Date of Birth: "+rs.getString(9);
				String spouseninedigit = "\n"+"Spouse 9 Digit Health Card #: "+rs.getString(10);
				
				String spousename = "\n" + "Spouse Name: ";

				if (isNumeric(rs.getString(10))) {
					try {
					String spousenamequery = "Select * FROM Clients WHERE NineDigit=?";
					PreparedStatement snpst = connection.prepareStatement(spousenamequery);
					Integer spouseninedigitInt = Integer.parseInt(rs.getString(10));
					snpst.setInt(1, spouseninedigitInt);
					ResultSet snrs = snpst.executeQuery();
					if (Integer.toString(spouseninedigitInt).length() == 9) {
					
					while (snrs.next()) {
						String spouseFirstName = snrs.getString(1);
						String spouseLastName = snrs.getString(2);
						spousename += spouseFirstName + " " + spouseLastName;
					}
					if (!snrs.next()) {
						snrs.close();
						snpst.close();
							}
						}
					snrs.close();
					snpst.close();
					}
					catch (Exception e) {
						JOptionPane.showMessageDialog(null, e);
					}
					}
				
				String sixdigit = "\n"+"Family 6 Digit Health Card Number: "+rs.getString(11);
				String gender = "\n" + "Gender: " + rs.getString("Gender");
				Integer haschildren = rs.getInt(12);
				if (haschildren == 1) {
					yesno = "Yes";
				}
				else if (haschildren == 0) {
					yesno = "No";
				}
				String doeshaschildren = "\n"+"Has Children: "+yesno;
				String parentname = rs.getString(14);
				String parentname2 = rs.getString("ParentName2");
				String childstatus = "\n"+"Child Status: Child of "+parentname+" "+parentname2;
				
				String cheque1number = "\n"+"Cheque Number: "+rs.getString("Cheque1Number");
				String cheque1num = rs.getString("Cheque1Number");
				String cheque1amount = "\n"+"Cheque Amount: $"+rs.getString("Cheque1Amount");
				String cheque1date = "\n"+"Cheque Date: "+rs.getString("Cheque1Date");
				String cheque2number = "\n"+"Cheque #2 Number: "+rs.getString("Cheque2Number");
				String cheque2num = rs.getString("Cheque2Number");
				String cheque2amount = "\n"+"Cheque #2 Amount: $"+rs.getString("Cheque2Amount");
				String cheque2date = "\n"+"Cheque #2 Date: "+rs.getString("Cheque2Date");
				String cheque3number = "\n"+"Cheque #3 Number: "+rs.getString("Cheque3Number");
				String cheque3num = rs.getString("Cheque3Number");
				String cheque3amount = "\n"+"Cheque #3 Amount: $"+rs.getString("Cheque3Amount");
				String cheque3date = "\n"+"Cheque #3 Date: "+rs.getString("Cheque3Date");
				String additionalinfo = "\n"+"Additional Information: "+rs.getString("AdditionalInfo");
				String additionalinformation = rs.getString("AdditionalInfo");
					
				personchild = countfirstlast + ninedigit + gender + phonenumber + streetaddress + city + postalcode + country + dateofbirth + spousename + spouseninedigit + sixdigit + doeshaschildren;
				
				if (!cheque1num.equals("")) {
					personchild+=cheque1number + cheque1amount + cheque1date;
				}
				if (!cheque2num.equals("")) {
					personchild+=cheque2number + cheque2amount + cheque2date;
				}
				if (!cheque3num.equals("")) {
					personchild+=cheque3number + cheque3amount + cheque3date;
				}
				if (!additionalinformation.equals("")) {
					personchild+=additionalinfo;
				}
				
				String childrenstring = "";
				
				if (ischild == 1) {
					personchild += childstatus;
				}
				
				//if the person has children, search for the children, add them next
				if (haschildren == 1) {
					String childquery = "select * FROM Clients WHERE IsChild=1 AND ParentName=?";
					PreparedStatement cpst=connection.prepareStatement(childquery);
					cpst.setString(1, firstlast);
					ResultSet crs = cpst.executeQuery();
					int ccount = 0;
					while (crs.next()) {
						ccount += 1;
						firstlast = crs.getString(1)+" "+crs.getString(2);
						String cfirstlast = "\n" + Integer.toString(ccount) + "." + "\n" + "Name: " + crs.getString(1)+" "+crs.getString(2);
						ninedigit = "\n"+"Health Card Number: "+crs.getInt(3);
						gender = "\n" + "Gender: " + crs.getString("Gender");
						parentname = crs.getString(14);
						parentname2 = crs.getString("ParentName2");
						dateofbirth = "\n"+"Date of Birth: "+crs.getString(9);
						if (parentname2 != null) {
						childstatus = "\n"+"Child status: Child of "+parentname+" and "+parentname2;
						}
						else if (parentname2 == null) {
							childstatus = "\n"+"Child status: Child of "+parentname;
						}
						childrenstring = "      " + cfirstlast + "      " + ninedigit + "      " + dateofbirth + "      " + childstatus;
					}
					
					crs.close();
					cpst.close();
				}
				personchild += childrenstring + "\n";
				clients += personchild + "\n";
				personchild = "";
			}
				rs.close();
				pst.close();		
			}
		catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, e);
			}
		
		JLabel lblNewLabel = new JLabel("Information on Client/s with Name: "+fn + " " + ln);
		lblNewLabel.setBounds(0, 0, 444, 22);
		frame.getContentPane().add(lblNewLabel);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		JButton btnEditClientInformation = new JButton("Edit Client Information");
		btnEditClientInformation.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnEditClientInformation.setBounds(472, 348, 168, 22);
		btnEditClientInformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
				String checkifexists = "SELECT * FROM Clients WHERE NineDigit=?";
				Integer ninedigitnumber = 0;
				if (!isNumeric(editClientField.getText())) {
					JOptionPane.showMessageDialog(null, "Please only numeric characters");
				}
				else if (isNumeric(editClientField.getText())) {
				ninedigitnumber = Integer.parseInt(editClientField.getText());
				}
				int length = editClientField.getText().length();
				if (length != 9)
				{
					JOptionPane.showMessageDialog(null, "Please enter a 9 digit number");
				}	
				else if (length == 9 && isNumeric(editClientField.getText()))				
				{
					PreparedStatement ciepst=connection.prepareStatement(checkifexists);
					ciepst.setString(1, editClientField.getText() );
					ResultSet ciers=ciepst.executeQuery();
					int count =0;
					
					while(ciers.next()) {
						count=count+1;
					}
					if(count >=1)
					{
						new EditClientInfo(ninedigitnumber);
						frame.dispose();
						ciers.close();
						ciepst.close();
					}
					else if(count==0)
					{
						JOptionPane.showMessageDialog(null, "No results found");
					}
				}
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		frame.getContentPane().add(btnEditClientInformation);
		
		
		JButton btnBackToSearch = new JButton("Back to Search Screen");
		btnBackToSearch.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnBackToSearch.setBounds(461, 0, 186, 31);
		btnBackToSearch.addActionListener(new ActionListener() {
			private String[] args;

			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				Screen1.main(args);
			}
		});
		frame.getContentPane().add(btnBackToSearch);
		
		final JTextArea textArea = new JTextArea();
		textArea.setText(clients);
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textArea.setWrapStyleWord(true);
		textArea.setBounds(15, 40, 500, 200);
		
		JPanel panel = new JPanel();
		panel.add(textArea);
		
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(17, 41, 631, 263);
		textArea.setCaretPosition(0);
		
		frame.getContentPane().add(scrollPane);
		
		JButton btnEditFirstClient = new JButton("Edit First Client Listed");
		btnEditFirstClient.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnEditFirstClient.setBounds(17, 309, 186, 31);
		btnEditFirstClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				new EditClientInfo(firstresultninedigit);
				}
			});
		frame.getContentPane().add(btnEditFirstClient);
		
		JLabel lblEnterA = new JLabel("Enter a 9 digit health card number of a client to edit:");
		lblEnterA.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblEnterA.setBounds(17, 349, 300, 23);
		frame.getContentPane().add(lblEnterA);
		
		editClientField = new JTextField();
		editClientField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		editClientField.setBounds(327, 349, 135, 20);
		frame.getContentPane().add(editClientField);
		editClientField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Enter a 9 digit health card number of a client to delete:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(17, 373, 314, 23);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		textField_1.setColumns(10);
		textField_1.setBounds(327, 373, 135, 20);
		frame.getContentPane().add(textField_1);
		
		JButton btnDeleteClientRecord = new JButton("Delete Client Record");
		btnDeleteClientRecord.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnDeleteClientRecord.setBounds(472, 373, 168, 22);
		btnDeleteClientRecord.addActionListener(new ActionListener() {
			private String[] args;
			public void actionPerformed(ActionEvent arg0) {
				Integer ninedigitdelete = Integer.parseInt(textField_1.getText());
				int length = textField_1.getText().length();
				try {
					if (length != 9)
					{
						JOptionPane.showMessageDialog(null, "Please enter a 9 digit number");
					}	
					else if (length == 9)
					{
						String deleteclientquery = "DELETE FROM Clients WHERE NineDigit=?";
						PreparedStatement sdpst=connection.prepareStatement(deleteclientquery);
						sdpst.setString(1, Integer.toString(ninedigitdelete));
						sdpst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Client Record Deleted");
						frame.dispose();
						sdpst.close();
						String clientquery = "SELECT * FROM Clients WHERE FirstName=? AND LastName=?";
						PreparedStatement adpst=connection.prepareStatement(clientquery);
						adpst.setString(1, fn);
						adpst.setString(2, ln);
						ResultSet adrs = adpst.executeQuery();
						if (adrs.next()) {
							new ClientData(fn, ln);
							adrs.close();
							adpst.close();
						}
						else if (!adrs.next()) {
							Screen1.main(args);
							adrs.close();
							adpst.close();
						}
					}
				}catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		frame.getContentPane().add(btnDeleteClientRecord);
		
		JLabel lblNewLabel_2 = new JLabel("(Deletes cannot be undone)");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		lblNewLabel_2.setBounds(472, 394, 187, 23);
		frame.getContentPane().add(lblNewLabel_2);
		
		
		frame.setSize(700, 500);
		frame.setVisible(true);

	}
}

