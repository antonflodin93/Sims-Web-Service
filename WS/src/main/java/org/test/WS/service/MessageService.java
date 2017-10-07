package org.test.WS.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

	// Returns all the messages
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

	// Returns employee with id
	public Message getMessage(int id) throws SQLException, ClassNotFoundException {
		dBconnection = new DBConnection();
		connection = DBConnection.setDBConnection();
		String query = "SELECT * FROM test WHERE employeeID = " + id;

		resultSet = DBConnection.getResultSet(query, connection);

		Message message = new Message(Integer.parseInt(resultSet.getString(1)), resultSet.getString(2));

		return message;
	}

	public Message addUser(Message message) throws ClassNotFoundException, SQLException {
		DBConnection connect = new DBConnection();
		String sql = "insert into test (employeeId, employeeName) values(?, ?)";
		connection = DBConnection.setDBConnection();
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, message.getEmployeeId());
		pst.setString(2, message.getEmployeeName());
		pst.executeUpdate();
		pst.close();
		return message;
		/*
		try {
			String returnmessage = "";

		} catch (SQLException e) {
			returnmessage = e.getMessage();
		}

		dBconnection = new DBConnection();
		connection = DBConnection.setDBConnection();
		/*
		 * String query = "INSERT INTO test (employeeName)\r\n" + "VALUES ('" +
		 * message.getEmployeeName() + "');";
		 */
		/*
		String query = "INSERT INTO test VALUES (NULL, 'Another Name2');";

		int i = DBConnection.getIntResult(query, connection);
		return i;

		*/
	}

}
