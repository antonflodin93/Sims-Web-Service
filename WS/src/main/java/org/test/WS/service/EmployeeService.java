package org.test.WS.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.test.WS.model.Employee;
import org.test.WS.database.DBConnection;

// Used for returning messages using DB

public class EmployeeService {
	DBConnection dBconnection;
	Connection connection;
	ResultSet resultSet;
	List<Employee> result;

	public EmployeeService() {

	}

	// Returns all the messages
	public List<Employee> getEmployees() throws SQLException, ClassNotFoundException {
			
		result = new ArrayList<Employee>();
		DBConnection connect = new DBConnection();
		connection = DBConnection.setDBConnection();
		String sql = "SELECT * from employees";
		PreparedStatement pst = connection.prepareStatement(sql);
		resultSet = pst.executeQuery();
		while (resultSet.next()) {
			Employee m = new Employee(Integer.parseInt(resultSet.getString(1)), resultSet.getString(2), resultSet.getString(3));
			result.add(m);
		}
		pst.close();
		
		return result;
	}

	// Returns employee with id
	public Employee getEmployee(int id) throws SQLException, ClassNotFoundException {
		DBConnection connect = new DBConnection();
		connection = DBConnection.setDBConnection();
		String sql = "Select * from employees where employeeID = ?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, id);
		resultSet = pst.executeQuery();
		resultSet.next();
		Employee message = new Employee(Integer.parseInt(resultSet.getString(1)), resultSet.getString(2), resultSet.getString(3));
		pst.close();
		return message;
	}

	// Inserts employee into db
	public Employee addEmployee(Employee message) throws ClassNotFoundException, SQLException {
		
		DBConnection connect = new DBConnection();
		connection = DBConnection.setDBConnection();
		String sql = "insert into employees (employeeId, employeeFirstName, employeeLastName) values(?, ?, ?)";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, message.getEmployeeId());
		pst.setString(2, message.getEmployeeFirstName());
		pst.setString(3, message.getEmployeeLastName());
		pst.executeUpdate();
		pst.close();
		return message;
	}

}
