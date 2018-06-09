import java.awt.BorderLayout;
import java.awt.Color;
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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.text.DefaultCaret;
import javax.swing.JButton;
import javax.swing.JTextField;

public class FamilyDat extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFrame frame;

	Connection connection = null;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	
	public FamilyDat(Integer i) {
		connection = SQliteConnection.dbConnector();
		initialize(i);
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
	
	private void initialize(Integer i) {
		
		String totalfam="";
		
		frame = new JFrame();
		frame.setBounds(100, 100, 551, 328);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		try {
			String famquery = "select * FROM Clients WHERE SixDigit=?";
			PreparedStatement pst=connection.prepareStatement(famquery);
			pst.setInt(1, i);
			ResultSet rs = pst.executeQuery();
			int count = 0;  
			while (rs.next()) {
				count=count+1;
				
				//check it see if the person is a child first, if yes, skip them
				Integer ischild = rs.getInt(13);
				String firstlast = rs.getString(1)+" "+rs.getString(2);
				if (ischild == 1) {
					continue;
				}
				firstlast = rs.getString(1)+" "+rs.getString(2);
				String personchild = "";
				String yesno = "";
				String countfirstlast = Integer.toString(count) + "." + "\n" + "Name: " + rs.getString(1)+" "+rs.getString(2);
				String ninedigit = "\n"+"Health Card Number: "+rs.getInt(3);
				String phonenumber = "\n"+"Telephone Number: "+rs.getString(4);
				String streetaddress = "\n"+"Street Address: "+rs.getString(5);
				String city = "\n"+"City: "+rs.getString(6);
				String postalcode = "\n"+"Postal Code: "+rs.getString(7);
				String country = "\n"+"Country of Origin: "+rs.getString(8);
				String dateofbirth = "\n"+"Date of Birth: "+rs.getString(9);
				String spouseninedigit = "\n"+"Spouse 9 Digit Health Card #: "+rs.getString(10);
				String spouseninedig = rs.getString(10);
				String spousename = "";
				
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
				int haschildren = rs.getInt(12);
				if (haschildren == 1) {
					yesno = "Yes";
				}
				else if (haschildren == 0) {
					yesno = "No";
				}
				String doeshaschildren = "\n"+"Has children: "+yesno;
				String parentname = rs.getString(14);
				
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
						String parentname2 = crs.getString("ParentName2");
						dateofbirth = "\n"+"Date of Birth: "+crs.getString(9);
						String childstatus = "";
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
				totalfam += personchild + "\n";
				personchild = "";
			}
			rs.close();
			pst.close();
			}
		catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, e);
			}
		
		JLabel lblNewLabel = new JLabel("Information on Family with Health Card Number: "+Integer.toString(i));
		lblNewLabel.setBounds(0, 0, 444, 22);
		frame.getContentPane().add(lblNewLabel);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		JButton btnBackToSearch = new JButton("Back to Search Screen");
		btnBackToSearch.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnBackToSearch.setBounds(461, 0, 186, 31);
		btnBackToSearch.addActionListener(new ActionListener() {
			private String[] args;

			public void actionPerformed(ActionEvent arg0) {
				try {
					frame.dispose();
					Screen1.main(args);
				}catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, e);
				}
			}
			});
		frame.getContentPane().add(btnBackToSearch);
		
		JLabel lblEnterHereA = new JLabel("Enter here a new six digit family health card number to give this family:");
		lblEnterHereA.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblEnterHereA.setBounds(17, 309, 411, 23);
		frame.getContentPane().add(lblEnterHereA);
		
		JLabel lblEnterA = new JLabel("Enter a 9 digit health card number of a client to edit:");
		lblEnterA.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblEnterA.setBounds(17, 335, 300, 23);
		frame.getContentPane().add(lblEnterA);
		
		final JTextArea textArea = new JTextArea();
		textArea.setText(totalfam);
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
		
		textField = new JTextField();
		textField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		textField.setBounds(430, 307, 116, 22);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnSubmit.setBounds(550, 308, 97, 22);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					final String sixdigupdate = "UPDATE Clients SET SixDigit = ? WHERE SixDigit ="+i;
					if (textField.getText().length() != 6)
					{
						JOptionPane.showMessageDialog(null, "Please enter a 6 digit number");
					}	
					else if (textField.getText().length() == 6)
					{
						PreparedStatement sdpst=connection.prepareStatement(sixdigupdate);
						sdpst.setString(1, textField.getText() );
						sdpst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Family Information Updated");
						frame.dispose();
						new FamilyDat(Integer.parseInt(textField.getText()));
						sdpst.close();
					}
				}catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		frame.getContentPane().add(btnSubmit);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		textField_1.setBounds(326, 335, 141, 21);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnEditClientInformation = new JButton("Edit Client Information");
		btnEditClientInformation.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnEditClientInformation.setBounds(476, 335, 172, 22);
		btnEditClientInformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
				String checkifexists = "SELECT * FROM Clients WHERE NineDigit=?";
				Integer ninedigitnumber = 0;
				if (!isNumeric(textField_1.getText())) {
					JOptionPane.showMessageDialog(null, "Please only numeric characters");
				}
				else if (isNumeric(textField_1.getText())) {
				ninedigitnumber = Integer.parseInt(textField_1.getText());
				}
				int length = textField_1.getText().length();
				if (length != 9)
				{
					JOptionPane.showMessageDialog(null, "Please enter a 9 digit number");
				}	
				else if (length == 9 && isNumeric(textField_1.getText()))				
				{
					PreparedStatement ciepst=connection.prepareStatement(checkifexists);
					ciepst.setString(1, textField_1.getText() );
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
		
		JLabel lblNewLabel_1 = new JLabel("Enter a 9 digit health card number of a client to delete:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(17, 361, 311, 23);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		textField_2.setBounds(326, 358, 141, 22);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnDeleteClientData = new JButton("Delete Client Record");
		btnDeleteClientData.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnDeleteClientData.setBounds(476, 361, 172, 22);
		btnDeleteClientData.addActionListener(new ActionListener() {
			String [] args;
			public void actionPerformed(ActionEvent arg0) {
				int ninedigitnumber = Integer.parseInt(textField_2.getText());
				int length = textField_2.getText().length();
				try {
					if (length != 9)
					{
						JOptionPane.showMessageDialog(null, "Please enter a 9 digit number");
					}	
					else if (length == 9)
					{
						final String deleteclientquery = "DELETE FROM Clients WHERE NineDigit =?";
						PreparedStatement sdpst=connection.prepareStatement(deleteclientquery);
						sdpst.setInt(1, ninedigitnumber);
						sdpst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Client Record Deleted");
						frame.dispose();
						sdpst.close();
						
						String famquery = "select * FROM Clients WHERE SixDigit=?";
						PreparedStatement adpst=connection.prepareStatement(famquery);
						adpst.setString(1, Integer.toString(i));
						ResultSet adrs = adpst.executeQuery();
						if (adrs.next()) {
							new FamilyDat(i);
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
		frame.getContentPane().add(btnDeleteClientData);
		
		JButton btnDeleteFamilyWith = new JButton("Delete Family With Health Card Number: "+Integer.toString(i));
		btnDeleteFamilyWith.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnDeleteFamilyWith.setBounds(17, 386, 358, 31);
		btnDeleteFamilyWith.addActionListener(new ActionListener() {
			String [] args;
			public void actionPerformed(ActionEvent arg0) {
				try {
					final String deletefamilyquery = "DELETE FROM Clients WHERE SixDigit =?";
					PreparedStatement sdpst=connection.prepareStatement(deletefamilyquery);
					sdpst.setString(1, Integer.toString(i));
					sdpst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Family Record Deleted");
					sdpst.close();
					frame.dispose();
					Screen1.main(args);
					}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		frame.getContentPane().add(btnDeleteFamilyWith);
		
		JLabel lblNewLabel_2 = new JLabel("(Deletes cannot be undone)");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		lblNewLabel_2.setBounds(392, 389, 192, 23);
		frame.getContentPane().add(lblNewLabel_2);
		
		
		frame.setSize(700, 500);
		frame.setVisible(true);
	}
}
