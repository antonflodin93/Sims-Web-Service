package org.test.WS.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {
	private static Connection connection;
	private static ResultSet resultset;
	
	
	private static String username = "userasd";
	private static String password = "Tu3TLRUPhQbcu53y";
	private static String serverIp = "193.10.119.34";

	// Set the connection
	public static Connection setDBConnection() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver"); // might be changed to other method
		connection = DriverManager.getConnection("jdbc:mysql://" + serverIp + ":3306/demo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", username, password);
		
		return connection;
	}
}
