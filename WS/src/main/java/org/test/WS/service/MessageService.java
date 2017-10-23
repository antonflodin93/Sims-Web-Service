package org.test.WS.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.test.WS.database.DBConnection;
import org.test.WS.model.Employee;
import org.test.WS.model.Message;

public class MessageService {
	DBConnection dBconnection;
	Connection connection;
	ResultSet resultSet;
	List<Message> result;

	public List<Message> getMessages() throws SQLException  {
		

		try {
			result = new ArrayList<Message>();
			connection = DBConnection.setDBConnection();
			String sql = "SELECT * from messages";
			PreparedStatement pst = connection.prepareStatement(sql);
			resultSet = pst.executeQuery();

			while (resultSet.next()) {

				Message m = new Message(Integer.parseInt(resultSet.getString("messageId")),
						resultSet.getString("messageText"));
				result.add(m);
			}

			pst.close();
			connection.close();

		} catch (ClassNotFoundException | SQLException e) {
			connection.close();

		}

		return result;
	}

}
