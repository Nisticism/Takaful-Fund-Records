import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class EditClientInfo extends JFrame {
	
	private JFrame frame;
	private JPanel panel;
	private Integer temp = null;

	Connection connection = null;
	private JTextField firstName;
	private JTextField lastName;
	private JTextField nineDigits;
	private JFormattedTextField dateOfBirth;
	private JTextField telephoneNumber;
	private JTextField streetAddress;
	private JTextField city;
	private JTextField postalCode;
	private JTextField country;
	private JTextField familySixDigit;
	private JTextField spouseNineDigit;
	private JTextField gender;
	private JTextField hasChildren;
	private JTextField isChild;
	private JTextField parent1;
	private JTextField parent2;
	private JTextField cheque1number;
	private JTextField cheque1amount;
	private JTextField cheque1date;
	private JTextField cheque2number;
	private JTextField cheque2amount;
	private JTextField cheque2date;
	private JTextField cheque3number;
	private JTextField cheque3amount;
	private JTextField cheque3date;
	private JTextArea AdditionalInform;
	
	/**
	 * Create the frame.
	 * @return 
	 * @throws SQLException 
	 */
	public EditClientInfo(Integer nine) {
		
		connection = SQliteConnection.dbConnector();
		initialize(nine);
	}
	
	private void initialize(Integer nine) {
		frame = new JFrame();
		frame.setBounds(100, 100, 551, 328);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Edit Client");
		lblNewLabel.setBounds(0, 0, 142, 22);
		frame.getContentPane().add(lblNewLabel);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		
		try {
			String clientquery = "SELECT * FROM Clients WHERE NineDigit=?";
			PreparedStatement pst=connection.prepareStatement(clientquery);
			pst.setString(1, Integer.toString(nine));
			ResultSet rs = pst.executeQuery();
			
			String firstname = rs.getString(1);
			String lastname = rs.getString(2);
			Integer ischild = rs.getInt(13);
			Integer ninedigit = rs.getInt(3);
			String phonenumber = rs.getString(4);
			String streetaddress = rs.getString(5);
			String cityvar = rs.getString(6);
			String postalcode = rs.getString(7);
			String countryvar = rs.getString(8);
			String dateofbirth = rs.getString(9);
			String spouseninedigit = rs.getString(10);
			String sixdigit = rs.getString(11);
			String gendervar = rs.getString("Gender");
			Integer haschildren = rs.getInt(12);
			String parentname = rs.getString(14);
			String parentname2 = rs.getString("ParentName2");
			String cheque1num = rs.getString("Cheque1Number");
			String cheque1amo = rs.getString("Cheque1Amount");
			String cheque1dat = rs.getString("Cheque1Date");
			String cheque2num = rs.getString("Cheque2Number");
			String cheque2amo = rs.getString("Cheque2Amount");
			String cheque2dat = rs.getString("Cheque2Date");
			String cheque3num = rs.getString("Cheque3Number");
			String cheque3amo = rs.getString("Cheque3Amount");
			String cheque3dat = rs.getString("Cheque3Date");
			String additionalinformation = rs.getString("AdditionalInfo");

			rs.close();
			pst.close();		
		
		JButton btnSubmit = new JButton("Update Client");
		btnSubmit.setBounds(473, 686, 186, 31);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (nineDigits.getText().length() != 9) {
						JOptionPane.showMessageDialog(null, "Make sure the health card number is exactly 9 digits");
					}
					if ((familySixDigit.getText().length() != 6) && (familySixDigit.getText().isEmpty() == false)) {
						JOptionPane.showMessageDialog(null, "Make sure the family health card number is exactly 6 digits (or empty)");
					}
					if ((spouseNineDigit.getText().length() != 9) && (spouseNineDigit.getText().isEmpty() == false)) {
						JOptionPane.showMessageDialog(null, "Make sure the spouse health card number is exactly 9 digits (or empty)");
					}
					if ((Integer.parseInt(isChild.getText()) != 0) && (Integer.parseInt(isChild.getText()) != 1)) {
						JOptionPane.showMessageDialog(null, "Please enter either 0 or 1 for the 'Is Child' field");
					}
					if ((Integer.parseInt(hasChildren.getText()) != 0) && (Integer.parseInt(hasChildren.getText()) != 1)) {
						JOptionPane.showMessageDialog(null, "Please enter either 0 or 1 for the 'Has Children' field");
					}
					else if ((nineDigits.getText().length() == 9) && ((familySixDigit.getText().length() == 6) || (familySixDigit.getText().isEmpty() == true)) && ((spouseNineDigit.getText().length() == 9) || (spouseNineDigit.getText().isEmpty() == true)) && ((Integer.parseInt(isChild.getText()) == 0) || (Integer.parseInt(isChild.getText()) == 1)) && ((Integer.parseInt(hasChildren.getText()) == 0) || (Integer.parseInt(hasChildren.getText()) == 1))) {
					String query = "UPDATE Clients SET FirstName=?,LastName=?,NineDigit=?,TelephoneNum=?,StreetAddress=?,City=?,PostalCode=?,CountryOfOrigin=?,DateOfBirth=?,SpouseNineDigit=?,SixDigit=?,HasChildren=?,IsChild=?,ParentName=?,Gender=?,ParentName2=?,Cheque1Number=?,Cheque1Amount=?,Cheque1Date=?,Cheque2Number=?,Cheque2Amount=?,Cheque2Date=?,Cheque3Number=?,Cheque3Amount=?,Cheque3Date=?,AdditionalInfo=? WHERE NineDigit="+nine;
					PreparedStatement upst=connection.prepareStatement(query);
					upst.setString(1, firstName.getText());
					upst.setString(2, lastName.getText());
					upst.setInt(3, Integer.parseInt(nineDigits.getText()));
					upst.setString(4, telephoneNumber.getText());
					upst.setString(5, streetAddress.getText());
					upst.setString(6, city.getText());
					upst.setString(7, postalCode.getText());
					upst.setString(8, country.getText());
					upst.setString(9, dateOfBirth.getText());
					upst.setString(10, spouseNineDigit.getText());
					upst.setString(11, familySixDigit.getText());
					upst.setString(12, hasChildren.getText());
					upst.setString(13, isChild.getText());
					upst.setString(14, parent1.getText());
					upst.setString(15, gender.getText());
					upst.setString(16, parent2.getText());
					upst.setString(17, cheque1number.getText());
					upst.setString(18, cheque1amount.getText());
					upst.setString(19, cheque1date.getText());
					upst.setString(20, cheque2number.getText());
					upst.setString(21, cheque2amount.getText());
					upst.setString(22, cheque2date.getText());
					upst.setString(23, cheque3number.getText());
					upst.setString(24, cheque3amount.getText());
					upst.setString(25, cheque3date.getText());
					upst.setString(26, AdditionalInform.getText());
					upst.executeUpdate();
					upst.close();
					JOptionPane.showMessageDialog(null, "Client Updated Successfully");
					frame.dispose();
					new ClientData(firstname,lastname);
					}
				}
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, e);
					}
			}
		});
		frame.getContentPane().add(btnSubmit);
		
		JLabel lblEnterHereA = new JLabel("First Name: ");
		lblEnterHereA.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblEnterHereA.setBounds(10, 44, 213, 23);
		frame.getContentPane().add(lblEnterHereA);
		
		firstName = new JTextField();
		firstName.setText(firstname);
		firstName.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		firstName.setBounds(235, 40, 166, 25);
		frame.getContentPane().add(firstName);
		firstName.setColumns(10);
		
		JButton btnBackToSearch = new JButton("Back to Search Screen");
		btnBackToSearch.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnBackToSearch.setBounds(448, 7, 186, 31);
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
		
		JLabel lblNewLabel_1 = new JLabel("Last Name: ");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 70, 83, 23);
		frame.getContentPane().add(lblNewLabel_1);
		
		lastName = new JTextField();
		lastName.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lastName.setText(lastname);
		lastName.setBounds(235, 69, 166, 22);
		frame.getContentPane().add(lastName);
		lastName.setColumns(10);
		
		nineDigits = new JTextField();
		nineDigits.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		nineDigits.setText(Integer.toString(ninedigit));
		nineDigits.setColumns(10);
		nineDigits.setBounds(235, 95, 166, 22);
		frame.getContentPane().add(nineDigits);
		
		JLabel lblDigitHealth = new JLabel("9 Digit Health Card #: ");
		lblDigitHealth.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblDigitHealth.setBounds(11, 96, 173, 23);
		frame.getContentPane().add(lblDigitHealth);
		
		JLabel lblDateOfBirth = new JLabel("Date of Birth (Day/Month/Year):");
		lblDateOfBirth.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblDateOfBirth.setBounds(10, 122, 186, 23);
		frame.getContentPane().add(lblDateOfBirth);
		
		JLabel lblTelephoneNumber = new JLabel("Telephone Number:");
		lblTelephoneNumber.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblTelephoneNumber.setBounds(10, 148, 149, 23);
		frame.getContentPane().add(lblTelephoneNumber);
		
		JLabel lblStreetAddress = new JLabel("Street Address:");
		lblStreetAddress.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblStreetAddress.setBounds(10, 174, 102, 23);
		frame.getContentPane().add(lblStreetAddress);
		
		JLabel lblCity = new JLabel("City:");
		lblCity.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblCity.setBounds(10, 200, 83, 23);
		frame.getContentPane().add(lblCity);
		
		JLabel lblPostalCode = new JLabel("Postal Code:");
		lblPostalCode.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblPostalCode.setBounds(10, 226, 83, 23);
		frame.getContentPane().add(lblPostalCode);
		
		JLabel lblCountry = new JLabel("Country of Origin:");
		lblCountry.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblCountry.setBounds(10, 252, 149, 23);
		frame.getContentPane().add(lblCountry);
		
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		dateOfBirth = new JFormattedTextField(format);
		dateOfBirth.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		dateOfBirth.setText(dateofbirth);
		dateOfBirth.setColumns(10);
		dateOfBirth.setBounds(235, 121, 166, 22);
		frame.getContentPane().add(dateOfBirth);
		
		telephoneNumber = new JTextField();
		telephoneNumber.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		telephoneNumber.setText(phonenumber);
		telephoneNumber.setColumns(10);
		telephoneNumber.setBounds(235, 147, 166, 22);
		frame.getContentPane().add(telephoneNumber);
		
		streetAddress = new JTextField();
		streetAddress.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		streetAddress.setText(streetaddress);
		streetAddress.setColumns(10);
		streetAddress.setBounds(235, 173, 166, 22);
		frame.getContentPane().add(streetAddress);
		
		city = new JTextField();
		city.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		city.setText(cityvar);
		city.setColumns(10);
		city.setBounds(235, 199, 166, 22);
		frame.getContentPane().add(city);
		
		postalCode = new JTextField();
		postalCode.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		postalCode.setText(postalcode);
		postalCode.setColumns(10);
		postalCode.setBounds(235, 225, 166, 22);
		frame.getContentPane().add(postalCode);
		
		country = new JTextField();
		country.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		country.setText(countryvar);
		country.setColumns(10);
		country.setBounds(235, 251, 166, 22);
		frame.getContentPane().add(country);
		
		familySixDigit = new JTextField();
		familySixDigit.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		familySixDigit.setText(sixdigit);
		familySixDigit.setColumns(10);
		familySixDigit.setBounds(235, 279, 134, 22);
		frame.getContentPane().add(familySixDigit);
		
		spouseNineDigit = new JTextField();
		spouseNineDigit.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		spouseNineDigit.setText(spouseninedigit);
		spouseNineDigit.setColumns(10);
		spouseNineDigit.setBounds(235, 303, 134, 22);
		frame.getContentPane().add(spouseNineDigit);
		
		JLabel lblFamilyDigit = new JLabel("Family 6 Digit Health Card #:");
		lblFamilyDigit.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblFamilyDigit.setBounds(10, 280, 174, 23);
		frame.getContentPane().add(lblFamilyDigit);
		
		JLabel lblSpouseDigit = new JLabel("Spouse 9 Digit Health Card #:");
		lblSpouseDigit.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblSpouseDigit.setBounds(10, 304, 174, 23);
		frame.getContentPane().add(lblSpouseDigit);
		
		JLabel lblGendermOr = new JLabel("Gender (M or F):");
		lblGendermOr.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblGendermOr.setBounds(10, 332, 174, 23);
		frame.getContentPane().add(lblGendermOr);
		
		JLabel lblNewLabel_2 = new JLabel("Fields with * are required");
		lblNewLabel_2.setFont(new Font("Trebuchet MS", Font.ITALIC, 12));
		lblNewLabel_2.setBounds(147, 2, 161, 23);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("*");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(407, 44, 23, 23);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel label = new JLabel("*");
		label.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		label.setBounds(407, 70, 23, 23);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("*");
		label_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		label_1.setBounds(407, 96, 23, 23);
		frame.getContentPane().add(label_1);
		
		JLabel label_3 = new JLabel("*");
		label_3.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		label_3.setBounds(407, 122, 23, 23);
		frame.getContentPane().add(label_3);
		
		JLabel lblHasChildren = new JLabel("Has Children?  0 for No, 1 for Yes:");
		lblHasChildren.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblHasChildren.setBounds(10, 357, 205, 22);
		frame.getContentPane().add(lblHasChildren);
		
		JLabel lblIsChild = new JLabel("Is Child?  0 for No, 1 for Yes:");
		lblIsChild.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblIsChild.setBounds(10, 386, 174, 23);
		frame.getContentPane().add(lblIsChild);
		
		JLabel lblParentNameonly = new JLabel("Parent Name 1 (Only if Is Child = 1):");
		lblParentNameonly.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblParentNameonly.setBounds(10, 414, 213, 23);
		frame.getContentPane().add(lblParentNameonly);
		
		JLabel lblParentName = new JLabel("Parent Name 2 (Only if Is Child = 1):");
		lblParentName.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblParentName.setBounds(10, 439, 220, 23);
		frame.getContentPane().add(lblParentName);
		
		gender = new JTextField();
		gender.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		gender.setText(gendervar);
		gender.setColumns(10);
		gender.setBounds(235, 331, 134, 22);
		frame.getContentPane().add(gender);
		
		hasChildren = new JTextField();
		hasChildren.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		hasChildren.setText(Integer.toString(haschildren));
		hasChildren.setColumns(10);
		hasChildren.setBounds(235, 356, 75, 22);
		frame.getContentPane().add(hasChildren);
		
		isChild = new JTextField();
		isChild.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		isChild.setText(Integer.toString(ischild));
		isChild.setColumns(10);
		isChild.setBounds(235, 385, 75, 22);
		frame.getContentPane().add(isChild);
		
		parent1 = new JTextField();
		parent1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		parent1.setText(parentname);
		parent1.setColumns(10);
		parent1.setBounds(235, 413, 134, 22);
		frame.getContentPane().add(parent1);
		
		parent2 = new JTextField();
		parent2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		parent2.setText(parentname2);
		parent2.setColumns(10);
		parent2.setBounds(235, 438, 134, 22);
		frame.getContentPane().add(parent2);
		
		JLabel lblCheque = new JLabel("Cheque #: ");
		lblCheque.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblCheque.setBounds(10, 535, 70, 22);
		frame.getContentPane().add(lblCheque);
		
		JLabel lblChequeAmount = new JLabel("Cheque Amount:    $");
		lblChequeAmount.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblChequeAmount.setBounds(10, 560, 117, 22);
		frame.getContentPane().add(lblChequeAmount);
		
		JLabel lblChequeDate = new JLabel("Cheque Date: ");
		lblChequeDate.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblChequeDate.setBounds(10, 583, 93, 22);
		frame.getContentPane().add(lblChequeDate);
		
		cheque1number = new JTextField();
		cheque1number.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		cheque1number.setText(cheque1num);
		cheque1number.setColumns(10);
		cheque1number.setBounds(131, 534, 195, 22);
		frame.getContentPane().add(cheque1number);
		
		cheque1amount = new JTextField();
		cheque1amount.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		cheque1amount.setText(cheque1amo);
		cheque1amount.setColumns(10);
		cheque1amount.setBounds(131, 559, 124, 22);
		frame.getContentPane().add(cheque1amount);
		
		cheque1date = new JFormattedTextField(format);
		cheque1date.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		cheque1date.setText(cheque1dat);
		cheque1date.setColumns(10);
		cheque1date.setBounds(131, 582, 123, 22);
		frame.getContentPane().add(cheque1date);
		
		JLabel lblCheque_1 = new JLabel("Cheque 2 #: ");
		lblCheque_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblCheque_1.setBounds(10, 607, 83, 22);
		frame.getContentPane().add(lblCheque_1);
		
		JLabel lblChequeAmount_1 = new JLabel("Cheque 2 Amount: $");
		lblChequeAmount_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblChequeAmount_1.setBounds(10, 632, 117, 22);
		frame.getContentPane().add(lblChequeAmount_1);
		
		JLabel lblChequeDate_1 = new JLabel("Cheque 2 Date: ");
		lblChequeDate_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblChequeDate_1.setBounds(10, 659, 102, 22);
		frame.getContentPane().add(lblChequeDate_1);
		
		JLabel lblCheque_2 = new JLabel("Cheque 3 #: ");
		lblCheque_2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblCheque_2.setBounds(335, 607, 83, 22);
		frame.getContentPane().add(lblCheque_2);
		
		JLabel lblChequeAmount_2 = new JLabel("Cheque 3 Amount:  $");
		lblChequeAmount_2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblChequeAmount_2.setBounds(335, 632, 124, 22);
		frame.getContentPane().add(lblChequeAmount_2);
		
		JLabel lblChequeDate_2 = new JLabel("Cheque 3 Date: ");
		lblChequeDate_2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblChequeDate_2.setBounds(335, 659, 102, 22);
		frame.getContentPane().add(lblChequeDate_2);
		
		cheque2number = new JTextField();
		cheque2number.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		cheque2number.setText(cheque2num);
		cheque2number.setColumns(10);
		cheque2number.setBounds(131, 606, 195, 22);
		frame.getContentPane().add(cheque2number);
		
		cheque2amount = new JTextField();
		cheque2amount.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		cheque2amount.setText(cheque2amo);
		cheque2amount.setColumns(10);
		cheque2amount.setBounds(130, 631, 124, 22);
		frame.getContentPane().add(cheque2amount);
		
		cheque2date = new JFormattedTextField(format);
		cheque2date.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		cheque2date.setText(cheque2dat);
		cheque2date.setColumns(10);
		cheque2date.setBounds(131, 658, 124, 22);
		frame.getContentPane().add(cheque2date);
		
		cheque3number = new JTextField();
		cheque3number.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		cheque3number.setText(cheque3num);
		cheque3number.setColumns(10);
		cheque3number.setBounds(458, 606, 201, 22);
		frame.getContentPane().add(cheque3number);
		
		cheque3amount = new JTextField();
		cheque3amount.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		cheque3amount.setText(cheque3amo);
		cheque3amount.setColumns(10);
		cheque3amount.setBounds(458, 631, 138, 22);
		frame.getContentPane().add(cheque3amount);
		
		cheque3date = new JFormattedTextField(format);
		cheque3date.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		cheque3date.setText(cheque3dat);
		cheque3date.setColumns(10);
		cheque3date.setBounds(458, 658, 138, 22);
		frame.getContentPane().add(cheque3date);
		
		JLabel lblAdditionalInformation = new JLabel("Additional Information: ");
		lblAdditionalInformation.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblAdditionalInformation.setBounds(448, 427, 174, 22);
		frame.getContentPane().add(lblAdditionalInformation);
		
		AdditionalInform = new JTextArea();
		AdditionalInform.setRows(20);
		AdditionalInform.setBounds(380, 453, 279, 135);
		AdditionalInform.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		AdditionalInform.setColumns(30);
		AdditionalInform.setText(additionalinformation);
		AdditionalInform.setLineWrap(true);
		AdditionalInform.setWrapStyleWord(true);
		frame.getContentPane().add(AdditionalInform);
		
		JButton btnBackToClient = new JButton("Back to Client Data");
		btnBackToClient.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnBackToClient.setBounds(448, 44, 186, 31);
		btnBackToClient.addActionListener(new ActionListener() {
			private String[] args;

			public void actionPerformed(ActionEvent arg0) {
				try {
					frame.dispose();
					new ClientData(firstname,lastname);
				}catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, e);
				}
			}
			});
		frame.getContentPane().add(btnBackToClient);
		
		JButton btnBackToFamily = new JButton("Back to Family Data");
		btnBackToFamily.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (String.valueOf(sixdigit).length() == 6) {
					frame.dispose();
					new FamilyDat(Integer.parseInt(sixdigit));
					}
					else if (String.valueOf(sixdigit).length() != 6) {
						JOptionPane.showMessageDialog(null, "No valid family health card number");
					}
				}catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		btnBackToFamily.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnBackToFamily.setBounds(448, 81, 186, 31);
		frame.getContentPane().add(btnBackToFamily);
		
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
		JLabel label_2 = new JLabel("*");
		label_2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		label_2.setBounds(312, 386, 23, 23);
		frame.getContentPane().add(label_2);
		
		JLabel label_4 = new JLabel("*");
		label_4.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		label_4.setBounds(312, 357, 23, 23);
		frame.getContentPane().add(label_4);
		
		JLabel label_5 = new JLabel("*");
		label_5.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		label_5.setBounds(372, 332, 23, 23);
		frame.getContentPane().add(label_5);
		
		JLabel lblNewLabel_4 = new JLabel("(If Parent Name 2 is filled, Parent Name 1 field must also ");
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		lblNewLabel_4.setBounds(20, 471, 344, 23);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("be filled to properly display in family searches)");
		lblNewLabel_5.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		lblNewLabel_5.setBounds(20, 493, 309, 23);
		frame.getContentPane().add(lblNewLabel_5);
		
		frame.setSize(700, 800);
		frame.setVisible(true);
	}
}
