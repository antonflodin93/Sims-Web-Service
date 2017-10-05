package org.test.WS.resources;

import java.sql.*;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.test.WS.model.Message;
import org.test.WS.service.MessageService;

// Test class for db connection 

@Path("db")
public class DBTest {

	// Default return messages from messageservice class
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() throws ClassNotFoundException {
		String user = "user";
		String password = "password";
		String returnmessage = "";
		Class.forName("com.mysql.jdbc.Driver");
		
		try {
			
			// Get connection to DB
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, password);

			// Create a statement
			Statement statement = connection.createStatement();
			
			// Execute query
			ResultSet resultSet = statement.executeQuery("select * from test");
			
			// Write the result
			if(resultSet.next()){
				   returnmessage = resultSet.getString(1);
				}
			

		} catch (SQLException ex) {
			// handle any errors
			returnmessage = "SQLException: " + ex.getMessage() + "\nSQLState: " + ex.getSQLState() + 
					"\nVendorError: " + ex.getErrorCode();
		}
		return returnmessage;

	}

}
