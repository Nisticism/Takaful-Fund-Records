import java.sql.*;
import javax.swing.*;

public class SQliteConnection {
	Connection conn=null;
	public static Connection dbConnector()
	{
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn=DriverManager.getConnection("jdbc:sqlite:C:\\Documents\\TakafulFundRecords\\ClientData.db");
			return conn;
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}

}
