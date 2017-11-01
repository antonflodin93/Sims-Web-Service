package org.test.WS.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.test.WS.database.DBConnection;
import org.test.WS.model.Message;
import org.test.WS.resources.MessageResource.MessageType;

public class MessageService {
	DBConnection dBconnection;
	Connection connection;
	ResultSet resultSet;
	List<Message> messages;

	public List<Message> getMessages(MessageType messageType) throws SQLException, ClassNotFoundException {

		messages = new ArrayList<Message>();
		connection = DBConnection.setDBConnection();
		String sql = "SELECT * from messages WHERE messageType = '" + messageType.name() + "'";
		PreparedStatement pst = connection.prepareStatement(sql);
		resultSet = pst.executeQuery();

		while (resultSet.next()) {

			Message m = new Message(Integer.parseInt(resultSet.getString("messageId")),
					resultSet.getString("messageText"), resultSet.getString("messageLabel"),
					resultSet.getString("messageType"));
			messages.add(m);
		}

		pst.close();
		connection.close();

		return messages;
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

	public void addBroadcastMessage(Message message) throws ClassNotFoundException, SQLException {
		// Add the message in the message table, get id of message
		addMessage(message);
	}

	public void addEmployeeMessage(Message message, int employeeId) throws ClassNotFoundException, SQLException {
		int messageId = addMessage(message);
		connection = DBConnection.setDBConnection();
		String sql = "INSERT INTO messageemployee (messageId, employeeId) values(?, ?)";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, messageId);
		pst.setLong(2, employeeId);
		pst.executeUpdate();
		pst.close();
		connection.close();
	}

	public List<Message> getEmployeeMessage(int employeeId) throws ClassNotFoundException, SQLException {
		messages = new ArrayList<Message>();
		connection = DBConnection.setDBConnection();
		String sql = "SELECT * FROM messages INNER JOIN messageemployee ON messages.messageId = messageemployee.messageId AND messageemployee.employeeId = ?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, employeeId);
		resultSet = pst.executeQuery();

		while (resultSet.next()) {
			Message m = new Message(Integer.parseInt(resultSet.getString("messageId")),
					resultSet.getString("messageText"), resultSet.getString("messageLabel"),
					resultSet.getString("messageType"));

			messages.add(m);
		}
		pst.close();
		connection.close();

		return messages;
	}

	public void addCompanyMessage(Message message, String companyName) throws ClassNotFoundException, SQLException {
		int messageId = addMessage(message);
		connection = DBConnection.setDBConnection();
		String sql = "INSERT INTO messagecompany (messageId, companyName) values(?, ?)";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, messageId);
		pst.setString(2, companyName);
		pst.executeUpdate();
		pst.close();
		connection.close();
	}

	public List<Message> getCompanyMessage(String companyName) throws ClassNotFoundException, SQLException {
		messages = new ArrayList<Message>();
		connection = DBConnection.setDBConnection();
		String sql = "SELECT * FROM messages INNER JOIN messagecompany ON messages.messageId = messagecompany.messageId AND messagecompany.companyName = ?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setString(1, companyName);
		resultSet = pst.executeQuery();

		while (resultSet.next()) {
			Message m = new Message(Integer.parseInt(resultSet.getString("messageId")),
					resultSet.getString("messageText"), resultSet.getString("messageLabel"),
					resultSet.getString("messageType"));

			messages.add(m);
		}
		pst.close();
		connection.close();

		return messages;
	}
}
