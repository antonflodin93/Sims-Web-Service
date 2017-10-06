package org.test.WS.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.test.WS.model.Message;
import org.test.WS.database.DBConnection;

// Used for returning messages
// This is the class that will be used for DB communication
public class MessageService {
	DBConnection dBconnection;
	Connection connection;
	ResultSet resultSet;
	List<Message> result;

	public MessageService() {

	}

	// Creates messages and returns them
	public List<Message> getMessages() throws SQLException, ClassNotFoundException {
		result = new ArrayList<Message>();
		dBconnection = new DBConnection();
		connection = DBConnection.setDBConnection();
		String query = "SELECT * from test";

		resultSet = DBConnection.getResultSet(query, connection);
		while (resultSet.next()) {
			Message m = new Message(Integer.parseInt(resultSet.getString(1)), resultSet.getString(2));
	        result.add(m);
	    }
		
		
		return result;
	}

}
