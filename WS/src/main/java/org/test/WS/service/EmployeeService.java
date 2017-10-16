package org.test.WS.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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
		connection = DBConnection.setDBConnection();
		String sql = "SELECT * from employees";
		PreparedStatement pst = connection.prepareStatement(sql);
		resultSet = pst.executeQuery();

		while (resultSet.next()) {
			Employee m = new Employee(Integer.parseInt(resultSet.getString(1)), resultSet.getString(2),
					resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
					resultSet.getString(7), resultSet.getString(8));
			result.add(m);
		}
		pst.close();

		return result;
	}

	// Returns employee with id
	public Employee getEmployeeById(int id) throws SQLException, ClassNotFoundException {
		connection = DBConnection.setDBConnection();
		String sql = "Select * from employees where employeeID = ?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, id);
		resultSet = pst.executeQuery();
		Employee employee = new Employee();
		if (resultSet.next() == true) {
			employee = new Employee(Integer.parseInt(resultSet.getString(1)), resultSet.getString(2),
					resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
					resultSet.getString(7), resultSet.getString(8));
		} else {
			employee = null;
		}
		pst.close();
		return employee;
	}

	// Returns employee with id
	public Employee getEmployeeByUsername(String username) throws SQLException, ClassNotFoundException {
		connection = DBConnection.setDBConnection();
		String sql = "Select * from employees where employeeUsername = ?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setString(1, username);
		resultSet = pst.executeQuery();
		Employee employee = new Employee();
		if (resultSet.next() == true) {
			employee = new Employee(Integer.parseInt(resultSet.getString(1)), resultSet.getString(2),
					resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
					resultSet.getString(7), resultSet.getString(8));
		} else {
			employee = null;
		}
		pst.close();
		return employee;
	}

	// Inserts employee into db
	public Employee addEmployee(Employee employee) throws ClassNotFoundException, SQLException {

		// Generate salt
		byte[] salt = new byte[16];
		new SecureRandom().nextBytes(salt);

		// Convert salt to string type
		StringBuffer saltBuffer = new StringBuffer();
		for (int i = 0; i < salt.length; i++) {
			saltBuffer.append(Integer.toString((salt[i] & 0xff) + 0x100, 16).substring(1));
		}
		String saltString = saltBuffer.toString();

		// Add the salt to the password
		String passwordandsalt = employee.getEmployeePassword() + saltString;
		MessageDigest md;

		try {
			// Hash it
			md = MessageDigest.getInstance("SHA-256");
			md.update(passwordandsalt.getBytes());

		} catch (NoSuchAlgorithmException e) {
			return employee;
		}

		byte byteData[] = md.digest();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}

		String hash = sb.toString();

		connection = DBConnection.setDBConnection();
		String sql = "INSERT INTO employees (employeeId, employeeFirstName, employeeLastName, employeeUsername, "
				+ "employeeEmail, employeePassword, employeePhonenumber, employeeCompany, employeeSalt) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, employee.getEmployeeId());
		pst.setString(2, employee.getEmployeeFirstName());
		pst.setString(3, employee.getEmployeeLastName());
		pst.setString(4, employee.getEmployeeUsername());
		pst.setString(5, employee.getEmployeeEmail());
		pst.setString(6, hash);
		pst.setString(7, employee.getEmployeePhonenumber());
		pst.setString(8, employee.getEmployeeCompany());
		pst.setString(9, saltString);
		pst.executeUpdate();
		pst.close();
		return employee;
	}

	public Employee deleteEmployeeById(int id) throws SQLException, ClassNotFoundException {
		// Get the employee that is going to be deleted
		Employee e = getEmployeeById(id);

		connection = DBConnection.setDBConnection();

		String sql = "DELETE FROM employees WHERE employeeId = ?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, id);
		pst.executeUpdate();
		pst.close();
		return e;
	}

}
