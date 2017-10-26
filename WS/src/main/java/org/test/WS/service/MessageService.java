package org.test.WS.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.ws.rs.core.Response;

import org.test.WS.database.DBConnection;
import org.test.WS.model.Employee;
import org.test.WS.model.Message;
import org.test.WS.resources.MessageResource.MessageType;

public class MessageService {
	DBConnection dBconnection;
	Connection connection;
	ResultSet resultSet;
	List<Message> result;

	public List<Message> getMessages(MessageType messageType) throws SQLException {

		try {
			result = new ArrayList<Message>();
			connection = DBConnection.setDBConnection();
			String sql = "SELECT * from messages WHERE messageType = '" + messageType.name() + "'";
			PreparedStatement pst = connection.prepareStatement(sql);
			resultSet = pst.executeQuery();

			while (resultSet.next()) {

				Message m = new Message(Integer.parseInt(resultSet.getString("messageId")),
						resultSet.getString("messageText"), resultSet.getString("messageLabel"), resultSet.getString("messageType"));
				result.add(m);
			}

			pst.close();
			connection.close();

		} catch (ClassNotFoundException | SQLException e) {
			connection.close();

		}

		return result;
	}
	
	// Add the message to the message table
	public int addMessage(Message message) throws ClassNotFoundException, SQLException {
		connection = DBConnection.setDBConnection();
		String sql = "INSERT INTO messages (messageId, messageLabel, messageText, messageType) values(?, ?, ?, ?)";
		PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pst.setLong(1, message.getMessageId());
		pst.setString(2, message.getMessageLabel());
		pst.setString(3, message.getMessageText());
		pst.setString(4, message.getMessageType());
		
		pst.executeUpdate();
		
		// Get the message id
		int messageId = 0;
		ResultSet rs = pst.getGeneratedKeys();
		if (rs.next()) {
		   messageId = rs.getInt(1);
		}
		pst.close();
		connection.close();
		return messageId;
	}

	public Response addBroadcastMessage(Message message) throws ClassNotFoundException, SQLException {
		// Add the message in the message table, get id of message
		int messageId = addMessage(message);	
		return Response.ok().entity("id: " + messageId).build();
	}

	public Response addEmployeeMessage(Message message, int employeeId) throws ClassNotFoundException, SQLException {		
		int messageId = addMessage(message);
		connection = DBConnection.setDBConnection();
		String sql = "INSERT INTO messageemployee (employeeId, messageId) values(?, ?)";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, messageId);
		pst.setLong(2, employeeId);
		pst.executeUpdate();
		pst.close();
		connection.close();
		return Response.ok().entity("Message: " + message.getMessageText()).build();
	}

}
