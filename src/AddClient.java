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
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class AddClient extends JFrame {
	
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
	private JTextArea AdditionalInfo;
	
	/**
	 * Create the frame.
	 * @return 
	 */
	public AddClient() {
		
		connection = SQliteConnection.dbConnector();
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 551, 328);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Add New Client");
		lblNewLabel.setBounds(0, 0, 142, 22);
		frame.getContentPane().add(lblNewLabel);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		
		JButton btnSubmit = new JButton("Submit New Client");
		btnSubmit.setBounds(473, 586, 186, 31);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String unique9digittest = "SELECT * FROM Clients WHERE NineDigit=?";
					PreparedStatement ndpst=connection.prepareStatement(unique9digittest);
					ndpst.setString(1, nineDigits.getText());
					ResultSet ndrs = ndpst.executeQuery();
					if (ndrs.next()) {
						JOptionPane.showMessageDialog(null, "Please enter a unique 9 Digit Health Card Number");
						ndrs.close();
						ndpst.close();
					}
					else if (!ndrs.next()) {
						ndrs.close();
						ndpst.close();
					
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
					String query = "INSERT INTO Clients VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					PreparedStatement pst=connection.prepareStatement(query);
					pst.setString(1, firstName.getText());
					pst.setString(2, lastName.getText());
					pst.setInt(3, Integer.parseInt(nineDigits.getText()));
					pst.setString(4, telephoneNumber.getText());
					pst.setString(5, streetAddress.getText());
					pst.setString(6, city.getText());
					pst.setString(7, postalCode.getText());
					pst.setString(8, country.getText());
					String date = dateOfBirth.getText();
					pst.setString(9, date);
					pst.setString(10, spouseNineDigit.getText());
					pst.setString(11, familySixDigit.getText());
					pst.setString(12, hasChildren.getText());
					pst.setString(13, isChild.getText());
					pst.setString(14, parent1.getText());
					pst.setString(15, gender.getText());
					pst.setString(16, parent2.getText());
					pst.setString(17, cheque1number.getText());
					pst.setString(18, cheque1amount.getText());
					pst.setString(19, cheque1date.getText());
					pst.setString(20, cheque2number.getText());
					pst.setString(21, cheque2amount.getText());
					pst.setString(22, cheque2date.getText());
					pst.setString(23, cheque3number.getText());
					pst.setString(24, cheque3amount.getText());
					pst.setString(25, cheque3date.getText());
					pst.setString(26, AdditionalInfo.getText());
					pst.executeUpdate();
					pst.close();
					JOptionPane.showMessageDialog(null, "Client Added Successfully");
					frame.dispose();
					new AddClient();
					}
				}
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, e);
					}
}
					
				}catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
				
			}
		});
		frame.getContentPane().add(btnSubmit);
		
		JLabel lblEnterHereA = new JLabel("First Name: ");
		lblEnterHereA.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblEnterHereA.setBounds(10, 44, 70, 23);
		frame.getContentPane().add(lblEnterHereA);
		
		firstName = new JTextField();
		firstName.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		firstName.setBounds(212, 42, 142, 25);
		frame.getContentPane().add(firstName);
		firstName.setColumns(10);
		
		JButton btnBackToSearch = new JButton("Back to Search Screen");
		btnBackToSearch.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnBackToSearch.setBounds(448, 19, 186, 31);
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
		lastName.setBounds(212, 69, 142, 22);
		frame.getContentPane().add(lastName);
		lastName.setColumns(10);
		
		nineDigits = new JTextField();
		nineDigits.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		nineDigits.setColumns(10);
		nineDigits.setBounds(212, 95, 142, 22);
		frame.getContentPane().add(nineDigits);
		
		JLabel lblDigitHealth = new JLabel("9 Digit Health Card #:");
		lblDigitHealth.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblDigitHealth.setBounds(11, 96, 131, 23);
		frame.getContentPane().add(lblDigitHealth);
		
		JLabel lblDateOfBirth = new JLabel("Date of Birth (Day/Month/Year): ");
		lblDateOfBirth.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblDateOfBirth.setBounds(10, 122, 194, 23);
		frame.getContentPane().add(lblDateOfBirth);
		
		JLabel lblTelephoneNumber = new JLabel("Telephone Number:");
		lblTelephoneNumber.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblTelephoneNumber.setBounds(10, 148, 115, 23);
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
		lblCountry.setBounds(10, 252, 132, 23);
		frame.getContentPane().add(lblCountry);
		
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		dateOfBirth = new JFormattedTextField(format);
		dateOfBirth.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		dateOfBirth.setColumns(10);
		dateOfBirth.setBounds(212, 121, 142, 22);
		frame.getContentPane().add(dateOfBirth);
		
		telephoneNumber = new JTextField();
		telephoneNumber.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		telephoneNumber.setColumns(10);
		telephoneNumber.setBounds(142, 145, 166, 22);
		frame.getContentPane().add(telephoneNumber);
		
		streetAddress = new JTextField();
		streetAddress.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		streetAddress.setColumns(10);
		streetAddress.setBounds(142, 168, 166, 22);
		frame.getContentPane().add(streetAddress);
		
		city = new JTextField();
		city.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		city.setColumns(10);
		city.setBounds(142, 194, 166, 22);
		frame.getContentPane().add(city);
		
		postalCode = new JTextField();
		postalCode.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		postalCode.setColumns(10);
		postalCode.setBounds(142, 220, 166, 22);
		frame.getContentPane().add(postalCode);
		
		country = new JTextField();
		country.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		country.setColumns(10);
		country.setBounds(142, 249, 166, 22);
		frame.getContentPane().add(country);
		
		familySixDigit = new JTextField();
		familySixDigit.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		familySixDigit.setColumns(10);
		familySixDigit.setBounds(220, 277, 134, 22);
		frame.getContentPane().add(familySixDigit);
		
		spouseNineDigit = new JTextField();
		spouseNineDigit.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		spouseNineDigit.setColumns(10);
		spouseNineDigit.setBounds(220, 301, 134, 22);
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
		lblNewLabel_2.setBounds(193, 2, 161, 23);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("*");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(357, 44, 23, 23);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel label = new JLabel("*");
		label.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		label.setBounds(357, 70, 23, 23);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("*");
		label_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		label_1.setBounds(357, 94, 23, 23);
		frame.getContentPane().add(label_1);
		
		JLabel label_3 = new JLabel("*");
		label_3.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		label_3.setBounds(357, 122, 23, 23);
		frame.getContentPane().add(label_3);
		
		JLabel lblHasChildren = new JLabel("Has Children?  0 for No, 1 for Yes:");
		lblHasChildren.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblHasChildren.setBounds(10, 357, 205, 22);
		frame.getContentPane().add(lblHasChildren);
		
		JLabel lblIsChild = new JLabel("Is Child?  0 for No, 1 for Yes:");
		lblIsChild.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblIsChild.setBounds(380, 122, 174, 23);
		frame.getContentPane().add(lblIsChild);
		
		JLabel lblParentNameonly = new JLabel("Parent Name 1 (Only if Is Child = 1):");
		lblParentNameonly.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblParentNameonly.setBounds(320, 148, 213, 23);
		frame.getContentPane().add(lblParentNameonly);
		
		JLabel lblParentName = new JLabel("Parent Name 2 (Only if Is Child = 1):");
		lblParentName.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblParentName.setBounds(320, 174, 220, 23);
		frame.getContentPane().add(lblParentName);
		
		gender = new JTextField();
		gender.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		gender.setColumns(10);
		gender.setBounds(220, 329, 134, 22);
		frame.getContentPane().add(gender);
		
		hasChildren = new JTextField();
		hasChildren.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		hasChildren.setColumns(10);
		hasChildren.setBounds(220, 354, 70, 22);
		frame.getContentPane().add(hasChildren);
		
		isChild = new JTextField();
		isChild.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		isChild.setColumns(10);
		isChild.setBounds(559, 121, 75, 22);
		frame.getContentPane().add(isChild);
		
		parent1 = new JTextField();
		parent1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		parent1.setColumns(10);
		parent1.setBounds(535, 148, 134, 22);
		frame.getContentPane().add(parent1);
		
		parent2 = new JTextField();
		parent2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		parent2.setColumns(10);
		parent2.setBounds(535, 175, 134, 22);
		frame.getContentPane().add(parent2);
		
		JLabel label_2 = new JLabel("*");
		label_2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		label_2.setBounds(636, 122, 23, 23);
		frame.getContentPane().add(label_2);
		
		JLabel label_4 = new JLabel("*");
		label_4.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		label_4.setBounds(296, 357, 23, 23);
		frame.getContentPane().add(label_4);
		
		JLabel label_5 = new JLabel("*");
		label_5.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		label_5.setBounds(357, 326, 23, 23);
		frame.getContentPane().add(label_5);
		
		JLabel lblNewLabel_4 = new JLabel("(If Parent Name 2 is filled, Parent Name 1 field must also ");
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		lblNewLabel_4.setBounds(325, 200, 344, 23);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("be filled to properly display in family searches)");
		lblNewLabel_5.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		lblNewLabel_5.setBounds(325, 223, 309, 23);
		frame.getContentPane().add(lblNewLabel_5);
		
		JLabel lblCheque = new JLabel("Cheque #: ");
		lblCheque.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblCheque.setBounds(10, 382, 70, 22);
		frame.getContentPane().add(lblCheque);
		
		JLabel lblChequeAmount = new JLabel("Cheque Amount:     $");
		lblChequeAmount.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblChequeAmount.setBounds(10, 408, 121, 22);
		frame.getContentPane().add(lblChequeAmount);
		
		JLabel lblChequeDate = new JLabel("Cheque Date: ");
		lblChequeDate.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblChequeDate.setBounds(10, 434, 93, 22);
		frame.getContentPane().add(lblChequeDate);
		
		cheque1number = new JTextField();
		cheque1number.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		cheque1number.setColumns(10);
		cheque1number.setBounds(131, 381, 233, 22);
		frame.getContentPane().add(cheque1number);
		
		cheque1amount = new JTextField();
		cheque1amount.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		cheque1amount.setColumns(10);
		cheque1amount.setBounds(131, 407, 134, 22);
		frame.getContentPane().add(cheque1amount);
		
		cheque1date = new JFormattedTextField(format);
		cheque1date.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		cheque1date.setColumns(10);
		cheque1date.setBounds(131, 433, 134, 22);
		frame.getContentPane().add(cheque1date);
		
		JLabel lblCheque_1 = new JLabel("Cheque 2 #: ");
		lblCheque_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblCheque_1.setBounds(10, 462, 83, 22);
		frame.getContentPane().add(lblCheque_1);
		
		JLabel lblChequeAmount_1 = new JLabel("Cheque 2 Amount:  $");
		lblChequeAmount_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblChequeAmount_1.setBounds(10, 487, 121, 22);
		frame.getContentPane().add(lblChequeAmount_1);
		
		JLabel lblChequeDate_1 = new JLabel("Cheque 2 Date: ");
		lblChequeDate_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblChequeDate_1.setBounds(10, 513, 102, 22);
		frame.getContentPane().add(lblChequeDate_1);
		
		JLabel lblCheque_2 = new JLabel("Cheque 3 #: ");
		lblCheque_2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblCheque_2.setBounds(10, 538, 83, 22);
		frame.getContentPane().add(lblCheque_2);
		
		JLabel lblChequeAmount_2 = new JLabel("Cheque 3 Amount:  $");
		lblChequeAmount_2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblChequeAmount_2.setBounds(10, 561, 121, 22);
		frame.getContentPane().add(lblChequeAmount_2);
		
		JLabel lblChequeDate_2 = new JLabel("Cheque 3 Date: ");
		lblChequeDate_2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblChequeDate_2.setBounds(10, 586, 102, 22);
		frame.getContentPane().add(lblChequeDate_2);
		
		cheque2number = new JTextField();
		cheque2number.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		cheque2number.setColumns(10);
		cheque2number.setBounds(131, 461, 233, 22);
		frame.getContentPane().add(cheque2number);
		
		cheque2amount = new JTextField();
		cheque2amount.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		cheque2amount.setColumns(10);
		cheque2amount.setBounds(131, 486, 134, 22);
		frame.getContentPane().add(cheque2amount);
		
		cheque2date = new JFormattedTextField(format);
		cheque2date.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		cheque2date.setColumns(10);
		cheque2date.setBounds(131, 512, 134, 22);
		frame.getContentPane().add(cheque2date);
		
		cheque3number = new JTextField();
		cheque3number.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		cheque3number.setColumns(10);
		cheque3number.setBounds(131, 537, 200, 22);
		frame.getContentPane().add(cheque3number);
		
		cheque3amount = new JTextField();
		cheque3amount.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		cheque3amount.setColumns(10);
		cheque3amount.setBounds(131, 560, 134, 22);
		frame.getContentPane().add(cheque3amount);
		
		cheque3date = new JFormattedTextField(format);
		cheque3date.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		cheque3date.setColumns(10);
		cheque3date.setBounds(131, 586, 134, 22);
		frame.getContentPane().add(cheque3date);
		
		JLabel lblAdditionalInformation = new JLabel("Additional Information: ");
		lblAdditionalInformation.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblAdditionalInformation.setBounds(448, 408, 174, 22);
		frame.getContentPane().add(lblAdditionalInformation);
		
		AdditionalInfo = new JTextArea();
		AdditionalInfo.setRows(20);
		AdditionalInfo.setBounds(380, 434, 279, 135);
		AdditionalInfo.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		AdditionalInfo.setColumns(30);
		AdditionalInfo.setLineWrap(true);
		AdditionalInfo.setWrapStyleWord(true);
		frame.getContentPane().add(AdditionalInfo);
		
		frame.setSize(700, 700);
		frame.setVisible(true);
	}
}
