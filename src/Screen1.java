import java.awt.EventQueue;
import java.sql.*;
import javax.swing.*;

import java.awt.Font;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class Screen1 {

	private JFrame frame;
	private JTextField healthcard;
	private JTextField namesearch;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Screen1 window = new Screen1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	Connection connection=null;
	/**
	 * Create the application.
	 */
	public Screen1() {
		initialize();
		connection = SQliteConnection.dbConnector();
	}
	
	public static void createNewDatabase(String fileName) {
		 
        String url = "jdbc:sqlite:C:/Documents/TakafulFundRecords/" + fileName;
 
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                conn.close();
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:C:/Documents/TakafulFundRecords/ClientData.db";
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS Clients (FirstName TEXT NOT NULL, LastName TEXT NOT NULL, NineDigit INTEGER NOT NULL, TelephoneNum TEXT, StreetAddress TEXT, City TEXT, PostalCode TEXT, CountryofOrigin TEXT, DateofBirth TEXT NOT NULL, SpouseNineDigit TEXT, SixDigit TEXT, HasChildren INTEGER NOT NULL, IsChild INTEGER NOT NULL, ParentName TEXT, Gender TEXT NOT NULL, ParentName2 TEXT, Cheque1Number TEXT, Cheque1Amount TEXT, Cheque1Date TEXT, Cheque2Number TEXT, Cheque2Amount TEXT, Cheque2Date TEXT, Cheque3Number TEXT, Cheque3Amount TEXT, Cheque3Date TEXT, AdditionalInfo TEXT, PRIMARY KEY(NineDigit));";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		new File("C:/Documents/TakafulFundRecords").mkdirs();
		createNewDatabase("ClientData.db");
		createNewTable();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 551, 328);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		healthcard = new JTextField();
		healthcard.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
		healthcard.setBounds(260, 52, 121, 29);
		frame.getContentPane().add(healthcard);
		healthcard.setColumns(10);
		
		JLabel lblHealthInsuranceNumber = new JLabel("Enter a 6 digit health card number:");
		lblHealthInsuranceNumber.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblHealthInsuranceNumber.setBounds(17, 56, 226, 23);
		frame.getContentPane().add(lblHealthInsuranceNumber);
		
		JLabel takafulFundRecords = new JLabel("Takaful Fund Records");
		takafulFundRecords.setFont(new Font("Arial", Font.ITALIC, 20));
		takafulFundRecords.setBounds(17, 0, 216, 37);
		frame.getContentPane().add(takafulFundRecords);
		
		JLabel lblNewLabel = new JLabel("Enter an individual's first and last name:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel.setBounds(17, 87, 237, 23);
		frame.getContentPane().add(lblNewLabel);
		
		namesearch = new JTextField();
		namesearch.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
		namesearch.setColumns(10);
		namesearch.setBounds(260, 83, 121, 29);
		frame.getContentPane().add(namesearch);
		
		JButton btnAddNewClient = new JButton("Add New Client");
		btnAddNewClient.setForeground(new Color(153, 0, 0));
		btnAddNewClient.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnAddNewClient.setBackground(UIManager.getColor("Button.light"));
		btnAddNewClient.setBounds(318, 131, 180, 31);
		btnAddNewClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddClient addclient = new AddClient();
				frame.dispose();
			}
		});
		frame.getContentPane().add(btnAddNewClient);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String cardquery="select * from Clients "
							+ "where SixDigit=?";
					if (healthcard.getText().length() != 6)
					{
						JOptionPane.showMessageDialog(null, "Please enter a 6 digit number");
					}	
					else if (healthcard.getText().length() == 6)
					{
						PreparedStatement pst=connection.prepareStatement(cardquery);
						pst.setString(1, healthcard.getText() );
						ResultSet rs=pst.executeQuery();
						int count =0;
						
						while(rs.next()) {
							count=count+1;
						}
						if(count >=1)
						{
							int famHealthCardNum = Integer.parseInt(healthcard.getText());
							new FamilyDat(famHealthCardNum);
							frame.dispose();
							rs.close();
							pst.close();
						}
						else if(count==0)
						{
							JOptionPane.showMessageDialog(null, "No results found");
						}
						rs.close();
						pst.close();
					}
				}catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		btnNewButton.setForeground(new Color(153, 0, 0));
		btnNewButton.setBackground(UIManager.getColor("Button.light"));
		btnNewButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
		btnNewButton.setBounds(411, 51, 87, 31);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Search");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String clientName = namesearch.getText();
					if (clientName.contains(" ") == true) {
					String nameparts[] = clientName.split(" ", 2);
					String firstname = nameparts[0];
					String lastname = nameparts[1];
					String namequery="select * from Clients WHERE FirstName=? AND LastName=?";
					PreparedStatement pst=connection.prepareStatement(namequery);
					pst.setString(1, firstname);
					pst.setString(2, lastname);
					ResultSet rs=pst.executeQuery();
					int count =0;
					
					while(rs.next()) {
						count=count+1;
					}
					if(count >=1)
					{
						new ClientData(firstname, lastname);
						frame.dispose();
						rs.close();
						pst.close();
					}
					else if(count==0)
					{
						JOptionPane.showMessageDialog(null, "No results found");
					}
					rs.close();
					pst.close();
					}
					else if (clientName.contains(" ") == false) {
						JOptionPane.showMessageDialog(null, "Please enter a first and last name");
					}
				}catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		btnNewButton_1.setBackground(UIManager.getColor("Button.light"));
		btnNewButton_1.setForeground(new Color(153, 0, 0));
		btnNewButton_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
		btnNewButton_1.setBounds(411, 82, 87, 31);
		frame.getContentPane().add(btnNewButton_1);
		
		JLabel label = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/database-icon.png")).getImage();
		label.setIcon(new ImageIcon(img));
		label.setBounds(27, 129, 216, 116);
		frame.getContentPane().add(label);
	}
}