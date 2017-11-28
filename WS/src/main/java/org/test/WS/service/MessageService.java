package org.test.WS.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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

	// Get all warning broadcast messages
	public List<Message> getBroadCastMessages(String messageType) throws SQLException, ClassNotFoundException {

		messages = new ArrayList<Message>();
		connection = DBConnection.setDBConnection();
		String sql = "SELECT * from messages WHERE messageBroadCast = 1 AND messageType = '" + messageType + "'";
		PreparedStatement pst = connection.prepareStatement(sql);
		resultSet = pst.executeQuery();

		while (resultSet.next()) {

			Message m = new Message(Integer.parseInt(resultSet.getString("messageId")),
					resultSet.getString("messageText"), resultSet.getString("messageLabel"),
					resultSet.getString("messageType"));
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
			Message m = new Message(Integer.parseInt(resultSet.getString("messageId")),
					resultSet.getString("messageText"), resultSet.getString("messageLabel"),
					resultSet.getString("messageType"));

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
			Message m = new Message(Integer.parseInt(resultSet.getString("messageId")),
					resultSet.getString("messageText"), resultSet.getString("messageLabel"),
					resultSet.getString("messageType"));

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
			Message m = new Message(Integer.parseInt(resultSet.getString("messageId")),
					resultSet.getString("messageText"), resultSet.getString("messageLabel"),
					resultSet.getString("messageType"));

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
			Message m = new Message(Integer.parseInt(resultSet.getString("messageId")),
					resultSet.getString("messageText"), resultSet.getString("messageLabel"),
					resultSet.getString("messageType"));

			messages.add(m);
		}
		resultSet.close();
		pst.close();
		connection.close();

		return messages;
	}

	// Get warning message for all objects in a floor
	public List<Message> getFactoryObjectFloorWarningMessages(int floorId) throws ClassNotFoundException, SQLException {
		messages = new ArrayList<Message>();
		// messages.add(new Message(1, "Num: ", "Label", "WARNING" ));

		connection = DBConnection.setDBConnection();
		String sql = "SELECT objectId FROM factoryobjects WHERE objectFloorId = ?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, floorId);
		ResultSet resultSetMessages = pst.executeQuery();

		while (resultSetMessages.next()) {
			
			//List<Message> objectmessages = getFactoryObjectWarningMessage(Integer.parseInt(resultSet.getString("objectId")));
			int objectId = Integer.parseInt(resultSet.getString("objectId")); // NULL POINTER
			
			if (getFactoryObjectWarningMessage(Integer.parseInt(resultSet.getString("objectId"))) != null) {
				messages.addAll(getFactoryObjectWarningMessage(Integer.parseInt(resultSet.getString("objectId"))));
			}

			// messages.add(new Message(1, "Num: " + resultSet.getString("objectId"),
			// "Label", "WARNING" ));
		}
		resultSetMessages.close();
		pst.close();
		connection.close();
		return messages;
	}

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

	private static java.sql.Timestamp getCurrentTimeStamp() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());

	}
}
