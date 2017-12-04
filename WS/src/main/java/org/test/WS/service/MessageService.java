package org.test.WS.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.test.WS.database.DBConnection;
import org.test.WS.model.Message;

public class MessageService {
	DBConnection dBconnection;
	Connection connection;
	ResultSet resultSet;
	List<Message> messages;

	// Add the message to the message table, return the id
	public int addMessage(Message message, int broadcast) throws ClassNotFoundException, SQLException {

		connection = DBConnection.setDBConnection();
		String sql = "INSERT INTO messages (messageId, messageLabel, messageText, messageType, messageBroadCast) values(?, ?, ?, ?, ?)";
		PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pst.setLong(1, message.getMessageId());
		pst.setString(2, message.getMessageLabel());
		pst.setString(3, message.getMessageText());
		pst.setString(4, message.getMessageType());
		pst.setLong(5, broadcast);
		// pst.setTimestamp(6,getCurrentTimeStamp());

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

	// Add message that is wanted to be acknowledged
	public int addMessageToBeAck(Message message, int broadcast) throws ClassNotFoundException, SQLException {

		connection = DBConnection.setDBConnection();
		String sql = "INSERT INTO messages (messageId, messageLabel, messageText, messageType, messageBroadCast, messageCurrentmillis) values(?, ?, ?, ?, ?, ?)";
		PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pst.setLong(1, message.getMessageId());
		pst.setString(2, message.getMessageLabel());
		pst.setString(3, message.getMessageText());
		pst.setString(4, message.getMessageType());
		pst.setLong(5, broadcast);
		pst.setLong(6, System.currentTimeMillis());

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

	// Get all broadcast messages
	public List<Message> getBroadCastMessages(String messageType) throws SQLException, ClassNotFoundException {

		messages = new ArrayList<Message>();
		connection = DBConnection.setDBConnection();
		String sql = "SELECT * from messages WHERE messageBroadCast = 1 AND messageType = '" + messageType + "'";
		PreparedStatement pst = connection.prepareStatement(sql);
		resultSet = pst.executeQuery();

		while (resultSet.next()) {
			// Format timestamp
			Timestamp timestamp = resultSet.getTimestamp("messageTimestamp");
			java.util.Date unformatedtime = timestamp;
			String date = new SimpleDateFormat("dd/MM").format(unformatedtime);
			String time = new SimpleDateFormat("HH:mm").format(unformatedtime);

			Message m = new Message(Integer.parseInt(resultSet.getString("messageId")),
					resultSet.getString("messageText"), resultSet.getString("messageLabel"),
					resultSet.getString("messageType"), time, date);
			messages.add(m);
		}

		resultSet.close();
		pst.close();
		connection.close();

		return messages;
	}

	public void addBroadcastRegularMessage(Message message) throws ClassNotFoundException, SQLException {
		// Add the message in the message table, get id of message
		addMessage(message, 1);
	}

	public void addEmployeeMessage(Message message, int employeeId) throws ClassNotFoundException, SQLException {
		int messageId = addMessage(message, 0);
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
			// Format timestamp
			Timestamp timestamp = resultSet.getTimestamp("messageTimestamp");
			java.util.Date unformatedtime = timestamp;
			String date = new SimpleDateFormat("dd/MM").format(unformatedtime);
			String time = new SimpleDateFormat("HH:mm").format(unformatedtime);

			Message m = new Message(Integer.parseInt(resultSet.getString("messageId")),
					resultSet.getString("messageText"), resultSet.getString("messageLabel"),
					resultSet.getString("messageType"), time, date);
			messages.add(m);
		}
		resultSet.close();
		pst.close();
		connection.close();

		return messages;
	}

	public void addCompanyMessage(Message message, String companyName) throws ClassNotFoundException, SQLException {
		int messageId = addMessage(message, 0);
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
			// Format timestamp
			Timestamp timestamp = resultSet.getTimestamp("messageTimestamp");
			java.util.Date unformatedtime = timestamp;
			String date = new SimpleDateFormat("dd/MM").format(unformatedtime);
			String time = new SimpleDateFormat("HH:mm").format(unformatedtime);

			Message m = new Message(Integer.parseInt(resultSet.getString("messageId")),
					resultSet.getString("messageText"), resultSet.getString("messageLabel"),
					resultSet.getString("messageType"), time, date);
			messages.add(m);
		}
		resultSet.close();
		pst.close();
		connection.close();

		return messages;
	}

	// Add regular message for a given object
	public void addFactoryObjectRegularMessage(Message message, int factoryobjectId)
			throws ClassNotFoundException, SQLException {
		int messageId = addMessage(message, 0);
		connection = DBConnection.setDBConnection();
		String sql = "INSERT INTO messagefactoryobject (messageId, objectId) values(?, ?)";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, messageId);
		pst.setLong(2, factoryobjectId);
		pst.executeUpdate();
		pst.close();
		connection.close();
	}

	// Get regular message for a given object
	public List<Message> getFactoryObjectRegularMessage(int factoryobjectId)
			throws ClassNotFoundException, SQLException {
		messages = new ArrayList<Message>();
		connection = DBConnection.setDBConnection();
		String sql = "SELECT * FROM messages INNER JOIN messagefactoryobject ON messages.messageId = messagefactoryobject.messageId AND messages.messageType = 'REGULAR' AND messagefactoryobject.objectId = ?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, factoryobjectId);
		resultSet = pst.executeQuery();

		while (resultSet.next()) {
			// Format timestamp
			Timestamp timestamp = resultSet.getTimestamp("messageTimestamp");
			java.util.Date unformatedtime = timestamp;
			String date = new SimpleDateFormat("dd/MM").format(unformatedtime);
			String time = new SimpleDateFormat("HH:mm").format(unformatedtime);

			Message m = new Message(Integer.parseInt(resultSet.getString("messageId")),
					resultSet.getString("messageText"), resultSet.getString("messageLabel"),
					resultSet.getString("messageType"), time, date);
			messages.add(m);
		}
		resultSet.close();
		pst.close();
		connection.close();

		return messages;
	}

	// Add warning message for a given object
	public void addFactoryObjectWarningMessage(Message message, int factoryobjectId)
			throws ClassNotFoundException, SQLException {
		int messageId = addMessage(message, 0);
		connection = DBConnection.setDBConnection();
		String sql = "INSERT INTO messagefactoryobject (messageId, objectId) values(?, ?)";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, messageId);
		pst.setLong(2, factoryobjectId);
		pst.executeUpdate();
		pst.close();
		connection.close();
	}

	// Get warning message for a given object
	public List<Message> getFactoryObjectWarningMessage(int factoryobjectId)
			throws ClassNotFoundException, SQLException {
		messages = new ArrayList<Message>();
		connection = DBConnection.setDBConnection();
		String sql = "SELECT * FROM messages INNER JOIN messagefactoryobject ON messages.messageId = messagefactoryobject.messageId AND messages.messageType = 'WARNING' AND messagefactoryobject.objectId = ?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, factoryobjectId);
		resultSet = pst.executeQuery();

		while (resultSet.next()) {
			// Format timestamp
			Timestamp timestamp = resultSet.getTimestamp("messageTimestamp");
			java.util.Date unformatedtime = timestamp;
			String date = new SimpleDateFormat("dd/MM").format(unformatedtime);
			String time = new SimpleDateFormat("HH:mm").format(unformatedtime);

			Message m = new Message(Integer.parseInt(resultSet.getString("messageId")),
					resultSet.getString("messageText"), resultSet.getString("messageLabel"),
					resultSet.getString("messageType"), time, date);
			messages.add(m);
		}
		resultSet.close();
		pst.close();
		connection.close();

		return messages;
	}

	/*
	 * 
	 * 
	 * FLOOR MESSAGES
	 * 
	 * 
	 */

	// Get warning message for all objects in a floor
	public List<Message> getFactoryObjectFloorRegularMessages(int floorId) throws ClassNotFoundException, SQLException {
		messages = new ArrayList<Message>();
		connection = DBConnection.setDBConnection();

		// Get all the objects in the given floor
		String sql = "SELECT objectId FROM factoryobjects WHERE objectFloorId = ?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, floorId);
		resultSet = pst.executeQuery();

		while (resultSet.next()) {
			int objectId = Integer.parseInt(resultSet.getString("objectId"));
			// Get all the warning messages for the object
			messages.addAll(getFactoryObjectRegularMessage(objectId));
		}

		pst.close();
		connection.close();

		return messages;
	}

	// Add warning message for a floor
	public void addFloorWarningMessage(Message message, int floorId) throws ClassNotFoundException, SQLException {
		int messageId = addMessageToBeAck(message, 0);
		connection = DBConnection.setDBConnection();
		String sql = "INSERT INTO messagefloor (messageId, floorId) values(?, ?)";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, messageId);
		pst.setLong(2, floorId);
		pst.executeUpdate();
		pst.close();
		connection.close();
	}

	// Get warning messages for a floor that has not yet been acknowledged by
	// employee
	public List<Message> getFloorWarningMessage(int floorId, int employeeID)
			throws ClassNotFoundException, SQLException {
		messages = new ArrayList<Message>();
		connection = DBConnection.setDBConnection();
		String sql = "SELECT messages.messageId, messages.messageText, messages.messageLabel, messages.messageType, messages.messageCurrentmillis FROM messages, messagefloor WHERE messages.messageId = messagefloor.messageId AND messages.messageType = 'WARNING' AND messagefloor.floorId = ? AND messages.messageId NOT IN (SELECT messageacknowledged.messageId FROM messageacknowledged WHERE messageacknowledged.employeeID = ?)";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, floorId);
		pst.setLong(2, employeeID);
		resultSet = pst.executeQuery();

		while (resultSet.next()) {

			Message m = new Message(Integer.parseInt(resultSet.getString("messageId")),
					resultSet.getString("messageText"), resultSet.getString("messageLabel"),
					resultSet.getString("messageType"), resultSet.getLong("messageCurrentmillis"));
			messages.add(m);
		}
		resultSet.close();
		pst.close();
		connection.close();

		return messages;
	}

	/*
	 * 
	 * 
	 * SELECT messages.messageText FROM messages, messagefloor WHERE
	 * messages.messageId = messagefloor.messageId AND messages.messageType =
	 * 'WARNING' AND messagefloor.floorId = 1 AND messages.messageId NOT IN (SELECT
	 * messageacknowledged.messageId FROM messageacknowledged WHERE
	 * messageacknowledged.employeeID = 6)
	 * 
	 * 
	 */

	// Acknowledge warning message for a floor
	public void acknowledgeFloorWarningMessage(int messageId, int employeeID)
			throws ClassNotFoundException, SQLException {
		connection = DBConnection.setDBConnection();
		String sql = "INSERT INTO messageAcknowledged (messageId, employeeID, acknowledgeCurrentmillis) values(?, ?, ?)";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, messageId);
		pst.setLong(2, employeeID);
		pst.setLong(3, System.currentTimeMillis());
		pst.executeUpdate();
		pst.close();
		connection.close();
	}

}
